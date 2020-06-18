package paintpix;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

@SuppressWarnings("serial")
public class ColorToggler extends JToolBar implements ActionListener {
	private MainController controller;
	private ColorButton primaryColorButton;
	private ColorButton secondaryColorButton;
	private JPanel primaryButtonContainer;
	private JPanel secondaryButtonContainer;
	private boolean primaryColorPicked;
	
	private static Color selectedBackgroundColor = new Color(37, 122, 253);
	private static Color defaultBackgroundColor;

	
	
	public ColorToggler(Color primaryColor, Color secondaryColor) {
		this.primaryColorButton = new ColorButton(primaryColor);
		this.primaryColorButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		this.primaryColorButton.addActionListener(this);
		
		this.secondaryColorButton = new ColorButton(secondaryColor);
		this.secondaryColorButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		this.secondaryColorButton.addActionListener(this);

		// select primary color by default
		this.primaryColorPicked = true;
		
		JLabel primaryLabel = new JLabel("Primary");
		primaryLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		JLabel secondaryLabel = new JLabel("Secondary");
		secondaryLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);


		this.primaryButtonContainer = new JPanel();
		this.primaryButtonContainer.setLayout(new BoxLayout(primaryButtonContainer, BoxLayout.Y_AXIS));
		this.primaryButtonContainer.add(primaryColorButton);
		this.primaryButtonContainer.add(primaryLabel);
		
		this.secondaryButtonContainer = new JPanel();
		this.secondaryButtonContainer.setLayout(new BoxLayout(secondaryButtonContainer, BoxLayout.Y_AXIS));
		this.secondaryButtonContainer.add(secondaryColorButton);
		this.secondaryButtonContainer.add(secondaryLabel);

		
		add(primaryButtonContainer);
		addSeparator();
		add(secondaryButtonContainer);
		setFloatable(false);
		defaultBackgroundColor = primaryButtonContainer.getBackground();
		revalidate();
	}
	

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	
	public void setColor(Color c)
	{
		if(this.primaryColorPicked)
			this.primaryColorButton.setSelectedColor(c);
		else
			this.secondaryColorButton.setSelectedColor(c);
		updateCanvas();
	}
	
	public void swapColors() {
		Color primary = this.primaryColorButton.getSelectedColor();
		Color secondary = this.secondaryColorButton.getSelectedColor();
		
		this.primaryColorButton.setSelectedColor(secondary);
		this.secondaryColorButton.setSelectedColor(primary);
		updateCanvas();
		repaint();
	}
	
	public void updateCanvas()
	{
		PixelCanvas canvas = controller.getCanvas();
		if(canvas == null)
			return;
		canvas.setPrimaryColor(this.primaryColorButton.getSelectedColor());
		canvas.setSecondaryColor(this.secondaryColorButton.getSelectedColor());
	}
	
	public void setController(MainController controller)
	{
		this.controller = controller;
	}


	@Override
	public void actionPerformed(ActionEvent e) {

		this.primaryColorPicked = e.getSource() == this.primaryColorButton;

		if(this.primaryColorPicked)
		{
			this.primaryColorButton.setBackground(selectedBackgroundColor);
			this.secondaryColorButton.setBackground(defaultBackgroundColor);
		}else {
			this.primaryColorButton.setBackground(defaultBackgroundColor);
			this.secondaryColorButton.setBackground(selectedBackgroundColor);
		}
		repaint();
	}
	
	
	
}
