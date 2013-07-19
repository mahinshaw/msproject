package GAIL.src.model;

import java.io.Serializable;
import java.util.ArrayList;

import GAIL.src.view.ConjunctionView;

public class Conjunction extends Node implements Serializable {

	private static final long serialVersionUID = 2755445L;

	private ConjunctionView view; // this conjunction's visual component

	ArrayList<Branch> branches;

	public Conjunction(String ID) {
		super(ID);
		branches = new ArrayList<Branch>();
	}

	public ArrayList<Branch> getBranches(){
		return branches;
	}
	
	public void addBranch(Branch s) {
		branches.add(s);
	}

	public void removeBranch(Branch s) {
		branches.remove(s);
	}

	public boolean containsBranch(Branch s) {
		for (Branch branch: branches) {
			if (branch == s)
				return true;
		}
		return false;
	}

	public boolean containsStatement(Statement statement){
		for (Branch b: branches){
			if (b.getSource() == statement)
				return true;
		}
		return false;
	}
	
	public void setView(ConjunctionView view) {
		this.view = view;
	}

	public ConjunctionView getView() {
		return view;
	}

	public void setID(String ID) {
		this.ID = ID;
	}
}
