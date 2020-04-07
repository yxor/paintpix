package pixyart;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.*;
import tools.*;

@SuppressWarnings("serial")
public class ToolPanel extends JToolBar {
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
		
		String[] buttonLabels = { "Pencil", "Brush", "Bucket", "EyeDropper", "Save", "Save As", "Open", "Eraser" };
		
		JButton[] buttons = new JButton[buttonLabels.length];
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton(buttonLabels[i]);
			buttons[i].addActionListener(listener);
			this.add(buttons[i]);
			this.addSeparator();
		}
		
		
	}

	private class ButtonListener implements ActionListener {
		GlobalStateManager state = GlobalStateManager.getInstance();
		@Override
		public synchronized void  actionPerformed(ActionEvent e) {
			switch(e.getActionCommand()) {
			case "Open":
				BufferedImage image = ImageFileManager.open();
				newCanvas(image);
				state.getCanvas().setSavePath(ImageFileManager.latestPath);
				break;

			case "Pencil":
				if(state.getCanvas() == null) return;
				state.getCanvas().setSelectedTool(pencil);
				break;
			case "Bucket":
				if(state.getCanvas() == null) return;
				state.getCanvas().setSelectedTool(bucket);
				break;
			case "Brush":
				if(state.getCanvas() == null) return;
				state.getCanvas().setSelectedTool(brush);
				break;
			
			case "EyeDropper":
				if(state.getCanvas() == null) return;
				state.getCanvas().setSelectedTool(eyeDropper);
				break;
			
			case "Save":
				if(state.getCanvas() == null) return;

				String path = state.getCanvas().getSavePath();
				if(path == null)
				{
					path = ImageFileManager.save(state.getCanvas().getImage());
					state.getCanvas().setSavePath(path);
				}else {
					ImageFileManager.save(state.getCanvas().getImage(), path);
				}
				break;
			case "Save As":
				if(state.getCanvas() == null) return;
				path = ImageFileManager.save(state.getCanvas().getImage());
				state.getCanvas().setSavePath(path);
				break;
			case "Eraser":
				if(state.getCanvas() == null) return;
				state.getCanvas().setSelectedTool(eraser);
				break;
			
			}
			
		}
	}
	

	public void newCanvas(int width, int height)
	{
		GlobalStateManager state = GlobalStateManager.getInstance();
		if(state.getCanvas() != null)
		{
			state.getMainFrame().getCanvasPanel().remove(state.getCanvas());
			state.getMainFrame().revalidate();
		}
		state.setCanvas(new PixelCanvas(width, height));
		resetTools();
	}
	
	public void newCanvas(BufferedImage image)
	{
		GlobalStateManager state = GlobalStateManager.getInstance();
		if(state.getCanvas() != null)
		{
			state.getMainFrame().getCanvasPanel().remove(state.getCanvas());
			state.getMainFrame().revalidate();
		}
		state.setCanvas(new PixelCanvas(image));
		resetTools();
	}
	
	private void resetTools()
	{
		GlobalStateManager state = GlobalStateManager.getInstance();
		state.getCanvas().setScale(10.0F);
		state.getCanvas().setSelectedTool(pencil);
	    	    
		state.getMainFrame().getCanvasPanel().add(state.getCanvas());
		state.getMainFrame().getCanvasContainer().repaint();
		state.getMainFrame().revalidate();
	}
	
	
}
