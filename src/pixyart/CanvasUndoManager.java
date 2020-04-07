package pixyart;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.concurrent.LinkedBlockingDeque;

public class CanvasUndoManager {
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
	
	
	public static BufferedImage deepCopy(BufferedImage image) {
	    ColorModel cm = image.getColorModel();
	    boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
	    WritableRaster raster = image.copyData(image.getRaster().createCompatibleWritableRaster());
	    return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	
	
}
