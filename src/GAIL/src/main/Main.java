package GAIL.src.main;

import java.awt.Insets;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import GAIL.src.controller.ApplicationController;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new ApplicationController();
			}
		});
	}
}
