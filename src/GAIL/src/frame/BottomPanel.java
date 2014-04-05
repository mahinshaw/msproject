package GAIL.src.frame;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.Style;

import ArgumentComparator.ComparatorTree;
import java.util.List;
import GAIL.src.controller.ApplicationController;

import java.util.StringTokenizer;

public class BottomPanel extends JPanel implements MouseListener {
	ApplicationController ac;
	ArgumentDisplay argumentDisplay;
    FeedbackDisplay feedbackDisplay;
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
		JPanel wdTextPanel = new JPanel();
		wdTextPanel.setLayout(new BorderLayout());
		wdTextPanel.setBorder(BorderFactory.createLineBorder(Color.blue, 3));
		JLabel wdLabel = new JLabel("    Workspace description");
		wdLabel.setOpaque(true);
		wdLabel.setPreferredSize(new Dimension(200, 19));
		refreshLabel = new JLabel(new ImageIcon(refreshPic));
		refreshLabel.addMouseListener(ac);
		refreshLabel.addMouseListener(this);
		refreshLabel.setName("REFRESH");
		refreshLabel.setToolTipText("Refresh");
		wdLabel.setBackground(Color.blue);
		wdLabel.setForeground(Color.white);
		wdTextPanel.setBackground(Color.blue);
		JPanel textPanelTop = new JPanel();
		textPanelTop.setLayout(new BorderLayout());
		textPanelTop.add(wdLabel, BorderLayout.LINE_START);
		textPanelTop.add(refreshLabel, BorderLayout.LINE_END);
		textPanelTop.setBackground(Color.blue);
		wdTextPanel.add(textPanelTop, BorderLayout.PAGE_START);
		wdTextPanel.add(argumentDisplay);

		chatPanel.setPreferredSize(new Dimension(chatWidth, userPanelHeight + 25));

		wdTextPanel.setPreferredSize(new Dimension(textWidth, userPanelHeight + 25));

        feedbackDisplay = new FeedbackDisplay(chatWidth, userPanelHeight +25);
		JLabel feedbackLabel = new JLabel("    Feedback window");
		feedbackLabel.setOpaque(true);
		feedbackLabel.setPreferredSize(new Dimension(200, 19));
		feedbackLabel.setBackground(Color.blue);
		feedbackLabel.setForeground(Color.white);

		JPanel feedbackPanel = new JPanel();
		feedbackPanel.setLayout(new BorderLayout());
		feedbackPanel.setBorder(BorderFactory.createLineBorder(Color.blue, 3));
		feedbackPanel.setBackground(Color.blue);
		//feedbackPanel.setPreferredSize(new Dimension(chatWidth, userPanelHeight + 25));

		feedbackPanel.add(feedbackLabel, BorderLayout.PAGE_START);
        JPanel blankPanel = new JPanel();
        blankPanel.setBackground(Color.white);
        feedbackPanel.add(feedbackDisplay);

		contentPanel.add(feedbackPanel);
		contentPanel.add(wdTextPanel);
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

    public void appendToFeedback(List<ComparatorTree> trees){
        feedbackDisplay.appendToTextArea(trees);
    }

	public void reset() {
		argumentDisplay.reset();
        feedbackDisplay.reset();
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
