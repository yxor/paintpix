package pixyart;

import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;
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
	
	public void createNewCanvas()
	{
		NewCanvasDialog d = new NewCanvasDialog(this.mainFrame);
		int closeOption = d.showOpenDialog();
		if(closeOption == NewCanvasDialog.APPROVE_OPTION) {
			createCanvas(d.getChosenWidth(), d.getChosenHeight());
			this.canvas.fill(d.getChosenFillColor());
		}
	}
	
	public void createCanvas(int width, int height)
	{
		if(this.canvas != null)
		{
			this.canvasPanel.remove(this.canvas);
			this.mainFrame.revalidate();
		}
		this.canvas = new PixelCanvas(width, height);
		// setting initial scale dynamically
		this.canvas.setScale(this.calculateScale());
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
		// setting initial scale dynamically
		this.canvas.setScale(this.calculateScale());
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
		if(image == null)
			return;
		this.createCanvas(image);
		this.canvas.setSavePath(ImageFileManager.latestPath);
		this.canvas.setSaveHeight(this.canvas.getImage().getHeight());
		this.canvas.setSaveWidth(this.canvas.getImage().getWidth());

	}
	
	
	public void saveCanvas()
	{
		if(this.canvas == null) return;

		String path = this.canvas.getSavePath();
		if(path == null)
		{
			saveCanvasAs();
		}else {
			BufferedImage original = this.canvas.getImage();
			BufferedImage scaled = ImageFileManager.resize(original, this.canvas.getSaveWidth(), this.canvas.getSaveHeight());
			ImageFileManager.save(scaled, path);
		}
	}
	
	public void closeCanvas()
	{
		if(this.canvas == null)
			return;
		int choice = JOptionPane.showConfirmDialog(this.mainFrame, "Do you want to save?", "Closing Canvas", JOptionPane.YES_NO_CANCEL_OPTION);
		switch(choice)
		{
		case JOptionPane.CANCEL_OPTION:
			return;
		case JOptionPane.NO_OPTION:
			break;
		case JOptionPane.OK_OPTION:
			this.saveCanvas();
		}
		
		this.canvasPanel.remove(this.canvas);
		this.mainFrame.revalidate();
		
	}
	
	public void saveCanvasAs()
	{
		if(this.canvas == null)
			return;
		
		SaveCanvasDialog d = new SaveCanvasDialog(this.mainFrame, this.canvas);
		
		int closeOption = d.showOpenDialog();
		
		if(closeOption != SaveCanvasDialog.APPROVE_OPTION)
			return;
		
		
		BufferedImage scaled = ImageFileManager.resize(this.canvas.getImage(), d.getChosenWidth(), d.getChosenHeight());
		ImageFileManager.save(scaled, d.getChosenAbsolutePath());
		this.canvas.setSavePath(d.getChosenAbsolutePath());
		this.canvas.setSaveWidth(d.getChosenWidth());
		this.canvas.setSaveHeight(d.getChosenHeight());
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
	
	public double calculateScale()
	{
		if(this.canvas == null)
			return 0;
		double widthScale = (double)(this.mainFrame.getWidth()/2) / this.canvas.getImage().getWidth();
		double heightScale = (double)(this.mainFrame.getHeight()/2) / this.canvas.getImage().getHeight();
		return Math.min(widthScale, heightScale);
	}

	
	
}
