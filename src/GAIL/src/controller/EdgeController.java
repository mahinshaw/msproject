package GAIL.src.controller;

import GAIL.src.model.*;
import GAIL.src.view.AnchorSet;
import GAIL.src.view.AnchorSet.Anchor;
import GAIL.src.model.Argument.ArgumentType;
import GAIL.src.view.ArgumentView;
import GAIL.src.view.BranchView;
import GAIL.src.view.WarrantView;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.ArrayList;

public class EdgeController implements ActionListener, Serializable, MouseListener {
	private static final long serialVersionUID = 122454L;
	private int argumentCount;
	private int warrantCount;
	private int branchCount;

	ApplicationController applicationController;
	StatementController statementController;
	ConjunctionController conjunctionController;
	private ArrayList<Argument> arguments;
	private ArrayList<Warrant> warrants;
	private ArrayList<Branch> branches;
	private ArrayList<AnchorSet> anchors;
	private String selection;
	private Edge edgeToDelete;
	boolean isArgument;
	boolean isBranch;

	public EdgeController(ApplicationController ac) {
		argumentCount = 0;
		warrantCount = 0;
		branchCount = 0;
		arguments = new ArrayList<Argument>();
		warrants = new ArrayList<Warrant>();
		branches = new ArrayList<Branch>();
		anchors = new ArrayList<AnchorSet>();
		setApplicationController(ac);

	}

	private void setApplicationController(ApplicationController ac) {
		applicationController = ac;
	}

	public Statement getTargetStatement(Conjunction conjunction) {
		Statement result = null;
		for (Argument argument : arguments) {
			if (argument.getSource().equals(conjunction))
				result = argument.getTarget();
		}
		return result;
	}

	public void createArgument(Node source, Statement target, ArgumentType type, Anchor sourceAnchor,
			AnchorSet.Anchor targetAnchor) {
		argumentCount++;
		applicationController.appendToSessionLog("ARGUMENT CREATED: number " + argumentCount + ": source = "
				+ source.getID() + " and target = " + target.getID());
		ArgumentView view = new ArgumentView(this, argumentCount, type);
		applicationController.addToDesktop(view.getLabelPanel());
		Argument connector = new Argument(source, target, view, argumentCount, type);
		view.setConnector(connector);
		arguments.add(connector);
		view.addAnchorActivePoint(target, sourceAnchor, targetAnchor);
		anchors.add(view.getAnchorSet());
	}

	public void createWarrant(Node source, Argument target) {
		warrantCount++;
		applicationController.appendToSessionLog("WARRANT CREATED: number " + warrantCount + ": source = "
				+ source.getID() + " and target = " + target.getID());
		WarrantView view = new WarrantView();
		Warrant warrant = new Warrant(source, target, view, warrantCount);
		target.addWarrant(warrant);
		view.setWarrant(warrant, source.getView().getAnchorSet().getActiveAnchor(), target.getView()
                .getAnchorSet().getActiveAnchor());
		warrants.add(warrant);
		view.setType(target.getView().getColor(), target.getArgumentType());
	}

	public void createBranch(Statement source, Conjunction target) {
		branchCount++;
		applicationController.appendToSessionLog("BRANCH CREATED: number " + branchCount + ": source = "
				+ source.getID() + " and target = " + target.getID());
		BranchView view = new BranchView();
		Branch branch = new Branch(source, target, view, branchCount);
		view.setBranch(branch);
		view.addAnchorActivePoint(source.getView().getAnchorSet().getActiveAnchor(), target.getView()
                .getAnchorSet().getActiveAnchor());
		branches.add(branch);
		target.addBranch(branch);
	}

	public void removeBranch(Branch b) {
		applicationController.appendToSessionLog("BRANCH REMOVED: " + b.getID());
		b.getTarget().removeBranch(b);
		branches.remove(b);
	}

	public void removeAnchors(Statement statement) {
		anchors.remove(statement.getView().getAnchorSet());
	}

	public void removeAnchorSet(AnchorSet as) {
		anchors.remove(as);
	}

