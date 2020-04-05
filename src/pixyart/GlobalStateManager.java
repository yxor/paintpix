package pixyart;


/** 
* using a singleton to handle global state
* https://en.wikipedia.org/wiki/Singleton_pattern
* 
*/
public final class GlobalStateManager {
	private final static GlobalStateManager INSTANCE = new GlobalStateManager();
	private GlobalStateManager() {} // hide the constructor;
	

	private PixelCanvas canvas;
	private ToolPanel tools;
	private ColorPicker colorPicker;
	private MainFrame mainFrame;
	
	
	
	
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

	
	public static GlobalStateManager getInstance()
	{
		return GlobalStateManager.INSTANCE;
	}
	
}
