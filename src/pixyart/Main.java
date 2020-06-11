package pixyart;

import java.awt.*;
import javax.swing.*;

// entry point class
public class Main {
	
	public static void main(String[] args) {
		
	    EventQueue.invokeLater(new Runnable() {
	    	public void run() {
	    		DatabaseManager.connect("recent_files.db");
	        	try{
	        	    // UI manager changes
	        	    UIManager.put("ColorChooser.swatchesRecentSwatchSize", new Dimension(20, 20));
	        	    UIManager.put("ColorChooser.swatchesSwatchSize", new Dimension(15, 15));
	        	    UIManager.put("ColorChooser.swatchesNameText", "Colors");
	        	    // native look and feel, looks better than crossplatform
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

