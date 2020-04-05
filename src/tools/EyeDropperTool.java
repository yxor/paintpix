package tools;

import java.awt.*;
import java.awt.event.*;

import pixyart.GlobalStateManager;
import pixyart.PixelCanvas;


public class EyeDropperTool extends Tool {	
	
    public void mousePressed(MouseEvent e)
    {
    	PixelCanvas canvas = GlobalStateManager.getInstance().getCanvas();
    	Point coords = e.getPoint();
    	boolean primaryColor = (e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == MouseEvent.BUTTON1_DOWN_MASK;
    	canvas.eyeDrop(coords.x, coords.y, primaryColor);
    }
    
}
