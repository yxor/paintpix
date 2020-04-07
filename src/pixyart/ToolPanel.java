package pixyart;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import tools.*;

@SuppressWarnings("serial")
public class ToolPanel extends JToolBar {
	private MainController controller;
	
	private BrushTool brush;
	private BucketTool bucket;
	private EyeDropperTool eyeDropper;
	private PencilTool pencil;
	private EraserTool eraser;

	
	
	
	public ToolPanel() {
		super();
		
		ButtonListener listener = new ButtonListener();
		
		this.brush = new BrushTool();
		this.bucket = new BucketTool();
		this.eyeDropper = new EyeDropperTool();
		this.pencil = new PencilTool();
		this.eraser = new EraserTool();
		
		
		String[] buttonLabels = {"Open", "Save", "Save As", "Pencil", "Brush", "Eraser", "EyeDropper", "Bucket"};
		
		JButton[] buttons = new JButton[buttonLabels.length];
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton(buttonLabels[i]);
			buttons[i].addActionListener(listener);
			this.add(buttons[i]);
			this.addSeparator();
		}
		
		
	}

	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			switch(e.getActionCommand()) {
			
			case "Open":
				controller.openCanvasFromFileSystem();
				break;
				
			case "Save":
				controller.saveCanvas();
				break;
				
			case "Save As":
				controller.saveCanvasAs();
				break;
				
			case "Pencil":
				controller.setCanvasTool(pencil);
				break;
				
			case "Bucket":
				controller.setCanvasTool(bucket);
				break;
				
			case "Brush":
				controller.setCanvasTool(brush);
				break;
				
			case "EyeDropper":
				controller.setCanvasTool(eyeDropper);
				break;
				
			case "Eraser":
				controller.setCanvasTool(eraser);
				break;
			}
		}
	}
	

	public void setController(MainController controller)
	{
		this.controller = controller;
	}
	
}
