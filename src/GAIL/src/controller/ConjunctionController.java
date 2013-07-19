package GAIL.src.controller;

import GAIL.src.frame.ApplicationFrame;
import GAIL.src.model.Branch;
import GAIL.src.model.Conjunction;
import GAIL.src.view.ConjunctionView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class ConjunctionController implements ActionListener, MouseListener {

	// arrays to hold conjunctions and their views
	private static ArrayList<Conjunction> conjunctions;
	private ArrayList<ConjunctionView> conjunctionViews;

	// "pointer" to other application components
	private ApplicationFrame appView;
	//	private StatementController statementController;

	// holds options (target for new conjunctions)
	private Object[] statementOptions;
	private EdgeController edgeController;
	private ApplicationController applicationController;

	public ConjunctionController(ApplicationController ac) {
		applicationController = ac;
		edgeController = ac.getEdgeController();
		conjunctions = new ArrayList<Conjunction>();
		conjunctionViews = new ArrayList<ConjunctionView>();
	}

	public void setApplicationView(ApplicationFrame av) {
		appView = av;
	}

	public ArrayList<Conjunction> getConjunctions() {
		return conjunctions;
	}

	// registers this controller with the edge controller
	public void registerEdgeController() {
		edgeController.setConjunctionController(this);
	}

	public String[] getFinalOutput() {
		ArrayList<String> output = new ArrayList<String>();
		output.add("  <Conjunctions>");
		for (Conjunction c : conjunctions) {
			output.add("    <Conjunction id=\"" + applicationController.removeEscapeChars(c.getID())
					+ "\" xCoordinate=\"" + c.getView().getLocation().x + "\" yCoordinate=\""
					+ c.getView().getLocation().y + "\" >");
			if (c.getBranches().size() > 0) {
				output.add("      <Branches>");
				for (Branch s : c.getBranches()) {
					output.add("        <Branch source=\""
							+ applicationController.removeEscapeChars(s.getSource().getID()) + "\" />");
				}
				output.add("      </Branches>");
			}
			output.add("    </Conjunction>");
		}
		output.add("  </Conjunctions>");

		String[] s = new String[output.size()];
		for (int i = 0; i < output.size(); i++) {
			s[i] = output.get(i);
		}
		return s;
	}

	public void reset() {
		//		conjunctions.clear();
		//		conjunctionViews.clear();//TODO
		conjunctionNum = 0;
		for (Conjunction conjunction : conjunctions) {
			ConjunctionView conjunctionView = conjunction.getView();
			applicationController.removeFromDesktop(conjunctionView.getPanel());
			edgeController.removeConjunctionEdges(conjunction);
		}
		conjunctions.clear();
		conjunctionViews.clear();

	}

	public void removeConjunction(Conjunction conjunction) {
		ConjunctionView conjunctionView = conjunction.getView();
		applicationController.removeFromDesktop(conjunctionView.getPanel());
		conjunctionViews.remove(conjunctionView);
		edgeController.removeConjunctionEdges(conjunction);
		conjunctions.remove(conjunction);
		applicationController.appendToSessionLog("CONJUNCTION REMOVED: " + conjunction.getID());
	}

	int conjunctionNum = 1;

	public void createConjunction() {

		// create ID based on # of conjunctions connected to target statement
		String ID = "And " + conjunctionNum++;
		applicationController.appendToSessionLog("CONJUNCTION CREATED: number " + conjunctionNum);
		// get the default location for the new conjunction panel

		// create the new conjunction and its visual component
		Conjunction conjunction = new Conjunction(ID);
		ConjunctionView view = new ConjunctionView(conjunction, applicationController,
				appView.getDesktopWidth() - 160 + appView.getDesktopViewportX(),
				appView.getDesktopViewPortY() + 60, ID);
		edgeController.addAnchorSet(view.getAnchorSet());
		conjunction.setView(view);
		conjunctions.add(conjunction);
		conjunctionViews.add(view);
		applicationController.addToDesktop(view.getPanel());
	}

	public void actionPerformed(ActionEvent ac) {
		createConjunction();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() instanceof JLabel) {
			JLabel p = (JLabel) e.getSource();
			if (p.getName().equals("REMOVE")) {
				for (Conjunction c : conjunctions) {
					if (p == c.getView().getXLabel()) {
						removeConjunction(c);
						return;
					}
				}
			} else if (p.getName().equals("INFO")) {//TODO
			} else if (p.getName().equals("ADD_AND")) {
				createConjunction();
			}
		}
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent arg0) {
	}

	public void draw(Graphics g) {
		for (ConjunctionView conjunctionView : conjunctionViews) {
			conjunctionView.draw(g);
		}
	}
}