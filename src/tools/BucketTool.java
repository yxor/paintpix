package tools;

import java.awt.*;
import java.awt.event.*;

import pixyart.GlobalStateManager;
import pixyart.PixelCanvas;


public class BucketTool extends Tool {	
	
    public void mousePressed(MouseEvent e)
    {
    	PixelCanvas canvas = GlobalStateManager.getInstance().getCanvas();
    	Point coords = e.getPoint();
    	Color c = (e.getButton() == MouseEvent.BUTTON1) ? canvas.getPrimaryColor(): canvas.getSecondaryColor();
    	canvas.floodFill(coords.x, coords.y, c);
    	canvas.changeHappened();
    	canvas.repaint();
    }
    
}
