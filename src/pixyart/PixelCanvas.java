package pixyart;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.*;

import javax.swing.*;
import javax.swing.undo.UndoManager;

import tools.Tool;

@SuppressWarnings("serial")
public class PixelCanvas extends JComponent{
	private BufferedImage pixels; 
	private String savePath;
	
	private double scaleFactor;
	private int width, height;
	private Color primaryColor;
	private Color secondaryColor;
	
	private Tool selectedTool;
	private CanvasUndoManager undoManager;
	private MouseAdapter mouseAdapter = new MouseAdapter () {
		
    	public void mouseWheelMoved(MouseWheelEvent e)
        {
    		double scale = e.getPreciseWheelRotation();
    		Point p = e.getPoint();
    		PixelCanvas.this.zoom(scale, p);

        }
    };
	

	/**
	 * Constructor that creates an empty canvas.
	 * 
	 * @param width Canvas width.
	 * @param height Canvas height.
	 */
	public PixelCanvas(int width, int height) {
		this.pixels = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);
		this.pixels.setAccelerationPriority(1);
		this.scaleFactor = 1.0F;
		this.width = width + 2;
		this.height = height + 2;
		this.primaryColor = new Color(0, 0, 0, 255);
		this.secondaryColor = new Color(255, 255, 255, 0);
        this.setPreferredSize(new Dimension(width+2, height+2));

		this.undoManager = new CanvasUndoManager(this.pixels);
		// zooming in and out support TODO improve this
		
