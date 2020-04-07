package pixyart;

import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import tools.Tool;

/**
 * Class responsible for the communication between different components
 * and perform operations
 */
public class MainController {
	private MainFrame mainFrame;
	private ToolPanel tools;
	private ColorPicker colorPicker;
	
	private JPanel canvasPanel;
	private PixelCanvas canvas;
	
	
	public MainController(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.tools = mainFrame.getTools();
		this.colorPicker = mainFrame.getColorPicker();
		this.canvasPanel = mainFrame.getCanvasPanel();
		
		this.tools.setController(this);
		this.colorPicker.setController(this);
	}
	

	
	public void createCanvas(int width, int height)
	{
		if(this.canvas != null)
		{
			this.canvasPanel.remove(this.canvas);
			this.mainFrame.revalidate();
		}
		this.canvas = new PixelCanvas(width, height);
		this.canvas.setScale(10.0F); // TODO: fix this and make it dynamically picked
		this.canvas.setController(this);
		this.canvasPanel.add(this.canvas);
		this.mainFrame.getCanvasContainer().repaint();
		this.mainFrame.revalidate();
	}
	
	
	public void createCanvas(BufferedImage image)
	{
		if(this.canvas != null)
		{
			this.canvasPanel.remove(this.canvas);
			this.mainFrame.revalidate();
		}
		this.canvas = new PixelCanvas(image);
		this.canvas.setScale(10.0F);
		this.canvas.setController(this);
		this.canvasPanel.add(this.canvas);
		this.mainFrame.getCanvasContainer().repaint();
		this.mainFrame.revalidate();
	}
	
	public void setCanvasTool(Tool t)
	{
		if(this.canvas == null)
			return;
		
		this.canvas.setSelectedTool(t);
	}
	
	public void openCanvasFromFileSystem()
	{
		BufferedImage image = ImageFileManager.open();
		this.createCanvas(image);
		this.canvas.setSavePath(ImageFileManager.latestPath);
	}
	
	public void openNewCanvas()
	{
		
	}
	
	public void saveCanvas()
	{
		if(this.canvas == null) return;

		String path = this.canvas.getSavePath();
		if(path == null)
		{
			path = ImageFileManager.save(this.canvas.getImage());
			this.canvas.setSavePath(path);
		}else {
			ImageFileManager.save(this.canvas.getImage(), path);
		}
	}
	
	public void saveCanvasAs()
	{
		if(this.canvas == null)
			return;
		String path = ImageFileManager.save(this.canvas.getImage());
		this.canvas.setSavePath(path);
	}
	
	// setters and getters
	
	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	public MainFrame getMainFrame()
	{
		return this.mainFrame;
	}
	
	public PixelCanvas getCanvas() 
	{
		return canvas;
	}

	public void setCanvas(PixelCanvas canvas) 
	{
		this.canvas = canvas;
	}


	public ToolPanel getTools() 
	{
		return tools;
	}


	public void setTools(ToolPanel tools) 
	{
		this.tools = tools;
	}


	public ColorPicker getColorPicker() 
	{
		return colorPicker;
	}


	public void setColorPicker(ColorPicker colorPicker) 
	{
		this.colorPicker = colorPicker;
	}



	public JPanel getCanvasPanel() {
		// TODO Auto-generated method stub
		return this.canvasPanel;
	}

	
	
}
