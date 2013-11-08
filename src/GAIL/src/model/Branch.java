package GAIL.src.model;

import java.awt.Graphics;

import GAIL.src.view.BranchView;
import GAIL.src.view.ConjunctionView;
import java.io.Serializable;

public class Branch extends Edge implements Serializable {
	private static final long serialVersionUID = 278109305L;
	Conjunction target; // branch targets are always conjunctions
	BranchView view; // the object that draws branches

	public Branch(Node source, Conjunction target, BranchView view, int number) {
		super(source, number);
		this.target = target;
		this.view = view;
	}

	public String getID() {
		StringBuilder result = new StringBuilder();
		result.append(EdgeLabel.BRANCH);
		result.append(" from ");
		result.append(getSource().ID);
		result.append(" to ");
		result.append(getTarget().getID());
		return result.toString();
	}

	protected void setEdgeType() {
		edgeType = EdgeType.BRANCH;
	}

    @Override
    public EdgeType getEdgeType() {
        return edgeType;
    }

	public BranchView getView(){
		return view;
	}
	
	// return the object responsible for drawing the target conjunction
	public ConjunctionView getTargetView() {
		return target.getView();
	}

	public Conjunction getTarget() {
		return target;
	}

	// call the object that draws branches to draw this branch
	public void draw(Graphics g) {
		view.draw(g);
	}

}
