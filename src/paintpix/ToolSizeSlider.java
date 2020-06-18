package paintpix;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class ToolSizeSlider extends JToolBar {
	private MainController controller;
	private JSlider slider;
	JLabel sliderLabel;
	int size;

	
	public ToolSizeSlider() {

		this.size = 2;
		sliderLabel = new JLabel(String.format("Size: %-2dpx", this.size));
		//primaryLabel.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		
		slider = new JSlider(2, 50, this.size);
		//slider.setAlignmentX(LEFT_ALIGNMENT);
		slider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				ToolSizeSlider.this.size = slider.getValue();
				ToolSizeSlider.this.controller.setSize(ToolSizeSlider.this.size);
				sliderLabel.setText(String.format("Size: %-2dpx", ToolSizeSlider.this.size));
			}
		});

		JPanel sliderContainer = new JPanel(new BorderLayout());

		sliderContainer.add(slider, BorderLayout.LINE_START);
		
		// limit the slider to 300px
		Dimension d = slider.getPreferredSize();
		d.width = 300;
		slider.setPreferredSize(d);
		
		addSeparator();
		add(sliderLabel);
		add(sliderContainer);

		setFloatable(false);
		this.setVisible(true);
		
	}
	
	

	

	public void setController(MainController controller)
	{
		this.controller = controller;
		this.size = controller.getSize();
		this.slider.setValue(this.size);
	}
	
	


	
	
}
