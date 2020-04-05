package pixyart;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayDeque;

public class CanvasUndoManager {
	private ArrayDeque<BufferedImage> history;
	private ArrayDeque<BufferedImage> undoed;
	
	/**
	 * Constructor
	 */
	public CanvasUndoManager() {
		this.history = new ArrayDeque<BufferedImage>();
		this.undoed = new ArrayDeque<BufferedImage>();
	}
	/**
	 * Constructor
	 * 
	 * @param image Image to add to the history
	 */
	public CanvasUndoManager(BufferedImage image)
	{
		this();
		this.history.addFirst(deepCopy(image));
	}
	
	public synchronized void changeHappened(BufferedImage image)
	{
		this.history.addFirst(deepCopy(image));
		//this.undoed.clear();

	}
	
	public synchronized BufferedImage undo()
	{
		if(this.history.isEmpty())
			return null;
		BufferedImage lastImage = this.history.poll();
		//this.undoed.addFirst(deepCopy(lastImage));
		return deepCopy(lastImage);
	}
	
	public synchronized BufferedImage redo()
	{
		if(this.undoed.isEmpty())
			return null;
		BufferedImage lastImage = this.undoed.poll();
		this.history.addFirst(deepCopy(lastImage));
		return deepCopy(lastImage);
	}
	
	
	public static BufferedImage deepCopy(BufferedImage image) {
	    ColorModel cm = image.getColorModel();
	    boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
	    WritableRaster raster = image.copyData(image.getRaster().createCompatibleWritableRaster());
	    return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	
	
}
