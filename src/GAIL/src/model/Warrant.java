package GAIL.src.model;

import java.awt.Graphics;
import java.io.Serializable;

import GAIL.src.view.ArgumentView;
import GAIL.src.view.WarrantView;

public class Warrant extends Edge implements Serializable {

	private static final long serialVersionUID = -5845556644222440308L;

	Argument target; // the argument this warrant points towards
	WarrantView view; // the object that draws warrants on the desktop
	int idNumber;
	public Warrant(Node source, Argument target, WarrantView view, int number) {
		super(source, number);
		System.out.println(target);
		this.view = view;
		this.target = target;
		idNumber=number;
	}

	public Edge getTarget() {
		return target;
	}

	public ArgumentView getTargetView() {
		return target.getView();
	}

	public String getID() {
		return "Warrant " + idNumber;
//		StringBuilder result = new StringBuilder();
//		result.append(EdgeLabel.WARRANT);
//		result.append(" from ");
//		result.append(getSource().ID);
//		result.append(" to ");
//		result.append(getTarget().getID());
//		return result.toString();
	}

	protected void setEdgeType() {
		edgeType = EdgeType.WARRANT;
	}

    @Override
    public EdgeType getEdgeType() {
        return edgeType;
    }

	// calls the warrant view object to draw this warrant
	public void draw(Graphics g) {
		view.draw(g);
	}
	public WarrantView getView(){
		return view;
	}
	
}
