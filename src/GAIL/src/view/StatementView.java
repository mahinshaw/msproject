package GAIL.src.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

import GAIL.src.model.MultiGeneralizationFactory;
import GAIL.src.model.MultiGeneralizationModel;
import GAIL.src.view.AnchorSet.Anchor;

import GAIL.src.controller.ApplicationController;
import GAIL.src.controller.EdgeController;
import GAIL.src.controller.StatementController;

import GAIL.src.model.Statement;

public class StatementView extends NodeView implements Serializable,
		MouseMotionListener, MouseListener, ComponentListener {

	private static final long serialVersionUID = -2342341;

	public static final int INITIAL_X_POSITION = 10;
	public static final int INITIAL_Y_POSITION = 10;
	public static final int INITIAL_X_INCREMENT = 10;
	public static final int INITIAL_Y_INCREMENT = 15;
	public static final int STATEMENT_WIDTH = 250;
	final static int MAX_TEXT_AREA_SIZE = 150000;
	final static int TOP_HEIGHT = 24;
	static int totalHeight;
	private static final int BORDER_SIZE = 3;
	private JInternalFrame viewFrame;
	JLabel topLabel;
	JLabel topQLabel;
	JLabel topMLabel;
	JLabel topXLabel;
	BufferedImage minImg = null;
	BufferedImage maxImg = null;
	Color topColor;
	Statement statement;
	StatementController sc;
	EdgeController ec;
	AnchorSet anchors;

	public StatementView(final Statement statement, ApplicationController ac,
			int initialX, int initialY) {
		final StatementController sc = ac.getStatementController();
		this.statement = statement;
		this.sc = ac.getStatementController();
		this.ec = ac.getEdgeController();
		viewFrame = new JInternalFrame();
		viewFrame.setLayout(new BorderLayout());
		JPanel sourcePanel = new JPanel(new BorderLayout());
		sourcePanel.add(new JLabel(" Source: " + statement.getSourceLabel()),
				BorderLayout.WEST);
		topColor = null;
		String title = "";
		if (statement.getType() == Statement.StatementType.DATA) {
			topColor = ColorPalette.isColorOn ? ColorPalette.DATUM_BORDER_COLOR
					: Color.gray;
			sourcePanel.setBackground(ColorPalette.DATUM_COLOR);
			viewFrame.setBorder(BorderFactory.createLineBorder(
					ColorPalette.DATUM_BORDER_COLOR, BORDER_SIZE));
			title = "Data item "
					+ statement.getID().charAt(statement.getID().length() - 1);
		} else if (statement.getType() == Statement.StatementType.GENERALIZATION) {
			viewFrame.setBorder(BorderFactory.createLineBorder(
					ColorPalette.GEN_BORDER_COLOR, BORDER_SIZE));
			sourcePanel.setBackground(ColorPalette.GEN_COLOR);
			topColor = ColorPalette.isColorOn ? ColorPalette.GEN_BORDER_COLOR
					: Color.gray;
			topColor = ColorPalette.GEN_BORDER_COLOR;
			title = statement.getID();
		} else if (statement.getType() == Statement.StatementType.HYPOTHESIS) {
			sourcePanel.setBackground(ColorPalette.HYPOT_COLOR);
			topColor = ColorPalette.isColorOn ? ColorPalette.HYPOT_BORDER_COLOR
					: Color.gray;
			topColor = ColorPalette.HYPOT_BORDER_COLOR;
			viewFrame.setBorder(BorderFactory.createLineBorder(
					ColorPalette.HYPOT_BORDER_COLOR, BORDER_SIZE));
			title = statement.getID();
		}
		javax.swing.plaf.InternalFrameUI ifu = viewFrame.getUI();
		((javax.swing.plaf.basic.BasicInternalFrameUI) ifu).setNorthPane(null);

		JTextPane textPane = new JTextPane();
		textPane.setFocusable(false);
		textPane.setEditable(false);
		textPane.setPreferredSize(new Dimension(1, 1));
		textPane.setText(statement.getText());
		JScrollPane scrollPane = new JScrollPane(textPane,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setSize(new Dimension(STATEMENT_WIDTH, MAX_TEXT_AREA_SIZE));
		viewFrame.setResizable(true);
		viewFrame.setClosable(true);
		viewFrame.addComponentListener(this);
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		BufferedImage qImg = null;
		BufferedImage xImg = null;
		try {
			qImg = ImageIO.read(new File("image/qwhite.png"));
			xImg = ImageIO.read(new File("image/xwhite.png"));
			minImg = ImageIO.read(new File("image/minwhite.png"));
			maxImg = ImageIO.read(new File("image/maxwhite.png"));

		} catch (IOException e) {
		}
		topPanel.setBackground(topColor);
		topQLabel = new JLabel(new ImageIcon(qImg));
		topQLabel.setName("INFO " + statement.getID());
		topQLabel.setToolTipText("Info");
		topXLabel = new JLabel(new ImageIcon(xImg));
		topXLabel.setName("CLOSE " + statement.getID());
		// topXLabel.setToolTipText("Close");
		topMLabel = new JLabel(new ImageIcon(minImg));
		topMLabel.setName("MIN " + statement.getID());
		topLabel = new JLabel("   " + title);// title.substring(0,
												// title.length() - 1));//TODO
		topLabel.setForeground(Color.WHITE);

		topPanel.add(topLabel, BorderLayout.WEST);
		JPanel topPanelRight = new JPanel();
		topPanelRight.setLayout(new BoxLayout(topPanelRight, BoxLayout.X_AXIS));
		topPanelRight.setBackground(topColor);
		if (ColorPalette.isQVisible) {
			topPanelRight.add(topQLabel);
			topPanelRight.add(Box.createRigidArea(new Dimension(3, 0)));
		}
		topPanelRight.add(topMLabel);
		topPanelRight.add(Box.createRigidArea(new Dimension(3, 0)));
		topPanelRight.add(topXLabel);
		topPanelRight.add(Box.createRigidArea(new Dimension(3, 0)));
		topPanel.setSize(STATEMENT_WIDTH, TOP_HEIGHT);
		topPanel.add(topPanelRight, BorderLayout.EAST);

		viewFrame.add(topPanel, BorderLayout.PAGE_START);

		if (statement.getType() == Statement.StatementType.HYPOTHESIS) {
			viewFrame.add(sourcePanel);
		}

		viewFrame.add(scrollPane, BorderLayout.CENTER);
		topLabel.addMouseMotionListener(this);
		topPanel.addMouseMotionListener(this);
		textPane.addMouseMotionListener(this);
		topLabel.addMouseListener(this);
		topPanel.addMouseListener(this);
		textPane.addMouseListener(this);

		topQLabel.addMouseListener(ac.getEdgeController());
		topMLabel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				minimizeMaximize();
			}
		});
		topXLabel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				sc.closeView(statement);
				dispose();
			}
		});
		viewFrame.reshape(initialX, initialY, STATEMENT_WIDTH, getViewHeight(statement.getText()));
		viewFrame.invalidate();
		viewFrame.revalidate();
		viewFrame.repaint();
		viewFrame.setVisible(true);
		scrollPane.getVerticalScrollBar().setValue(0);
		textPane.setCaretPosition(0);
		anchors = new AnchorSet(statement, initialX, initialY, STATEMENT_WIDTH,
				viewFrame.getHeight());
	}


	public static int getViewHeight(String text) {
		JTextPane dummyPane = new JTextPane();
		dummyPane.setSize(STATEMENT_WIDTH - 9, 1000);
		dummyPane.setText(text);
		int bottomHeight = dummyPane.getPreferredSize().height;
		totalHeight = bottomHeight + TOP_HEIGHT + 10;
		return bottomHeight + TOP_HEIGHT + 10;
	}
	
	// return array with all four statement connection points
	public ArrayList<Point> getConnectingPoints() {
		ArrayList<Point> results = new ArrayList<Point>();
		results.add(getNConnector());
		results.add(getEConnector());
		results.add(getWConnector());
		results.add(getSConnector());
		return results;
	}

	public boolean isClosed() {
		return viewFrame.isClosed();
	}

	public int getX() {
		return viewFrame.getX();
	}

	public int getY() {
		return viewFrame.getY();
	}

	public int getWidth() {
		return viewFrame.getWidth();
	}

	public int getHeight() {
		return viewFrame.getHeight();
	}

	// returns the North connector (middle top)
	public Point getNConnector() {
		return new Point(viewFrame.getX() + (viewFrame.getWidth() / 2),
				viewFrame.getY());
	}

	// returns the South connector (middle bottom)
	public Point getSConnector() {
		return new Point(viewFrame.getX() + (viewFrame.getWidth() / 2),
				viewFrame.getY() + viewFrame.getHeight());
	}

	// returns the East connector (middle right)
	public Point getEConnector() {
		return new Point(viewFrame.getX() + viewFrame.getWidth(),
				viewFrame.getY() + (viewFrame.getHeight() / 2));
	}

	// returns the West connector (middle left)
	public Point getWConnector() {
		return new Point(viewFrame.getX(), viewFrame.getY()
				+ (viewFrame.getHeight() / 2));
	}

	public Point getCenter() {
		return new Point(
				viewFrame.getLocation().x + (viewFrame.getWidth() / 2),
				viewFrame.getLocation().y + (viewFrame.getHeight() / 2));
	}

	// return point that is the origin of the first connected conjunction
	public Point getCentralConjunctionOrigin() {
		return new Point(getSConnector().x - (100 / 2), getSConnector().y
				+ ConjunctionView.Y_OFFSET - (20 / 2));
	}

	// called to dispose of the statement's frame
	public void dispose() {
		viewFrame.dispose();
	}

	// returns the statement's visual component
	public JInternalFrame getFrame() {
		return viewFrame;
	}

	boolean isMinimized;

	void minimizeMaximize() {
		if (isMinimized) {
			System.out.println(totalHeight);
			isMinimized = false;
			viewFrame.reshape(viewFrame.getX(), viewFrame.getY(),
					StatementView.STATEMENT_WIDTH, totalHeight);
			topMLabel.setIcon(new ImageIcon(minImg));
			// topMLabel.setToolTipText("Minimize");
		} else {
			isMinimized = true;
			viewFrame.reshape(viewFrame.getX(), viewFrame.getY(),
					StatementView.STATEMENT_WIDTH, TOP_HEIGHT - 2);
			topMLabel.setIcon(new ImageIcon(maxImg));
			// topMLabel.setToolTipText("Maximize");
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		viewFrame.reshape(viewFrame.getX() + (e.getX() - offsetX),
				viewFrame.getY() + (e.getY() - offsetY), viewFrame.getWidth(),
				viewFrame.getHeight());
		anchors.setLocation(viewFrame.getX(), viewFrame.getY(),
				viewFrame.getWidth(), viewFrame.getHeight());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	int offsetX, offsetY;

	@Override
	public void mousePressed(MouseEvent e) {
		offsetX = e.getX();
		offsetY = e.getY();
		// if the statement is in a group, remove it from the group
		if(statement.group != null) {
			statement.group.removeGeneralization(statement);
			statement.group = null;
		}
	}

	public void mouseReleased(MouseEvent arg0) {
		if(statement.getType() != Statement.StatementType.GENERALIZATION)
			return;
		assert(statement.group == null);
		for(Statement s : sc.getStatements()) {
			if(s == statement)
				continue;
			if(s.getType() != Statement.StatementType.GENERALIZATION)
				continue;
			StatementView thatSv = s.getView();
			for(Anchor thatAnchor : thatSv.getAnchorSet().getAnchors().subList(0, 2)) {
				for(Anchor thisAnchor : getAnchorSet().getAnchors().subList(0, 2)) {
					if(thisAnchor.getLocation().distance(thatAnchor.getLocation())
					          < AnchorSet.RADIUS) {
						int newHeight = Math.max(getHeight(), thatSv.getHeight());
						if(s.group != null) {
							newHeight = thatSv.getHeight();
							int newX = thatSv.getX();
							if(getX() < newX)
								newX -= getWidth();
							else
								newX += thatSv.getWidth();
							viewFrame.setBounds(newX, thatSv.getY(), getWidth(), newHeight);
						} else if(thatSv.getX() < getX()) {
							thatSv.viewFrame.setBounds(thatSv.getX(), thatSv.getY(),
							                           thatSv.getWidth(), newHeight);
							viewFrame.setBounds(thatSv.getX() + thatSv.getWidth(), thatSv.getY(),
							                    getWidth(), newHeight);
						} else {
							viewFrame.setBounds(getX(), getY(),
							                    getWidth(), newHeight);
							thatSv.viewFrame.setBounds(getX() + getWidth(), getY(),
							                           thatSv.getWidth(), newHeight);
						}
						anchors.setLocation(viewFrame.getX(), viewFrame.getY(),
						                    viewFrame.getWidth(), viewFrame.getHeight());
						thatSv.anchors.setLocation(thatSv.getX(), thatSv.getY(),
						                           thatSv.getWidth(), thatSv.getHeight());

						if(s.group == null) {
							s.group = MultiGeneralizationFactory.create(s);
						}
						s.group.addGeneralization(statement);
						ec.removeWarrantAssociatedWith(statement);
						ec.removeWarrantAssociatedWith(s);
						statement.group = s.group;
						return;
					}
				}
			}
		}
	}

	int mouseX, mouseY;

	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void draw(Graphics g) {
		if(!anchors.isVisible())
			return;
		ArrayList<Anchor> a = anchors.getAnchors();
		for (Anchor ab : a) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			g2.setColor(topColor);
			g2.fillRect(ab.getLocation().x - AnchorSet.RADIUS / 2,
					ab.getLocation().y - AnchorSet.RADIUS / 2,
					AnchorSet.RADIUS, AnchorSet.RADIUS);
			g2.setColor(Color.white);
			g2.fillRect(ab.getLocation().x - AnchorSet.RADIUS / 2 + 3,
					ab.getLocation().y - AnchorSet.RADIUS / 2 + 3,
					AnchorSet.RADIUS - 6, AnchorSet.RADIUS - 6);

		}
	}

	public AnchorSet getAnchorSet() {
		return anchors;
	}

	public Point getAnchorPoint() {
		return anchors.getActivePoint().getLocation();
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		anchors.setLocation(viewFrame.getX(), viewFrame.getY(),
				viewFrame.getWidth(), viewFrame.getHeight());
		if (viewFrame.getHeight() < TOP_HEIGHT - 2) {
			viewFrame.reshape(viewFrame.getX(), viewFrame.getY(),
					viewFrame.getWidth(), TOP_HEIGHT - 2);
		}
		int minWidth = 140;
		if (viewFrame.getWidth() < minWidth) {
			viewFrame.reshape(viewFrame.getX(), viewFrame.getY(), minWidth,
					viewFrame.getHeight());
		}
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
	}
}