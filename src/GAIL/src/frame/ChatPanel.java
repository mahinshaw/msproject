package GAIL.src.frame;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import GAIL.src.controller.ChatController;

public class ChatPanel extends JPanel {

	private JTextField textField;
	private JTextArea textArea;
	private JPanel topPanel;
	private JScrollPane scrollPane;

	public ChatPanel(final ChatController chatController) {
		BufferedImage chatIcon = null;
		try {
			chatIcon = ImageIO.read(new File("src/GAIL/image/chaticon.png"));
		} catch (IOException e) { }
		JLabel chatIconLabel = new JLabel(new ImageIcon(chatIcon));
		setLayout(new BorderLayout());
		setBackground(Color.BLUE);
		setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.setBackground(Color.WHITE);
		textArea.setFocusable(false);
		textField = new JTextField();
		textField.setBackground(Color.WHITE);
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String s = textField.getText();
				if (s.isEmpty()) {
					return;
				}
				textArea.append(" You: " + s + "\n");
				chatController.sendMessageToHost(s + "\n");
				textField.setText("");
			}
		});

		scrollPane = new JScrollPane(textArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		JLabel label = new JLabel("    Chat");
		label.setBackground(Color.BLUE);
		label.setForeground(Color.WHITE);
		
		topPanel = new JPanel(new BorderLayout());
		topPanel.add(label, BorderLayout.WEST);
		topPanel.setBackground(Color.BLUE);
		chatIconLabel.setBackground(Color.WHITE);
		JPanel lowerPanel = new JPanel();
		lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.X_AXIS));
		lowerPanel.add(Box.createRigidArea(new Dimension(2,0)));
		lowerPanel.add(chatIconLabel);
		lowerPanel.add(textField);
		add(topPanel, BorderLayout.PAGE_START);
		add(scrollPane, BorderLayout.CENTER);
		add(lowerPanel, BorderLayout.SOUTH);
	}

	public String getOutputString() {
		return textField.getText();
	}

	public void appendToTextArea(String string) {
		textArea.append(string + "\n");
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}

	final String CLEAR_DIALOG = "CLEAR_DIALOG";

	public void clearText() {
		textArea.setText("");
		textField.setText("");
	}
}