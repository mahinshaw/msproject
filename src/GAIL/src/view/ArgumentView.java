package GAIL.src.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
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

import GAIL.src.controller.EdgeController;
import GAIL.src.model.Argument;
import GAIL.src.model.Conjunction;
import GAIL.src.view.AnchorSet.ActivePoint;
import GAIL.src.view.AnchorSet.Anchor;

public class ArgumentView extends EdgeView {

	Argument argument; // the argument represented by this view
	JPanel panel; // the label panel marking the argument number
	JLabel xLabel;
	JLabel cLabel;
	JLabel qLabel;
	JLabel label;
	BufferedImage bImg;
	BufferedImage rImg;
	AnchorSet anchors;
	private Color mainColor;
	final int PRO_SIZE = 82;
	final int CON_SIZE = 86;
	final int TWO_CON_SIZE = 135; 
	public ArgumentView() {
	}

	public ArgumentView(EdgeController e, int num, Argument.ArgumentType type) {
		BufferedImage xImg = null;
		BufferedImage qImg = null;
		try {
			xImg = ImageIO.read(new File("src/GAIL/image/xwhite.png"));
			qImg = ImageIO.read(new File("src/GAIL/image/qwhite.png"));
			bImg = ImageIO.read(new File("src/GAIL/image/swap.png"));
			rImg = ImageIO.read(new File("src/GAIL/image/swap2.png"));
		} catch (IOException de) {
		}

		mainColor = Color.BLUE;

		panel = new JPanel();
		panel.setBackground(mainColor);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		label = new JLabel();
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Arial", Font.BOLD, 12));
		cLabel = new JLabel(new ImageIcon(rImg));
		cLabel.setToolTipText("Change argument type");
		cLabel.addMouseListener(e);
		qLabel = new JLabel(new ImageIcon(qImg));
		qLabel.addMouseListener(e);
		xLabel = new JLabel(new ImageIcon(xImg));
		xLabel.setName("REMOVE");
		xLabel.addMouseListener(e);
		panel.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(label);
		panel.add(Box.createRigidArea(new Dimension(5, 0)));
		panel.add(cLabel);
		panel.add(Box.createRigidArea(new Dimension(3, 0)));
		if (ColorPalette.isQVisible) {
			panel.add(qLabel);
			panel.add(Box.createRigidArea(new Dimension(3, 0)));
		}
		panel.add(xLabel);
		panel.add(Box.createRigidArea(new Dimension(3, 0)));
		panel.setSize(PRO_SIZE, 20);
		panel.setVisible(false);
	}

	public void updateLocation() {
		Point point = getCenterPoint();
		panel.setLocation(point.x - panel.getWidth() / 2, point.y - panel.getHeight() / 2);
		anchors.setLocation(panel.getX(), panel.getY(), panel.getWidth(), panel.getHeight());
		panel.setVisible(true);
	}

	public JPanel getLabelPanel() {
		return panel;
	}

	public void setConnector(Argument argument) {
		this.argument = argument;
		xLabel.setName("REMOVE " + argument.getID());
		cLabel.setName("CHANGE_TYPE" + argument.getID().charAt(argument.getID().length() - 1));
		anchors = new AnchorSet(argument, panel.getX(), panel.getY(), panel.getPreferredSize().width,
				panel.getPreferredSize().height);
		label.setText("Pro " + argument.getID().substring(argument.getID().length() - 1));
		qLabel.setName("INFO " + argument.getID());
	}

	public void addAnchorActivePoint(Object target, Anchor sourceAnchor, Anchor targetAnchor) {
		anchors.addActivePoint(target, sourceAnchor, targetAnchor);
	}

	public Color getColor() {
		return mainColor;
	}

	public ArrayList<Point> getConnectingPoints() {
		ArrayList<Point> results = new ArrayList<Point>();
		ArrayList<AnchorSet.ActivePoint> a = anchors.getActivePoints();
		for (ActivePoint ab : a) {
			results.add(ab.getSourcePoint());
			results.add(ab.getTargetPoint());
		}
		return results;
	}

	// draws a con arrow (red with an X)
	public void drawConArrow(Graphics g) {

		g.setColor(mainColor);
		drawArrow(g, mainColor);
	}

	// draws two-way con arrow (two arrow heads, two Xs)TODO
	public void drawTwoWayCon(Graphics g) {

		Graphics2D g2 = setToStandardWidth(g);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		ArrayList<Point> points = new ArrayList<Point>();
		points.add(getConnectingPoints().get(0));
		points.add(getConnectingPoints().get(1));
		drawArrow(g2, points, mainColor);
		points.clear();
		points.add(getConnectingPoints().get(1));
		points.add(getConnectingPoints().get(0));
		drawArrow(g2, points, mainColor);
	}

	// draw this argument
	public void draw(Graphics g) {
		g.setColor(mainColor);
		// if this is a conjunction, draw a standard 'pro' arrow
		if (argument.getSource() instanceof Conjunction) {
			drawArrow(g, mainColor);
		}

		// otherwise, draw the correct arrow for the argument type
		else {

			switch (argument.getArgumentType()) {
			case PRO:
				drawArrow(g, mainColor);
				break;
			case CON:
				drawConArrow(g);
				break;
			case CON_TWO_WAY:
				drawTwoWayCon(g);
				break;
			}

		}
		ArrayList<Anchor> a = anchors.getAnchors();
		for (Anchor ab : a) {
			g.setColor(mainColor);
			g.fillRect(ab.getLocation().x - AnchorSet.RADIUS / 2, ab.getLocation().y - AnchorSet.RADIUS / 2,
					AnchorSet.RADIUS, AnchorSet.RADIUS);
			g.setColor(Color.white);
			g.fillRect(ab.getLocation().x - AnchorSet.RADIUS / 2 + 3, ab.getLocation().y - AnchorSet.RADIUS
					/ 2 + 3, AnchorSet.RADIUS - 6, AnchorSet.RADIUS - 6);
		}
		updateLocation();
	}

	public AnchorSet getAnchorSet() {
		return anchors;
	}

	public void updateView() {
		if (argument.getArgumentType() == Argument.ArgumentType.PRO) {
			mainColor = Color.blue;
			cLabel.setIcon(new ImageIcon(bImg));
			label.setText("Pro " + argument.getID().substring(argument.getID().length() - 1) + " ");
			panel.setSize(PRO_SIZE, 20);
		} else if (argument.getArgumentType() == Argument.ArgumentType.CON) {
			mainColor = Color.red;
			cLabel.setIcon(new ImageIcon(rImg));
			label.setText("Con " + argument.getID().substring(argument.getID().length() - 1) + " ");
			panel.setSize(CON_SIZE, 20);
		} else if (argument.getArgumentType() == Argument.ArgumentType.CON_TWO_WAY) {
			mainColor = Color.red;
			cLabel.setIcon(new ImageIcon(rImg));
			label.setText("Con two-way " + argument.getID().substring(argument.getID().length() - 1));
			panel.setSize(TWO_CON_SIZE, 20);
		}
		panel.setBackground(mainColor);
		panel.setBorder(BorderFactory.createLineBorder(mainColor, 2));

	}
}
