package GAIL.src.frame;

import ArgumentComparator.ComparatorTree;
import ArgumentStructure.Generalization;

import java.util.ArrayDeque;
import java.util.List;

import javax.swing.*;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
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
        StringBuffer str = new StringBuffer();
        for (ComparatorTree tree : trees){
            str.append(buildOutput(tree));
        }
        // clear this pane.
        textPane.setText("");
        try {
//            for (int i = 0; i < s.length; i++) {
//                String d = s[i];
//                if (d.startsWith("Arg")) {
//                    doc.removeStyle("indented");
//                    doc.removeStyle("bold");
//                    if (d.contains("Pro")) {
//                        doc.setLogicalStyle(doc.getLength(), blueBoldStyle);
//                        doc.insertString(doc.getLength(), " " + d, blueBoldStyle);
//                    } else {
//                        doc.setLogicalStyle(doc.getLength(), redBoldStyle);
//                        doc.insertString(doc.getLength(), " " + d, redBoldStyle);
//                    }
//
//
//                } else {
//                    doc.removeStyle("indented");
//                    doc.removeStyle("bold");
//                    doc.setLogicalStyle(doc.getLength(), indentedStyle);
//                    doc.insertString(doc.getLength(), " " + d, indentedStyle);
//                }
//                if (i < s.length - 1) {
//                    doc.insertString(doc.getLength(), "\n", blueBoldStyle);
//                }
//            }
            textPane.setText(str.toString());
            textPane.setCaretPosition(0);
        } catch (Exception e) { e.printStackTrace(); }
    }

    private String buildOutput(ComparatorTree tree){
        StringBuffer str = new StringBuffer();
        // first see if the answer is correct and save some space.
        if (tree.isTreeCorrect()){
            str.append("Argument " + tree.getArgIndex() + " is Correct.\n\n");
            return str.toString();
        }
        ArrayDeque<ComparatorTree> treeStack = new ArrayDeque<ComparatorTree>();
        ComparatorTree workingTree;
        treeStack.push(tree);

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
                str.append("Incorrect hypothesis; " + workingTree.getUser().getHypothesis().getTEXT() + "\n\n");
            }
            // check the generalizations.
            if (!workingTree.getRoot().compareGeneralization()){
                for (Generalization gen : workingTree.getUser().getGeneralizations()){
                    str.append("Incorrect generalization: " + gen.getTEXT() + "\n\n");
                }
            }

            if (!workingTree.getRoot().compareDatum()){
                if (workingTree.getGen().getDatum().isConjunction() && !workingTree.getUser().getDatum().isConjunction()){
                    // there should be a conjuntion after the hypothesis
                    str.append("There should be a Conjunction after: " + workingTree.getUser().getHypothesis().getTEXT() + "\n\n");
                }
                else if (workingTree.getGen().getDatum().isEmptyDatum() && !workingTree.getUser().getDatum().isEmptyDatum()){
                    str.append("There should be a Hypothesis after: " + workingTree.getUser().getHypothesis().getTEXT() + "\n\n");
                }
                else {
                    // the datum is outright wrong.
                    str.append("Incorrect Datum: " + workingTree.getUser().getDatum().getTEXT());
                }
            }

            if (workingTree.hasChildren())
                treeStack.addAll(workingTree.getChildren());
        }

        return str.toString();
    }
}
