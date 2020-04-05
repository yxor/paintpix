package pixyart;

import java.awt.*;
import javax.swing.*;

// entry point class
public class Main {
	
	public static void main(String[] args) {
		
	    java.awt.EventQueue.invokeLater(new Runnable() {
	    	public void run() {
	        	try{
	        	    // UI manager changes
	        	    UIManager.put("ColorChooser.swatchesRecentSwatchSize", new Dimension(20, 20));
	        	    UIManager.put("ColorChooser.swatchesSwatchSize", new Dimension(15, 15));
	        	    UIManager.put("ColorChooser.swatchesNameText", "Colors");
	        		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	        	}catch (Exception e){
	      	    	
	        	}
	            MainFrame frame = new MainFrame();
	            frame.setVisible(true);
	            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
	    	}
	    });
	}
}

