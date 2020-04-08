package pixyart;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

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
		
		JMenuItem saveAsMenuItem = new JMenuItem("Save As..");
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
				System.exit(0);
				
			}
		});
		
		fileMenu.add(openMenuItem);
		fileMenu.add(newMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.add(saveAsMenuItem);
		fileMenu.add(exitMenuItem);
		
		this.add(fileMenu);
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
