package paintpix;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import managers.CanvasDatabaseObject;
import managers.DatabaseManager;

@SuppressWarnings("serial")
public class MainMenuBar extends JMenuBar{
	private MainController controller;
	
	
	public MainMenuBar() {
		super();
		JMenu fileMenu = new JMenu("File");
		
		// small hack to make the menu wider
		JMenuItem openMenuItem = new JMenuItem("Open       ");
		openMenuItem.setIcon(new ImageIcon("resources/open_s.png"));
		openMenuItem.addActionListener(new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.openCanvasFromFileSystem();
				
			}
		});

		JMenuItem newMenuItem = new JMenuItem("New");
		newMenuItem.setIcon(new ImageIcon("resources/new_s.png"));
		newMenuItem.addActionListener(new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.createNewCanvas();
				
			}
		});
		
		JMenuItem saveMenuItem = new JMenuItem("Save");
		saveMenuItem.setIcon(new ImageIcon("resources/save_s.png"));
		saveMenuItem.addActionListener(new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.saveCanvas();
				
			}
		});
		
		JMenuItem saveAsMenuItem = new JMenuItem("Save As...");
		saveAsMenuItem.setIcon(new ImageIcon("resources/save_s.png"));
		saveAsMenuItem.addActionListener(new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.saveCanvasAs();	
			}
		});
		
		
		
		JMenuItem closeMenuItem = new JMenuItem("Close");
		closeMenuItem.addActionListener(new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.closeCanvas();
			}
		});
		
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean closeWindow = controller.closeCanvas();
	        	if(!closeWindow)
	        		return;
	        	controller.getMainFrame().setVisible(false);
				controller.getMainFrame().dispose();
				
			}
		});
		
		// recently opened canvases menu item
		JMenu recentlyOpenedMenu = new JMenu("Recent files");
		recentlyOpenedMenu.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent e) {
				ArrayList<CanvasDatabaseObject> l = DatabaseManager.list();
				if(l == null || l.isEmpty()) // sanity check
					return;
				
				recentlyOpenedMenu.removeAll();
				for(CanvasDatabaseObject o: l) {
					JMenuItem item = new JMenuItem(String.format("%s\t%s", o.getName(), o.getDate()));
					item.addActionListener(new AbstractAction() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							byte[] canvasData = DatabaseManager.get(o.getId());
							PixelCanvas newCanvas = PixelCanvas.fromBytes(canvasData);
							controller.createCanvas(newCanvas);
						}
					});
					recentlyOpenedMenu.add(item);

				}
				
			}
			
			@Override
			public void menuDeselected(MenuEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void menuCanceled(MenuEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		

		
		fileMenu.add(openMenuItem);
		fileMenu.add(newMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(recentlyOpenedMenu);
		fileMenu.addSeparator();
		fileMenu.add(saveMenuItem);
		fileMenu.add(saveAsMenuItem);
		fileMenu.add(closeMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(exitMenuItem);

		
		JMenu editMenu = new JMenu("Edit");
		
		// small hack to make the menu wider
		JMenuItem undoMenuItem = new JMenuItem("Undo       ", KeyEvent.VK_U);
		undoMenuItem.setIcon(new ImageIcon("resources/undo_s.png"));
		undoMenuItem.addActionListener(new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(controller.getCanvas() == null)
					return;
				controller.getCanvas().undo();
				
			}
		});
		
		JMenuItem redoMenuItem = new JMenuItem("Redo", KeyEvent.VK_R);
		redoMenuItem.setIcon(new ImageIcon("resources/redo_s.png"));
		redoMenuItem.addActionListener(new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(controller.getCanvas() == null)
					return;
				controller.getCanvas().redo();
				
			}
		});

		editMenu.add(undoMenuItem);
		editMenu.addSeparator();
		editMenu.add(redoMenuItem);
		
		// hide unusable menu items
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
		
		fileMenu.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent e) {
				PixelCanvas canvas = controller.getCanvas();
				boolean doesCanvasExist = canvas != null;
				saveMenuItem.setEnabled(doesCanvasExist);
				saveAsMenuItem.setEnabled(doesCanvasExist);
				closeMenuItem.setEnabled(doesCanvasExist);
				
				ArrayList<CanvasDatabaseObject> l = DatabaseManager.list();
				if(l == null || l.isEmpty()) {
					recentlyOpenedMenu.setEnabled(false);
					return;
				}
				recentlyOpenedMenu.setEnabled(true);
				
				
					
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
