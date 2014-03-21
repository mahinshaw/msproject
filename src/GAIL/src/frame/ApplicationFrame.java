package GAIL.src.frame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;

import GAIL.src.controller.ApplicationController;
import GAIL.src.view.StatementView;

public class ApplicationFrame extends JFrame {

	// the content pane of this container
	private Container contentPane;

	// major components within the main frame
	private LeftPanel leftPanel;
	private Desktop desktop;
	private FileOpenerPopUp fileOpenerView;
	private ChatOptionsPopUp chatOptionsPopUp;
	public  BottomPanel bottomPanel;
	private GlassPanel glassPane;

	public ApplicationFrame(ApplicationController ac) {
		//setUndecorated(true); //TODO: reenable undecorated mode at some point...
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0,0,screenSize.width, screenSize.height);
		setTitle("MT's GAIL version 2.0");
		ToolTipManager.sharedInstance().setInitialDelay(200);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setIconImage(new ImageIcon("src/GAIL/image/icon.png").getImage());
		// create the major components
		fileOpenerView = new FileOpenerPopUp(ac.getStatementController());
		chatOptionsPopUp = new ChatOptionsPopUp(ac.getChatController(), this);
		leftPanel = new LeftPanel(ac);
		desktop = new Desktop(this, ac);
		bottomPanel = new BottomPanel(ac);
		JPanel middlePanel = new JPanel(new BorderLayout());
		middlePanel.add(desktop, BorderLayout.CENTER);
		middlePanel.add(bottomPanel, BorderLayout.SOUTH);

		// Add components to their respective tabs and set the tabbed pane to be the content
		JPanel argumentPanel = new JPanel();
		argumentPanel.setLayout(new BoxLayout(argumentPanel, BoxLayout.X_AXIS));
		argumentPanel.add(leftPanel);
		argumentPanel.add(middlePanel);

		setContentPane(argumentPanel);

		glassPane = new GlassPanel(ac);
		setGlassPane(glassPane);
		desktop.setToolbar(new Toolbar(ac));
		setVisible(true);
	}

	// calls the desktop to reset to initial state
	public void resetDesktop() {
		desktop.reset();
		bottomPanel.reset();
	}

	// adds a statement's visual component to the desktop
	public void addToDesktop(StatementView sv) {
		desktop.addToDesktop(sv);
	}

	public void addToDesktop(JInternalFrame sv) {
		desktop.addToDesktop(sv);
	}
	
	// adds a panel (argument label, etc.) to the desktop
	public void addToDesktop(JPanel panel) {
		desktop.addToDesktop(panel);
	}

	// remove a statement's visual component from the desktop
	public void removeFromDesktop(StatementView sv) {
		desktop.removeFromDesktop(sv);
	}
	
	public void removeFromDesktop(JInternalFrame sv) {
		desktop.removeFromDesktop(sv);
	}

	// removes a panel (argument label, etc.) from the desktop
	public void removeFromDesktop(JPanel panel) {
		desktop.removeFromDesktop(panel);
	}

	public void showChatOptionsView() {
		chatOptionsPopUp.setVisible(true);
	}

	public void showFileOpenView(boolean isKeepSessionButtonEnabled) {
		fileOpenerView.createAndShowGUI(isKeepSessionButtonEnabled);
	}

	public LeftPanel getLeftPanel() {
		return leftPanel;
	}

	public void appendToUserPanel(String[] s) {
		bottomPanel.appendToTextArea(s);
	}

	public GlassPanel getGlassPane(){
		return glassPane;
	}
	
	public int getDesktopWidth() {
		return desktop.getVisibleRect().width;
	}

	public int getDesktopHeight() {
		return desktop.getVisibleRect().height;

	}

	public int getDesktopYCoor() {
		return desktop.getLocationOnScreen().y;
	}

	public int getDesktopXCoor() {
		return desktop.getLocationOnScreen().x;
	}

	public int getDesktopViewportX() {
		return desktop.getViewport().getViewPosition().x;
	}

	public int getDesktopViewPortY() {
		return desktop.getViewport().getViewPosition().y;
	}
	
	public void dim(){
		glassPane.dim();
		invalidate();
		repaint();
	}
	public void undim(){
		glassPane.undim();
		invalidate();
		repaint();
	}
}