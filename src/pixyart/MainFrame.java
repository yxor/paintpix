package pixyart;


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class MainFrame extends JFrame{

	public MainFrame() {
	    super("Pixy Art"); // temporary name
	    
	    Container mainPane = this.getContentPane();
	    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	    // what happens  when the window is closed
	    addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent e) {
	        	// TODO: add prompt user to save if unsaved
	        	MainFrame.this.setVisible(false);
	        	MainFrame.this.dispose();
	        }
	    });
	
	    PixelCanvas canvas = new PixelCanvas(20, 20);
	    mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));
        JPanel panel = new JPanel();
        panel.add(canvas);
	    mainPane.add(panel);
	    canvas.setScale(10.0F);
	    pack();
	}
}