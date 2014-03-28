package GAIL.src.controller;

import GAIL.src.XMLHandler.StatementFileReader;
import GAIL.src.frame.ApplicationFrame;
import GAIL.src.frame.FileOpenerPopUp;
import GAIL.src.frame.MenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MenuBarController implements ActionListener {
	ApplicationController applicationController;
	StatementFileReader statementFileReader;
	ApplicationFrame appView;
	FileOpenerPopUp fileOpenerView;
	StatementController statementController;

	public MenuBarController(ApplicationController ac) {
		applicationController = ac;
		statementController = ac.getStatementController();
		ac.setMenuBar(new MenuBar(ac, this));
		appView = ac.getApplicationView();
	}

	public void actionPerformed(ActionEvent ae) {

		String action = ae.getActionCommand();

		if (action.equals("Open tutorial")){
			applicationController.getApplicationView().getJMenuBar().setVisible(false);
			//applicationController.getTutorialController().displayTutorial(true);
			applicationController.showTutorial();
		}
		if (action.equals("Open file")) {
			appView.showFileOpenView((statementController.getProblemText() != null));
		} else if (action.equals("Chat options")) {
			appView.showChatOptionsView();
		} else if (action.equals("Exit")) {
			applicationController.getApplicationView().dispose();
			System.exit(0);
		} else if (action.equals("Save session")){
			applicationController.saveAllOutput();
		}
	}
}
