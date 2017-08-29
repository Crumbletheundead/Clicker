package GUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.*;

public class UI extends JFrame{
	
	public int width, height;
	public int column, row;
	private Font largeFont;
	private Font smallFont;
	Dimension screenSize;
	
	EventHandler eHandler;
	
	JButton record, continueRecord, stop, play;
	JLabel state;

	JRadioButton save1, save2;
	ButtonGroup group;
	
	JLabel delayText;
	JTextField selectDelay;
	public int delayInSecs;
	JLabel currentDelay;
	
	JButton cycleSaves;
	
	JLabel repeatText;
	JTextField selectRepeat;
	public int repeatCycles;
	JLabel currentRepeat;
	
	JButton manualAdd;
	JTextField manualX, manualY;
	JLabel manualStatus;
	
	JLabel showX, showY;
	
	JButton makeLastMultiClick;
	JLabel multiStatus;
	
	JLabel version;
	
	public int save1Clicks, save2Clicks;
	
	
	
	public UI(int width, int height) {
		super("ClickerBot 2 Alpha");
		this.width = width;
		this.height = height;
		this.setLayout(null);
		this.setSize(width, height);
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		column = 110;
		row = 40;
		largeFont = new Font("Dialog", Font.PLAIN, 18);
		smallFont = new Font("Dialog", Font.BOLD, 10);
		
		save1Clicks = 0;
		save2Clicks = 0;
		delayInSecs = 5;
		repeatCycles = 1;
		
		//start adding stuff here
		version = new JLabel();
		version.setText("version 0.4");
		version.setFont(largeFont);
		version.setBounds(20, height-90, 200, 40);
		add(version);
		
		state = new JLabel();
		state.setText("Idle");
		state.setBounds(column, row, 250, 40);
		state.setFont(largeFont);
		add(state);
		
		record = new JButton();
		record.setText("Record");
		record.setBounds(column, row*2, 100, 30);
		add(record);
		
		continueRecord = new JButton();
		continueRecord.setText("Cont Record");
		continueRecord.setFont(smallFont);
		continueRecord.setBounds(column,row*3,100,30);
		add(continueRecord);
		
		play = new JButton();
		play.setText("Play");
		play.setBounds(column, row*4, 100, 30);
		add(play);
		
		stop = new JButton();
		stop.setText("Stop");
		stop.setBounds(column*2, row*2, 200, 110);
		add(stop);
		
		selectDelay = new JTextField();
		selectDelay.setBounds(column*2+90, row*5, 110, 30);
		selectDelay.setText("default 5");
		add(selectDelay);
		
		delayText = new JLabel("Delay in secs:");
		delayText.setBounds(column*2, row*5,80,30);
		add(delayText);
		
		currentDelay = new JLabel("CurrentDelay: " + Integer.toString(delayInSecs)
				+ " (Cannot <5)");
		currentDelay.setBounds(column*2, row*6-10, 200, 30);
		add(currentDelay);
		
		repeatText = new JLabel("Repeat:");
		repeatText.setBounds(column*2+50, row*7, 50, 30);
		add(repeatText);
		
		selectRepeat = new JTextField();
		selectRepeat.setBounds(column*2+100, row*7, 100, 30);
		selectRepeat.setText("default is 1");
		add(selectRepeat);
		
		currentRepeat = new JLabel("CurrentRepeat: " 
				+ Integer.toString(repeatCycles));
		currentRepeat.setBounds(column*2+50, row*8-10, 200, 30);
		add(currentRepeat);
		
		cycleSaves = new JButton();
		cycleSaves.setText("Cycle Saves");
		cycleSaves.setBounds(column, row*7, 130, 30);
		add(cycleSaves);
		
		save1 = new JRadioButton("Save1", true);
		save2 = new JRadioButton("Save2", false);
		save1.setBounds(column, row *5, 100, 30);
		save2.setBounds(column, row *6, 100, 30);
		add(save1);
		add(save2);
		
		group = new ButtonGroup();
		group.add(save1);
		group.add(save2);
		
		manualAdd = new JButton();
		manualAdd.setText("Manual Add X,Y");
		manualAdd.setBounds(column, row*9, 130, 30);
		add(manualAdd);
		
		manualX = new JTextField();
		manualX.setText("X here");
		manualX.setBounds(column*3 -60, row*9, 70, 30);
		add(manualX);
		
		manualY = new JTextField();
		manualY.setText("Y here");
		manualY.setBounds(column*3 +20, row*9, 70, 30);
		add(manualY);
		
		manualStatus = new JLabel("Record First");
		manualStatus.setBounds(column, row*10-10, 150, 30);
		add(manualStatus);
		
		showX = new JLabel("X: ");
		showX.setBounds(column*3-60, row*10, 50, 30);
		add(showX);
		
		showY = new JLabel("Y: ");
		showY.setBounds(column*3+20, row*10, 50, 30);
		add(showY);
		
		makeLastMultiClick = new JButton();
		makeLastMultiClick.setText("Increment last multiClick");
		makeLastMultiClick.setBounds(column, row*11, 200, 30);
		add(makeLastMultiClick);
		
		multiStatus = new JLabel("Record first");
		multiStatus.setBounds(column + 210, row*11, 100, 30);
		add(multiStatus);
		
		eHandler = new EventHandler(this);
		MousePositionListener mpListener = new MousePositionListener(this);
		
		record.addActionListener(eHandler);
		continueRecord.addActionListener(eHandler);
		stop.addActionListener(eHandler);
		play.addActionListener(eHandler);
		
		save1.addItemListener(eHandler);
		save2.addItemListener(eHandler);
		
		selectDelay.addActionListener(eHandler);
		selectRepeat.addActionListener(eHandler);
		cycleSaves.addActionListener(eHandler);
		manualAdd.addActionListener(eHandler);
		makeLastMultiClick.addActionListener(eHandler);
		
		this.addMouseListener(eHandler);
		this.addMouseMotionListener(mpListener);
	}
	
	public void updateLabel(String s, JLabel label) {
		label.setText(s);
	}
	
}
