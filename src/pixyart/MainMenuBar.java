package pixyart;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

@SuppressWarnings("serial")
public class MainMenuBar extends JMenuBar{
	private MainController controller;
	
	
	public MainMenuBar() {
		super();
		JMenu fileMenu = new JMenu("File");
		
		JMenuItem openMenuItem = new JMenuItem("Open");
		openMenuItem.addActionListener(new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.openCanvasFromFileSystem();
				
			}
		});

		JMenuItem newMenuItem = new JMenuItem("New");
		newMenuItem.addActionListener(new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.openNewCanvas();
				
			}
		});
		
		JMenuItem saveMenuItem = new JMenuItem("Save");
		saveMenuItem.addActionListener(new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.saveCanvas();
				
			}
		});
		
		JMenuItem saveAsMenuItem = new JMenuItem("Save As...");
		saveAsMenuItem.addActionListener(new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.saveCanvasAs();	
			}
		});
		
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.getMainFrame().dispose();
				
			}
		});
		
		fileMenu.add(openMenuItem);
		fileMenu.add(newMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.add(saveAsMenuItem);
		fileMenu.add(exitMenuItem);
		
		JMenu editMenu = new JMenu("Edit");
		JMenuItem undoMenuItem = new JMenuItem("Undo", KeyEvent.VK_U);
		undoMenuItem.addActionListener(new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(controller.getCanvas() == null)
					return;
				controller.getCanvas().undo();
				
			}
		});
		
		JMenuItem redoMenuItem = new JMenuItem("Redo", KeyEvent.VK_R);
		redoMenuItem.addActionListener(new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(controller.getCanvas() == null)
					return;
				controller.getCanvas().redo();
				
			}
		});

		editMenu.add(undoMenuItem);
		editMenu.add(redoMenuItem);
		editMenu.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent e) {
				PixelCanvas canvas = controller.getCanvas();
				if(canvas == null) {
					undoMenuItem.setEnabled(false);
					redoMenuItem.setEnabled(false);
					return;
				}
					
				boolean canUndo = canvas.canUndo();
				boolean canRedo = canvas.canRedo();
				
				undoMenuItem.setEnabled(canUndo);
				redoMenuItem.setEnabled(canRedo);
			}
			
			@Override
			public void menuDeselected(MenuEvent e) {}
			
			@Override
			public void menuCanceled(MenuEvent e) {}
		});
		
		this.add(fileMenu);
		this.add(editMenu);
		this.revalidate();
	}
	
	public MainMenuBar(MainController controller)
	{
		this();
		this.controller = controller;
	}

	
	public void setController(MainController controller)
	{
		this.controller = controller;
	}
}
