package pixyart;

import java.awt.Event;
import java.awt.EventQueue;
import java.awt.RenderingHints.Key;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

public class GlobalKeyBinder {
	
	
	public GlobalKeyBinder(JRootPane root) {
		KeyStroke undoKey = KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK);
		KeyStroke redoKey = KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK & Event.SHIFT_MASK);
		
		root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(undoKey, "undo");
		root.getActionMap().put("undo", new AbstractAction() {
			
			@Override
			public synchronized void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						GlobalStateManager.getInstance().getCanvas().undo();
					}
				});
			}
		});
		root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(redoKey, "redo");
		root.getActionMap().put("redo", new AbstractAction() {
			
			@Override
			public synchronized void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						GlobalStateManager.getInstance().getCanvas().redo();
					}
				});
				
			}
		});
		
	}
}
