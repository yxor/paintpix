package pixyart;

import java.awt.Frame;

// entry point class
public class Main {
	
	public static void main(String[] args) {
		
	    java.awt.EventQueue.invokeLater(new Runnable() {
	          public void run() {
	               MainFrame frame = new MainFrame();
	               frame.setVisible(true);
	               frame.setExtendedState(Frame.MAXIMIZED_BOTH);
	          }
	    });
	}
}

