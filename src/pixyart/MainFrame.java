package pixyart;


import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class MainFrame extends JFrame  {
	private ColorPicker colorPicker;
	private ToolPanel tools;
	private JPanel canvasPanel;
	private JScrollPane canvasContainer;
	
	public MainFrame() {
	    super("Pixy Art"); // temporary name


	    Container mainPane = this.getContentPane();
	    mainPane.setLayout(new BorderLayout());
	    
	    setPreferredSize(new Dimension(600, 700));
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
	  
	    
	    colorPicker = new ColorPicker(Color.BLACK);
		tools = new ToolPanel();
        canvasPanel = new JPanel(new GridBagLayout());
        
        canvasContainer = new JScrollPane(canvasPanel);
        new GlobalKeyBinder(this.getRootPane());

        // updating global state

        GlobalStateManager.getInstance().setColorPicker(colorPicker);
        GlobalStateManager.getInstance().setTools(tools);
        GlobalStateManager.getInstance().setMainFrame(this);
        

        mainPane.add(canvasContainer, BorderLayout.CENTER);
        mainPane.add(colorPicker, BorderLayout.EAST); 
        mainPane.add(tools, BorderLayout.NORTH);
	    pack();
	}
	
	public JPanel getCanvasPanel()
	{
		return this.canvasPanel;
	}
	
	public JScrollPane getCanvasContainer()
	{
		return this.canvasContainer;
	}
	
}