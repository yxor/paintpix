package tools;

import java.awt.*;
import java.awt.event.*;

import pixyart.PixelCanvas;


public class BucketTool extends Tool {	
	
    public void mousePressed(MouseEvent e)
    {
    	PixelCanvas canvas = (PixelCanvas) e.getSource();
    	Point coords = e.getPoint();
    	Color c = (e.getButton() == MouseEvent.BUTTON1) ? canvas.getPrimaryColor(): canvas.getSecondaryColor();
    	canvas.changeHappened();
    	canvas.floodFill(coords.x, coords.y, c);
    	canvas.repaint();
    }
    
}
