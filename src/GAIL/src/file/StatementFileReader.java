package GAIL.src.file;

import java.io.File;
import java.util.ArrayList;

import ArgumentGenerator.ArgGenerator.ArgGen;
import GAIL.src.XMLHandler.StatementContainer;
import GAIL.src.XMLHandler.xmlReader;
import GAIL.src.controller.StatementController;
import KB.XMLinterface.Interface;

public class StatementFileReader {

    /* private final char DELIMETER = '@'; */
    private final String FOLDER = "src/XMLInput/";
    StatementController statementController;
    xmlReader reader;
    Interface xmlInterface;

    public StatementFileReader(StatementController statementController) {
        this.statementController = statementController;
    }

    public void readStatements(String fileName) {
        /**
         * ADDED - Tobey
         * ArrayLists are copied from the xmlReader
         * File path sent to xmlReader
         */
        reader = new xmlReader(FOLDER + fileName);
        reader.readFile();
        xmlInterface = new Interface(FOLDER + fileName);
        statementController.setText(reader.getContainer());
    }

    /**
     * TODO: Move away from GAIL once all is complete
     * TEMPORARY location for ArgGen initiation.
     * Initiates ArgGen with the current selected question, problemNode
     * problemNode - the node for the current question.
     *
     * @param problemNode
     */
    public void initiateArgGen(StatementContainer problemNode) {

        int node_id = Integer.parseInt(problemNode.getNode_id());
        ArgGen argGen = new ArgGen(node_id, problemNode.getText(), xmlInterface.getGraph().getNodelist());
        argGen.findArgument();
    }

    public String[] getFileNames() {
        ArrayList<String> fileNamesArr = new ArrayList<String>();
        File file = new File(FOLDER);
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            fileNamesArr.add(files[i].getName());
        }
        String[] fileNames = new String[fileNamesArr.size()];
        for (int i = 0; i < fileNames.length; i++) {
            fileNames[i] = fileNamesArr.get(i);
        }
        return fileNames;
    }
}