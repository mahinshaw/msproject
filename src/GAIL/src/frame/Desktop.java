///920 x 816 FOR RECORDING AREA
package GAIL.src.frame;

import java.awt.Adjustable;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.Scrollable;

import GAIL.src.controller.*;
import GAIL.src.model.Branch;
import GAIL.src.model.Warrant;
import GAIL.src.view.MultiGeneralizationView;
import GAIL.src.view.StatementView;

public class Desktop extends JScrollPane {

	public CenterDesktopPane desktop;
	final int DESKTOP_SIZE = 3500;

	public CenterDesktopPane g() {
		return desktop;
	}

	ApplicationFrame av;

	public Desktop(ApplicationFrame applicationView, ApplicationController applicationController) {
		av = applicationView;
		desktop = new CenterDesktopPane(applicationController.getEdgeController(),
				applicationController.getConjunctionController(),
				applicationController.getStatementController());
		this.getViewport().add(desktop);
		this.getHorizontalScrollBar().addAdjustmentListener(desktop);
		this.getVerticalScrollBar().addAdjustmentListener(desktop);
		setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	}

	public void setViewportPosition(int x) {
		this.getViewport().setViewPosition(new Point(x - 650, x - 650));
	}

	public void reset() {
		desktop.reset();
	}

	public void setToolbar(Toolbar t) {
		desktop.setToolbar(t);
	}

	public void addToDesktop(StatementView presentation) {
		desktop.addToDesktop(presentation);
	}

	public void addToDesktop(JInternalFrame presentation) {
		desktop.addToDesktop(presentation);
	}

	public void addToDesktop(JPanel panel) {
		desktop.addToDesktop(panel);
	}

	public void removeFromDesktop(JPanel panel) {
		desktop.removeFromDesktop(panel);
	}

	public void removeFromDesktop(JInternalFrame panel) {
		desktop.removeFromDesktop(panel);
	}

	public void removeFromDesktop(StatementView sv) {
		desktop.removeFromDesktop(sv);
	}

	// Quick and dirty __TEMPORARY__ hook for MultiGeneralizationView
	public static ArrayList<MultiGeneralizationView> mgvList // TODO: Remove temporary hook.
							= new ArrayList<MultiGeneralizationView>();

	class CenterDesktopPane extends JDesktopPane implements Scrollable, Runnable, MouseListener,
			MouseMotionListener, AdjustmentListener {
		//	//957 520
		EdgeController edgeController;
		ConjunctionController conjunctionController;
		ChatController chatController;
		StatementController statementController;
		Toolbar toolbar;

		int initPosition = DESKTOP_SIZE/2;
		
		public CenterDesktopPane(EdgeController edgeController, ConjunctionController conjunctionController,
				StatementController statementController) {
			setPreferredSize(new Dimension(DESKTOP_SIZE, DESKTOP_SIZE));
			this.edgeController = edgeController;
			this.conjunctionController = conjunctionController;
			this.statementController = statementController;
			this.addMouseListener(this);
			this.addMouseMotionListener(this);
			new Thread(this).start();
			
		}

		int initToolbarX, initToolbarY;

		public void setToolbar(Toolbar t) {//TODO 1275    615 recording            845 with cam at 
			this.toolbar = t;
			add(toolbar.getPanel());
			toolbar.setLocation(initPosition + 957 - toolbar.getPanel().getWidth() - 18, 0);
			initToolbarX = 957 - toolbar.getPanel().getWidth() - 17;
			initToolbarY = 0;
			setViewportPosition(initPosition);
		}

		public Dimension getPreferredScrollableViewportSize() {
			return getPreferredSize();
		}

		public int getScrollableUnitIncrement(Rectangle r, int axis, int dir) {
			return 50;
		}

		public int getScrollableBlockIncrement(Rectangle r, int axis, int dir) {
			return 200;
		}

		public boolean getScrollableTracksViewportWidth() {
			return false;
		}

		public boolean getScrollableTracksViewportHeight() {
			return false;
		}

		//TODO
		public void addToDesktop(StatementView presentation) {
			JInternalFrame frame = presentation.getFrame();
			this.add(frame);
			this.getDesktopManager().activateFrame(frame);
		}

		public void addToDesktop(JInternalFrame presentation) {
			JInternalFrame frame = presentation;
			this.add(frame);
			this.getDesktopManager().activateFrame(frame);
		}

		public void addToDesktop(JPanel panel) {
			this.add(panel);
		}

		public void removeFromDesktop(StatementView sv) {
			this.remove(sv.getFrame());
		}

		public void removeFromDesktop(JInternalFrame sv) {
			sv.dispose();
		}

		public void removeFromDesktop(JPanel panel) {
			this.remove(panel);
		}

		public void paintComponent(Graphics g) {
			for(MultiGeneralizationView v : mgvList) // TODO: Remove temporary hook.
				v.tempDraw(g);
			if (this.conjunctionController != null) {
				conjunctionController.draw(g);
			}
			if (statementController != null) {
				statementController.draw(g);
			}
			if (edgeController != null) {
				edgeController.drawLines(g);
				if (edgeController.someArgumentExists()) {
					edgeController.drawArguments(g);
				}

				if (edgeController.someWarrantExists()) {
					edgeController.drawWarrants(g);
				}

				if (edgeController.someBranchExists()) {
					edgeController.drawBranches(g);
				}
			}
		}

		public void reset() {
			this.removeAll();
			add(toolbar.getPanel());
		}

		private boolean isRunning = true;

		public void run() {
			while (isRunning) {
				try {
					Thread.sleep(18);
				} catch (InterruptedException e) {
				}
				repaint();
			}
		}

		public void mouseDragged(MouseEvent e) {
			edgeController.mouseMoved(e.getX(), e.getY());
			mouseX = e.getX();
			mouseY = e.getY();
		}

		int mouseX, mouseY;

		public void mouseMoved(MouseEvent e) {
			edgeController.mouseMoved(e.getX(), e.getY());
			mouseX = e.getX();
			mouseY = e.getY();
		}

		public void mouseClicked(MouseEvent e) {
		
			for (Warrant w : edgeController.getWarrants()) {
				if (w.getView().isClicked(e.getX(), e.getY())) {
					edgeController.removeWarrant(w);
					return;
				}
			}
			for (Branch b : edgeController.getBranches()) {
				if (b.getView().isClicked(e.getX(), e.getY())) {
					edgeController.removeBranch(b);
					return;
				}
			}
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
			edgeController.mousePressed(e.getX(), e.getY());
		}

		public void mouseReleased(MouseEvent e) {
			edgeController.mouseReleased(e.getX(), e.getY());
		}

		@Override
		public void adjustmentValueChanged(AdjustmentEvent arg0) {
			Adjustable o = arg0.getAdjustable();
			int value = arg0.getValue();
			if (o.getOrientation() == Adjustable.HORIZONTAL) {
				toolbar.getPanel().setLocation(initToolbarX + value, toolbar.getPanel().getLocation().y);
			} else if (o.getOrientation() == Adjustable.VERTICAL) {
				toolbar.getPanel().setLocation(toolbar.getPanel().getLocation().x, value);
			}
		}
	}
}
