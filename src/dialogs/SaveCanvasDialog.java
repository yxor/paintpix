package dialogs;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.text.NumberFormat;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import managers.ImageFileManager;
import paintpix.PixelCanvas;

@SuppressWarnings("serial")
public class SaveCanvasDialog extends JDialog {
	public final static int APPROVE_OPTION = 1;
	public final static int CANCEL_OPTION = 0;
	
	private int width;
	private int height;
	private double scale;
	private String canvasSavePath;
	private int closeOperationOption = CANCEL_OPTION;
	JFormattedTextField heightInput;
	JFormattedTextField widthInput;
	JFormattedTextField scaleInput;
	
	public SaveCanvasDialog(JFrame parent, PixelCanvas canvas) {
		super(parent, "Save a Canvas", true);
		this.width = canvas.getImage().getWidth();
		this.height = canvas.getImage().getHeight();
		this.scale = 1.0F;
		
		JFormattedTextField savePath = new JFormattedTextField();
		savePath.setValue(canvas.getSavePath());
		savePath.setColumns(25);
		savePath.addPropertyChangeListener("value", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				canvasSavePath = (String) ((JFormattedTextField) e.getSource()).getValue();
			}
		});
		
		JButton browseButton = new JButton("Browse...");
		browseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify a file to save");   
				fileChooser.setAcceptAllFileFilterUsed(false);
				
				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG file", "png"));
				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JPEG file", "jpg", "jpeg"));
				int userSelection = fileChooser.showSaveDialog(null);
				if(userSelection == JFileChooser.APPROVE_OPTION) {
					String path = ImageFileManager.getSelectedFileWithExtension(fileChooser).getAbsolutePath();
					savePath.setValue(path);
					canvasSavePath = path;
				}
				
			}
		});
		
		widthInput = new JFormattedTextField(NumberFormat.getIntegerInstance());
		widthInput.setColumns(5);
		widthInput.setValue(canvas.getImage().getWidth());
		widthInput.addPropertyChangeListener("value", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				width = ((Number) ((JFormattedTextField) e.getSource()).getValue()).intValue();
				//modify scale
				scale = calculateScale(canvas.getImage().getWidth(), width);
				scaleInput.setValue(scale);
				//modify height
				height = (int) (canvas.getImage().getHeight() * scale);
				heightInput.setValue(height);
				
			}
		});
		
		heightInput = new JFormattedTextField(NumberFormat.getIntegerInstance());
		heightInput.setColumns(5);
		heightInput.setValue(canvas.getImage().getHeight());
		heightInput.addPropertyChangeListener("value", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				height = ((Number) ((JFormattedTextField) e.getSource()).getValue()).intValue();
				// modify width
				scale = calculateScale(canvas.getImage().getHeight(), height);
				scaleInput.setValue(scale);
				//modify height
				width = (int) (canvas.getImage().getWidth() * scale);
				widthInput.setValue(width);
			}
		});
		
		scaleInput = new JFormattedTextField(NumberFormat.getNumberInstance());
		scaleInput.setColumns(3);
		scaleInput.setValue(1.0F);
		scaleInput.addPropertyChangeListener("value", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				scale = ((Number) ((JFormattedTextField) e.getSource()).getValue()).doubleValue();
				// modify height and width
				width = (int) (canvas.getImage().getWidth() * scale);
				widthInput.setValue(width);
				
				height = (int) (canvas.getImage().getHeight() * scale);
				heightInput.setValue(height);
			}
		});
		
		
		
		JButton okButton  = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(validatePath(SaveCanvasDialog.this.canvasSavePath))
				{
					SaveCanvasDialog.this.closeOperationOption = APPROVE_OPTION;
					SaveCanvasDialog.this.dispose();
				}else {
					JOptionPane.showMessageDialog(SaveCanvasDialog.this, "Invalid Path.", "Saving Failed",
					        JOptionPane.ERROR_MESSAGE);
					SaveCanvasDialog.this.closeOperationOption = CANCEL_OPTION;
					SaveCanvasDialog.this.dispose();
				}
			}
		});
		
		JButton cancelButton  = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SaveCanvasDialog.this.dispose();
			}
		});
		

		
		JPanel widthPanel = new JPanel();
		widthPanel.add(new JLabel("Scaled Width:"));
		widthPanel.add(widthInput);
		
		JPanel heightPanel = new JPanel();
		heightPanel.add(new JLabel("Scaled Height:"));
		heightPanel.add(heightInput);
		
		JPanel scalePanel = new JPanel();
		scalePanel.add(new JLabel("Scale Ratio:"));
		scalePanel.add(scaleInput);
		

		JPanel pathPanel = new JPanel();
		pathPanel.add(new JLabel("Saving Path:"));
		pathPanel.add(savePath);
		pathPanel.add(browseButton);
		
		JPanel controls = new JPanel();
		controls.add(okButton);
		controls.add(cancelButton);
		
		JPanel settings = new JPanel();
		settings.add(scalePanel);
		settings.add(widthPanel);
		settings.add(heightPanel);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(pathPanel);
		mainPanel.add(settings);
		mainPanel.add(controls);

		
		this.getContentPane().add(mainPanel);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}
	
	public int showOpenDialog()
	{
		return this.closeOperationOption;
	}

	
	public int getChosenWidth()
	{
		return this.width;
	}
	
	public int getChosenHeight()
	{
		return this.height;
	}
	
	
	public String getChosenAbsolutePath()
	{
		return this.canvasSavePath;
	}
	
	private double calculateScale(int originalLength, int newLength)
	{
		return (double) newLength / originalLength;
	}
	
	private boolean validatePath(String path)
	{
	    try {
	        Paths.get(path);
	    } catch (InvalidPathException | NullPointerException ex) {
	        return false;
	    }
	    
	    File file = new File(path);
	    if (file.isDirectory())
	       return false;
	    if (file.exists()){
	        // maybe ask user if you want to rewrite, for now do nothing.
	    }
	    return true;
	}
}
