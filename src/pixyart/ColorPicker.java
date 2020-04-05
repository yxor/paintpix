package pixyart;

import java.awt.Color;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class ColorPicker extends JColorChooser{
	private class Listener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			if (GlobalStateManager.getInstance().getCanvas() == null)
				return;
			GlobalStateManager.getInstance().getCanvas().setPrimaryColor(ColorPicker.this.getColor());
		}
		
	}
	
	ColorPicker(Color initialColor){
		super(initialColor);
		this.setPreviewPanel(new JPanel());
		
		// removing Panel except swatches and RGB
        for(AbstractColorChooserPanel p: this.getChooserPanels())
        {
            String displayName = p.getDisplayName();
            
            switch (displayName) {
                case "HSV":
                case "HSL":
                case "CMYK":
                    this.removeChooserPanel(p);
                    break;
            }
        }
        
	    this.getSelectionModel().addChangeListener(new Listener()); 
	    this.setBorder(BorderFactory.createTitledBorder(
            "Color Picker")); 
	}
	
}
