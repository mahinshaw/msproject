package GAIL.src.frame;

import java.awt.*;

import GAIL.src.controller.ApplicationController;
import GAIL.src.view.StatementView;
import javax.swing.*;


public class GlassPanel extends JComponent {
	ApplicationController ac;

	public GlassPanel(ApplicationController ac) {
		this.ac = ac;
	}

	boolean isDim;
	int offsetMenuBarY = 20;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if (isDim) {
			int x = 965;
			int xo = 312;
			int width = 87;//62;
			g2.setColor(new Color(0, 0, 0, 113));
			/*g2.fillRect(0, 0, 1620, x);
			g2.fillRect(0, x, xo + 848, 300);
			g2.fillRect(xo + 1086 + width + 59, x, 514 + width, 300);*/
			g2.fillRect(0,0, ac.getApplicationView().getWidth(), ac.getApplicationView().getHeight());
		} else if (ac.getStatementController().isDragActive()) {
			g2.setColor(new Color(0, 0, 0, 75));
			g2.fillRect(boxX + mouseX + offsetX, boxY - mouseY + offsetY
					+ offsetMenuBarY, boxWidth, boxHeight);
		} else {
			setVisible(false);
		}
		g2.dispose();
	}

	int mouseX, mouseY, boxWidth, boxHeight, boxX, boxY;

	public void activateDrag(int boxX, int boxY, int boxWidth, int boxHeight) {
		setVisible(true);
		this.boxX = boxX;
		this.boxY = boxY - 20;
		this.boxWidth = boxWidth;
		this.boxHeight = boxHeight;
		offsetX = 0;
		offsetY = 0;
		repaint();
	}

	public void setCoordinates(int x, int y) {
		mouseX = x;
		mouseY = y;
		repaint();
	}

	public void setLargeSize(int boxHeight) {
		boxWidth = StatementView.STATEMENT_WIDTH;
		this.boxHeight = boxHeight;
	}

	public void setSmallSize(int width, int height) {
		boxWidth = width;
		boxHeight = height;
	}

	int offsetX, offsetY;

	public void setOffset(int x, int y) {
		offsetX = x;
		offsetY = y;
		repaint();
	}

	public void dim() {
		setVisible(true);
		isDim = true;
	}

	public void undim() {
		setVisible(false);
		isDim = false;
	}
}