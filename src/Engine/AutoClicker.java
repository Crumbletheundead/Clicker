package Engine;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.ArrayList;

public class AutoClicker implements Runnable{

	private Robot robot;
	
	public ArrayList<Point> save1List, save2List;
	
	private volatile boolean running;
	private volatile boolean continueRobot;

	private volatile int state;
	//0 = idle
	//1 = record
	//2 = play
	//3 = cycle
	private volatile int delay;
	private volatile int repeat;
	private int saveSelected;
	
	private final int SECOND_IN_MILLIS;
	
	public AutoClicker() {
		save1List = new ArrayList<Point>();
		save2List = new ArrayList<Point>();
		
		running = true;
		continueRobot = false;
		state = 0;
		delay = 5;
		repeat = 1;
		saveSelected = 1;
		SECOND_IN_MILLIS = 1000;

	}
	
	public void run(){

		try {
			robot = new Robot();
			robot.setAutoWaitForIdle(true);
		} catch (AWTException e) {e.printStackTrace();}
		
		//while loop is to prevent thread from just disappearing
		//but it also causes the clicks to recycle without stopping
		while(running) {
			
			if(state == 0) {
				synchronized(this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}else if(state == 2) {
				for(Point point : getSave(getSaveSelected())) {
					if(continueRobot) {
						robot.mouseMove(point.getX(), point.getY());
						robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
						robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
						if(point.getMultiClick() ==1) {
							synchronized(this) {
								try {
									this.wait(delay*SECOND_IN_MILLIS);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}else {
							for(int clckCnt = 0; clckCnt < point.getMultiClick(); clckCnt++) {
								synchronized(this) { 
									try {
										this.wait(750);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
								robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
								robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
							}
						}
						//maybe add a ifCycle boolean test here
						//that way ifCycle == false, add a method
						//that causes continueRobot to also equal false, 
						//AND set this object's state to 0
					}else {
						break;
					}
				}
			}else if(state ==3) {
				for(int iteration = 0; iteration <= repeat-1; iteration++) {
					for(Point point : getSave(getSaveSelected())) {
						if(continueRobot) {
							robot.mouseMove(point.getX(), point.getY());
							robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
							robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
							if(point.getMultiClick() ==1) {
								synchronized(this) {
									try {
										this.wait(delay*SECOND_IN_MILLIS);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							}else {
								for(int clckCnt = 0; clckCnt < point.getMultiClick(); clckCnt++) {
									synchronized(this) { 
										try {
											this.wait(750);
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
									}
									robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
									robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
								}
							}
							//maybe add a ifCycle boolean test here
							//that way ifCycle == false, add a method
							//that causes continueRobot to also equal false, 
							//AND set this object's state to 0
							
							//ifDoubleClick boolean test
							//then add makeLastClickDouble button in GUI
							//solve click DMG problem when resetting
						}else {
							break;
						}
					}
				}
				//save 2 loops only once here
				for(Point point : getOtherSave(getSaveSelected())) {
					if(continueRobot) {
						robot.mouseMove(point.getX(), point.getY());
						robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
						robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
						if(point.getMultiClick() ==1) {
							synchronized(this) {
								try {
									this.wait(delay*SECOND_IN_MILLIS);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}else {
							for(int clckCnt = 0; clckCnt < point.getMultiClick(); clckCnt++) {
								synchronized(this) { 
									try {
										this.wait(750);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
								robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
								robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
							}
						}
					}else {
						break;
					}
				}
			}
		}
	}
	
	
	//called by GUI
	public void addPoint(int x, int y, int save) {
		Point point = new Point(x,y);
		if(save == 1) {
			save1List.add(point);
		}else if(save == 2) {
			save2List.add(point);
		}
		
		System.out.println("click added");
	}
	
	public void addMultiClick(int save) {
		if(save ==1) {
			save1List.get(save1List.size()-1).incrementMultiClick();
		}else if(save == 2) {
			save2List.get(save2List.size()-1).incrementMultiClick();
		}
	}

	//called by GUI
	public void setState(int state) {
		this.state = state;
	}
	//called by GUI
	public int getState() {
		return state;
	}
	//called by GUI and robot thread
	public ArrayList<Point> getSave(int save){
		if(save == 1) {
			return save1List;
		}else if(save == 2) {
			return save2List;
		}else {
			System.out.println("warning returned default save 1");
			return save1List;
		}
	}
	
	public ArrayList<Point> getOtherSave(int save){
		if(save == 1) {
			return save2List;
		}else if(save == 2) {
			return save1List;
		}else {
			System.out.println("warning returned default save 1");
			return save1List;
		}
	}
	
	public boolean getContinueRobot() {
		return continueRobot;
	}

	public void setContinueRobot(boolean continueRobot) {
		this.continueRobot = continueRobot;
	}
	
	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}


	public synchronized int getSaveSelected() {
		return saveSelected;
	}


	public synchronized void setSaveSelected(int saveSelected) {
		this.saveSelected = saveSelected;
	}

	public synchronized void setRepeat(int userSetRepeat) {
		this.repeat = userSetRepeat;
		
	}

	public synchronized int getRepeat() {
		return repeat;
	}


}
