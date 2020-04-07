package tools;

import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;

import pixyart.*;


public class PencilTool extends Tool {
	private HashSet<Point> visited = new HashSet<Point>();
	
    public void mousePressed(MouseEvent e)
    {
    	PixelCanvas canvas = GlobalStateManager.getInstance().getCanvas();
    	Point coords = e.getPoint();
    	// check if the point is already painted, if its painted do not do anything
    	Color c = ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == MouseEvent.BUTTON1_DOWN_MASK)? canvas.getPrimaryColor(): canvas.getSecondaryColor();
    	if(visited.isEmpty())
    		canvas.changeHappened();
    	if(visited.contains(canvas.getScaledCoord(coords)))
    	{
    		return;
    	}
    	visited.add(canvas.getScaledCoord(coords));
    	
    	canvas.drawPixel(coords.x, coords.y, c);
    	canvas.repaint();
    }

    public void mouseDragged(MouseEvent e)
    {
    	PixelCanvas canvas = GlobalStateManager.getInstance().getCanvas();
    	Point coords = e.getPoint();
    	// check if the point is already painted, if its painted dont do anything
    	if(visited.contains(canvas.getScaledCoord(coords)))
    	{
    		return;
    	}
    	visited.add(canvas.getScaledCoord(coords));
    	Color c = ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == MouseEvent.BUTTON1_DOWN_MASK)? canvas.getPrimaryColor(): canvas.getSecondaryColor();
    	canvas.drawPixel(coords.x, coords.y, c);
    	canvas.repaint();
    }

    public void mouseReleased(MouseEvent e)
    {
    	PixelCanvas canvas = GlobalStateManager.getInstance().getCanvas();
    	visited.clear();
    	canvas.repaint();
    }

}
