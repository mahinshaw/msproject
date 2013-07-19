package GAIL.src.frame;

import javax.swing.*;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;

class ArgumentDisplay extends JScrollPane {
    JTextPane textPane;
    DefaultStyledDocument doc;
    StyleContext sc;
    Style indentedStyle;
    Style blueBoldStyle;
    Style redBoldStyle;

    ArgumentDisplay(int paneWidth, int userPanelHeight) {
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

    public void appendToTextArea(String[] s) {
        textPane.setText("");
        try {
            for (int i = 0; i < s.length; i++) {
                String d = s[i];
                if (d.startsWith("Arg")) {
                    doc.removeStyle("indented");
                    doc.removeStyle("bold");
                    if (d.contains("Pro")) {
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
        } catch (Exception e) { e.printStackTrace(); } // TODO: Implement Logger?
    }
}