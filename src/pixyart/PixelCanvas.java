package pixyart;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

@SuppressWarnings("serial")
public class PixelCanvas extends JComponent{
	private BufferedImage pixels; 
	private double scaleFactor;
	private int width, height;
	
	
	/**
	 * Constructor that creates an empty canvas.
	 * 
	 * @param width Canvas width.
	 * @param height Canvas height.
	 */
	public PixelCanvas(int width, int height) {
		this.pixels = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);
		this.scaleFactor = 1.0F;
		this.width = width;
		this.height = height;
		this.setSize(width + 2, height + 2); // two pixels for the border
		
		MouseAdapter mouseAdapter = new MouseAdapter () {
			private Color c = null;
			
            public void mousePressed(MouseEvent e)
            {
            	e.consume();
            	Point coords = e.getPoint();
            	c = (e.getButton() == MouseEvent.BUTTON1) ? new Color(0, 0, 0, 255) : new Color(255, 255, 255, 255);
            	PixelCanvas.this.drawPixel( coords.x, coords.y, c);
            	repaint();
            }

            public void mouseDragged(MouseEvent e)
            {
            	Point coords = e.getPoint();
            	PixelCanvas.this.drawPixel( coords.x, coords.y, c);

            	repaint();
            }

            public void mouseReleased(MouseEvent e)
            {
            	c = null;
            	repaint();
            }
            
        	public void mouseWheelMoved(MouseWheelEvent e)
            {
        		double scale = (e.getPreciseWheelRotation() < 0) ? 1.1F : 0.9F;
            	PixelCanvas.this.setScale(PixelCanvas.this.getScale() * scale);
            }
        };
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        addMouseWheelListener(mouseAdapter);
	}
	
	/**
	 * Constructor that creates an empty canvas.
	 * 
	 * @param width Canvas width.
	 * @param height Canvas height.
	 */
	public PixelCanvas(BufferedImage image) {
		this.pixels = image;
		this.scaleFactor = 1.0F;
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.setSize(width + 2, height + 2); // two pixels for the border
	}
	
	
	
	@Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        
        // recalculate the Image dimensions
        this.width = (int) (this.pixels.getWidth() * this.scaleFactor) + 2;
        this.height = (int) (this.pixels.getHeight() * this.scaleFactor) + 2;
        this.setPreferredSize(new Dimension(width, height));
        this.revalidate();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                //RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
                //RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.drawImage(this.pixels, 1, 1, width-2, height-2, null);
        
        // draw one pixel wide border around the canvas
        g2d.drawRect(0, 0, width-1, height-1);
    }
	
	public void drawPixel(int x, int y, Color c)
	{
		int realX = (int) ((x - 1) / scaleFactor);
		int realY = (int) ((y - 1) / scaleFactor);
		
		Graphics2D g2d = (Graphics2D) this.pixels.getGraphics();
		g2d.setColor(c);
		g2d.drawLine(realX, realY, realX, realY);
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
}
