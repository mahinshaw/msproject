package GAIL.src.frame;

import ArgumentComparator.ComparatorTree;
import ArgumentStructure.Generalization;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

/**
 * This class prints the feedback for the Argument Comparator to the screen.
 */
public class FeedbackDisplay extends JScrollPane{
    JTextPane textPane;
    DefaultStyledDocument doc;
    StyleContext sc;
    Style indentedStyle;
    Style blueBoldStyle;
    Style redBoldStyle;

    public FeedbackDisplay(int paneWidth, int userPanelHeight) {
        sc = new StyleContext();
        doc = new DefaultStyledDocument(sc);
        textPane = new JTextPane(doc);
        textPane.setEditable(false);
        getViewport().add(textPane);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        setPreferredSize(new Dimension(paneWidth, userPanelHeight));
        setBackground(Color.white);
        textPane.setBackground(Color.white);

        Style defaultStyle = sc.getStyle(StyleContext.DEFAULT_STYLE);
        indentedStyle = sc.addStyle("indented", defaultStyle);
        StyleConstants.setLeftIndent(indentedStyle, 30);
        StyleConstants.setRightIndent(indentedStyle, 30);
        blueBoldStyle = sc.addStyle("blue_bold", defaultStyle);
        StyleConstants.setBold(blueBoldStyle, true);
        StyleConstants.setForeground(blueBoldStyle, Color.blue);

        redBoldStyle = sc.addStyle("red_bold", defaultStyle);
        StyleConstants.setBold(redBoldStyle, true);
        StyleConstants.setForeground(redBoldStyle, Color.red);
    }

    void reset() {
        textPane.setText("");
    }

    public void appendToTextArea(List<ComparatorTree> trees) {
        List<String> strings = new ArrayList<String>();
        for (ComparatorTree tree : trees){
            strings.addAll(buildOutput(tree));
        }
        String[] s = new String[strings.size()];
        for(int i = 0; i < s.length; i++){
            s[i] = strings.get(i);
        }

        // clear this pane.
        textPane.setText("");
        try {
            for (int i = 0; i < s.length; i++) {
                String d = s[i];
                if (d.startsWith("Arg")) {
                    doc.removeStyle("indented");
                    doc.removeStyle("bold");
                    if (d.contains("Correct")) {
                        doc.setLogicalStyle(doc.getLength(), blueBoldStyle);
                        doc.insertString(doc.getLength(), " " + d, blueBoldStyle);
                    } else {
                        doc.setLogicalStyle(doc.getLength(), redBoldStyle);
                        doc.insertString(doc.getLength(), " " + d, redBoldStyle);
                    }


                } else {
                    doc.removeStyle("indented");
                    doc.removeStyle("bold");
                    doc.setLogicalStyle(doc.getLength(), indentedStyle);
                    doc.insertString(doc.getLength(), " " + d, indentedStyle);
                }
                if (i < s.length - 1) {
                    doc.insertString(doc.getLength(), "\n", blueBoldStyle);
                }
            }
            textPane.setCaretPosition(0);
        } catch (Exception e) { e.printStackTrace(); }
    }

    private ArrayList<String> buildOutput(ComparatorTree tree){
        ArrayList<String> str = new ArrayList<String>();
        // first see if the answer is correct and save some space.
        if (tree.isTreeCorrect()){
            str.add("Argument " + tree.getArgIndex() + " is Correct.");
            str.add("");
            return str;
        }
        ArrayDeque<ComparatorTree> treeStack = new ArrayDeque<ComparatorTree>();
        ComparatorTree workingTree;
        treeStack.push(tree);

        str.add("Argument " + tree.getArgIndex() + " is incorrect");

        while (!treeStack.isEmpty()){
            workingTree = treeStack.pop();
            // check to see if there is a user node, if not, continue on.
            // if there is no user, then it probably doesn't have children with user input.
            if (workingTree.getUser() == null)
                continue;
            // if the node is correct continue on.
            if (workingTree.isNodeCorrect()){
                treeStack.addAll(workingTree.getChildren());
                continue;
            }
            // check the hypothesis.
            if (!workingTree.getRoot().compareHypothesis()){
                str.add("");
                str.add("Incorrect Hypothesis; " + workingTree.getUser().getHypothesis().getTEXT());
            }
            // check the generalizations.
            if (!workingTree.getRoot().compareGeneralization()){
                if (workingTree.getUser().getGeneralizations().isEmpty()){
                    str.add("");
                    str.add("Missing Generalization for Hypothesis: " + workingTree.getUser().getHypothesis().getTEXT());
                }
                for (Generalization gen : workingTree.getUser().getGeneralizations()){
                    str.add("");
                    str.add("Incorrect Generalization: " + gen.getTEXT());
                }
            }

            if (!workingTree.getRoot().compareDatum()){
                if (workingTree.getGen() == null) {
                    if (workingTree.getUser().getDatum() != null) {
                        str.add("");
                        str.add("Incorrect Datum: " + workingTree.getUser().getDatum().getTEXT());
                    }
                }
                else if (workingTree.getGen().getDatum().isConjunction() && !workingTree.getUser().getDatum().isConjunction()){
                    // there should be a conjunction after the hypothesis
                    str.add("");
                    str.add("A Conjunction should follow the Hypothesis: " + workingTree.getUser().getHypothesis().getTEXT());
                }
                else if (workingTree.getGen().getDatum().isChained() && !workingTree.getUser().getDatum().isChained()){
                    str.add("");
                    str.add("A Hypothesis should follow the Hypothesis: " + workingTree.getUser().getHypothesis().getTEXT());
                }
                else {
                    // the datum is outright wrong.
                    if (workingTree.getUser().getDatum() != null) {
                        str.add("");
                        str.add("Incorrect Datum: " + workingTree.getUser().getDatum().getTEXT());
                    }
                }
            }

            if (workingTree.hasChildren())
                treeStack.addAll(workingTree.getChildren());
        }

        str.add("");
        return str;
    }
}
