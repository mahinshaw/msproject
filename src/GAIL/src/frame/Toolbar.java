package GAIL.src.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import GAIL.src.controller.ApplicationController;

public class Toolbar implements MouseListener {

	JInternalFrame panel;
	JLabel addHypLabel;
	JLabel addAndLabel;
	JLabel clearLabel;
	JLabel submitLabel;
	JLabel tutorialLabel;
	Color bgColor;

	public Toolbar(final ApplicationController ac) {
		bgColor = Color.LIGHT_GRAY;
		BufferedImage aImg = null;
		BufferedImage hImg = null;
		BufferedImage cImg = null;
		BufferedImage tImg = null;
		BufferedImage sImg = null;
		try {
			aImg = ImageIO.read(new File("src/GAIL/image/addand.png"));
			hImg = ImageIO.read(new File("src/GAIL/image/addhypothesis.png"));
			cImg = ImageIO.read(new File("src/GAIL/image/clearworkspace.png"));
			sImg = ImageIO.read(new File("src/GAIL/image/submit.png"));
			tImg = ImageIO.read(new File("src/GAIL/image/tutorial.png"));
		} catch (IOException de) {
		}

		panel = new JInternalFrame();
		panel.setResizable(false);
		panel.setLayer(JLayeredPane.PALETTE_LAYER);
		panel.setClosable(false);
		panel.setBackground(bgColor);
		panel.setLayout(new BoxLayout(panel.getContentPane(), BoxLayout.X_AXIS));
		javax.swing.plaf.InternalFrameUI ifu = panel.getUI();
		((javax.swing.plaf.basic.BasicInternalFrameUI) ifu).setNorthPane(null);
		clearLabel = new JLabel(new ImageIcon(cImg));
		clearLabel.setName("CLEAR_WORKSPACE");
		clearLabel.addMouseListener(ac);
		clearLabel.addMouseListener(this);
		clearLabel.setToolTipText("Clear the workspace");
		clearLabel.setBorder(BorderFactory.createLineBorder(bgColor, 1));

		addAndLabel = new JLabel(new ImageIcon(aImg));
		addAndLabel.setName("ADD_AND");
		addAndLabel.addMouseListener(ac.getConjunctionController());
		addAndLabel.addMouseListener(this);
		addAndLabel.setToolTipText("Add an AND");
		addAndLabel.setBorder(BorderFactory.createLineBorder(bgColor, 1));

        /*
		addHypLabel = new JLabel(new ImageIcon(hImg));
		addHypLabel.setName("ADD_HYP");
		addHypLabel.addMouseListener(ac.getStatementController());
		addHypLabel.addMouseListener(this);
		addHypLabel.setToolTipText("Create a hypothesis");
		addHypLabel.setBorder(BorderFactory.createLineBorder(bgColor, 1));    */

		tutorialLabel = new JLabel(new ImageIcon(tImg));
		tutorialLabel.setName("TUTORIAL");
        tutorialLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ac.showTutorial();
            }
        });
		tutorialLabel.addMouseListener(this);
		tutorialLabel.setToolTipText("Display tutorial");
		tutorialLabel.setBorder(BorderFactory.createLineBorder(bgColor, 1));

		submitLabel = new JLabel(new ImageIcon(sImg));
		submitLabel.setName("SUBMIT");
		submitLabel.addMouseListener(ac);
		submitLabel.addMouseListener(this);
		submitLabel.setToolTipText("Submit argument");
		submitLabel.setBorder(BorderFactory.createLineBorder(bgColor, 1));

		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		panel.add(Box.createRigidArea(new Dimension(6, 0)));
		panel.add(clearLabel);
		panel.add(Box.createRigidArea(new Dimension(6, 0)));
		panel.add(addAndLabel);
		panel.add(Box.createRigidArea(new Dimension(3, 0)));
		//panel.add(addHypLabel);
		panel.add(Box.createRigidArea(new Dimension(3, 0)));
		panel.add(tutorialLabel);
		panel.add(Box.createRigidArea(new Dimension(3, 0)));
		panel.add(submitLabel);
		panel.add(Box.createRigidArea(new Dimension(3, 0)));
		panel.setSize(panel.getPreferredSize());
		panel.setVisible(true);
	}

	public JInternalFrame getPanel() {
		return panel;
	}

	public void setLocation(int x, int y) {
		panel.setLocation(x, y);
		// panel.setLocation(400,400);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JLabel l = (JLabel) e.getSource();
		if (l.getName().equals("TUTORIAL")) {
			l.setBorder(BorderFactory.createLineBorder(bgColor, 1));
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		JLabel l = (JLabel) e.getSource();
		l.setBorder(BorderFactory.createLineBorder(Color.black, 1));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		JLabel l = (JLabel) e.getSource();
		l.setBorder(BorderFactory.createLineBorder(bgColor, 1));

	}

	@Override
	public void mousePressed(MouseEvent e) {
		JLabel l = (JLabel) e.getSource();
		l.setBorder(BorderFactory.createLineBorder(Color.white, 1));

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		JLabel l = (JLabel) e.getSource();
		l.setBorder(BorderFactory.createLineBorder(Color.black, 1));
	}
}
