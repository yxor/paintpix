package paintpix;


import java.awt.image.Raster;
import java.io.Serializable;
import java.util.concurrent.LinkedBlockingDeque;

@SuppressWarnings("serial")
public class CanvasUndoManager implements Serializable{
	private LinkedBlockingDeque<Raster> history;
	private LinkedBlockingDeque<Raster> undoed;
	
	/**
	 * Constructor
	 */
	public CanvasUndoManager() {
		this.history = new LinkedBlockingDeque<Raster>();
		this.undoed = new LinkedBlockingDeque<Raster>();
	}

	public void changeHappened(Raster image)
	{
		this.history.addFirst(image);
		this.undoed.clear();
	}
	
	public Raster undo(Raster redoImage)
	{
		if(this.history.isEmpty())
			return null;
		
		Raster lastImage = this.history.poll();
		this.undoed.addFirst(redoImage);
		return lastImage;
	}
	
	public synchronized Raster redo(Raster undoImage)
	{
		if(this.undoed.isEmpty())
			return null;
		
		Raster lastImage = this.undoed.poll();
		this.history.addFirst(undoImage);
		return lastImage;
	}
	
	
	public boolean canUndo()
	{
		return !this.history.isEmpty();
	}

	public boolean canRedo()
	{
		return !this.undoed.isEmpty();
	}
	
	
}
