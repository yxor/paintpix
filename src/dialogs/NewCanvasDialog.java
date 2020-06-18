package dialogs;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dialogs.ColorChooserButton.ColorChangedListener;

@SuppressWarnings("serial")
public class NewCanvasDialog extends JDialog {
	public final static int APPROVE_OPTION = 1;
	public final static int CANCEL_OPTION = 0;
	
	private static int defaultWidth = 10;
	private static int defaultHeight = 10;
	private static Color defaultFillColor = new Color(0, 0, 0, 0); // transparent empty canvas
	
	private int width;
	private int height;
	private Color fillColor;
	private int closeOperationOption = CANCEL_OPTION;

	
	
	public NewCanvasDialog(JFrame parent) {
		super(parent, "Create a new Canvas", true);
		width = defaultWidth;
		height = defaultHeight;
		fillColor = defaultFillColor;
		
		JFormattedTextField widthInput = new JFormattedTextField(NumberFormat.getIntegerInstance());
		widthInput.setValue(width);
		widthInput.setColumns(5);
		widthInput.addPropertyChangeListener("value", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				NewCanvasDialog.this.width = ((Number) ((JFormattedTextField) e.getSource()).getValue()).intValue();
			}
		});
		
		JFormattedTextField heightInput = new JFormattedTextField(NumberFormat.getIntegerInstance());
		heightInput.setColumns(5);
		heightInput.setValue(height);
		heightInput.addPropertyChangeListener("value", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				NewCanvasDialog.this.height = ((Number) ((JFormattedTextField) e.getSource()).getValue()).intValue();
			}
		});
		
		ColorChooserButton colorChooser = new ColorChooserButton(defaultFillColor, "Choose a fill color");
		colorChooser.addColorChangedListener(new ColorChangedListener() {
		    @Override
		    public void colorChanged(Color newColor) {
		    	NewCanvasDialog.this.fillColor = newColor;
		    }
		});
		
		
		JButton okButton  = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultWidth = width;
				defaultHeight = height;
				defaultFillColor = fillColor;
				NewCanvasDialog.this.closeOperationOption = APPROVE_OPTION;
				NewCanvasDialog.this.dispose();
			}
		});
		
		JButton cancelButton  = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NewCanvasDialog.this.dispose();
			}
		});
		
		JPanel widthPanel = new JPanel();
		widthPanel.add(new JLabel("width:"));
		widthPanel.add(widthInput);
		
		JPanel heightPanel = new JPanel();
		heightPanel.add(new JLabel("height:"));
		heightPanel.add(heightInput);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(new JLabel("Fill Color:"));
		buttonPanel.add(colorChooser);
		
		JPanel controls = new JPanel();
		controls.add(okButton);
		controls.add(cancelButton);
		
		JPanel settings = new JPanel();
		settings.add(widthPanel);
		settings.add(heightPanel);
		settings.add(buttonPanel);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
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
	
	public Color getChosenFillColor()
	{
		return this.fillColor;
	}
	
}
