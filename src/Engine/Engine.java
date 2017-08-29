package Engine;

import javax.swing.*;

import GUI.UI;

public class Engine {
	
	public UI ui;
	
	public Engine(int width, int height) {
		
		ui = new UI(width, height);
		ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ui.setResizable(false);
		ui.setVisible(true);


	}
	
	

}
