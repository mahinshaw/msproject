//affected files: submitDialog, chatcontroller, applicationcontroller

package GAIL.src.controller;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import GAIL.src.frame.ApplicationFrame;
import GAIL.src.frame.MenuBar;
import GAIL.src.frame.SubmitDialog;
import GAIL.src.frame.TutorialDialog;
import GAIL.src.model.MultiGeneralizationFactory;
import GAIL.src.model.MultiGeneralizationModel;
import GAIL.src.model.Statement;
import GAIL.src.view.StatementView;


public class ApplicationController implements MouseListener, ActionListener {
    public static boolean noSumbitDialog = true;
    private ApplicationFrame appView;
    private EdgeController edgeController;
    private StatementController statementController;
    private ConjunctionController conjunctionController;
    private MenuBarController menuBarController;
    private ChatController chatController;

    private List<MultiGeneralizationModel> multiGeneralizations;

    public ApplicationController() {
        chatController = new ChatController(this);
        setEdgeController(new EdgeController(this));
        List<MultiGeneralizationModel> mgList = new ArrayList<MultiGeneralizationModel>();
        MultiGeneralizationFactory.setEdgeController(edgeController);
        MultiGeneralizationFactory.setApplicationController(this);
        MultiGeneralizationFactory.setModelList(mgList);
        multiGeneralizations = Collections.unmodifiableList(mgList);
        setStatementController(new StatementController(this));
        setConjunctionController(new ConjunctionController(this));
        setApplicationView(new ApplicationFrame(this));
        setMenuBarController(new MenuBarController(this));
        conjunctionController.setApplicationView(appView);
    }

    private void setEdgeController(EdgeController ec) {
        edgeController = ec;
    }

    private void setApplicationView(ApplicationFrame av) {
        appView = av;
    }

    private void setStatementController(StatementController sc) {
        statementController = sc;
    }

    private void setConjunctionController(ConjunctionController cc) {
        conjunctionController = cc;
    }

    private void setMenuBarController(MenuBarController mbc) {
        menuBarController = mbc;
    }

    public void setMenuBar(MenuBar menuBarView) {
        appView.setJMenuBar(menuBarView);
    }

    public ChatController getChatController() {
        return chatController;
    }

    public EdgeController getEdgeController() {
        return edgeController;
    }

    public ApplicationFrame getApplicationView() {
        return appView;
    }

    public StatementController getStatementController() {
        return statementController;
    }

    public ConjunctionController getConjunctionController() {
        return conjunctionController;
    }

    public MenuBarController getMenuBarController() {
        return menuBarController;
    }

    public Point getDesktopViewportLocation() {
        return new Point(appView.getDesktopViewportX(),
                appView.getDesktopViewPortY());
    }

    public void reset() {
        chatLog.clear();
        sessionLog.clear();
        edgeController.reset();
        statementController.reset();
        conjunctionController.reset();
        MultiGeneralizationFactory.reset();
        appView.resetDesktop();
    }

    public void addToDesktop(StatementView sv) {
        appView.addToDesktop(sv);
    }

    public void addToDesktop(JInternalFrame j) {
        appView.addToDesktop(j);
    }

    public void removeFromDesktop(StatementView sv) {
        appView.removeFromDesktop(sv);
    }

    public void removeFromDesktop(JInternalFrame sv) {
        appView.removeFromDesktop(sv);
    }

    public void addToDesktop(JPanel panel) {
        appView.addToDesktop(panel);
    }

    public void removeFromDesktop(JPanel panel) {
        appView.removeFromDesktop(panel);
    }

    public String getTimeStamp() {
        Calendar ca = Calendar.getInstance();
        String hour = "" + ca.get(Calendar.HOUR_OF_DAY);
        String minute = "" + ca.get(Calendar.MINUTE);
        String second = "" + ca.get(Calendar.SECOND);
        if (hour.length() < 2) {
            hour = 0 + hour;
        }
        if (minute.length() < 2) {
            minute = 0 + minute;
        }
        if (second.length() < 2) {
            second = 0 + second;
        }
        return new SimpleDateFormat("MMddyy").format(new Date()) + "_" + hour
                + "" + minute + "" + second;
    }

    //TODO: Enable file if needed by removing comment, and removing the corresponding lines below
    //final String FOLDER_FINAL_ARGUMENT = "src/GAIL/text/output/finalargument/";
    //final String FOLDER_SESSION_LOG = "src/GAIL/text/output/sessionlog/";
    final String FOLDER_FINAL_ARGUMENT = "src/output/finalargument/";
    final String FOLDER_SESSION_LOG = "src/output/sessionlog/";
    final String FOLDER_CHAT_LOG = "src/GAIL/text/output/chatlog/";
    final String FOLDER_SERIALIZED = "src/GAIL/text/output/serialized/";

