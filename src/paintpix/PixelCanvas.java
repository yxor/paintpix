package paintpix;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import tools.Tool;

@SuppressWarnings("serial")
/**
 * Custom Canvas component
 */
public class PixelCanvas extends JComponent implements Serializable{
	transient private BufferedImage pixels; 
	
	// save information
	private String savePath;
	private int saveWidth;
	private int saveHeight;
	private boolean changedAfterSave = true;
	
	private double scaleFactor;
	private int width, height;
	private Color primaryColor;
	private Color secondaryColor;
	
	transient private Tool selectedTool;
	transient private CanvasUndoManager undoManager;
	transient private MainController controller;
	transient private MouseAdapter mouseAdapter;
	

    private PixelCanvas() {
    	this.mouseAdapter = new MouseAdapter () {
    		
        	public void mouseWheelMoved(MouseWheelEvent e)
            {
        		double scale = e.getPreciseWheelRotation();
        		Point p = e.getPoint();
        		PixelCanvas.this.zoom(scale, p);

            }
        };
        
        this.undoManager = new CanvasUndoManager();
    }
    
	/**
	 * Constructor that creates an empty canvas.
	 * 
	 * @param width Canvas width.
	 * @param height Canvas height.
	 */
	public PixelCanvas(int width, int height) {
		this();
		this.pixels = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		this.pixels.setAccelerationPriority(1);
		this.scaleFactor = 1.0F;
		this.width = width + 2;
		this.height = height + 2;
		this.primaryColor = new Color(0, 0, 0, 255);
		this.secondaryColor = new Color(255, 255, 255, 0);
        this.setPreferredSize(new Dimension(width+2, height+2));

		
        addMouseWheelListener(mouseAdapter);
	}
	
	/**
	 * Constructor that creates a canvas from an image.
	 * 
	 * @param image Canvas width.
	 */
	public PixelCanvas(BufferedImage image) {
		this();
		this.pixels = image;
		this.scaleFactor = 1.0F;
		this.width = image.getWidth() + 2;
		this.height = image.getHeight() + 2;
		
		this.primaryColor = new Color(0, 0, 0, 255);
		this.secondaryColor = new Color(255, 255, 255, 255);

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
	
	public void fill(Color c)
	{
		Graphics2D g2d = this.pixels.createGraphics();
		g2d.setColor(c);
		g2d.setComposite(AlphaComposite.Src);
		g2d.fillRect(0, 0, this.pixels.getWidth(), this.pixels.getWidth());
		repaint();
		
	}
	
	public void drawPixel(int x, int y, Color c)
	{
		int realX = this.getScaledCoord(x);
		int realY = this.getScaledCoord(y);
		
		Graphics2D g2d = this.pixels.createGraphics();
		
		
        Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) c.getAlpha() / 255f);
        
		g2d.setColor(c);
        g2d.setComposite(comp);
        g2d.drawLine(realX, realY, realX, realY);

		g2d.dispose();
	}
	
	public void zoom(double scale, Point p)
	{	
		JPanel canvasPanel = this.controller.getCanvasPanel();
		double oldScale = this.scaleFactor;
		double newScale = scaleFactor * ((scale < 0) ? 1.1f : 0.9f);
		// restrict zooming in too far or out too far (10 times).
		double scaleByDefault = newScale / controller.calculateScale();
		if(scaleByDefault < 0.1 || scaleByDefault > 10)
			return;
		
		this.scaleFactor = newScale;
		double scaleChange = this.scaleFactor / oldScale;
		
        Rectangle visibleRect = canvasPanel.getVisibleRect();
        double scrollX = p.getX() * scaleChange - (p.getX()-visibleRect.getX());
        double scrollY = p.getY() * scaleChange - (p.getY()-visibleRect.getY());
        
        visibleRect.setRect(scrollX, scrollY, visibleRect.getWidth(), visibleRect.getHeight());
        canvasPanel.scrollRectToVisible(visibleRect);
        repaint();
	}
	
	
	public void drawBrush(int x, int y, Color c)
	{
		int size = controller.getSize();
		int realX = this.getScaledCoord(x, size);
		int realY = this.getScaledCoord(y, size);
		
		Graphics2D g2d = this.pixels.createGraphics();
		
        Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) c.getAlpha() / 255f);
        
		g2d.setColor(c);
        g2d.setComposite(comp);
        g2d.fillOval(realX, realY, size, size);

		g2d.dispose();
	}
	
	public void erase(int x, int y)
	{
		int size = controller.getSize();
		int realX = this.getScaledCoord(x, size);
		int realY = this.getScaledCoord(y, size);
		
		Graphics2D g2d = this.pixels.createGraphics();
        
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
	
	
	public void changeHappened()
	{
		this.changedAfterSave = true;
		this.undoManager.changeHappened(this.pixels.getData());
	}
	
	
	public void undo()
	{
		Raster image = this.undoManager.undo(this.pixels.getData());
		if(image == null)
			return;
		
		this.pixels.setData(image);
		repaint();
	}
	
	public void redo()
	{
		Raster image = this.undoManager.redo(this.pixels.getData());
		if(image == null)
			return;
		
		this.pixels.setData(image);
		repaint();
	}
	
	public boolean canUndo() 
	{
		return this.undoManager.canUndo();
	}
	
	public boolean canRedo() 
	{
		return this.undoManager.canRedo();
	}
	
	public void eyeDrop(int x, int y) {
		int rgb = this.pixels.getRGB(getScaledCoord(x), getScaledCoord(y));
		Color c = new Color(rgb, true);
		
		this.controller.getColorPicker().setColor(c);
		this.controller.getColorToggler().setColor(c);
		
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
		this.changedAfterSave = false;
		this.savePath = savePath;
	}
	
	public int getSaveWidth() {
		return saveWidth;
	}

	public void setSaveWidth(int saveWidth) {
		this.saveWidth = saveWidth;
	}

	public int getSaveHeight() {
		return saveHeight;
	}

	public void setSaveHeight(int saveHeight) {
		this.saveHeight = saveHeight;
	}
	
	public boolean isChangedAfterSave()
	{
		return this.changedAfterSave;
	}

	public void setController(MainController controller)
	{
		this.controller = controller;
	}

	public byte[] toBytes() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(bos);
			// serialize the whole object
			out.writeObject(this);
			out.flush();
			
			// serialize the pixels
			ImageIO.setUseCache(false);
			ImageIO.write(this.pixels, "png", out);
			
			byte[] bytes = bos.toByteArray();
		  
			return bytes;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bos.close();
			} catch (IOException ex) {
				// ignore close exception
			}
		}
		
		return null;
	}
	
	public static PixelCanvas fromBytes(byte[] bytes) {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(bis);
			
			PixelCanvas canvas = (PixelCanvas) in.readObject();
			
			BufferedImage image = ImageIO.read(in);
			canvas.pixels = image;
			canvas.addMouseWheelListener(new MouseAdapter () {
	    		
	        	public void mouseWheelMoved(MouseWheelEvent e)
	            {
	        		double scale = e.getPreciseWheelRotation();
	        		Point p = e.getPoint();
	        		canvas.zoom(scale, p);

	            }
	        });
	        
	        canvas.undoManager = new CanvasUndoManager();
			return canvas;
		  
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				// ignore close exception
			}
		}
		
		return null;
	}
	
}
