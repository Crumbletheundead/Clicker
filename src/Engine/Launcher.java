package Engine;

public class Launcher {

	public static void main(String[] args) {
		
		//this is to be used in 
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                new Engine(500, 600);
	            }
	    });

	}

}
