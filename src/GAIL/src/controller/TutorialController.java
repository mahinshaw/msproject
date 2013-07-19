package GAIL.src.controller;

import GAIL.src.frame.TutorialDialog;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class TutorialController implements HyperlinkListener {
	private final static String TUTORIAL_PATH = "text/input/tutorial/";
	private final static String INIT_FILE = "1.html";
	private final static String FILE_NOT_FOUND_HTML =
		"<html><body><h1 align=\"center\">Could not load page</h1></body></html>";

	private TutorialDialog view;

	public TutorialController(final TutorialDialog view) {
		this.view = view.withController(this);
	}

	public void init() {
		loadHTML(INIT_FILE);
	}

	private void loadHTML(String fileName) {
		try {
			FileInputStream fis = new FileInputStream(TUTORIAL_PATH + fileName);
			Scanner sc = new Scanner(fis);
			StringBuilder sb = new StringBuilder(100);
			while(sc.hasNextLine()) {
				sb.append(sc.nextLine());
			}
			view.setTutorialText(sb.toString());
		} catch(FileNotFoundException e) {
			e.printStackTrace();
			view.setTutorialText(FILE_NOT_FOUND_HTML);
		}
	}

	private void loadVideo(String fileName) {
		try {
			Desktop.getDesktop().open(new File(fileName));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(
				view,
				e.getLocalizedMessage(),
				"File Not Found",
				JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(
				view,
				e.getLocalizedMessage(),
				"File Not Found",
				JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private boolean isAVideo(String fileName) {
		return fileName.endsWith(".mpg")
		       || fileName.endsWith(".avi")
		       || fileName.endsWith(".wmv")
		       || fileName.endsWith(".mp4");
	}

	@Override
	public void hyperlinkUpdate(HyperlinkEvent event) {
		if (event.getEventType() != HyperlinkEvent.EventType.ACTIVATED) {
			return;
		}

		String href = event.getDescription();

		if(isAVideo(href)) {
			loadVideo(href);
		} else if(href.endsWith(".html")) {
			loadHTML(href);
		} else if(href.equals("javascript:close();")) {
			view.setVisible(false);
		} else {
			throw new UnsupportedOperationException("Cannot support this type of link: " + href);
		}
	}
}
