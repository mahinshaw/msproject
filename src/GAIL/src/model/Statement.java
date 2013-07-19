package GAIL.src.model;

import java.awt.Graphics;
import java.io.Serializable;

import GAIL.src.view.StatementView;
import GAIL.src.frame.StatementPanel.StatementUnit;

public class Statement extends Node implements Serializable {

	private static final long serialVersionUID = -6758405351286879706L;

	// statements can be Hypotheses, Data, or Generalizations
	public static enum StatementType {
		HYPOTHESIS, DATA, GENERALIZATION
	}

	// the four possible sources from which statements are created
	public static enum StatementSource {
		USER_HYPOTHESIS, PROBLEM_HYPOTHESIS, DATUM, GENERALIZATION
	}

	// the text that labels each type of statement
	public interface StatementLabel {
		final String HYPOTHESIS = "Hypothesis";
		final String DATUM = "Data Item";
		final String GENERALIZATION = "Generalization";
	}

	// the label that elaborates each statement's source
	public interface SourceLabel {

		// a hypothesis that is entered by the user
		final String USER_HYPOTHESIS = "HypothesisUser";

		// a "pre-written" hypothesis that restates the problem
		final String PROBLEM_HYPOTHESIS = "HypothesisGiven";

		// data come from medical records
		final String DATUM = "Data";

		// generalizations come from medical knowledge
		final String GENERALIZATION = "Generalization";
	}

	private String sourceLabel; // label with its source
	private String text; // text displayed in text area 
	private StatementView statementView; // the statement's visual component
	private StatementType type;
	public MultiGeneralizationModel group = null;

	public Statement(String ID, StatementType type, StatementSource source, String text) {
		super(ID);
		this.text = text;
		this.type = type;
		// assign source label based on its source
		switch (source) {
		case USER_HYPOTHESIS:
			sourceLabel = SourceLabel.USER_HYPOTHESIS;
			break;
		case PROBLEM_HYPOTHESIS:
			sourceLabel = SourceLabel.PROBLEM_HYPOTHESIS;
			break;
		case DATUM:
			sourceLabel = SourceLabel.DATUM;
			break;
		case GENERALIZATION:
			sourceLabel = SourceLabel.GENERALIZATION;
			break;
		}

	}

	
	
	public String getText() {
		return text;
	}

	public StatementType getType() {
		return type;
	}

	public String getSourceLabel() {
		return sourceLabel;
	}

	public StatementView getView() {
		return statementView;
	}

	public void setView(StatementView view) {
		this.statementView = view;
	}
	
	
	StatementUnit statementUnit;
	public StatementUnit getStatementUnit(){
		return statementUnit;
	}
	
	public void setStatementUnit(StatementUnit statementUnit){
		this.statementUnit = statementUnit;
	}
	
	public void draw(Graphics g) {
		statementView.draw(g);
	}
}
