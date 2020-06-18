package paintpix;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class ColorButton extends JButton {
	/**
	 * Button with a color as its background
	*/
    private Color current;

    public ColorButton(Color c) {
        setSelectedColor(c);
    }

    public Color getSelectedColor() {
        return current;
    }

    public void setSelectedColor(Color newColor) {

        if (newColor == null) return;

        this.current = newColor;
        setIcon(createIcon(current, 24, 24));
        repaint();
    }

    public static ImageIcon createIcon(Color main, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(main);
        graphics.setComposite(AlphaComposite.Src);
        graphics.fillRect(0, 0, width, height);
        graphics.setXORMode(Color.DARK_GRAY);
        graphics.drawRect(0, 0, width-1, height-1);
        image.flush();
        ImageIcon icon = new ImageIcon(image);
        return icon;
    }
}