	public void removeWarrant(Warrant w) {
		for (Argument a : arguments) {
			if (a.containsWarrant(w)) {
				a.removeWarrant(w);
				break;
			}
		}
		applicationController.appendToSessionLog("WARRANT REMOVED: " + w.getID());
		warrants.remove(w);
	}

	public void removeConjunctionEdges(Conjunction c) {
		ArrayList<Branch> branchesToRemove = new ArrayList<Branch>();
		for (Branch b : branches) {
			if (c.containsBranch(b)) {
				branchesToRemove.add(b);
			}
		}

		ArrayList<Argument> argumentToRemove = new ArrayList<Argument>();
		for (Argument argument : arguments) {
			if (argument.getSource().equals(c)) {
				applicationController.appendToSessionLog("ARGUMENT REMOVED: " + argument.getID());
				argumentToRemove.add(argument);
				applicationController.removeFromDesktop(argument.getView().getLabelPanel());
				anchors.remove(argument.getView().getAnchorSet());
			}
		}
		for (Argument argument : argumentToRemove) {
			ArrayList<Warrant> warrantsToRemove = new ArrayList<Warrant>();
			for (Warrant warrant : warrants) {
				if (argument.containsWarrant(warrant)) {
					warrantsToRemove.add(warrant);
				}
			}
			for (Warrant warrant : warrantsToRemove) {
				warrants.remove(warrant);
			}
			applicationController.removeFromDesktop(argument.getView().getLabelPanel());
			arguments.remove(edgeToDelete);
			if (edgeToDelete != null) {// TODO
				if (edgeToDelete.getID() != null) {
					applicationController.appendToSessionLog("ARGUMENT REMOVED: " + edgeToDelete.getID());
				}
			}
		}// TODO

		arguments.removeAll(argumentToRemove);
		branches.removeAll(branchesToRemove);
		anchors.remove(c.getView().getAnchorSet());
		// \ argumentCount-= argumentToRemove.size();
	}

	public void removeStatementEdges(Statement statement) {
		ArrayList<Argument> argumentsToRemove = new ArrayList<Argument>();
		ArrayList<Warrant> warrantsToRemove = new ArrayList<Warrant>();
		ArrayList<Branch> branchesToRemove = new ArrayList<Branch>();

		for (Argument argument : arguments) {
			if (argument.getSource().equals(statement) || argument.getTarget().equals(statement)) {
				argumentsToRemove.add(argument);
			}
		}

		for (Warrant warrant : warrants) {
			if (warrant.getSource().equals(statement)) {
				warrantsToRemove.add(warrant);
			}
		}

		for (Branch branch : branches) {
			if (branch.getSource().equals(statement)) {
				branchesToRemove.add(branch);
			}
		}

		for (Argument a : arguments) {
			for (Warrant w : a.getWarrants()) {
				if (w.getSource().equals(statement)) {
					a.getWarrants().clear();
					break;
				}
			}
		}

		// TODO`
		for (Argument argument : argumentsToRemove) {
			for (Warrant warrant : warrants) {
				if (argument.containsWarrant(warrant)) {
					warrantsToRemove.add(warrant);
				}
			}
			applicationController.removeFromDesktop(argument.getView().getLabelPanel());
			if (argument.getSource() instanceof Conjunction) {
				Conjunction conjunction = (Conjunction) argument.getSource();
				// System.out.println(conjunction + 33423423);
				// applicationController.removeFromDesktop(conjunction.getView()
				// .getPanel());
				// conjunctionController.removeConjunction(conjunction);
				for (Branch branch : branches) {
					if (branch.getTargetView().equals(argument.getSource().getView())) {
						// branchesToRemove.add(branch);
						System.out.println("EEEE");
					}
				}
			}

			anchors.remove(argument.getView().getAnchorSet());
			applicationController.appendToSessionLog("ARGUMENT REMOVED: " + argument.getID());
			arguments.remove(argument);
		}

		for (Warrant warrant : warrantsToRemove) {
			warrants.remove(warrant);
		}
		for (Branch branch : branchesToRemove) {
			branches.remove(branch);
		}

	}

