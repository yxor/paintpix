package pixyart;

import java.awt.*;
import java.awt.event.*;


public class PencilTool extends Tool {
	private Color c = null;
	
    public void mousePressed(MouseEvent e)
    {
    	
    	
    	Point coords = e.getPoint();
    	
    	c = (e.getButton() == MouseEvent.BUTTON1) ? workingCanvas.getPrimaryColor(): workingCanvas.getSecondaryColor();
    	workingCanvas.drawPixel(coords.x, coords.y, c);
    	workingCanvas.repaint();
    }

    public void mouseDragged(MouseEvent e)
    {
    	Point coords = e.getPoint();
    	workingCanvas.drawPixel(coords.x, coords.y, c);
    	workingCanvas.repaint();
    }

    public void mouseReleased(MouseEvent e)
    {
    	c = null;
    	workingCanvas.repaint();
    }

}
