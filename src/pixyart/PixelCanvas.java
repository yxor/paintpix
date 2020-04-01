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
	private Color primaryColor;
	private Color secondaryColor;
	private Tool selectedTool;
	
	
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
		this.primaryColor = new Color(255, 0, 20, 100);
		this.secondaryColor = new Color(6, 0, 255, 100);

		
		// zooming in and out support TODO improve this
		MouseAdapter mouseAdapter = new MouseAdapter () {
			
        	public void mouseWheelMoved(MouseWheelEvent e)
            {
        		double scale = (e.getPreciseWheelRotation() < 0) ? 1.1F : 0.9F;
            	PixelCanvas.this.setScale(PixelCanvas.this.getScale() * scale);
            }
        };
        addMouseWheelListener(mouseAdapter);
	}
	



	@Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        
        // recalculate the Image dimensions
        this.width = (int) (this.pixels.getWidth() * this.scaleFactor) + 2;
        this.height = (int) (this.pixels.getHeight() * this.scaleFactor) + 2;
        this.setPreferredSize(new Dimension(width, height));
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
		int realX = (int) ((x - 1) / scaleFactor);
		int realY = (int) ((y - 1) / scaleFactor);
		
		Graphics2D g2d = (Graphics2D) this.pixels.getGraphics();
		
		
        Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) c.getAlpha() / 255f);
        
		g2d.setColor(c);
        g2d.setComposite(comp);
        g2d.drawLine(realX, realY, realX, realY);
        //g2d.fillRect(realX-1, realY-1, 2, 2);

		g2d.dispose();
	}
	
	
	
	// getters and setters
	
	
	public Tool getSelectedTool() {
		return selectedTool;
	}


	public void setSelectedTool(Tool selectedTool) {
		this.selectedTool = selectedTool;
        addMouseMotionListener(selectedTool);
        addMouseListener(selectedTool);
        
        this.selectedTool.setCanvas(this);
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
}
