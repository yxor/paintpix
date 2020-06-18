package managers;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public final class ImageFileManager {
	private ImageFileManager() {} // hide the constructor
	
	public static String latestPath = null;
	
	public static BufferedImage open() 
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to open");   
		fileChooser.setAcceptAllFileFilterUsed(false);
		
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG file (Supports transparency)", "png"));
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JPEG file (No transparency)", "jpg", "jpeg"));
		 
		int userSelection = fileChooser.showOpenDialog(null);
		BufferedImage image = null;
		if (userSelection == JFileChooser.APPROVE_OPTION) {
		    File fileToOpen = fileChooser.getSelectedFile();
		    
		    try {
		    	ImageIO.setUseCache(false);
				image = ImageIO.read(fileToOpen);
				latestPath = fileToOpen.getAbsolutePath();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Failed Opening the Image.", "Opening Failed",
				        JOptionPane.ERROR_MESSAGE);

			}
		}
		return image;
	}
	
	public static void save(BufferedImage image, String absolutePath)
	{
		File fileToSave = new File(absolutePath);
		String name = fileToSave.getName();
	    String format = name.substring(1+name.lastIndexOf(".")).toLowerCase();
	    
	    try {
	    	ImageIO.setUseCache(false);
			ImageIO.write(image, format, fileToSave);
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Failed saving the Image.", "Saving Failed",
			        JOptionPane.ERROR_MESSAGE);

		}
	}
	
	
	public static String save(BufferedImage image)
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to save");   
		fileChooser.setAcceptAllFileFilterUsed(false);
		
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG file", "png"));
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JPEG file", "jpg", "jpeg"));
		 
		int userSelection = fileChooser.showSaveDialog(null);
		 
		if (userSelection == JFileChooser.APPROVE_OPTION) {
		    File fileToSave = getSelectedFileWithExtension(fileChooser);
		    String name = fileToSave.getName();
		    String format = name.substring(1+name.lastIndexOf(".")).toLowerCase();
		    
		    try {
		    	ImageIO.setUseCache(false);
				ImageIO.write(image, format, fileToSave);
				return fileToSave.getAbsolutePath();
				
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Failed saving the Image.", "Saving Failed",
				        JOptionPane.ERROR_MESSAGE);

			}
		}
		return null;
	}
	
	public static File getSelectedFileWithExtension(JFileChooser c) 
	{
	    File file = c.getSelectedFile();
	    if (c.getFileFilter() instanceof FileNameExtensionFilter) {
	        String[] exts = ((FileNameExtensionFilter)c.getFileFilter()).getExtensions();
	        String nameLower = file.getName().toLowerCase();
	        for (String ext : exts) { // check if it already has a valid extension
	            if (nameLower.endsWith('.' + ext.toLowerCase())) {
	                return file; // if yes, return as-is
	            }
	        }
	        // if not, append the first extension from the selected filter
	        file = new File(file.toString() + '.' + exts[0]);
	    }
	    return file;
	}
	
	public static BufferedImage resize(BufferedImage original, int newWidth, int newHeight)
	{
		BufferedImage resized = new BufferedImage(newWidth, newHeight, original.getType());
		Graphics2D g2d = resized.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawImage(original, 0, 0, newWidth, newHeight, 0, 0, original.getWidth(),
		    original.getHeight(), null);
		g2d.dispose();
		
		return resized;
	}

}
