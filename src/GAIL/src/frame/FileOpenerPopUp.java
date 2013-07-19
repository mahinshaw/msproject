package GAIL.src.frame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import GAIL.src.controller.StatementController;

public class FileOpenerPopUp extends JFrame implements ListSelectionListener {
	JButton button;
	JLabel selectProblemLabel;
	DefaultListModel listModel;
	final int WIDTH = 500;
	final int HEIGHT = 500;
	int x = 350, y = 350;
	StatementController sc;
	JList list;
	JRadioButton newSessionButton;
	JRadioButton keepSessionButton;

	public void createAndShowGUI(boolean isKeepButtonEnabled){
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		//listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));)
		JLabel openFileLabel = new JLabel("Select a file to open:");
		selectProblemLabel = new JLabel("Select a problem to display");
		selectProblemLabel.setForeground(Color.LIGHT_GRAY);
		//TODO Changed -Tobey
		JComboBox fileNamesComboBox = new JComboBox(sc.getStatementFileReader().getFileNames());
        //JComboBox fileNamesComboBox = new JComboBox();
		fileNamesComboBox.setMaximumSize(new Dimension(300, 25));
		fileNamesComboBox.setBackground(Color.WHITE);
		button = new JButton("Open file");
		button.setEnabled(false);
		listModel = new DefaultListModel();
		list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.addListSelectionListener(this);
		list.setVisibleRowCount(5);
		list.setCellRenderer(new ListCell(4, 5));
		JScrollPane listScrollPane = new JScrollPane(list);
		listScrollPane.setPreferredSize(new Dimension(x, y));
		list.setEnabled(false);
		button.addActionListener(new ButtonListener());
		keepSessionButton = new JRadioButton("Keep current session", false);
		newSessionButton = new JRadioButton("Start new session", true);
		keepSessionButton.addActionListener(new RadioButtonListener());
		newSessionButton.addActionListener(new RadioButtonListener());
		Box box = Box.createVerticalBox();
		openFileLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		fileNamesComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		selectProblemLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		listScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		newSessionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		keepSessionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		box.add(Box.createVerticalStrut(3));
		box.add(openFileLabel);
		box.add(Box.createVerticalStrut(2));
		box.add(fileNamesComboBox);
		box.add(Box.createVerticalStrut(6));
		box.add(selectProblemLabel);
		box.add(listScrollPane);
		box.add(Box.createVerticalStrut(6));
		box.add(button);
		box.add(newSessionButton);
		box.add(keepSessionButton);
		panel.add(box);
		fileNamesComboBox.addActionListener(new OpenFileComboListener());
        fileNamesComboBox.setSelectedIndex(0);
		setSize(new Dimension(WIDTH, HEIGHT));
		setVisible(false);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Open file");
		add(panel);
		setContentPane(panel);
		keepSessionButton.setEnabled(isKeepButtonEnabled);
		setVisible(true);	
	}
	
	
	public FileOpenerPopUp(StatementController sc) {
		this.sc = sc;
	}

	boolean isStartingNewSession = true;

	class RadioButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (event.getSource().equals(keepSessionButton)) {
				isStartingNewSession = false;
				keepSessionButton.setSelected(true);
				newSessionButton.setSelected(false);
			} else {
				isStartingNewSession = true;
				newSessionButton.setSelected(true);
				keepSessionButton.setSelected(false);
			}
		}
	}

	String[] data;
	String problemText;
    int problemIndex;

	private class OpenFileComboListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			String fileName = (String) ((JComboBox) event.getSource()).getSelectedItem();
            sc.getStatementFileReader().readStatements(fileName);
			selectProblemLabel.setForeground(Color.BLACK);
			data = sc.getProblemText();
			listModel.clear();
			for (String s : data) {
				listModel.addElement(s);
			}
			list.setEnabled(true);
		}
	}

	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			sc.setProblem(problemText);
            sc.setProblemIndex(problemIndex);
			sc.updateAllText(isStartingNewSession);
			dispose();
		}
	}

	private class ListCell extends JTextArea implements ListCellRenderer {
		protected ListCell(int rows, int cols) {
			super(rows, cols);
			//			setMaximumSize(new Dimension(50,30));
			setLineWrap(true);
			setWrapStyleWord(true);
			setBackground(Color.WHITE);
			setBorder(BorderFactory.createLineBorder(Color.blue, 1));
		}

		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			setText("");
			append(data[index]);
			if (isSelected) {
				button.setEnabled(true);
                problemIndex = list.getSelectedIndex();
				problemText = data[problemIndex];
				setBackground(new Color(230, 240, 255));
			} else {
				setBackground(Color.white);
			}
			invalidate();
			revalidate();
			repaint();
			return new JScrollPane(this);
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
	}
}