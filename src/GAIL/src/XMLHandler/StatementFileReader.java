package GAIL.src.XMLHandler;

import java.io.File;
import java.util.ArrayList;

import GAIL.src.controller.StatementController;

public class StatementFileReader {

    /* private final char DELIMETER = '@'; */
    private final String FOLDER = "src/XMLInput/";
    StatementController statementController;
    xmlReader reader;

    public StatementFileReader(StatementController statementController) {
        this.statementController = statementController;
    }

    public void readStatements(String fileName) {
        reader = new xmlReader(FOLDER + fileName);
        reader.readFile();
        statementController.setText(reader.getContainer());
        statementController.setFileName(FOLDER+fileName);
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