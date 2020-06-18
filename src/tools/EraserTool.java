package tools;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

import paintpix.PixelCanvas;

public class EraserTool extends Tool{
	private boolean pressed;
	
	public EraserTool() {
		super();
		this.pressed = false;
	}
	
	public EraserTool(ImageIcon icon) {
		super(icon);
		this.pressed = false;
	}
	
    public void mousePressed(MouseEvent e)
    {
    	PixelCanvas canvas = (PixelCanvas) e.getSource();
    	Point coords = e.getPoint();
    	if(!this.pressed)
    		canvas.changeHappened();
    	this.pressed = true;
    	canvas.erase(coords.x, coords.y);
    	canvas.repaint();
    }

    public void mouseDragged(MouseEvent e)
    {
    	PixelCanvas canvas = (PixelCanvas) e.getSource();
    	Point coords = e.getPoint();
    	this.pressed = true;
    	
    	canvas.erase(coords.x, coords.y);
    	canvas.repaint();
    }

    public void mouseReleased(MouseEvent e)
    {
    	PixelCanvas canvas = (PixelCanvas) e.getSource();
    	this.pressed = false;
    	canvas.repaint();

    }

}
