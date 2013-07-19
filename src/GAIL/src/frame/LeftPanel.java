package GAIL.src.frame;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import GAIL.src.controller.ApplicationController;
import GAIL.src.view.ColorPalette;
import GAIL.src.frame.StatementPanel.Type;

public class LeftPanel extends JPanel implements MouseListener {

	// JScrollPane scrollPane;
	JTextPane textPane;
	DefaultStyledDocument doc;
	StyleContext sc;
	Style indentedStyle;
	Style boldStyle;

	JButton refreshButton;
	JButton tutorialButton;
	JButton submitButton;
	// JTextArea problemArea;
	JPanel hypothesisPanelHolder;
	JPanel dPanel;
	JPanel hPanel;
	JPanel gPanel;
	JPanel problemPanel;
	JPanel emptyPanel;
	StatementPanel hypPanel;
	StatementPanel dataPanel;
	StatementPanel genPanel;
	private JPanel titlePanelHypothesis;
	private JPanel titlePanelGeneralization;
	private JPanel titlePanelData;
	private JLabel picLabelHypothesis;
	private JLabel picLabelData;
	private JLabel picLabelGeneralization;
	private BufferedImage minimizePic;
	private BufferedImage minimizePicRollOver;
	private BufferedImage maximizePic;
	private BufferedImage maximizePicRollOver;
	ApplicationController ac;
	boolean isMinimized;
	Font labelFont = new Font("", Font.BOLD, 14);

	public static final int LEFT_PANEL_WIDTH = 320;
	public static int LEFT_PANEL_HEIGHT = 1115; // 1115
	int pheight = 80;
	int theight = 20;
	int gheight = (int) (.4 * (LEFT_PANEL_HEIGHT - pheight));
	int dheight = (int) (.4 * (LEFT_PANEL_HEIGHT - pheight));
	int hheight = (int) (.2 * (LEFT_PANEL_HEIGHT - pheight));