    // This prints the document - OLD VERSION
    public void saveFinalArgument() {
        File file = new File("finalargument_" + getTimeStamp() + "__p"
                + currentProblem + ".xml");
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
                    FOLDER_FINAL_ARGUMENT + file.getName()));

            bufferedWriter.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            bufferedWriter.newLine();
            bufferedWriter.append("<Session time=\"" + getTimeStamp() + "\">");
            bufferedWriter.newLine();

            bufferedWriter.append("  <Problem text=\""
                    + removeEscapeChars(problems[currentProblem]) + "\" />");
            bufferedWriter.newLine();

            String t[] = statementController.getFinalOutput();
            for (String s : t) {
                bufferedWriter.append(s);
                bufferedWriter.newLine();
            }
            t = conjunctionController.getFinalOutput();
            for (String s : t) {
                bufferedWriter.append(s);
                bufferedWriter.newLine();
            }
            bufferedWriter.append("<MultiGeneralizations>");
            bufferedWriter.newLine();
            for (MultiGeneralizationModel m : multiGeneralizations) {
                bufferedWriter.append("<MultiGeneralization id=\"")
                        .append(removeEscapeChars(m.getID()))
                        .append("\">");
                bufferedWriter.newLine();
                for (Statement s : m.getGeneralizations()) {
                    bufferedWriter.append("<Generalization id=\"").append(s.getID()).append("\" />");
                    bufferedWriter.newLine();
                }
                bufferedWriter.append("</MultiGeneralization>");
                bufferedWriter.newLine();
            }
            bufferedWriter.append("</MultiGeneralizations>");
            bufferedWriter.newLine();
            t = edgeController.getFinalOutput();
            for (String s : t) {
                bufferedWriter.append(s);
                bufferedWriter.newLine();
            }
            bufferedWriter.append("</Session>");
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            //System.out.println(e);TODO: Remove comment to enable and remove next line println
            System.out.println("Chris' output file is disabled for now. Enable from here: GAIL/ApplicationController");
        }
    }

    final String ESCAPE_APOSTROPHE = "&apos;";
    final String ESCAPE_AMPERSAND = "&amp;";
    final String ESCAPE_QUOTE = "&quot;";
    final String ESCAPE_GREATER_THAN = "&gt;";
    final String ESCAPE_LESS_THAN = "&lt;";

    public String removeEscapeChars(String s) {
        String temp = "";
        char c[] = s.toCharArray();
        for (char d : c) {
            if (d == '\''/* || d == '�'*/) {
                temp += ESCAPE_APOSTROPHE;
            } else if (d == '&') {
                temp += ESCAPE_AMPERSAND;
            } else if (d == '\"'/* || d == '�' || d == '�'*/) {
                temp += ESCAPE_QUOTE;
            } else if (d == '>') {
                temp += ESCAPE_GREATER_THAN;
            } else if (d == '<') {
                temp += ESCAPE_LESS_THAN;
            } else {
                temp += d;
            }
        }
        return temp;
    }

    public void resetDesktop() {
        appView.resetDesktop();
    }

    ArrayList<String> sessionLog = new ArrayList<String>();

    public void appendToSessionLog(String string) {
        sessionLog.add(getTimeStamp() + "  " + string);
    }

    public void saveSessionLog() {
        File file = new File("sessionlog_" + getTimeStamp() + ".txt");
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
                    FOLDER_SESSION_LOG + file.getName()));
            for (String s : sessionLog) {
                bufferedWriter.append(s);
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
        }
    }

    ArrayList<String> chatLog = new ArrayList<String>();

    void appendToChatSession(String string) {
        chatLog.add(getTimeStamp() + "    " + string);
        appendToSessionLog(string);
    }

    void saveChatSession() {
        File file = new File("chatlog_" + getTimeStamp() + ".txt");
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
                    FOLDER_CHAT_LOG + file.getName()));
            for (String s : chatLog) {
                bufferedWriter.append(s);
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
        }
    }

    public void saveAllOutput() {
        saveChatSession();
        saveSessionLog();
        saveFinalArgument();

        /**
         * Create a GraphBuilder which will GAIL.output to the proper file.
         * Added by Mark Hinshaw October 2013.
         */
        ArrayList<MultiGeneralizationModel> mgmList = new ArrayList<MultiGeneralizationModel>();
        for (MultiGeneralizationModel m : multiGeneralizations) {
            mgmList.add(m);
        }
        GraphBuilder gb = new GraphBuilder(statementController, edgeController, conjunctionController, mgmList);
        ArgumentStructure.XMLWriter xmlWriter = new ArgumentStructure.XMLWriter();
        /**
         * The last parameter checks for pro or con arguments. For now, default is set to true
         * Added by Tobey T. January 2014
         */
        xmlWriter.writeXML(gb.getArgumentTrees(), problems[currentProblem]);

        // Added July 23, Mark Hinshaw
        //XMLWriter writer = new XMLWriter();

        //CHANGES- Tobey 7/25/13
        //TEST
        //writer.writeXML(edgeController.getArgStructureArrayList(), currentProblem);

    }

    private void startArgGen() {
        statementController.startArgGen();
    }


    @Override
    public void mouseClicked(MouseEvent arg0) {
        String name = ((JLabel) arg0.getSource()).getName();
        if (name.equals("CLEAR_WORKSPACE")) {
            createAndShowClearWorkspacePrompt();
        } else if (name.equals("REFRESH")) {
            appView.appendToUserPanel(edgeController.getUserDisplayOutput());
        } else if (name.equals("SUBMIT")) {
            initAndShowSubmitDialog();
            startArgGen();
        }
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        this.appendToSessionLog("REFRESHED WORKSPACE DESCRIPTION");
        appView.appendToUserPanel(edgeController.getUserDisplayOutput());
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
    }

    public void refreshArgumentToBottomPanel() {
        this.appendToSessionLog("REFRESHED WORKSPACE DESCRIPTION");
        appView.appendToUserPanel(edgeController.getUserDisplayOutput());
    }

    SubmitDialog submitDialog;

    public void initAndShowSubmitDialog() {
        //System.out.println(noSumbitDialog);
        this.saveAllOutput();
        if (ApplicationController.noSumbitDialog) {
            this.chatController
                    .sendMessageToHost(ChatController.SUBMIT_PRESSED);
            //ChatController.chatPanel.appendToTextArea(" Helper : Thanks for submitting an argument. Please wait a moment while it is being reviewed.");
        } else {
            submitDialog = new SubmitDialog(this);
        }
    }

    public static boolean isEnabled;

    public void enableSubmission() {
        isEnabled = true;
        submitDialog.enableSumbitButton();
    }

    public void disableSubmission() {
        isEnabled = false;
        submitDialog.disableSumbitButton();
    }

    public void update() {
        appendToSessionLog("SUBMITTED FINAL ARGUMENT (and saved all GAIL.output) FOR PROBLEM "
                + currentProblem);
        saveAllOutput();
        appView.resetDesktop();
        edgeController.reset();
        statementController.reset();
        conjunctionController.reset();
        MultiGeneralizationFactory.reset();
        incrementProblem();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        appendToSessionLog("SUBMITTED FINAL ARGUMENT (and saved all GAIL.output) FOR PROBLEM "
                + currentProblem);
        saveAllOutput();
        appView.resetDesktop();
        edgeController.reset();
        statementController.reset();
        conjunctionController.reset();
        MultiGeneralizationFactory.reset();
        incrementProblem();
    }

    public int getProblemCount() {
        return currentProblem;
    }

    int currentProblem = 0;
    String[] problems;

    public void setProblems(String[] problems) {
        this.problems = problems;
    }

    public void setCurrentProblem(int index) {
        currentProblem = index;
    }

    public void incrementProblem() {
        if (currentProblem < 2) {
            currentProblem++;
            this.appView.getLeftPanel().setProblem(problems[currentProblem]);
        } else
            this.appView.getLeftPanel().setProblem("");
    }

    public void createAndShowClearWorkspacePrompt() {
        appendToSessionLog("BUTTON PRESSED: clear workspace");
        final JDialog d = new JDialog(getApplicationView(), "Clear workspace?",
                true);
        JPanel panel = new JPanel();
        // panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel label = new JLabel(
                "<html><body><h4>Would you like to remove all items in the workspace?</h4><body><html>");
        // "Would you like to remove all items in the workspace?");
        panel.add(label);
        JButton okButton = new JButton("Ok");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                appendToSessionLog("CLEARED WORKSPACE");
                appView.resetDesktop();
                edgeController.reset();
                statementController.reset();
                conjunctionController.reset();
                MultiGeneralizationFactory.reset();
                d.dispose();
            }
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                d.dispose();
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
        d.setLocationRelativeTo(getApplicationView());
        d.pack();
        d.setVisible(true);
    }

    public void showTutorial() {
        appView.dim();
        TutorialDialog view = new TutorialDialog(appView);
        new TutorialController(view).init();
        view.setVisible(true);
        appView.undim();
    }
}