package pixyart;


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;


import java.awt.*;

@SuppressWarnings("serial")
public class MainFrame extends JFrame  {
	private ColorPicker colorPicker;
	private PixelCanvas canvas;
	
	public MainFrame() {
	    super("Pixy Art"); // temporary name
	    
	    Container mainPane = this.getContentPane();
	    mainPane.setLayout(new BorderLayout());
	    setPreferredSize(new Dimension(800, 700));

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
	
	    canvas = new PixelCanvas(50, 50);
	    canvas.setScale(20.0F);
	    canvas.setSelectedTool(new PencilTool());
	    

	    colorPicker = new ColorPicker(canvas.getPrimaryColor());
	    colorPicker.setCanvas(canvas);
	    
        JPanel canvasPanel = new JPanel();
        canvasPanel.add(canvas);
	    mainPane.add(canvasPanel, BorderLayout.CENTER);
        mainPane.add(colorPicker, BorderLayout.NORTH); 
	    pack();
	}
	

}