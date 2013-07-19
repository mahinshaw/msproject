package GAIL.src.frame;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import GAIL.src.controller.ApplicationController;
import GAIL.src.controller.MenuBarController;

public class MenuBar extends JMenuBar {

	JMenu fileMenu;
	JMenuItem fileClose;
	JMenuItem fileOpen;
	JMenuItem chatOptions;
	JMenuItem exitItem;
	JMenuItem saveItem;
	//JMenuItem tutorial;
	JMenu helpMenu;
	JMenuItem aboutThisProgram;

	MenuBarController menuBarController;

	public MenuBar(ApplicationController applicationController, MenuBarController menuBarController) {

		this.menuBarController = menuBarController;

		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		fileOpen = new JMenuItem("Open file");
		fileOpen.setMnemonic(KeyEvent.VK_O);
		fileOpen.addActionListener(menuBarController);
		fileClose = new JMenuItem("Restart session");
		fileClose.setMnemonic(KeyEvent.VK_C);
		fileClose.addActionListener(menuBarController);
		/*tutorial= new JMenuItem("Open tutorial");
		tutorial.setMnemonic(KeyEvent.VK_T);
		tutorial.addActionListener(menuBarController);*/
		chatOptions = new JMenuItem("Chat options");
		chatOptions.setMnemonic(KeyEvent.VK_H);
		chatOptions.addActionListener(menuBarController);
		saveItem = new JMenuItem("Save session");
		saveItem.setMnemonic(KeyEvent.VK_A);
		saveItem.addActionListener(menuBarController);
		exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic(KeyEvent.VK_X);
		exitItem.addActionListener(menuBarController);
		fileMenu.add(fileOpen);
		fileMenu.add(fileClose);
		fileMenu.add(saveItem);
		fileMenu.add(chatOptions);
		//fileMenu.add(tutorial);
		fileMenu.add(exitItem);
		helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		add(fileMenu);
	}
}