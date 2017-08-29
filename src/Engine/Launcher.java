package Engine;

public class Launcher {

	public static void main(String[] args) {
		
		//This needs to be used first to ensure that the GUI starts in the EDT Thread
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                new Engine(500, 600);
	            }
	    });

	}

}
