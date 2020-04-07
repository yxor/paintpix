package tools;

import java.awt.Point;
import java.awt.event.MouseEvent;

import pixyart.PixelCanvas;

public class EraserTool extends Tool{
	private int size;
	private boolean pressed;
	
	public EraserTool() {
		this.size = 5;
		this.pressed = false;
	}
	
    public void mousePressed(MouseEvent e)
    {
    	PixelCanvas canvas = (PixelCanvas) e.getSource();
    	Point coords = e.getPoint();
    	if(!this.pressed)
    		canvas.changeHappened();
    	this.pressed = true;
    	canvas.erase(coords.x, coords.y, size);
    	canvas.repaint();
    }

    public void mouseDragged(MouseEvent e)
    {
    	PixelCanvas canvas = (PixelCanvas) e.getSource();
    	Point coords = e.getPoint();
    	this.pressed = true;
    	
    	canvas.erase(coords.x, coords.y, size);
    	canvas.repaint();
    }

    public void mouseReleased(MouseEvent e)
    {
    	PixelCanvas canvas = (PixelCanvas) e.getSource();
    	this.pressed = false;
    	canvas.repaint();

    }

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
