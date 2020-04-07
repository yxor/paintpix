package pixyart;

import java.awt.Event;
import java.awt.event.*;
import javax.swing.*;


@SuppressWarnings("serial")
public class GlobalKeyBinder {
	
	
	public GlobalKeyBinder(JRootPane root) {
		KeyStroke undoKey = KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK);
		KeyStroke redoKey = KeyStroke.getKeyStroke(KeyEvent.VK_Y, Event.CTRL_MASK);
		
		root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(undoKey, "undo");
		root.getActionMap().put("undo", new AbstractAction() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				GlobalStateManager.getInstance().getCanvas().undo();
			}
		});
		root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(redoKey, "redo");
		root.getActionMap().put("redo", new AbstractAction() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				GlobalStateManager.getInstance().getCanvas().redo();
			}
		});
		
	}
}