	public void removeWarrantAssociatedWith(Node node) {
		Warrant warrantToRemove = null;
		for(Warrant w : getWarrants()) {
			if(w.getSource() == node) {
				warrantToRemove = w;
				break;
			}
		}
		if(warrantToRemove != null)
			removeWarrant(warrantToRemove);
	}

	public boolean someArgumentExists() {
		if (null == arguments)
			return false;
		if (arguments.size() > 0)
			return true;
		return false;
	}

	public boolean someWarrantExists() {
		if (null == warrants)
			return false;
		if (warrants.size() > 0)
			return true;
		return false;
	}

	public boolean someBranchExists() {
		if (null == branches)
			return false;
		if (branches.size() > 0)
			return true;
		return false;
	}

	public Warrant getWarrant(String ID) {

		Warrant result = null;

		for (Warrant connector : warrants)
			if (connector.getID().equals(ID))
				result = connector;

		return result;

	}

	public Argument getArgument(String ID) {

		Argument result = null;

		for (Argument connector : arguments)
			if (connector.getID().equals(ID))
				result = connector;

		return result;

	}

	public ArrayList<Warrant> getWarrants() {
		return warrants;
	}

	public ArrayList<Argument> getArguments() {
		return arguments;
	}

	public ArrayList<Branch> getBranches() {
		return branches;
	}

	public boolean isStatementConnected(Statement statement) {
		boolean result = false;
		for (Argument argument : arguments) {
			if (argument.getSource().equals(statement) || argument.getTarget().equals(statement)) {
				result = true;
			}
		}
		for (Warrant warrant : warrants) {
			if (warrant.getSource().equals(statement))
				result = true;
		}
		for (Branch branch : branches) {
			if (branch.getSource().equals(statement))
				result = true;
		}

		return result;
	}

	public boolean isArgumentPresent(Statement statement1, Statement statement2) {
		boolean result = false;

		for (Argument argument : arguments) {
			if (isStatementConnected(statement1)) {
				if (argument.getSource().equals(statement1) && argument.getTarget().equals(statement2))
					result = true;
				if (argument.getTarget().equals(statement1) && argument.getSource().equals(statement2))
					result = true;
			}
		}

		return result;
	}

	public void reset() {
		anchors.clear();
		arguments.clear();
		warrants.clear();
		branches.clear();
		argumentCount = 0;
		warrantCount = 0;
		branchCount = 0;

	}

	public boolean isWarrantPresent(Statement source, Argument targetConnector) {
		boolean result = false;

		for (Warrant warrant : warrants) {
			if (warrant.getSource().equals(source)) {
				if (warrant.getTarget().equals(targetConnector)) {
					result = true;
				}
			}
		}

		return result;

	}

	public boolean isStatementArgumentConnected(Statement source, Argument targetConnector) {
		if (targetConnector.getSource().equals(source))
			return true;
		if (targetConnector.getTarget().equals(source))
			return true;
		return false;

	}

	public void actionPerformed(ActionEvent ae) {
		// String action = ae.getActionCommand();
		// if (action.equals(ActionCommand.ADD_EDGE)) {
		// dialogController.createConnector();
		// }
	}

	public void setStatementController(StatementController sc) {
		statementController = sc;
	}

	public void setConjunctionController(ConjunctionController cc) {
		conjunctionController = cc;
	}

	public String getConjunctionArgumentID(Conjunction conjunction) {

		String result = null;

		for (Argument argument : arguments) {
			if (argument.getSource().equals(conjunction))
				result = argument.getID();
		}

		return result;

	}

