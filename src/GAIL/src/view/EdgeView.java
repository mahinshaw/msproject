package GAIL.src.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;

abstract public class EdgeView {

	final int STANDARD_LINE_WIDTH = 3;

	public EdgeView() {

	}

	public Point getCenterPoint() {

		return new Point((getConnectingPoints().get(0).x + getConnectingPoints().get(1).x) / 2,
				(getConnectingPoints().get(0).y + getConnectingPoints().get(1).y) / 2);

	}

	// draw an arrow between the two points defined by specific edge view
	public void drawArrow(Graphics g, Color color) {
		drawArrow(g, getConnectingPoints(), color);
	}

	// draw an arrow from the first point to the second point stored in points
	public void drawArrow(Graphics g, ArrayList<Point> points, Color color) {
		// NOTE: This was copied from the following public forum:
		// http://www.bytemycode.com/snippets/snippet/82/

		float arrowWidth = 15.5f;
		float arrowLength = 0.59f;
		int[] xPoints = new int[3];
		int[] yPoints = new int[3];
		float[] vecLine = new float[2];
		float[] vecLeft = new float[2];
		float fLength, th, ta, baseX, baseY;

		// set the first x and y points according to the target point
		xPoints[0] = points.get(1).x;
		yPoints[0] = points.get(1).y;

		// build the line vector
		vecLine[0] = (float) xPoints[0] - points.get(0).x;
		vecLine[1] = (float) yPoints[0] - points.get(0).y;

		// build the arrow base vector - normal to the line
		vecLeft[0] = -vecLine[1];
		vecLeft[1] = vecLine[0];

		// setup length parameters
		fLength = (float) Math.sqrt((vecLine[0] * vecLine[0]) + (vecLine[1] * vecLine[1]));
		th = arrowWidth / (2.0f * fLength);
		ta = arrowWidth / (2.0f * ((float) Math.tan(arrowLength) / 2.0f) * fLength);

		// find the base of the arrow
		baseX = ((float) xPoints[0] - ta * vecLine[0]);
		baseY = ((float) yPoints[0] - ta * vecLine[1]);

		// build the points on the sides of the arrow
		xPoints[1] = (int) (baseX + th * vecLeft[0]);
		yPoints[1] = (int) (baseY + th * vecLeft[1]);
		xPoints[2] = (int) (baseX - th * vecLeft[0]);
		yPoints[2] = (int) (baseY - th * vecLeft[1]);

		Graphics2D g2 = setToStandardWidth(g);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(color);
		g2.drawLine(points.get(0).x, points.get(0).y, (int) baseX, (int) baseY);
		g2.fillPolygon(xPoints, yPoints, 3);
	}

	// set line thickness for standard edge width
	public Graphics2D setToStandardWidth(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(STANDARD_LINE_WIDTH));
		return g2;
	}

	abstract ArrayList<Point> getConnectingPoints();
	
	Point anchorLocation;
	public void setAnchorLocation(Point location){
		anchorLocation = location;
	}
}
