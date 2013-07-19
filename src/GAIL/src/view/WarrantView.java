package GAIL.src.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import GAIL.src.view.AnchorSet.Anchor;
import GAIL.src.model.Argument;
import GAIL.src.model.Warrant;

public class WarrantView extends EdgeView {

	Anchor targetAnchor;
	Anchor sourceAnchor;
	int x, y, width = 18;
	Color mainColor;
	BufferedImage blueX;
	BufferedImage redX;

	public WarrantView() {
		try {
			blueX = ImageIO.read(new File("src/GAIL/image/xblue.png"));
			redX = ImageIO.read(new File("src/GAIL/image/xred.png"));
		} catch (Exception e) {
		}
	}

	public void setWarrant(Warrant warrant, Anchor sourceAnchor, Anchor targetAnchor) {
		this.targetAnchor = targetAnchor;
		this.sourceAnchor = sourceAnchor;
	}

	Argument.ArgumentType type;

	public void setType(Color mainColor, Argument.ArgumentType type) {
		this.mainColor = mainColor;
		this.type = type;
	}

	public ArrayList<Point> getConnectingPoints() {
		ArrayList<Point> results = new ArrayList<Point>();
		results.add(sourceAnchor.getLocation());
		results.add(targetAnchor.getLocation());
		return results;
	}

	public boolean isClicked(int mouseX, int mouseY) {
		if (mouseX > x - width / 2 && mouseX < x + width / 2 && mouseY > y - width / 2
				&& mouseY < y + width / 2) {
			return true;
		}
		return false;
	}

	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(mainColor);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(1));
		g2.drawLine(getConnectingPoints().get(0).x, getConnectingPoints().get(0).y,
				getConnectingPoints().get(1).x, getConnectingPoints().get(1).y);
		x = getConnectingPoints().get(0).x / 2 + getConnectingPoints().get(1).x / 2;
		y = getConnectingPoints().get(0).y / 2 + getConnectingPoints().get(1).y / 2;
		if (type == Argument.ArgumentType.PRO) {
			g.drawImage(blueX, x - blueX.getWidth() / 2, y - blueX.getHeight() / 2, null);
		} else {
			g.drawImage(redX, x - redX.getWidth() / 2, y - redX.getHeight() / 2, null);
		}
	}
}