package GAIL.src.view;

import GAIL.src.controller.EdgeController;
import GAIL.src.frame.*;
import GAIL.src.frame.Desktop;
import GAIL.src.model.MultiGeneralizationModel;
import GAIL.src.model.Statement;
import GAIL.src.model.Warrant;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MultiGeneralizationView extends NodeView {
	private MultiGeneralizationModel model;
	private EdgeController ec;

	public MultiGeneralizationView(MultiGeneralizationModel model, EdgeController ec) {
		if(model == null)
			throw new NullPointerException("model may not be null");

		anchorSet = new AnchorSet(model, 0, 0, 0, 0);
		this.model = model;
		model.setView(this);
        GAIL.src.frame.Desktop.mgvList.add(this); // TODO: Remove temporary hook.
		this.ec = ec;
		ec.addAnchorSet(anchorSet);
	}

	public void updateAnchorSet() {
		List<Statement> generalizations = model.getGeneralizations();
		if(generalizations.isEmpty()) {
			anchorSet.setVisible(false);
			Desktop.mgvList.remove(this); // TODO: Remove temporary hook.
			ec.removeAnchorSet(anchorSet);
			ec.removeWarrantAssociatedWith(model);
		} else {
			StatementView sv = generalizations.get(0).getView();
			Point upperLeft = new Point(sv.getX(), sv.getY());
			Point lowerRight = new Point(0, sv.getHeight());
			for(Statement s : generalizations.subList(0, generalizations.size())) {
				sv = s.getView();
				if(upperLeft.getX() > sv.getX())
					upperLeft.setLocation(sv.getX(), sv.getY());
				lowerRight.translate(sv.getWidth(), 0);
			}
			//lowerRight.translate(upperLeft.x, upperLeft.y);

			anchorSet.setLocation(upperLeft.x, upperLeft.y,
			                      lowerRight.x, lowerRight.y);
			anchorSet.setVisible(true);
		}
	}

	public void tempDraw(Graphics g) { // TODO: Remove temporary hook.
		if(!anchorSet.isVisible())
			return;
		ArrayList<AnchorSet.Anchor> a = anchorSet.getAnchors();
		for (AnchorSet.Anchor ab : a) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			                    RenderingHints.VALUE_ANTIALIAS_ON);

			g2.setColor(ColorPalette.GEN_BORDER_COLOR);
			g2.fillRect(ab.getLocation().x - AnchorSet.RADIUS / 2,
			            ab.getLocation().y - AnchorSet.RADIUS / 2,
			            AnchorSet.RADIUS, AnchorSet.RADIUS);
			g2.setColor(Color.white);
			g2.fillRect(ab.getLocation().x - AnchorSet.RADIUS / 2 + 3,
			            ab.getLocation().y - AnchorSet.RADIUS / 2 + 3,
			            AnchorSet.RADIUS - 6, AnchorSet.RADIUS - 6);

		}
	}


	@Override
	ArrayList<Point> getConnectingPoints() {
		ArrayList<Point> result = new ArrayList<Point>(4);
		for(AnchorSet.Anchor a : anchorSet.getAnchors())
			result.add(a.getLocation());
		return result;
	}

    public void reset() {
        anchorSet.setVisible(false);
        Desktop.mgvList.remove(this); // TODO: Remove temporary hook.
        ec.removeAnchorSet(anchorSet);
    }
}
