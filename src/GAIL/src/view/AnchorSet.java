package GAIL.src.view;

import java.awt.Point;
import java.util.ArrayList;

public class AnchorSet {

	public static final int RADIUS = 15;
	private ArrayList<Anchor> anchors = new ArrayList<Anchor>();
	private ArrayList<ActivePoint> activePoints = new ArrayList<ActivePoint>();
	private Object parent;
	private Anchor sourceAnchor;
	private int width, height;
	private boolean visible;

	public AnchorSet(Object parent, int x, int y, int width, int height) {
		this.width = width;
		this.height = height;
		this.parent = parent;
		anchors.add(new Anchor(x, y + height / 2));
		anchors.add(new Anchor(x + width, y + height / 2));
		anchors.add(new Anchor(x + width / 2, y));
		anchors.add(new Anchor(x + width / 2, y + height));
		visible = true;
	}

	public ArrayList<ActivePoint> getActivePoints() {
		return activePoints;
	}

	public boolean isVisible() { return visible; }

	public void setVisible(boolean visible) { this.visible = visible; }

	//0 = left     1 = right   2 = top    3 = bottom
	public boolean isTouchingAnchor(int mouseX, int mouseY, boolean isReleased) {
		if(!isVisible())
			return false;

		Anchor a;
		for (int i = 0; i < anchors.size(); i++) {
			a = anchors.get(i);
			if (Math.hypot(mouseX - a.getLocation().x, mouseY - a.getLocation().y) < RADIUS + 4) {
				if (!isReleased) {
					if (i == 0) {//left
						if (mouseX > a.getLocation().x) {
							continue;
						}
					} else if (i == 1) {//right
						if (mouseX < a.getLocation().x) {
							continue;
						}
					} else if (i == 2) {//up
						if (mouseY > a.getLocation().y) {
							continue;
						}
					} else if (i == 3) {//down
						if (mouseY < a.getLocation().y) {
							continue;
						}
					}
				}
				sourceAnchor = a;
				return true;
			}
		}
		return false;
	}

	public Anchor getActiveAnchor() {
		return sourceAnchor;
	}

	public Point getActivePoint() {
		return sourceAnchor.getLocation();
	}

	public Object getParent() {
		return parent;
	}

	public void setLocation(int x, int y, int width, int height) {
		this.width = width;
		this.height = height;
		anchors.get(0).getLocation().x = x;
		anchors.get(0).getLocation().y = y + height / 2;
		anchors.get(1).getLocation().x = x + width;
		anchors.get(1).getLocation().y = y + height / 2;
		anchors.get(2).getLocation().x = x + width / 2;
		anchors.get(2).getLocation().y = y;
		anchors.get(3).getLocation().x = x + width / 2;
		anchors.get(3).getLocation().y = y + height;
	}

	public ArrayList<Anchor> getAnchors() {
		return anchors;
	}

	public void addActivePoint(Object target, Anchor sourceAnchor, Anchor targetAnchor) {
		activePoints.add(new ActivePoint(target, sourceAnchor, targetAnchor));
	}

	public class Anchor {
		Point location;

		public Anchor(int x, int y) {
			location = new Point(x, y);
		}

		public Point getLocation() {
			return location;
		}

		public void setLocation(Point location) {
			this.location = location;
		}
	}

	public class ActivePoint {
		Object target;
		Anchor sourcePoint;
		Anchor targetPoint;

		ActivePoint(Object target, Anchor sourcePoint, Anchor targetPoint) {
			this.target = target;
			this.sourcePoint = sourcePoint;
			this.targetPoint = targetPoint;
		}

		public Point getSourcePoint() {
			return sourcePoint.getLocation();
		}

		public Point getTargetPoint() {
			return targetPoint.getLocation();
		}

		public Object getTarget() {
			return target;
		}
	}
}