	public String[] getUserDisplayOutput() {

        ArrayList<String> output = new ArrayList<String>();


        for (Argument argument : arguments) {
			// TITLE
			String argumentType = "";
			if (argument.getArgumentType() == Argument.ArgumentType.PRO) {
				argumentType = "Pro";
			} else if (argument.getArgumentType() == Argument.ArgumentType.CON) {
				argumentType = "Con";
			} else {
				argumentType = "Con two-way";
			}

			output.add(argument.getID() + " (" + argumentType + ")" + " consists of");
			output.add("");
			output.add("Conclusion: " + argument.getTarget().getID() + ": " + argument.getTarget().getText());

			// SOURCE CONJUNCTION TODO
			if (argument.getSource() instanceof Conjunction) {
				Conjunction conjunction = (Conjunction) argument.getSource();
				ArrayList<Branch> conjunctionStatements = conjunction.getBranches();
				for (int i = 0; i < conjunctionStatements.size(); i++) {
					Statement s = (Statement) conjunctionStatements.get(i).getSource();
					if (i == 0)
						output.add("");
					output.add("" + s.getID() + ": " + s.getText());
					if (i < conjunctionStatements.size() - 1) {
						output.add("AND");
					}
				}
			} else { // SOURCE STATEMENT
				Statement s = (Statement) argument.getSource();
				// /SPECIAL CASE: SOURCE IS HYPOTHESIS AND THERE ARE NO WARRANTS
				if (s.getText().equals(Statement.StatementType.HYPOTHESIS)
						&& argument.getWarrants().size() == 0) {
					output.add("\n\n(No supporting Data or Generalization given for the argument");
					continue;
				}
				output.add("");
				output.add("" + s.getID() + ": " + s.getText());
			}

			// /WARRANTS -- MAY BE CONJUNCTIONS OR STATEMENTS
			ArrayList<Warrant> argumentWarrants = argument.getWarrants();
			for (Warrant w : argumentWarrants) {
				// CONJUNCTIONS
				if (w.getSource() instanceof Conjunction) {
					Conjunction conjunction = (Conjunction) w.getSource();
					ArrayList<Branch> conjunctionBranches = conjunction.getBranches();
					for (int i = 0; i < conjunctionBranches.size(); i++) {
						Statement s = (Statement) conjunctionBranches.get(i).getSource();
						if (i == 0)
							output.add("");
						output.add("" + s.getID() + ": " + s.getText());
						if (i < conjunctionBranches.size() - 1) {
							output.add("AND");
						}
					}
				} else if(w.getSource() instanceof Statement) {
					// STATEMENTS
					Statement s = (Statement) w.getSource();
					output.add("");
					output.add("" + s.getID() + ": " + s.getText());
				} else if(w.getSource() instanceof MultiGeneralizationModel) {
					MultiGeneralizationModel mgm = (MultiGeneralizationModel) w.getSource();
					for(Statement g : mgm.getGeneralizations()) {
						output.add("");
						output.add(g.getID() + ": " + g.getText());
					}
				} else {
					throw new NotImplementedException();
				}
			}
		}
		String s[] = new String[output.size()];
		for (int i = 0; i < output.size(); i++) {
			s[i] = output.get(i);
		}

		return s;
	}

	public String[] getFinalOutput() {
		ArrayList<String> output = new ArrayList<String>();
		output.add("  <Arguments>");
		for (Argument a : arguments) {
			if (a.getWarrants().size() == 0) {
				output.add("    <Argument id=\"" + applicationController.removeEscapeChars(a.getID())
						+ "\" type=\"" + applicationController.removeEscapeChars(a.getArgumentType() + "")
						+ "\" source=\"" + applicationController.removeEscapeChars(a.getSource().getID())
						+ "\" target=\"" + applicationController.removeEscapeChars(a.getTarget().getID())
						+ "\" />");
			} else {
				output.add("    <Argument id=\"" + applicationController.removeEscapeChars(a.getID())
						+ "\" type=\"" + applicationController.removeEscapeChars(a.getArgumentType() + "")
						+ "\" source=\"" + applicationController.removeEscapeChars(a.getSource().getID())
						+ "\" target=\"" + applicationController.removeEscapeChars(a.getTarget().getID())
						+ "\">");
				for (Warrant w : a.getWarrants()) {
					output.add("            <Warrant id=\""
							+ applicationController.removeEscapeChars(w.getID()) + "\" source=\""
							+ applicationController.removeEscapeChars(w.getSource().getID()) + "\" />");
				}
				output.add("</Argument>");
			}
		}
		output.add("  </Arguments>");

		String[] s = new String[output.size()];
		for (int i = 0; i < output.size(); i++) {
			s[i] = output.get(i);
		}
		return s;
	}

