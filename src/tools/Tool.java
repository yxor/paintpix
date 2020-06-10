package tools;

import javax.swing.ImageIcon;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.*;


public abstract class Tool extends MouseAdapter {
	private Cursor cursor;
	
	public Tool()
	{
		super();
	}
	
	public Tool(ImageIcon icon) {
		super();
		this.cursor = Toolkit.getDefaultToolkit().createCustomCursor(icon.getImage() , new Point(0, 0), "");
	}
	
	public void setCursor(ImageIcon icon)
	{
		this.cursor = Toolkit.getDefaultToolkit().createCustomCursor(icon.getImage() , new Point(0, 0), "");
	}
	
	public Cursor getCursor()
	{
		return this.cursor;
	}
}
