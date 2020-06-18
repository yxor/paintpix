package tools;

import java.awt.*;
import java.awt.event.*;

import javax.swing.ImageIcon;

import paintpix.PixelCanvas;


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
    	canvas.eyeDrop(coords.x, coords.y);
    }
    
}