	public LeftPanel(final ApplicationController ac) {
		this.ac = ac;
		LEFT_PANEL_HEIGHT = 1115; // 48 37 15
		gheight = (int) (.51 * (LEFT_PANEL_HEIGHT - pheight));
		dheight = (int) (.28 * (LEFT_PANEL_HEIGHT - pheight));
		hheight = (int) (.21 * (LEFT_PANEL_HEIGHT - pheight));
		try {
			minimizePic = ImageIO.read(new File("image/minimize.png"));
			maximizePic = ImageIO.read(new File("image/maximize.png"));
			minimizePicRollOver = ImageIO.read(new File(
					"image/minimizerollover.png"));
			maximizePicRollOver = ImageIO.read(new File(
					"image/maximizerollover.png"));
		} catch (IOException e) {
		}

		titlePanelHypothesis = new JPanel();
		titlePanelHypothesis.setBackground(ColorPalette.HYPOT_BORDER_COLOR);
		titlePanelHypothesis.setLayout(new BorderLayout());
		titlePanelHypothesis.setMaximumSize(new Dimension(
				LeftPanel.LEFT_PANEL_WIDTH + 10, 20));
		picLabelHypothesis = new JLabel(new ImageIcon(minimizePic));
		picLabelHypothesis.addMouseListener(this);
		picLabelHypothesis.setToolTipText("Minimize");
		picLabelHypothesis.setName("H");
		JLabel label = new JLabel("   Hypotheses");
		label.setFont(labelFont);
		label.setForeground(Color.WHITE);
		titlePanelHypothesis.add(label, BorderLayout.WEST);
		titlePanelHypothesis.add(picLabelHypothesis, BorderLayout.EAST);

		titlePanelGeneralization = new JPanel();
		titlePanelGeneralization.setBackground(ColorPalette.GEN_BORDER_COLOR);
		titlePanelGeneralization.setLayout(new BorderLayout());
		titlePanelGeneralization.setMaximumSize(new Dimension(
				LeftPanel.LEFT_PANEL_WIDTH + 10, 20));
		picLabelGeneralization = new JLabel(new ImageIcon(minimizePic));
		picLabelGeneralization.addMouseListener(this);
		picLabelGeneralization.setName("G");
		picLabelGeneralization.setToolTipText("Minimize");
		label = new JLabel("   Connections");
		label.setFont(labelFont);
		label.setForeground(Color.WHITE);
		titlePanelGeneralization.add(label, BorderLayout.WEST);
		titlePanelGeneralization.add(picLabelGeneralization, BorderLayout.EAST);

		titlePanelData = new JPanel();
		titlePanelData.setBackground(ColorPalette.DATUM_BORDER_COLOR);
		titlePanelData.setLayout(new BorderLayout());
		titlePanelData.setMaximumSize(new Dimension(
				LeftPanel.LEFT_PANEL_WIDTH + 10, 20));
		picLabelData = new JLabel(new ImageIcon(minimizePic));
		picLabelData.addMouseListener(this);
		picLabelData.setName("D");
		picLabelData.setToolTipText("Minimize");
		label = new JLabel("   Data from Medical Records");
		label.setFont(labelFont);
		label.setForeground(Color.WHITE);
		titlePanelData.add(label, BorderLayout.WEST);
		titlePanelData.add(picLabelData, BorderLayout.EAST);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setMinimumSize(new Dimension(LEFT_PANEL_WIDTH, getMinimumSize().height));

		problemPanel = new JPanel();
		problemPanel.setLayout(new BoxLayout(problemPanel, BoxLayout.Y_AXIS));
		problemPanel.setBackground(Color.white);
		sc = new StyleContext();
		doc = new DefaultStyledDocument(sc);
		textPane = new JTextPane(doc);
		textPane.setEditable(false);
		setBackground(Color.white);
		textPane.setBackground(Color.white);

		Style defaultStyle = sc.getStyle(StyleContext.DEFAULT_STYLE);
		indentedStyle = sc.addStyle("indented", defaultStyle);
		StyleConstants.setLeftIndent(indentedStyle, 17);
		StyleConstants.setRightIndent(indentedStyle, 17);
		StyleConstants.setBold(indentedStyle, true);
		// boldStyle = sc.addStyle("bold", defaultStyle);
		// StyleConstants.setBold(boldStyle, true);

		JPanel problemTitlePanel = new JPanel();
		problemTitlePanel.setBackground(Color.BLUE);
		problemTitlePanel.setLayout(new BorderLayout());
		label = new JLabel("   Problem");
		label.setFont(labelFont);
		label.setForeground(Color.WHITE);
		label.setPreferredSize(new Dimension(LeftPanel.LEFT_PANEL_WIDTH + 10,
				20));
		problemTitlePanel.add(label);
		textPane.setPreferredSize(new Dimension(LEFT_PANEL_WIDTH + 10, 60));
		// scrollPane.setPreferredSize(new Dimension(LEFT_PANEL_WIDTH + 10,
		// 60));
		problemPanel.add(problemTitlePanel);
		problemPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		problemPanel.setBackground(Color.white);
		// problemPanel.add(problemArea);//TODO
		problemPanel.add(textPane);
		textPane.setBackground(Color.white);
		hypPanel = new StatementPanel(ac.getStatementController(),
				Type.HYPOTHESIS);
		dataPanel = new StatementPanel(ac.getStatementController(), Type.DATUM);
		genPanel = new StatementPanel(ac.getStatementController(),
				Type.GENERALIZATION);

		// create an organizational panel to hold the primary panels
		hPanel = new JPanel();
		hPanel.setLayout(new GridLayout(0, 1));
		hPanel.setPreferredSize(new Dimension(LEFT_PANEL_WIDTH, hheight));
		hPanel.add(hypPanel);

		dPanel = new JPanel();
		dPanel.setLayout(new GridLayout(0, 1));
		dPanel.setPreferredSize(new Dimension(LEFT_PANEL_WIDTH, dheight));
		dPanel.add(dataPanel);

		gPanel = new JPanel();
		gPanel.setPreferredSize(new Dimension(LEFT_PANEL_WIDTH, gheight));
		gPanel.setLayout(new GridLayout(0, 1));
		gPanel.add(genPanel);

		emptyPanel = new JPanel();
		emptyPanel.setPreferredSize(new Dimension(0, 0));

		JPanel buttonPanel = new JPanel();
		submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initAndShowSubmitDialog();
			}
		});
		tutorialButton = new JButton("Tutorial");
		refreshButton = new JButton("Refresh display");
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ac.refreshArgumentToBottomPanel();
			}
		});
		buttonPanel.add(tutorialButton);
		buttonPanel.add(refreshButton);
		buttonPanel.add(submitButton);

		add(problemPanel);
		add(titlePanelHypothesis);
		add(hPanel);
		add(titlePanelData);
		add(dPanel);
		add(titlePanelGeneralization);
		add(gPanel);
		add(emptyPanel);
		if (!ColorPalette.isNewButton) {
			add(buttonPanel);
		}
	}

	public void setProblem(String problem) {
		try {
			doc.setLogicalStyle(doc.getLength(), indentedStyle);
			textPane.setText("");
			doc.insertString(doc.getLength(), problem, this.indentedStyle);
		} catch (BadLocationException e) {
		}
	}

	public void setText(ArrayList<String> hypothesisText,
			ArrayList<String> dataText, ArrayList<String> genText) {
		hypPanel.setText(hypothesisText);
		dataPanel.setText(dataText);
		genPanel.setText(genText);
		setVisible(true);
		invalidate();
		revalidate();
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getSource() == picLabelData) {
			if (!dataPanel.isMinimized) {
				dataPanel.isMinimized = true;
				picLabelData.setIcon(new ImageIcon(maximizePicRollOver));
				picLabelData.setToolTipText("Maximize");
			} else {
				picLabelData.setIcon(new ImageIcon(minimizePicRollOver));
				dataPanel.isMinimized = false;
				picLabelData.setToolTipText("Minimize");
			}
		} else if (arg0.getSource() == picLabelGeneralization) {
			if (!genPanel.isMinimized) {
				genPanel.isMinimized = true;
				picLabelGeneralization.setIcon(new ImageIcon(
						maximizePicRollOver));
				picLabelGeneralization.setToolTipText("Maximize");
			} else {
				picLabelGeneralization.setIcon(new ImageIcon(
						minimizePicRollOver));
				genPanel.isMinimized = false;
				picLabelGeneralization.setToolTipText("Minimize");
			}
		} else if (arg0.getSource() == picLabelHypothesis) {
			if (!hypPanel.isMinimized) {
				hypPanel.isMinimized = true;
				picLabelHypothesis.setIcon(new ImageIcon(maximizePicRollOver));
				picLabelHypothesis.setToolTipText("Maximize");
			} else {
				picLabelHypothesis.setIcon(new ImageIcon(minimizePicRollOver));
				hypPanel.isMinimized = false;
				picLabelHypothesis.setToolTipText("Minimize");
			}
		}
		calculateSizes();
	}

	void calculateSizes() {
		emptyPanel.setPreferredSize(new Dimension(0, 0));
		if (hypPanel.isMinimized) {
			hPanel.setPreferredSize(new Dimension(0, 0));
			if (dataPanel.isMinimized) {
				dPanel.setPreferredSize(new Dimension(0, 0));
				if (genPanel.isMinimized) {
					gPanel.setPreferredSize(new Dimension(0, 0));
					emptyPanel.setPreferredSize(new Dimension(0, hheight
							+ dheight + gheight));
				} else {
					gPanel.setPreferredSize(new Dimension(0, hheight + dheight
							+ gheight));
				}
			} else {
				if (genPanel.isMinimized) {
					dPanel.setPreferredSize(new Dimension(0, hheight + dheight
							+ gheight));
					gPanel.setPreferredSize(new Dimension(0, 0));
				} else {
					dPanel.setPreferredSize(new Dimension(0, hheight / 2
							+ dheight));
					gPanel.setPreferredSize(new Dimension(0, hheight / 2
							+ gheight));
				}
			}
		} else {
			hPanel.setPreferredSize(new Dimension(0, hheight));
			if (dataPanel.isMinimized) {
				dPanel.setPreferredSize(new Dimension(0, 0));
				if (genPanel.isMinimized) {
					gPanel.setPreferredSize(new Dimension(0, 0));
					emptyPanel.setPreferredSize(new Dimension(0, dheight
							+ gheight));
				} else {
					gPanel.setPreferredSize(new Dimension(0, dheight + gheight));
				}
			} else {
				if (genPanel.isMinimized) {
					gPanel.setPreferredSize(new Dimension(0, 0));
					dPanel.setPreferredSize(new Dimension(0, dheight + gheight));
				} else {
					gPanel.setPreferredSize(new Dimension(0, gheight));
					dPanel.setPreferredSize(new Dimension(0, dheight));
				}
			}
		}
		invalidate();
		revalidate();
		repaint();
	}

	public void mouseEntered(MouseEvent arg0) {
		if (arg0.getSource() == picLabelData) {
			if (!dataPanel.isMinimized) {
				picLabelData.setIcon(new ImageIcon(minimizePicRollOver));
			} else {
				picLabelData.setIcon(new ImageIcon(maximizePicRollOver));
			}
		} else if (arg0.getSource() == picLabelHypothesis) {
			if (!hypPanel.isMinimized) {
				picLabelHypothesis.setIcon(new ImageIcon(minimizePicRollOver));
			} else {
				picLabelData.setIcon(new ImageIcon(maximizePicRollOver));
			}
		} else if (arg0.getSource() == picLabelGeneralization) {
			if (!genPanel.isMinimized) {
				picLabelGeneralization.setIcon(new ImageIcon(
						minimizePicRollOver));
			} else {
				picLabelGeneralization.setIcon(new ImageIcon(
						maximizePicRollOver));
			}
		}
	}

	public void mouseExited(MouseEvent arg0) {
		if (arg0.getSource() == picLabelData) {
			if (!dataPanel.isMinimized) {
				picLabelData.setIcon(new ImageIcon(minimizePic));
			} else {
				picLabelData.setIcon(new ImageIcon(maximizePic));
			}
		} else if (arg0.getSource() == picLabelHypothesis) {
			if (!hypPanel.isMinimized) {
				picLabelHypothesis.setIcon(new ImageIcon(minimizePic));
			} else {
				picLabelHypothesis.setIcon(new ImageIcon(maximizePic));
			}
		} else if (arg0.getSource() == picLabelGeneralization) {
			if (!genPanel.isMinimized) {
				picLabelGeneralization.setIcon(new ImageIcon(minimizePic));
			} else {
				picLabelGeneralization.setIcon(new ImageIcon(maximizePic));
			}
		}

	}

	public void mousePressed(MouseEvent arg0) {
	}

	public void mouseReleased(MouseEvent arg0) {
	}

	public void initAndShowSubmitDialog() {
		final JDialog dialog = new JDialog(ac.getApplicationView(),
				"Argument completed?", true);
		JButton doneButton = new JButton("Yes");
		doneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ac.saveAllOutput();
				dialog.dispose();
			}
		});
		JButton cancelButton = new JButton("No");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(doneButton);
		buttonPanel.add(cancelButton);
		JLabel label = new JLabel(
				"      Are you sure that your argument is completed?      ");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(label);
		panel.add(buttonPanel);
		dialog.add(panel);
		dialog.setLocationRelativeTo(ac.getApplicationView());
		dialog.pack();
		dialog.setVisible(true);
	}

}