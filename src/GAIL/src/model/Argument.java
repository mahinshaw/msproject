package GAIL.src.model;

import java.awt.Graphics;

import GAIL.src.view.ArgumentView;
import GAIL.src.view.StatementView;
import java.util.ArrayList;
public class Argument extends Edge {


	public static enum ArgumentType {
		PRO, // supportive arguments
		CON, // non-supportive arguments (unidirectional)
		CON_TWO_WAY
		// non-supportive arguments (bidirectional)
	}

	Statement target; // target statement of this argument
	ArgumentType argumentType; // the type of this argument
	ArgumentView argumentView; // draws arguments on the desktop
	ArrayList<Warrant> warrants;
	int argumentNumber;

	public Argument(Node source, Statement target, ArgumentView view, int argumentNumber, ArgumentType type) {
		super(source, argumentNumber);
		this.argumentType = type;
		this.argumentView = view;
		this.target = target;
		this.argumentNumber = argumentNumber;
		warrants = new ArrayList<Warrant>();
	}

	public String getText() {
		String string = "Added " + EdgeLabel.ARGUMENT + " " + argumentNumber + " from " + getSource().ID;// + " to "
				//+ getTarget().getID();
		if (argumentType == ArgumentType.PRO) {
			string = string + " in support of " + getTarget().getID();
		} else if (argumentType == ArgumentType.CON) {
			string = string + " in opposition to " + getTarget().getID();
		} else if (argumentType == ArgumentType.CON_TWO_WAY) {
			string = string + " in support and opposition to " + getTarget().getID();
		}
		return string;
	}

	public int getArgumentNumber(){
		return argumentNumber;
	}
	
	public void setType(ArgumentType type){
		this.argumentType = type;
	}
	
	public boolean containsWarrant(Warrant warrant){
		for (Warrant w: warrants){
			if (w == warrant){
				return true;
			}
		}
		return false;
	}
	
	public void removeWarrant(Warrant warrant){
		warrants.remove(warrant);
	}
	
	public void addWarrant(Warrant warrant){
		warrants.add(warrant);
	}
	
	public Statement getTarget() {
		return target;
	}

	public StatementView getTargetView() {
		return target.getView();
	}

	public ArgumentView getView() {
		return argumentView;
	}

	public ArgumentType getArgumentType() {
		return argumentType;
	}

	protected void setEdgeType() {
		edgeType = EdgeType.ARGUMENT;
	}

	public void setTarget(Statement target) {
		this.target = target;
	}

	// calls the argument view object to draw this argument
	public void draw(Graphics g) {
		argumentView.draw(g);
	}

	// is the argument connected to the passed statement?
	public boolean isConnectedTo(Statement statement) {
		if (this.getSource().equals(statement))
			return true;
		if (this.getTarget().equals(statement))
			return true;
		return false;
	}

	public ArrayList<Warrant> getWarrants(){
		return warrants;
	}
}
