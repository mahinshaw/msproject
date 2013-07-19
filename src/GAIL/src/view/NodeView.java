package GAIL.src.view;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class NodeView implements Serializable {

	abstract ArrayList<Point> getConnectingPoints();

	AnchorSet anchorSet;
	//source.getView().getAnchorSet().getActiveAnchor(), target.getView()
//	.getAnchorSet().getActiveAnchor())
	public AnchorSet getAnchorSet() {
		return anchorSet;
	}
}
