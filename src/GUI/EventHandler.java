package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Engine.AutoClicker;
import Engine.RobotThread;


public class EventHandler implements ActionListener, MouseListener, ItemListener{

	private UI ui;
	private AutoClicker autoClicker = null;
	private RobotThread robotThread = null;
	private int currentSaveSelected = 1;
	
	int currentState = 0;
	//0 = idle
	//1 = recording
	//2 = playing
	
	public EventHandler(UI ui) {
		this.ui = ui;
	}
	
	public void actionPerformed(ActionEvent actionEvent) {
		if(actionEvent.getSource().equals(ui.stop)) { //stop button
			if(robotThread != null) {
				autoClicker.setContinueRobot(false);
				autoClicker.setState(0);
				ui.updateLabel("Idle", ui.state);
				System.out.println("stopped");
				synchronized(autoClicker) {
					autoClicker.notify();
				}
			}
			currentState =0;
			ui.updateLabel("Idle", ui.state);
		}else if(actionEvent.getSource().equals(ui.play)) { //play button
			if(currentState == 0) {
				if(robotThread == null) {
					ui.updateLabel("Nothing to play", ui.state);
				}else {
					if(autoClicker.getSave(currentSaveSelected).isEmpty()) {
						System.out.println("No save in save" + 
								Integer.toString(currentSaveSelected));
						ui.updateLabel("No Save", ui.state);
					}else { //if everything goes to plan, execute this
						ui.updateLabel("playing", ui.state);
						autoClicker.setState(2);
						synchronized(autoClicker) {
							autoClicker.notify();
						}
						autoClicker.setContinueRobot(true);
					}
					currentState = 2;
				}
				System.out.println("playing on "+ Integer.toString(currentSaveSelected) );
				
			}else {
				ui.updateLabel("Stop first", ui.state);
				currentState = 0;
				autoClicker.setState(0);
			}

		}else if(actionEvent.getSource().equals(ui.cycleSaves)) { // cycle button
			if(currentState == 0) {
				if(robotThread == null) {
					ui.updateLabel("Nothing to play", ui.state);
				}else {
					if(autoClicker.getSave(currentSaveSelected).isEmpty()
							|| autoClicker.getOtherSave(currentSaveSelected).isEmpty()) {
						System.out.println("Not all saves populated");
						ui.updateLabel("Not all saves populated", ui.state);
					}else if(!autoClicker.getSave(currentSaveSelected).isEmpty()
								&& !autoClicker.getOtherSave(currentSaveSelected).isEmpty()){ 
						//if everything goes to plan, execute this
						ui.updateLabel("Cycling", ui.state);
						autoClicker.setState(3);
						synchronized(autoClicker) {
							autoClicker.notify();
						}
						autoClicker.setContinueRobot(true);
					}
					currentState = 2;
				}
				System.out.println("playing both" );
				
			}else {
				ui.updateLabel("Stop first", ui.state);
				currentState = 0;
				autoClicker.setState(0);
			}

		}else if(actionEvent.getSource().equals(ui.record)) { //record button
			if(currentState == 0) {
				if(robotThread == null) {
					autoClicker= new AutoClicker();
					robotThread = new RobotThread(autoClicker);
					robotThread.start();
					ui.updateLabel("recording", ui.state);
				}else {
					autoClicker.setContinueRobot(false);
					//should it notify? or set autoclicker state first? 
					if(!autoClicker.getSave(currentSaveSelected).isEmpty()) {
						autoClicker.getSave(currentSaveSelected).clear();
						ui.updateLabel("recording over save", ui.state);
					}else {
						ui.updateLabel("recording", ui.state);
					}
					
				}
				System.out.println("recording on "+ Integer.toString(currentSaveSelected) );
				autoClicker.setState(1);
				currentState = 1;
			}else {
				ui.updateLabel("Stop first", ui.state);
				currentState = 0;
				autoClicker.setState(0);
			}

		}else if(actionEvent.getSource().equals(ui.continueRecord)) {//continue rec
			if(currentState == 0) {
				if(robotThread == null) {
					ui.updateLabel("record first", ui.state);
				}else {
					autoClicker.setContinueRobot(false);
					//should it notify? or set autoclicker state first? 
					if(!autoClicker.getSave(currentSaveSelected).isEmpty()) {
						//if all goes to plan, execute this
						ui.updateLabel("Continuing save", ui.state);
						autoClicker.setState(1);
						currentState = 1;
					}else {
						ui.updateLabel("Record first", ui.state);
					}
				}

			}else {
				ui.updateLabel("Stop first", ui.state);
				currentState = 0;
				autoClicker.setState(0);
			}
		}else if(actionEvent.getSource().equals(ui.selectDelay)) { //delay counter
			int userSetDelay = 0;
			try {
				userSetDelay = Integer.parseInt(ui.selectDelay.getText());
				if(userSetDelay <5) {
					ui.updateLabel("must be over 5", ui.currentDelay);
				}else {
					//if everything goes to plan, execute this
					ui.updateLabel("Current Delay: " 
						+ Integer.toString(userSetDelay), ui.currentDelay);
					
					autoClicker.setDelay(userSetDelay);
				}
			}catch(NullPointerException e) {
				ui.updateLabel("Record first", ui.currentDelay);
			}catch(Exception e) {
				ui.updateLabel("Not a number", ui.currentDelay);
			}
			
		}else if(actionEvent.getSource().equals(ui.selectRepeat)) { //repeat counter
			if(autoClicker == null) {
				ui.updateLabel("Record first", ui.currentRepeat);
			}else {
				if(!autoClicker.getSave(1).isEmpty() && !autoClicker.getSave(2).isEmpty()) {
					int userSetRepeat = 0;
					try {
						userSetRepeat = Integer.parseInt(ui.selectRepeat.getText());
						if(userSetRepeat <1) {
							ui.updateLabel("must be over 1", ui.currentRepeat);
						}else {
							//if everything goes to plan, execute this
							ui.updateLabel("Current Repeat: " 
								+ Integer.toString(userSetRepeat), ui.currentRepeat);
								
							autoClicker.setRepeat(userSetRepeat);
						}
					}catch(Exception e) {
						ui.updateLabel("Not a number", ui.currentRepeat);
					}
				}else {
					ui.updateLabel("not all saves unpopulated", ui.currentRepeat);
				}
			}

		}else if(actionEvent.getSource().equals(ui.manualAdd)) {
			if(currentState == 0 && robotThread != null) {
				double maxX, maxY;
				int mX, mY;

				maxX = ui.screenSize.getWidth();
				maxY = ui.screenSize.getHeight();

				try {
					mX = Integer.parseInt(ui.manualX.getText());
					mY = Integer.parseInt(ui.manualY.getText());
					if(mX>0 && mX<maxX && mY>0 && mY<maxY) {
						autoClicker.addPoint(mX, mY, currentSaveSelected);
						ui.updateLabel("added", ui.manualStatus);
					}
				}catch(NumberFormatException e) {
					ui.updateLabel("not a number", ui.manualStatus);
				}

			}else if(currentState == 2) {
				ui.updateLabel("stop first", ui.manualStatus);
			}else if(currentState == 1) {
				ui.updateLabel("weird, but stop first", ui.manualStatus);
			}
		}else if(actionEvent.getSource().equals(ui.makeLastMultiClick)) {
			if(currentState == 0 ) {
				try {
					autoClicker.addMultiClick(currentSaveSelected);
					ui.updateLabel("incremented", ui.multiStatus);
				}catch(NullPointerException e) {
					ui.updateLabel("record first", ui.multiStatus);
				}
			}else {
				ui.updateLabel("stop first", ui.multiStatus);
			}
		}
			
	}
		
	

	public void itemStateChanged(ItemEvent iEvent) {
		if(robotThread != null) {
			if(ui.save1.isSelected()) {
				currentSaveSelected = 1;
				autoClicker.setSaveSelected(1);
				System.out.println("save is 1");
			}else if(ui.save2.isSelected()) {
				currentSaveSelected = 2;
				autoClicker.setSaveSelected(2);
				System.out.println("save is 2");
			}
		}else {
			ui.updateLabel("Record before changing saves", ui.state);
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent mEvent) {
		
		
	}

	@Override
	public void mouseEntered(MouseEvent mEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent mEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent mEvent) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent mEvent) {
		if(currentState == 1) {
			int x, y;
			
			x = mEvent.getXOnScreen();
			y = mEvent.getYOnScreen();
			autoClicker.addPoint(x,y, currentSaveSelected);
			System.out.println("click registered on UI");
		}
		
	}

}
