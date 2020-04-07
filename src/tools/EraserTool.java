package tools;

import java.awt.Point;
import java.awt.event.MouseEvent;

import pixyart.GlobalStateManager;
import pixyart.PixelCanvas;

public class EraserTool extends Tool{
	private int size;
	
	public EraserTool() {
		super();
		this.size = 5;
	}
	
    public void mousePressed(MouseEvent e)
    {
    	PixelCanvas canvas = GlobalStateManager.getInstance().getCanvas();
    	Point coords = e.getPoint();

    	canvas.erase(coords.x, coords.y, size);
    	canvas.repaint();
    }

    public void mouseDragged(MouseEvent e)
    {
    	PixelCanvas canvas = GlobalStateManager.getInstance().getCanvas();
    	Point coords = e.getPoint();

    	
    	canvas.erase(coords.x, coords.y, size);
    	canvas.repaint();
    }

    public void mouseReleased(MouseEvent e)
    {
    	PixelCanvas canvas = GlobalStateManager.getInstance().getCanvas();
    	canvas.changeHappened();
    	canvas.repaint();

    }

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
