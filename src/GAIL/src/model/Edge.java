package GAIL.src.model;

import java.awt.Graphics;
import java.io.Serializable;

import GAIL.src.view.NodeView;

abstract public class Edge implements Serializable {

	private static final long serialVersionUID = -7365396306836167731L;

	// an edge must be an argument, warrant, or branch
	public static enum EdgeType {
		ARGUMENT, WARRANT, BRANCH
	}

	// string assigned as label to each type of edge
	public interface EdgeLabel {
		final String ARGUMENT = "Argument";
		final String WARRANT = "Warrant";
		final String BRANCH = "Branch";
		final String DEFAULT = "Edge";
	}

	Node source; // source node of the edge
	EdgeType edgeType; // the type of this edge
	int number; // unique identifier (for this type of edge)

	public Edge(Node source, int number) {
		this.source = source;
		this.number = number;
		setEdgeType();
	}

	public Node getSource() {
		return source;
	}

	public NodeView getSourceView() {
		return source.getView();
	}

	public String getID() {

		// ID composed of edge type + unique number (for that type)
		switch (edgeType) {
		case ARGUMENT:
			return EdgeLabel.ARGUMENT + " " + number;
		default:
			return EdgeLabel.DEFAULT + " " + number;
		}

	}

	abstract void draw(Graphics g);

	protected abstract void setEdgeType();

}
