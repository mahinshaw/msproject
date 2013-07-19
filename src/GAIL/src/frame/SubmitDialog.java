package GAIL.src.frame;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import GAIL.src.controller.ApplicationController;

public class SubmitDialog extends JDialog {

	// JButton submitButton;
	// JButton reviseButton;
	public static int reviseCount;
	ApplicationController ac;
	static JButton submitButton;
	static JButton reviseButton;

	public SubmitDialog(final ApplicationController ac) {
		super(ac.getApplicationView(), "Submit argument", true);
		this.ac = ac;
		ac.appendToSessionLog("OPENED SUBMIT ARGUMENT WINDOW");
		reviseCount++;
		JPanel buttonPanel = new JPanel();
		reviseButton = new JButton("Revise");
		buttonPanel.add(reviseButton);
		submitButton = new JButton("Submit");
		submitButton.setEnabled(true);
		submitButton.addActionListener(ac);
		if (reviseCount > 2 || ac.isEnabled) {
			ac.isEnabled = true;
			submitButton.setEnabled(true);
		}
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reviseCount = 0;
				ac.saveAllOutput();
				dispose();
				ac.isEnabled = false;
				ac.incrementProblem();
				submitButton.setEnabled(false);
			}
		});
		reviseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				ac.isEnabled = false;
			}
		});
		buttonPanel.add(submitButton);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JLabel l = new JLabel("This is the argument you have constructed: ");
		JPanel labelPanel = new JPanel();
		l.setAlignmentX(CENTER_ALIGNMENT);
		labelPanel.add(l);
		panel.add(labelPanel);
		ArgumentDisplay a = new ArgumentDisplay(400, 600);
		a.appendToTextArea(ac.getEdgeController().getUserDisplayOutput());
		panel.add(a);
		if (ac.getProblemCount() >= 3) {
			l = new JLabel(
					"Would you like to submit this argument? This will conclude the session.");
		} else {
			l = new JLabel(
					"Would you like to submit this argument and continue to the next problem?");
		}
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		panel.add(l);
		panel.add(buttonPanel);
		add(panel);
		setLocation(550, 180);
		setPreferredSize(new Dimension(700, 700));
		pack();
		setVisible(true);
	}

	public static void enableSumbitButton() {
		submitButton.setEnabled(true);
		submitButton.repaint();
	}

	public static void disableSumbitButton() {
		submitButton.setEnabled(false);
		submitButton.repaint();
	}
}
