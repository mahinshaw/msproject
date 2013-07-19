package GAIL.src.controller;

import GAIL.src.file.StatementFileReader;
import GAIL.src.frame.StatementPanel;
import GAIL.src.model.Statement;
import GAIL.src.model.Statement.StatementLabel;
import GAIL.src.model.Statement.StatementSource;
import GAIL.src.model.Statement.StatementType;
import GAIL.src.view.StatementView;
import GAIL.src.frame.StatementPanel.StatementUnit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class StatementController implements ActionListener, MouseListener,
		MouseMotionListener {

	private int nData;
	private int nHypotheses;
	private int nGeneralizations;

	private StatementFileReader statementFileReader;
	private ArrayList<Statement> statements;
	private ArrayList<StatementView> statementViews;
	private ArrayList<String> problemTextArr;
	private ArrayList<String> datumTextArr;
	private ArrayList<String> hypothTextArr;
	private ArrayList<String> genTextArr;
	private String[] problemText;
	private ApplicationController applicationController;
	private EdgeController edgeController;

	// From Drag and Drop stuff
	private StatementUnit draggedItem;
	private int pressedX, pressedY;
	private int oBoxX, oBoxY;
	private boolean isShiftingBox;
	private int newBoxHeight;
	private int offsetX, offsetY;
	private int mouseX, mouseY;
	private int desktopX, desktopY, desktopWidth, desktopHeight;

	private ArrayList<StatementUnit> statementUnits = new ArrayList<StatementUnit>();

	public StatementController(ApplicationController ac) {
		this.applicationController = ac;
		statementFileReader = new StatementFileReader(this);
		nData = 0;
		nHypotheses = 0;
		nGeneralizations = 0;
		edgeController = ac.getEdgeController();
		statements = new ArrayList<Statement>();
		statementViews = new ArrayList<StatementView>();
	}

	public StatementFileReader getStatementFileReader() {
		return statementFileReader;
	}

	private int getNumHypothesesCreated() {
		return nHypotheses;
	}

	private int getNumGeneralizationsCreated() {
		return nGeneralizations;
	}

	private int getNumDataCreated() {
		return nData;
	}

	private int getNumStatements() {
		return statements.size();
	}

	public ArrayList<Statement> getStatements() {
		return statements;
	}

	private String[] getStatementText() {
		ArrayList<String> results = new ArrayList<String>();

		for (Statement statement : statements) {
			results.add(statement.getText());
		}
		String[] s = new String[results.size()];
		for (int i = 0; i < results.size(); i++) {
			s[i] = results.get(i);
		}
		return s;
	}

	private Statement getStatement(String ID) {
		Statement result = null;
		for (Statement statement : statements) {
			if (statement.getID().equals(ID)) {
				result = statement;
			}
		}
		return result;
	}

	public void updateAllText(boolean isResettingSession) {
		if (isResettingSession) {
			applicationController.reset();
		}
		applicationController.getApplicationView().getLeftPanel()
				.setText(hypothTextArr, datumTextArr, genTextArr);
		applicationController.getApplicationView().getLeftPanel()
				.setProblem(problem);
	}

	private String problem;

	private String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

    public void setProblemIndex(int index) {
        applicationController.setCurrentProblem(index);
    }

	public void setText(ArrayList<String> problemTextArr,
			ArrayList<String> hypothTextArr, ArrayList<String> datumTextArr,
			ArrayList<String> genTextArr) {
		this.problemTextArr = problemTextArr;
		this.hypothTextArr = hypothTextArr;
		this.datumTextArr = datumTextArr;
		this.genTextArr = genTextArr;

	}

	private void setProblemTextArr(ArrayList<String> problemTextArr) {
		problemText = new String[problemTextArr.size()];
		for (int i = 0; i < problemTextArr.size(); i++) {
			problemText[i] = problemTextArr.get(i);
		}
	}

	public String[] getProblemText() {
		if (problemTextArr == null)
			return null;
		problemText = new String[problemTextArr.size()];
		for (int i = 0; i < problemTextArr.size(); i++) {
			problemText[i] = problemTextArr.get(i);
		}
		this.applicationController.setProblems(problemText);
        //for (String s: problemText){System.out.println(s + "asdf");}
		return problemText;
	}

	private void createProblemHypothesis(StatementSource source, String text,
			int x, int y) {
		int desktopX = applicationController.getApplicationView()
				.getDesktopViewportX();
		int desktopY = applicationController.getApplicationView()
				.getDesktopViewPortY();
		nHypotheses++;
		String ID = StatementLabel.HYPOTHESIS + " " + nHypotheses;
		Statement statement = new Statement(ID, StatementType.HYPOTHESIS,
				source, text);
		if (source == StatementSource.USER_HYPOTHESIS) {
			x = StatementView.INITIAL_X_INCREMENT;
			y = StatementView.INITIAL_Y_INCREMENT;
			for (StatementView s : statementViews) {
				if ((s.getX() - desktopX) % StatementView.INITIAL_X_INCREMENT == 0
						&& (s.getY() + desktopY)
								% StatementView.INITIAL_Y_INCREMENT == 0
						&& s.getX() > x) {
					x = s.getX() - desktopX;
					y = s.getY() + desktopY;
				}
			}
			x += StatementView.INITIAL_X_INCREMENT;
			y += StatementView.INITIAL_Y_INCREMENT;
			registerStatement(statement, true, x, y);
			applicationController
					.appendToSessionLog("STATEMENT CREATED: hypothesisUser "
							+ nHypotheses + ": " + text);
		} else {
			applicationController
					.appendToSessionLog("STATEMENT CREATED: hypothesisGiven "
							+ nHypotheses + ": " + text);
			registerStatement(statement, false, x, y);
		}

	}

	private void createDatum(StatementSource source, String text, int x, int y) {
		if (null != text) {
			nData++;
			applicationController
					.appendToSessionLog("STATEMENT CREATED: Data item " + nData
							+ ": " + text);
			String ID = StatementLabel.DATUM + " " + nData;
			Statement statement = new Statement(ID, StatementType.DATA, source,
					text);
			registerStatement(statement, false, x, y);
		}
	}

	private void createGeneralization(StatementSource source, String text,
			int x, int y) {
		if (null != text) {
			nGeneralizations++;
			applicationController
					.appendToSessionLog("STATEMENT CREATED: Generalization "
							+ nGeneralizations + ": " + text);
			String ID = StatementLabel.GENERALIZATION + " " + nGeneralizations;
			Statement statement = new Statement(ID,
					StatementType.GENERALIZATION, source, text);
			registerStatement(statement, false, x, y);
		}
	}

	private void registerStatement(Statement statement, boolean isEditable,
			int x, int y) {
		StatementView statementView = new StatementView(statement,
				applicationController, x
						+ applicationController.getDesktopViewportLocation().x,
				y + applicationController.getDesktopViewportLocation().y);
		edgeController.addAnchorSet(statementView.getAnchorSet());
		statement.setView(statementView);
		statements.add(statement);
		statementViews.add(statementView);
		applicationController.addToDesktop(statementView);
	}

	public void reset() {
		nData = 0;
		nHypotheses = 0;
		nGeneralizations = 0;
		statements.clear();
		statementViews.clear();
	}

	public void actionPerformed(ActionEvent ae) {
	}

	public String[] getFinalOutput() {
		System.out.println("state");
		ArrayList<String> output = new ArrayList<String>();
		output.add("  <Statement>");
		for (Statement s : statements) {
			output.add("    <"
					+ applicationController.removeEscapeChars(s
							.getSourceLabel()) + " id=\""
					+ applicationController.removeEscapeChars(s.getID())
					+ "\" text=\""
					+ applicationController.removeEscapeChars(s.getText())
					+ "\" xCoordinate=\"" + s.getView().getX()
					+ "\" yCoordinate=\"" + s.getView().getY() + "\" />");
		}
		output.add("  </Statement>");
		String s[] = new String[output.size()];
		for (int i = 0; i < output.size(); i++) {
			s[i] = output.get(i);
		}
		for (String ss : s)
			System.out.println(ss);
		return s;

	}

	public void closeView(Statement statement) {
		edgeController.removeStatementEdges(statement);
		statementViews.remove(statement.getView());
		edgeController.removeAnchors(statement);
		statements.remove(statement);
		if(statement.group != null) {
			statement.group.removeGeneralization(statement);
			statement.group = null;
		}
		applicationController.appendToSessionLog("STATEMENT REMOVED: "
				+ statement.getID());

	}

	private void registerEdgeController() {
		edgeController.setStatementController(this);
	}

	public void addStatementUnit(StatementUnit s) {
		statementUnits.add(s);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() instanceof JLabel) {
			JLabel l = (JLabel) e.getSource();
			if (l.getName().equals("ADD_HYP")) {
				createAndShowNewHypothesisDialog();
			}
		}
		draggedItem = null;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		isShiftingBox = false;
		mouseX = e.getXOnScreen();
		mouseY = e.getYOnScreen();
		desktopX = applicationController.getApplicationView().getDesktopXCoor();
		desktopY = applicationController.getApplicationView().getDesktopYCoor();
		desktopWidth = applicationController.getApplicationView()
				.getDesktopWidth();
		desktopHeight = applicationController.getApplicationView()
				.getDesktopHeight();
		for (StatementUnit s : statementUnits) {
			if (e.getSource() instanceof StatementUnit.TextPane) {
				StatementUnit.TextPane ss = (StatementUnit.TextPane) e
						.getSource();
				if (s == ss.getStatementUnit()) {
					pressedX = e.getXOnScreen();
					pressedY = e.getYOnScreen();
					draggedItem = ss.getStatementUnit();
					oBoxX = draggedItem.getLocationOnScreen().x;
					oBoxY = draggedItem.getLocationOnScreen().y;
					applicationController
							.getApplicationView()
							.getGlassPane()
							.activateDrag(draggedItem.getLocationOnScreen().x,
									draggedItem.getLocationOnScreen().y,
									draggedItem.getWidth(),
									draggedItem.getHeight());
					newBoxHeight = StatementView.getViewHeight(ss
							.getStatementUnit().getText());
					checkBoxLocation();
					if (pressedX > oBoxX + StatementView.STATEMENT_WIDTH
							|| pressedY > oBoxY + newBoxHeight) {
						isShiftingBox = true;
					}
					return;
				}
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		mouseX = arg0.getXOnScreen();
		mouseY = arg0.getYOnScreen();
		checkBoxLocation();
	}

	private void checkBoxLocation() {
		if (draggedItem == null)
			return;
		int boxX = oBoxX + (mouseX - pressedX) - desktopX;
		// int boxY = oBoxY - (pressedY - mouseY) - desktopY;
		applicationController.getApplicationView().getGlassPane()
				.setCoordinates(mouseX - pressedX, pressedY - mouseY);
		if (boxX > 0) {
			applicationController.getApplicationView().getGlassPane()
					.setLargeSize(newBoxHeight);
			if (isShiftingBox) {// TODO
				offsetX = mouseX - (boxX + StatementView.STATEMENT_WIDTH * 2);
				// offsetY = mouseY - (boxY + newBoxHeight*2);
				applicationController.getApplicationView().getGlassPane()
						.setOffset(offsetX, offsetY);
				isShiftingBox = false;
			}
		} else {
			if (pressedX > oBoxX + StatementView.STATEMENT_WIDTH
					|| pressedY > oBoxY + newBoxHeight) {
				isShiftingBox = true;
			}
			offsetX = 0;
			offsetY = 0;
			applicationController.getApplicationView().getGlassPane()
					.setOffset(0, 0);
			applicationController
					.getApplicationView()
					.getGlassPane()
					.setSmallSize(draggedItem.getWidth(),
							draggedItem.getHeight());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (draggedItem == null) {
			return;
		}
		int boxX = oBoxX + (mouseX - pressedX) - desktopX + offsetX;
		int boxY = oBoxY - (pressedY - mouseY) - desktopY + offsetY;
		if (boxX > 0 && boxX < desktopWidth - 20 && boxY > 0
				&& boxY < desktopHeight) {
			if (draggedItem.getType() == StatementPanel.Type.DATUM) {
				createDatum(StatementSource.DATUM, draggedItem.getText(), boxX,
						boxY);
			} else if (draggedItem.getType() == StatementPanel.Type.GENERALIZATION) {
				createGeneralization(StatementSource.GENERALIZATION,
						draggedItem.getText(), boxX, boxY);
			} else if (draggedItem.getType() == StatementPanel.Type.HYPOTHESIS) {
				createProblemHypothesis(StatementSource.PROBLEM_HYPOTHESIS,
						draggedItem.getText(), boxX, boxY);
			}
		}
		draggedItem = null;
		applicationController.getApplicationView().getGlassPane()
				.setCoordinates(e.getXOnScreen(), e.getYOnScreen());

	}

	public boolean isDragActive() {
		return draggedItem != null;
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
	}

	public void draw(Graphics g) {
		for (StatementView s : statementViews) {
			s.draw(g);
		}
	}

	private void createAndShowNewHypothesisDialog() {
		applicationController
				.appendToSessionLog("BUTTON PRESSED: create new hypothesis window");
		final JDialog d = new JDialog(
				applicationController.getApplicationView(), "Add a hypothesis",
				true);
		JPanel panel = new JPanel();
		final JTextArea textArea = new JTextArea(5, 24);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().add(textArea);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JLabel label = new JLabel("Enter a new hypothesis");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(label);
		panel.add(scrollPane);
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createProblemHypothesis(StatementSource.USER_HYPOTHESIS,
						textArea.getText(), 10,
						10);
				d.dispose();
			}
		});
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				d.dispose();
				applicationController
						.appendToSessionLog("CLOSED ADD HYPOTHESIS WINDOW WITHOUT CREATING HYPOTHESIS");
			}
		});
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(panel);
		mainPanel.add(buttonPanel);
		d.add(mainPanel);
		d.setResizable(false);
		d.setLocationRelativeTo(this.applicationController.getApplicationView());
		d.pack();
		d.setVisible(true);
	}
}