	public void changeArgumentType(int argNumber) {
		for (Argument a : arguments) {
			if (a.getArgumentNumber() == argNumber) {
				if (a.getArgumentType() == Argument.ArgumentType.PRO) {
					this.applicationController
							.appendToSessionLog("CHANGE ARGUMENT TYPE: argument = " + a.getID() + ", from "
									+ a.getArgumentType() + " to " + Argument.ArgumentType.CON);
					a.setType(Argument.ArgumentType.CON);
				} else if (a.getArgumentType() == Argument.ArgumentType.CON) {
					this.applicationController.appendToSessionLog("CHANGE ARGUMENT TYPE: argument = "
							+ a.getID() + ", from " + a.getArgumentType() + " to "
							+ Argument.ArgumentType.CON_TWO_WAY);
					a.setType(Argument.ArgumentType.CON_TWO_WAY);
				} else if (a.getArgumentType() == Argument.ArgumentType.CON_TWO_WAY) {
					this.applicationController
							.appendToSessionLog("CHANGE ARGUMENT TYPE: argument = " + a.getID() + ", from "
									+ a.getArgumentType() + " to " + Argument.ArgumentType.PRO);
					a.setType(Argument.ArgumentType.PRO);
				}
			}
			a.getView().updateView();
			for (Warrant w : warrants) {
				if (w.getTarget() == a) {
					w.getView().setType(a.getView().getColor(), a.getArgumentType());

				}
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		JLabel l = (JLabel) e.getSource();
		String name = l.getName();
		if (name.startsWith("CHANGE_TYPE")) {
			System.out.println(name);
			changeArgumentType(Integer.parseInt(name.substring(11)));
		} else if (name.startsWith("REMOVE")) {
			selection = name.substring(7);
			isArgument = false;
			isBranch = false;
			for (Argument argument : arguments) {
				if (argument.getID().equals(selection)) {
					edgeToDelete = argument;
					isArgument = true;
					isBranch = false;
				}
			}
			for (Branch branch : branches) {
				if (branch.getID().equals(selection)) {
					edgeToDelete = branch;
					isBranch = true;
					isArgument = false;
				}
			}
			for (Warrant warrant : warrants) {
				if (warrant.getID().equals(selection)) {
					edgeToDelete = warrant;
					isBranch = false;
				}
			}
			// ///////////////////////////
			// ///////////////////////////
			// ////////////////////////////
			if (isArgument) {
				Argument argumentCurrent = (Argument) edgeToDelete;
				ArrayList<Warrant> warrantsToRemove = new ArrayList<Warrant>();
				for (Warrant warrant : warrants) {
					if (argumentCurrent.containsWarrant(warrant)) {
						warrantsToRemove.add(warrant);
					}
				}
				for (Warrant warrant : warrantsToRemove) {
					warrants.remove(warrant);
				}
				applicationController.removeFromDesktop(argumentCurrent.getView().getLabelPanel());
				arguments.remove(edgeToDelete);
				applicationController.appendToSessionLog("ARGUMENT REMOVED: " + edgeToDelete.getID());
				anchors.remove(argumentCurrent.getView().getAnchorSet());
			} else if (isBranch) {
				branches.remove(edgeToDelete);
			} else {
				warrants.remove(edgeToDelete);
			}
			// applicationController.appendToTextPanel("Removed " +
			// edgeToDelete.getID());
		} else if (name.startsWith("INFO")) {
			String itemName = name.substring(5);
			this.applicationController.appendToSessionLog("INFO BUTTON PRESSED FOR: " + itemName);
			Object o = null;
			if (itemName.startsWith("H") || itemName.startsWith("G") || itemName.startsWith("D")) {
				for (Statement s : applicationController.getStatementController().getStatements()) {
					if (itemName.equals(s.getID())) {
						o = s;
						break;
					}
				}
			} else if (itemName.startsWith("Ar")) {
				for (Argument a : arguments) {
					if (itemName.equals(a.getID())) {
						o = a;
						break;
					}
				}
			} else if (itemName.startsWith("An")) {
				for (Conjunction c : applicationController.getConjunctionController().getConjunctions()) {
					if (itemName.equals(c.getID())) {
						o = c;
						break;
					}
				}
			}
		}
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent arg0) {
	}

	public void mouseReleased(MouseEvent arg0) {
	}

	// ////////////////////
	// ///////////////////
	// //////////////////
	public void addAnchorSet(AnchorSet anchorSet) {
		anchors.add(anchorSet);
	}

	Object source;
	Object target;
	Point tempAnchorPoint;
	AnchorSet anchorSet;

	public void mousePressed(int x, int y) {
		mouseX = x;
		mouseY = y;
		if (source == null) {
			for (AnchorSet anchorSet : anchors) {
				if (anchorSet == null)
					continue;
				if (anchorSet.isTouchingAnchor(x, y, false)) {
					source = anchorSet.getParent();
					this.anchorSet = anchorSet;
					tempAnchorPoint = anchorSet.getActivePoint();
					return;
				}
			}
		}
	}

	public void mousePressed() {

	}

	public void mouseMoved(int x, int y) {
		mouseX = x;
		mouseY = y;
	}

	int mouseX, mouseY;

	public void mouseReleased(int x, int y) {
		tempAnchorPoint = null;
		if (source == null) {
			return;
		}
		for (AnchorSet anchor : anchors) {
			if (anchor == null)
				continue;
			if (anchor.isTouchingAnchor(x, y, true) && anchorSet != anchor) {
				if (anchor.getParent() != source) {
					target = anchor.getParent();
					processNewEdge();
				}
				return;
			}
		}
		source = null;
	}

	public void drawArguments(Graphics g) {
		for (Argument argument : arguments) {
			argument.draw(g);
		}
	}

	public void drawWarrants(Graphics g) {
		for (Warrant warrant : warrants) {
			warrant.draw(g);
		}
	}

	public void drawBranches(Graphics g) {
		for (Branch branch : branches) {
			branch.draw(g);
		}
	}

	public void drawLines(Graphics g) {
		if (tempAnchorPoint == null) {
			return;
		}
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(2));
		g2.setColor(Color.black);
		g2.drawLine(mouseX, mouseY, tempAnchorPoint.x, tempAnchorPoint.y);
		int y[] = { -7, 0, 7 };
		int x[] = { -7, 13, -7 };
		Graphics2D temp = (Graphics2D) g2.create();
		temp.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		temp.translate(mouseX, mouseY);
		double angle = Math.atan2(mouseY - tempAnchorPoint.y, mouseX - tempAnchorPoint.x);
		temp.rotate(angle);
		temp.fillPolygon(x, y, 3);
		temp.dispose();
	}

	void processNewEdge() {
		if (source instanceof MultiGeneralizationModel && target instanceof Argument) {
			MultiGeneralizationModel multiGenSource = (MultiGeneralizationModel) source;
			Argument argumentTarget = (Argument) target;
			if (argumentTarget.getWarrants().size() > 0)
				return;
			// TODO: check if already connected, if so, do nothing
			createWarrant(multiGenSource, argumentTarget);
		} else if(source instanceof Argument && target instanceof MultiGeneralizationModel) {
			Argument argumentSource = (Argument) source;
			MultiGeneralizationModel multiGenTarget = (MultiGeneralizationModel) target;
			if (argumentSource.getWarrants().size() > 0)
				return;
			// TODO: check if already connected, if so, do nothing
			createWarrant(multiGenTarget, argumentSource);
		} else if (source instanceof Statement && target instanceof Statement) {
			Statement statementSource = (Statement) source;
			Statement statementTarget = (Statement) target;
			if ((statementSource.getType() != Statement.StatementType.HYPOTHESIS && statementTarget.getType() != Statement.StatementType.HYPOTHESIS)) {
				source = null;
				target = null;
				return;
			}

			if (statementSource.getType() == Statement.StatementType.DATA
					&& statementTarget.getType() == Statement.StatementType.GENERALIZATION) {
				source = null;
				target = null;
				return;
			}

			if (statementSource.getType() == Statement.StatementType.GENERALIZATION
					&& statementTarget.getType() == Statement.StatementType.DATA) {
				source = null;
				target = null;
				return;
			}

			if (statementSource.getType() == Statement.StatementType.GENERALIZATION
					&& statementTarget.getType() == Statement.StatementType.HYPOTHESIS) {
				source = null;
				target = null;
				return;
			}
			if (statementSource.getType() == Statement.StatementType.HYPOTHESIS
					&& statementTarget.getType() == Statement.StatementType.GENERALIZATION) {
				source = null;
				target = null;
				return;
			}

			Statement temp = null;
			if (statementSource.getType() == Statement.StatementType.HYPOTHESIS
					&& (statementTarget.getType() == Statement.StatementType.DATA || statementTarget.getType() == Statement.StatementType.GENERALIZATION)) {
				temp = statementSource;
				statementSource = statementTarget;
				statementTarget = temp;
			}

			if (source == target || isArgumentPresent(statementSource, statementTarget)) {
				// do nothing ERROR
			} else {
				createArgument(statementSource, statementTarget, ArgumentType.PRO,
				               statementSource.getView()
					               .getAnchorSet().getActiveAnchor(),
				               statementTarget.getView().getAnchorSet()
					               .getActiveAnchor());
			}
		} else if (source instanceof Statement && target instanceof Conjunction) {
			Statement statementSource = (Statement) source;
			Conjunction conjunctionTarget = (Conjunction) target;
			if (statementSource.getType() == Statement.StatementType.GENERALIZATION) {
				source = null;
				target = null;
				statementSource = null;
				return;
			}
			if (conjunctionTarget.containsStatement(statementSource)) {
				// ERROR
			} else {
				createBranch(statementSource, conjunctionTarget);
			}
		} else if (source instanceof Statement && target instanceof Argument) {
			Statement statementSource = (Statement) source;
			Argument argumentTarget = (Argument) target;

			if (statementSource.getType() != Statement.StatementType.GENERALIZATION) {
				source = null;
				target = null;
				return;
			}
			if (argumentTarget.getWarrants().size() > 0)
				return;
			if (isStatementArgumentConnected(statementSource, argumentTarget)
					|| isWarrantPresent(statementSource, argumentTarget)) {
				// ERROR
			} else {
				createWarrant(statementSource, argumentTarget);
			}
		} else if (source instanceof Conjunction && target instanceof Statement) {
			Statement statementTarget = (Statement) target;
			Conjunction conjunctionSource = (Conjunction) source;
			
			if (statementTarget.getType() == Statement.StatementType.GENERALIZATION){
				source = null;
				target = null;
				statementTarget = null;
				conjunctionSource = null;
				return;
			}
			if (statementTarget.getType() == Statement.StatementType.DATA){
				createBranch(statementTarget, conjunctionSource);
				return;
			}

			if (conjunctionSource.containsStatement(statementTarget)) {
				// ERROR
			} else {
				createArgument(conjunctionSource, statementTarget, ArgumentType.PRO,
				               conjunctionSource
					               .getView().getAnchorSet().getActiveAnchor(),
				               statementTarget.getView().getAnchorSet()
					               .getActiveAnchor());
			}
		} else if (source instanceof Conjunction && target instanceof Conjunction) {
			// ERROR
		} else if (source instanceof Conjunction && target instanceof Argument) {
			Conjunction conjunctionSource = (Conjunction) source;
			Argument argumentTarget = (Argument) target;
			if (argumentTarget.getWarrants().size() > 0)
				return;
			for (Argument a : arguments) {
				if (a.getSource() == conjunctionSource) {
					return;
				}
			}
			createWarrant(conjunctionSource, argumentTarget);
		} else if (source instanceof Argument && target instanceof Statement) {
			Statement statementSource = (Statement) target;
			Argument argumentTarget = (Argument) source;
			if (argumentTarget.getWarrants().size() > 0)
				return;
			if (isStatementArgumentConnected(statementSource, argumentTarget)
					|| isWarrantPresent(statementSource, argumentTarget)) {
				// ERROR
			} else {
				createWarrant(statementSource, argumentTarget);
			}
			// ERROR
		} else if (source instanceof Argument && target instanceof Conjunction) {
			// ERROR
		} else if (source instanceof Argument && target instanceof Argument) {
			// ERROR
		}
		source = null;
		target = null;
	}
}