        addMouseWheelListener(mouseAdapter);
	}
	
	/**
	 * Constructor that creates a canvas from an image.
	 * 
	 * @param image Canvas width.
	 */
	public PixelCanvas(BufferedImage image) {
		this.pixels = image;;

		this.scaleFactor = 1.0F;
		this.width = image.getWidth() + 2;
		this.height = image.getHeight() + 2;
		
		this.primaryColor = new Color(0, 0, 0, 255);
		this.secondaryColor = new Color(255, 255, 255, 255);
		this.undoManager = new CanvasUndoManager(this.pixels);

        addMouseWheelListener(mouseAdapter);
	}

	@Override
	public Dimension getPreferredSize() {
	    return new Dimension((int)Math.round(pixels.getWidth() * scaleFactor + 2), 
	  		  (int)Math.round(pixels.getHeight() * scaleFactor + 2));
	}


	@Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        
        // recalculate the Image dimensions
        this.width = (int)Math.round(pixels.getWidth() * scaleFactor + 2);
        this.height = (int) Math.round(pixels.getHeight() * scaleFactor + 2);
        this.revalidate();
        
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER);
        g2d.setComposite(comp);

        g2d.drawImage(this.pixels, 1, 1, width-2, height-2, null);

        // draw one pixel wide border around the canvas
        g2d.drawRect(0, 0, width-1, height-1);
		g2d.dispose();
    }
	
	
	public void drawPixel(int x, int y, Color c)
	{
		int realX = this.getScaledCoord(x);
		int realY = this.getScaledCoord(y);
		
		Graphics2D g2d = (Graphics2D) this.pixels.getGraphics();
		
		
        Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) c.getAlpha() / 255f);
        
		g2d.setColor(c);
        g2d.setComposite(comp);
        g2d.drawLine(realX, realY, realX, realY);

		g2d.dispose();
	}
	
	public void zoom(double scale, Point p)
	{	
		JPanel canvasPanel = GlobalStateManager.getInstance().getMainFrame().getCanvasPanel();

		double oldScale = this.scaleFactor;
		this.scaleFactor *= (scale < 0) ? 1.1f : 0.9f;
		double scaleChange = this.scaleFactor / oldScale;
		
        Rectangle visibleRect = canvasPanel.getVisibleRect();
        double scrollX = p.getX() * scaleChange - (p.getX()-visibleRect.getX());
        double scrollY = p.getY() * scaleChange - (p.getY()-visibleRect.getY());
        
        visibleRect.setRect(scrollX, scrollY, visibleRect.getWidth(), visibleRect.getHeight());
        canvasPanel.scrollRectToVisible(visibleRect);
        repaint();
	}
	
	
	public void drawBrush(int x, int y, Color c, int size)
	{
		int realX = this.getScaledCoord(x, size);
		int realY = this.getScaledCoord(y, size);
		
		Graphics2D g2d = (Graphics2D) this.pixels.getGraphics();
		
        Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) c.getAlpha() / 255f);
        
		g2d.setColor(c);
        g2d.setComposite(comp);
        g2d.fillOval(realX, realY, size, size);

		g2d.dispose();
	}
	
	public void erase(int x, int y, int size)
	{
		int realX = this.getScaledCoord(x, size);
		int realY = this.getScaledCoord(y, size);
		
		Graphics2D g2d = (Graphics2D) this.pixels.getGraphics();
        
		g2d.setColor(new Color(0, 0, 0, 0));
        g2d.setComposite(AlphaComposite.Src);
        g2d.fillOval(realX, realY, size, size);

		g2d.dispose();
	}
	
	
	public void floodFill(int x, int y, Color c)
	{
		int realX = getScaledCoord(x);
		int realY = getScaledCoord(y);

		int targetColor = this.pixels.getRGB(realX, realY);
		int replaceColor = c.getRGB();

		// sanity checks
		if(targetColor == replaceColor)
			return;
		Point node = new Point(realX, realY);
		Queue<Point> q = new LinkedList<Point>();
		q.add(node);
		
		while(!q.isEmpty()) {
			Point n = q.poll();
			Point west = (Point) n.clone();
			Point east = (Point) n.clone();
			
			while(west.x > -1 && this.pixels.getRGB(west.x, west.y) == targetColor)
				west.x--;
			
			while(east.x < this.pixels.getWidth() && this.pixels.getRGB(east.x, east.y) == targetColor)
				east.x++;

			for(int i = west.x + 1; i < east.x; i++) {
				int yLevel = west.y;
				this.pixels.setRGB(i, yLevel, replaceColor);
				
				if(west.y < this.pixels.getHeight()-1 && this.pixels.getRGB(i, yLevel+1) == targetColor)
					q.add(new Point(i, yLevel+1));
				if(west.y > 0 && this.pixels.getRGB(i, yLevel-1) == targetColor)
					q.add(new Point(i, yLevel-1));
			}
		}
	}
	
	public synchronized void changeHappened()
	{
		this.undoManager.changeHappened(this.pixels);
	}
	
	public synchronized void undo()
	{
		
		BufferedImage image = this.undoManager.undo();
		if(image == null)
			return;
		this.pixels = image;
		repaint();
	}
	
	public synchronized void redo()
	{
		BufferedImage image = this.undoManager.redo();
		if(image == null)
			return;
		this.pixels = image;
		repaint();
	}
	
	
	public void eyeDrop(int x, int y, boolean primaryColor) {
		int rgb = this.pixels.getRGB(getScaledCoord(x), getScaledCoord(y));
		Color c = new Color(rgb, true);
		
		GlobalStateManager.getInstance().getColorPicker().setColor(c);
		if(primaryColor)
			this.primaryColor = c;
		else
			this.secondaryColor = c;
	}
	
	public BufferedImage getImage()
	{
		return this.pixels;
	}
	
	public int getScaledCoord(int coord)
	{
		return (int) ((coord - 1) / scaleFactor);
	}
	
	public int getScaledCoord(int coord, int size)
	{
		return (int) ((coord - 1) / scaleFactor) - (size / 2);
	}
	
	
	public Point getScaledCoord(Point p, int size)
	{
		int realX = this.getScaledCoord(p.x, size);
		int realY = this.getScaledCoord(p.y, size);
		
		return new Point(realX, realY);
	}
	
	public Point getScaledCoord(Point p)
	{
		return getScaledCoord(p, 0);
	}

	
	public Tool getSelectedTool() {
		return selectedTool;
	}

	
	public void setSelectedTool(Tool selectedTool) {
		if(this.selectedTool != null)
		{
			removeMouseListener(this.selectedTool);
			removeMouseMotionListener(this.selectedTool);
		}
		this.selectedTool = selectedTool;
        addMouseMotionListener(selectedTool);
        addMouseListener(selectedTool);
    }
	
	
	public Color getPrimaryColor() {
		return primaryColor;
	}

	
	public void setPrimaryColor(Color primaryColor) {
		this.primaryColor = primaryColor;
	}

	
	public Color getSecondaryColor() {
		return secondaryColor;
	}
	
	
	public void setSecondaryColor(Color secondaryColor) {
		this.secondaryColor = secondaryColor;
	}
	
	
	public double getScale()
	{
		return this.scaleFactor;
	}
	
	
	public void setScale(double scale)
	{
		this.scaleFactor = scale;
        this.repaint();
	}

	
	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	
}
