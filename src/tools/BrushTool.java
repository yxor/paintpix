package tools;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.HashSet;

import javax.swing.ImageIcon;

import paintpix.PixelCanvas;

public class BrushTool extends Tool{
	private HashSet<Point> visited;
	
	public BrushTool() {
		super();
		this.visited = new HashSet<Point>();
	}
	
	public BrushTool(ImageIcon icon) {
		super(icon);
		this.visited = new HashSet<Point>();
	}
	
    public void mousePressed(MouseEvent e)
    {
    	PixelCanvas canvas = (PixelCanvas) e.getSource();
    	Point coords = e.getPoint();
    	
    	// check if the point is already painted, if its painted do not do anything
    	if(visited.isEmpty())
    		canvas.changeHappened();
    	
    	if(visited.contains(canvas.getScaledCoord(coords)))
    	{
    		return;
    	}
    	visited.add(canvas.getScaledCoord(coords));
    	
    	Color c = ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == MouseEvent.BUTTON1_DOWN_MASK)? canvas.getPrimaryColor(): canvas.getSecondaryColor();
    	canvas.drawBrush(coords.x, coords.y, c);
    	canvas.repaint();
    }

    public void mouseDragged(MouseEvent e)
    {
    	PixelCanvas canvas = (PixelCanvas) e.getSource();
    	Point coords = e.getPoint();
    	// check if the point is already painted, if its painted do not do anything
    	if(visited.contains(canvas.getScaledCoord(coords)))
    	{
    		return;
    	}
    	visited.add(canvas.getScaledCoord(coords));
    	Color c = ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == MouseEvent.BUTTON1_DOWN_MASK)? canvas.getPrimaryColor(): canvas.getSecondaryColor();

    	canvas.drawBrush(coords.x, coords.y, c);
    	canvas.repaint();
    }

    public void mouseReleased(MouseEvent e)
    {
    	PixelCanvas canvas = (PixelCanvas) e.getSource();
    	visited.clear();
    	canvas.repaint();

    }

}
