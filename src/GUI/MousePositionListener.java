package GUI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MousePositionListener implements MouseMotionListener{

	public int mouseX, mouseY;
	private UI ui;
	
	public MousePositionListener(UI ui) {
		this.ui = ui;
	}
	
	public void mouseMoved(MouseEvent mEvent) {
		if(ui.eHandler.currentState == 0) {
			mouseX = mEvent.getXOnScreen();
			mouseY = mEvent.getYOnScreen();
			ui.updateLabel("X: " + Integer.toString(mouseX), ui.showX);
			ui.updateLabel("Y: " + Integer.toString(mouseY), ui.showY);
		}
	}
	
	public void mouseDragged(MouseEvent mEvent) {
		
		
	}

}
