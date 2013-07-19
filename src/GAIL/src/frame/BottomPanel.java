package GAIL.src.frame;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import GAIL.src.controller.ApplicationController;

import java.util.StringTokenizer;

public class BottomPanel extends JPanel implements MouseListener {
	ApplicationController ac;
	ArgumentDisplay argumentDisplay;
	ChatPanel chatPanel;
	JLabel refreshLabel;
	BufferedImage refreshPic;
	BufferedImage refreshPicRollOver;

	public BottomPanel(ApplicationController ac) {// 1003 new width
		try {
			refreshPic = ImageIO.read(new File("src/GAIL/image/refresh1.png"));
			refreshPicRollOver = ImageIO.read(new File("src/GAIL/image/refresh12.png"));
		} catch (IOException ignored) {}

		this.ac = ac;
		int textWidth = 540, chatWidth = 385,
			paneWidth = 100, userPanelHeight = 210;
		setLayout(new GridLayout(0, 1));
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		chatPanel = ac.getChatController().getChatPanel();
		argumentDisplay = new ArgumentDisplay(paneWidth, userPanelHeight);
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new BorderLayout());
		textPanel.setBorder(BorderFactory.createLineBorder(Color.blue, 3));
		JLabel label = new JLabel("    Workspace description");
		label.setOpaque(true);
		label.setPreferredSize(new Dimension(200, 19));
		refreshLabel = new JLabel(new ImageIcon(refreshPic));
		refreshLabel.addMouseListener(ac);
		refreshLabel.addMouseListener(this);
		refreshLabel.setName("REFRESH");
		refreshLabel.setToolTipText("Refresh");
		label.setBackground(Color.blue);
		label.setForeground(Color.white);
		textPanel.setBackground(Color.blue);
		JPanel textPanelTop = new JPanel();
		textPanelTop.setLayout(new BorderLayout());
		textPanelTop.add(label, BorderLayout.LINE_START);
		textPanelTop.add(refreshLabel, BorderLayout.LINE_END);
		textPanelTop.setBackground(Color.blue);
		textPanel.add(textPanelTop, BorderLayout.PAGE_START);
		textPanel.add(argumentDisplay);

		chatPanel.setPreferredSize(new Dimension(chatWidth, userPanelHeight + 25));

		textPanel.setPreferredSize(new Dimension(textWidth, userPanelHeight + 25));

		JLabel pedigreeLabel = new JLabel("    Pedigree window");
		pedigreeLabel.setOpaque(true);
		pedigreeLabel.setPreferredSize(new Dimension(200, 19));
		pedigreeLabel.setBackground(Color.blue);
		pedigreeLabel.setForeground(Color.white);

		JPanel pedigreePanel = new JPanel();
		pedigreePanel.setLayout(new BorderLayout());
		pedigreePanel.setBorder(BorderFactory.createLineBorder(Color.blue, 3));
		pedigreePanel.setBackground(Color.blue);
		pedigreePanel.setPreferredSize(new Dimension(chatWidth, userPanelHeight + 25));

		pedigreePanel.add(pedigreeLabel, BorderLayout.PAGE_START);
        JPanel blankPanel = new JPanel();
        blankPanel.setBackground(Color.white);
        pedigreePanel.add(blankPanel);
		//pedigreePanel.add(new PrototypePedigreeFamilyView());

		contentPanel.add(pedigreePanel);
		contentPanel.add(textPanel);
		contentPanel.add(Box.createHorizontalGlue());
		contentPanel.add(chatPanel);

		add(contentPanel);
		contentPanel.addMouseListener(this);
	}

	StringTokenizer stringTokenizer;
	String token;
	boolean isRestOfStringItalic;
	boolean isCheckingNextWordForConjunction;
	Style prevStyle;

	public void appendToTextArea(String[] s) {
		argumentDisplay.appendToTextArea(s);
	}

	public void reset() {
		argumentDisplay.reset();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getX() > getWidth() - 10 && arg0.getY() > getHeight() - 10) {
			if (ac.getApplicationView().getJMenuBar().isVisible()) {
				ac.getApplicationView().getJMenuBar().setVisible(false);
			} else {
				ac.getApplicationView().getJMenuBar().setVisible(true);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		refreshLabel.setIcon(new ImageIcon(refreshPicRollOver));
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		refreshLabel.setIcon(new ImageIcon(refreshPic));
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}
