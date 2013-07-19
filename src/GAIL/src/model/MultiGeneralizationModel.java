package GAIL.src.model;

import GAIL.src.controller.ApplicationController;
import GAIL.src.view.MultiGeneralizationView;
import GAIL.src.view.NodeView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MultiGeneralizationModel extends Node {
	private ArrayList<Statement> generalizations;
	private MultiGeneralizationView view;
	private ApplicationController ac;

	public MultiGeneralizationModel(String ID, ApplicationController ac) {
		super(ID);
		generalizations = new ArrayList<Statement>(3);
		this.ac = ac;
	}

	public void setView(MultiGeneralizationView view) {
		this.view = view;
	}

	@Override
	public NodeView getView() {
		return view;
	}

	public void addGeneralization(Statement s) {
		if(s.getType() != Statement.StatementType.GENERALIZATION)
			throw new IllegalArgumentException("Statement passed was not a generalization.");

		generalizations.add(s);
		ac.appendToSessionLog("STATEMENT \"" + s.getID()
		                      + "\" ADDED TO MULTI-GENERALIZATION \"" + getID() + "\"");
		s.getView().getAnchorSet().setVisible(false);
		view.updateAnchorSet();
	}

	public void removeGeneralization(Statement s) {
		generalizations.remove(s);
		ac.appendToSessionLog("STATEMENT \"" + s.getID()
		                      + "\" REMOVED FROM MULTI-GENERALIZATION \"" + getID() + "\"");
		s.getView().getAnchorSet().setVisible(true);
		if(generalizations.size() == 1) {
			generalizations.get(0).getView().getAnchorSet().setVisible(true);
			generalizations.get(0).group = null;
			ac.appendToSessionLog("STATEMENT \"" + generalizations.get(0).getID()
			                      + "\" REMOVED FROM MULTI-GENERALIZATION \"" + getID() + "\"");
			generalizations.clear();
		}
		view.updateAnchorSet();

		if(generalizations.isEmpty())
			MultiGeneralizationFactory.recycle(this);
	}

	public List<Statement> getGeneralizations() {
		return Collections.unmodifiableList(generalizations);
	}

    public void reset() {
        for(Statement s : generalizations) {
            s.group = null;
        }
        generalizations.clear();
        view.reset();
    }
}
