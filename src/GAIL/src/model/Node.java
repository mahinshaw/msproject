package GAIL.src.model;

import java.io.Serializable;

import GAIL.src.view.NodeView;

abstract public class Node implements Serializable {

	private static final long serialVersionUID = 3623076700854059441L;

	protected String ID; // unique identifier - type + number
	public Node(String ID) {
		this.ID = ID;
	}

	public String getID() {
		return ID;
	}

	abstract public NodeView getView();
}