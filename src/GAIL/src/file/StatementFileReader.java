package GAIL.src.file;

import java.io.File;
import java.util.ArrayList;

import ArgumentGenerator.ArgGen;
import GAIL.src.XMLHandler.xmlReader;
import GAIL.src.controller.StatementController;
import GAIL.src.XMLHandler.StatementContainer.Node;
import KB.XMLinterface.Interface;
import KB.XMLinterface.UserInterfaceReader;

public class StatementFileReader {

    /*
    private final char DELIMETER = '@';      */
    private final String FOLDER = "src/XMLInput/";
    StatementController statementController;
    xmlReader reader;
    Interface xmlInterface;
    UserInterfaceReader uiRead;//TODO: Temporary location for ArgGen calls

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
        statementController.setText(reader.getArg());
        uiRead = new UserInterfaceReader(FOLDER + fileName);//TODO: Temporary location for ArgGen calls
        uiRead.readFile();
    }

    //TODO Test for ArgumentGenerator- Tobey 8/2/2013
    /**
     * Node ID: 1 and index 0
     Node ID: 2 and index 1
     Node ID: 3 and index 2
     Node ID: 4 and index 3
     Node ID: 5 and index 4
     Node ID: 6 and index 5
     Node ID: 7 and index 6
     Node ID: 8 and index 7
     Node ID: 9 and index 8
     Node ID: 10 and index 9
     Node ID: 11 and index 10
     Node ID: 12 and index 11
     Node ID: 13 and index 12
     Node ID: 14 and index 13
     Node ID: 15 and index 14
     Node ID: 16 and index 15
     */

    /**
     * TODO: Move away from GAIL once all is complete
     * TEMPORARY location for ArgGen initiation.
     * Initiates ArgGen with the current selected question, problemNode
     *
     * @param problemNode
     */
    public void initiateArgGen(Node problemNode) {

        int node_id = Integer.parseInt(problemNode.getNode_id());

        ArgGen argGen = new ArgGen(node_id, uiRead.getArg(), problemNode.getText(), xmlInterface.getGraph().getNodelist());
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