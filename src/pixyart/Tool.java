package pixyart;
import javax.swing.event.MouseInputListener;
import java.awt.event.*;


public abstract class Tool extends MouseAdapter implements MouseInputListener{
	protected PixelCanvas workingCanvas;

	public void setCanvas(PixelCanvas canvas) {
		this.workingCanvas = canvas;
	}
}
