package tools;

import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;

import javax.swing.ImageIcon;

import paintpix.*;


public class PencilTool extends Tool {
	private HashSet<Point> visited = new HashSet<Point>();
	
	public PencilTool() {
		super();
	}
	
	public PencilTool(ImageIcon icon) {
		super(icon);
	}

    public void mousePressed(MouseEvent e)
    {
    	PixelCanvas canvas = (PixelCanvas) e.getSource();
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
    	PixelCanvas canvas = (PixelCanvas) e.getSource();
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
    	PixelCanvas canvas = (PixelCanvas) e.getSource();
    	visited.clear();
    	canvas.repaint();
    }

}
