package tools;

import java.awt.*;
import java.awt.event.*;

import javax.swing.ImageIcon;

import pixyart.PixelCanvas;


public class EyeDropperTool extends Tool {	
	
	public EyeDropperTool() {
		super();
	}
	
    public EyeDropperTool(ImageIcon icon) {
		super(icon);
	}

	public void mousePressed(MouseEvent e)
    {
    	PixelCanvas canvas = (PixelCanvas) e.getSource();
    	Point coords = e.getPoint();
    	boolean primaryColor = (e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == MouseEvent.BUTTON1_DOWN_MASK;
    	canvas.eyeDrop(coords.x, coords.y, primaryColor);
    }
    
}
