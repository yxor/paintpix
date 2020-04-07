package pixyart;

import java.awt.Event;
import java.awt.event.*;
import javax.swing.*;


@SuppressWarnings("serial")
public class GlobalKeyBinder {
	private MainController controller;
	

	public GlobalKeyBinder(JRootPane root) {
		KeyStroke undoKey = KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK);
		KeyStroke redoKey = KeyStroke.getKeyStroke(KeyEvent.VK_Y, Event.CTRL_MASK);
		
		root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(undoKey, "undo");
		root.getActionMap().put("undo", new AbstractAction() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				GlobalKeyBinder.this.controller.getCanvas().undo();
			}
		});
		root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(redoKey, "redo");
		root.getActionMap().put("redo", new AbstractAction() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				GlobalKeyBinder.this.controller.getCanvas().redo();
			}
		});
		
	}
	
	public void setController(MainController controller)
	{
		this.controller = controller;
	}
	
}
