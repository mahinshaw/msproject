package GAIL.src.frame;

import GAIL.src.controller.TutorialController;

import javax.swing.*;
import java.awt.*;

public class TutorialDialog extends JDialog {
	private final static Dimension TUTORIAL_DIMENSION = new Dimension(800,600);

	private JTextPane textPane;
	private TutorialController controller;

	public TutorialDialog(final JFrame frame) {
		super(frame, true);

		textPane = new JTextPane();
		textPane.setContentType("text/html");
		textPane.setEditable(false);
		textPane.setFocusable(false);

		JScrollPane rightPane = new JScrollPane(textPane);
		getContentPane().add(rightPane);

		setMinimumSize(TUTORIAL_DIMENSION);
		setPreferredSize(TUTORIAL_DIMENSION);
		setLocationRelativeTo(frame);
	}

	public TutorialDialog withController(final TutorialController controller) {
		if(this.controller != null) {
			textPane.removeHyperlinkListener(controller);
		}

		this.controller = controller;
		textPane.addHyperlinkListener(controller);
		return this;
	}

	public void setTutorialText(String text) {
		textPane.setText(text);
		textPane.setCaretPosition(0);
	}


}
