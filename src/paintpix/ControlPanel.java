package paintpix;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class ControlPanel extends JToolBar {
	private MainController controller;
	
	
	public ControlPanel() {
		super("Controls", JToolBar.HORIZONTAL);
		
		ButtonListener listener = new ButtonListener();
		
		String[] buttonLabels = {"New", "Open", "Save", "Save As", "Undo", "Redo"};
		ImageIcon[] buttonIcons = {
				new ImageIcon("resources/new.png"),
				new ImageIcon("resources/open.png"),
				new ImageIcon("resources/save.png"),
				new ImageIcon("resources/save.png"),
				new ImageIcon("resources/undo.png"),
				new ImageIcon("resources/redo.png"),
				};
		
		JButton[] buttons = new JButton[buttonLabels.length];
		for (int i = 0; i < buttons.length; i++) {

			buttons[i] = new JButton(buttonIcons[i]);
			buttons[i].setSize(20, 20);
			buttons[i].setActionCommand(buttonLabels[i]);
			buttons[i].setToolTipText(buttonLabels[i]);
			buttons[i].addActionListener(listener);
			this.add(buttons[i]);
			this.addSeparator();
		}
		
		setFloatable(false);
		
	}

	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			switch(e.getActionCommand()) {
			case "New":
				controller.createNewCanvas();
				break;
			case "Open":
				controller.openCanvasFromFileSystem();
				break;	
			case "Save":
				if(controller.getCanvas() == null) return;
				controller.saveCanvas();
				break;
				
			case "Save As":
				if(controller.getCanvas() == null) return;
				controller.saveCanvasAs();
				break;
				
			case "Undo":
				if(controller.getCanvas() == null) return;
				controller.getCanvas().undo();
				break;
				
			case "Redo":
				if(controller.getCanvas() == null) return;
				controller.getCanvas().redo();
				break;
				
			}
		}
	}
	

	public void setController(MainController controller)
	{
		this.controller = controller;
	}
	
}
