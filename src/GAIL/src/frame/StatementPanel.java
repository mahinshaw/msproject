package GAIL.src.frame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

import GAIL.src.controller.StatementController;
import GAIL.src.view.ColorPalette;

public class StatementPanel extends JPanel {

	public boolean isMinimized;

	public void s() {
		if (selector == null || selector.scrollPane == null)
			return;
		selector.scrollPane.getVerticalScrollBar().setValue(0);
	}

	boolean isS;
	public boolean isSet() {

		if (selector == null || selector.scrollPane == null
				|| selector.scrollPane.getVerticalScrollBar() == null) {
			return false;
		}
		if (selector.scrollPane.getVerticalScrollBar().getValue() != 0 || !selector.scrollPane.getVerticalScrollBar().isVisible() || isS) {
			isS=true;
			return true;
		}

		return false;
	}

	StatementController sc;
	Type type;
	StatementSelector selector;

	public StatementPanel(StatementController sc, Type type) {
		this.type = type;
		this.sc = sc;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		selector = new StatementSelector();
		add(selector);
	}

	public void setText(ArrayList<String> text) {
		remove(selector);
		selector = new StatementSelector();
		selector.setText(text);
		add(selector);
		selector.scrollPane.getVerticalScrollBar().setValue(0);
		s();
	}

	class StatementSelector extends JPanel {
		JPanel holder;
		JScrollPane scrollPane;

		public void setText(ArrayList<String> text) {
			holder = new JPanel();
			holder.setLayout(new BoxLayout(holder, BoxLayout.Y_AXIS));
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			for (String string : text) {
				StatementUnit statement = new StatementUnit(string);
				holder.add(statement);
				sc.addStatementUnit(statement);
			}
			scrollPane = new JScrollPane();
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			holder.setBackground(Color.white);
			scrollPane.getViewport().add(holder);
			add(scrollPane);
		}
	}

	private int hypothesisCount, dataCount, genCount;

	public class StatementUnit extends JPanel {

		TextPane textPane;
		String text;

		public StatementUnit(String text) {
			String name = "";
			this.text = text;
			addMouseListener(sc);
			addMouseMotionListener(sc);
			setLayout(new GridLayout(0, 1));
			setName(type + "");
			JPanel outerBorderPanel = new JPanel(new GridLayout(0, 1));
			JPanel innerVHolder = new JPanel();
			int borderSize = 5;
			Border border = null;
			if (ColorPalette.isColorOn) {
				if (type == Type.DATUM) {
					name = type + "" + dataCount;
					setName(name);
					dataCount++;
					outerBorderPanel.setBackground(ColorPalette.DATUM_COLOR);
					innerVHolder.setBackground(ColorPalette.DATUM_COLOR);
					border = BorderFactory.createLineBorder(ColorPalette.DATUM_BORDER_COLOR, borderSize);
				} else if (type == Type.GENERALIZATION) {
					name = type + "" + genCount;
					setName(name);
					genCount++;
					outerBorderPanel.setBackground(ColorPalette.GEN_COLOR);
					innerVHolder.setBackground(ColorPalette.GEN_COLOR);
					border = BorderFactory.createLineBorder(ColorPalette.GEN_BORDER_COLOR, borderSize);
				} else if (type == Type.HYPOTHESIS) {
					name = type + "" + hypothesisCount;
					hypothesisCount++;
					setName(name);
					outerBorderPanel.setBackground(ColorPalette.HYPOT_COLOR);
					innerVHolder.setBackground(ColorPalette.HYPOT_COLOR);
					border = BorderFactory.createLineBorder(ColorPalette.HYPOT_BORDER_COLOR, borderSize);
				}
			}
			innerVHolder.setBorder(border);

			JTextPane dummyPane = new JTextPane();
			dummyPane.setSize(LeftPanel.LEFT_PANEL_WIDTH - 40, 1000);
			dummyPane.setText(text);
			textPane = new TextPane(this);
			textPane.setFocusable(false);
			textPane.setEditable(false);
			textPane.addMouseListener(sc);
			textPane.addMouseMotionListener(sc);
			textPane.setName(name);

			textPane.setPreferredSize(new Dimension(22, dummyPane.getPreferredSize().height));
			textPane.setText(text);
			outerBorderPanel.setBorder(border);
			outerBorderPanel.add(textPane);
			add(outerBorderPanel);
		}

		public Type getType() {
			return type;
		}

		public String getText() {
			return text;
		}

		public void setTextColor(Color color) {
			textPane.setForeground(color);
		}

		public class TextPane extends JTextPane {
			StatementUnit s;

			public TextPane(StatementUnit s) {
				this.s = s;
			}

			public StatementUnit getStatementUnit() {
				return s;
			}

		}
	}

	public enum Type {
		GENERALIZATION, DATUM, HYPOTHESIS
	}
}
