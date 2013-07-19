package GAIL.src.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import GAIL.src.controller.ChatController;

public class ChatOptionsPopUp extends JDialog {
	JPanel panel;
	JRadioButton enableButton;
	JRadioButton disableButton;
	JButton saveButton;
	JButton cancelButton;
	JTextField ipField;
	JTextField portField;
	JLabel enabledLabel;
	JLabel portLabel;
	JLabel ipLabel;
	final int WIDTH = 250;
	final int HEIGHT = 125;
	ChatController chatController;
	ButtonListener buttonListener;

	public ChatOptionsPopUp(ChatController chatController, ApplicationFrame a) {
		super(a, "Chat setup", true);
		buttonListener = new ButtonListener();
		this.chatController = chatController;
		panel = new JPanel(new GridLayout(4, 2));
		enableButton = new JRadioButton("Enabled", false);
		disableButton = new JRadioButton("Disabled", true);
		saveButton = new JButton("Apply settings");
		cancelButton = new JButton("Cancel");
		ipField = new JTextField(8);
		ipField.setText("10.80.76.183");
		portField = new JTextField(8);
		portField.setText("777");
		portLabel = new JLabel("  Port address:");
		ipLabel = new JLabel("  Host IP Address:");
		panel.add(portLabel);
		panel.add(portField);
		panel.add(ipLabel);
		panel.add(ipField);
		panel.add(enableButton);
		panel.add(disableButton);
		panel.add(saveButton);
		panel.add(cancelButton);
		enableButton.addActionListener(buttonListener);
		disableButton.addActionListener(buttonListener);
		saveButton.addActionListener(buttonListener);
		cancelButton.addActionListener(buttonListener);
		ipField.setEnabled(false);
		portField.setEnabled(false);
		setSize(new Dimension(WIDTH, HEIGHT));
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(false);
		setTitle("Chat options");
		add(panel);
		setContentPane(panel);
	}

	boolean isChatEnabled;

	class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (event.getSource().equals(enableButton)) {
				isChatEnabled = true;
				ipField.setEnabled(true);
				portField.setEnabled(true);
				enableButton.setSelected(true);
				disableButton.setSelected(false);
			} else if (event.getSource().equals(disableButton)) {
				isChatEnabled = false;
				ipField.setEnabled(false);
				portField.setEnabled(false);
				enableButton.setSelected(false);
				disableButton.setSelected(true);
			} else if (event.getSource().equals(saveButton)) {
				if (isChatEnabled) {
					chatController.establishConnection(Integer.parseInt(portField.getText()),
							ipField.getText());
				} else {
					chatController.disconnect();
				}
				dispose();
			} else if (event.getSource().equals(cancelButton)) {
				dispose();
			}
		}
	}
}