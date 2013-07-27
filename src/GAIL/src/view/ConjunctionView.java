package GAIL.src.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GAIL.src.view.AnchorSet.ActivePoint;
import GAIL.src.view.AnchorSet.Anchor;
import GAIL.src.controller.ApplicationController;
import GAIL.src.model.Conjunction;

public class ConjunctionView extends NodeView implements MouseListener, MouseMotionListener {

	Color mainColor = Color.orange;

	AnchorSet anchors;
	// distance from conjunction to target statement
	public static final int Y_OFFSET = 120;

	// distance between conjunctions with same target statement
	public static final int X_OFFSET_1 = 0;
	public static final int X_OFFSET_2 = -75;
	public static final int X_OFFSET_3 = 75;
	String ID;
	Conjunction conjunction;
	JPanel contentPanel;
	JLabel removeLabel;
	JLabel questionLabel;
	JLabel textLabel;

	public ConjunctionView(Conjunction conjunction, ApplicationController ac, int x, int y, String ID) {
		this.ID = ID;
		this.conjunction = conjunction;
		conjunction.setView(this);

		BufferedImage xImg = null;
		BufferedImage qImg = null;
		try {
			xImg = ImageIO.read(new File("src/GAIL/image/xwhite.png"));
			qImg = ImageIO.read(new File("src/GAIL/image/qwhite.png"));
		} catch (IOException de) {
		}
		textLabel = new JLabel("  " + ID + "  ");
		textLabel.setName(ID);
		textLabel.addMouseMotionListener(this);
		textLabel.addMouseListener(this);
		textLabel.setForeground(Color.WHITE);
		textLabel.setAlignmentX(0);
		questionLabel = new JLabel(new ImageIcon(qImg));
		questionLabel.setName("INFO " + conjunction.getID());
		questionLabel.addMouseListener(ac.getEdgeController());
		questionLabel.setForeground(Color.WHITE);
		removeLabel = new JLabel(new ImageIcon(xImg));
		removeLabel.setName("REMOVE");
		removeLabel.addMouseListener(ac.getConjunctionController());
		removeLabel.setForeground(Color.WHITE);
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		topPanel.setBackground(mainColor);
		topPanel.add(Box.createHorizontalGlue());
		if (ColorPalette.isQVisible) {
			topPanel.add(questionLabel);
		}
		topPanel.add(Box.createRigidArea(new Dimension(4, 10)));
		topPanel.add(removeLabel);
		topPanel.add(Box.createRigidArea(new Dimension(3, 10)));

		contentPanel = new JPanel();
		contentPanel.setBackground(mainColor);
		contentPanel.addMouseMotionListener(this);
		contentPanel.addMouseListener(this);

		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		contentPanel.add(Box.createRigidArea(new Dimension(0, 2)));
		contentPanel.add(topPanel);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 1)));
		JPanel p = new JPanel();
		p.setBackground(mainColor);
		p.add(textLabel);
		contentPanel.add(p);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 2)));
		contentPanel.setSize(contentPanel.getPreferredSize());
		contentPanel.setLocation(x, y);//x - 665
		anchors = new AnchorSet(conjunction, contentPanel.getX(), contentPanel.getY(),
				contentPanel.getWidth(), contentPanel.getHeight());
	}

	public JLabel getXLabel() {
		return removeLabel;
	}

	public JLabel getQLabel() {
		return questionLabel;
	}

	// returns an array list with this conjunction's connecting point
	ArrayList<Point> getConnectingPoints() {
		ArrayList<Point> results = new ArrayList<Point>();
		ArrayList<AnchorSet.ActivePoint> a = anchors.getActivePoints();
		for (ActivePoint ab : a) {
			results.add(ab.getSourcePoint());
			results.add(ab.getTargetPoint());
		}
		return results;
	}

	public String getID() {
		return ID;
	}

	public JPanel getPanel() {
		return contentPanel;
	}

	public void setPanelLocation(int x, int y) {
		contentPanel.setLocation(x, y);
		anchors.setLocation(x, y, contentPanel.getWidth(), contentPanel.getHeight());
	}

	public Point getLocation() {
		return contentPanel.getLocation();
	}

	public void draw(Graphics g) {
		ArrayList<Anchor> a = anchors.getAnchors();
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		for (Anchor ab : a) {
			g2.setColor(mainColor);
			g2.fillRect(ab.getLocation().x - AnchorSet.RADIUS / 2, ab.getLocation().y - AnchorSet.RADIUS / 2,
					AnchorSet.RADIUS, AnchorSet.RADIUS);
			g2.setColor(Color.white);
			g2.fillRect(ab.getLocation().x - AnchorSet.RADIUS / 2 + 3, ab.getLocation().y - AnchorSet.RADIUS
					/ 2 + 3, AnchorSet.RADIUS - 6, AnchorSet.RADIUS - 6);
		}
	}

	public AnchorSet getAnchorSet() {
		return anchors;
	}

	public Point getAnchorPoint() {
		return anchors.getActivePoint().getLocation();
	}

	public void mouseMoved(MouseEvent arg0) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	int offsetX;
	int offsetY;

	public void mousePressed(MouseEvent e) {
		offsetX = e.getX();
		offsetY = e.getY();
	}

	public void mouseDragged(MouseEvent e) {
		int xCoor = e.getX() - offsetX;
		int yCoor = e.getY() - offsetY;
		setPanelLocation(contentPanel.getX() + xCoor, contentPanel.getY() + yCoor);
		return;
	}
}
