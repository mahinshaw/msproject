package GAIL.src.file;

import java.io.File;
import java.util.ArrayList;

import GAIL.src.XMLHandler.xmlReader;
import GAIL.src.controller.StatementController;

public class StatementFileReader {

    /*
	private final char DELIMETER = '@';      */
	private final String FOLDER = "src/XMLInput/";
	StatementController statementController;
    xmlReader reader;

	public StatementFileReader(StatementController statementController) {
        this.statementController = statementController;
	}

	public void readStatements(String fileName) {
        /**
         * ADDED - Tobey
         * ArrayLists are copied from the xmlReader
         * File path sent to xmlReader
         */
        reader = new xmlReader(FOLDER+fileName);
        reader.readFile();

        //TODO Object file transfers. Empty ArrayList for now - Tobey
        ArrayList<String> problemText = new ArrayList<String>(reader.getProblemText());
		ArrayList<String> hypothText = new ArrayList<String>(reader.getProblemText());
		ArrayList<String> dataText = new ArrayList<String>(reader.getProblemText());
		ArrayList<String> genText = new ArrayList<String>(reader.getProblemText());

		/*
        try {
			String nextLine;
			int count = 0;
			Scanner scanner = new Scanner(new FileReader(FOLDER + fileName));
			while (scanner.hasNext()) {
				nextLine = scanner.nextLine();
				if (nextLine.isEmpty()) {
					continue;
				}
				if (count == 0) {
					if (nextLine.charAt(nextLine.length() - 1) == DELIMETER) {
						count++;
						nextLine = nextLine.substring(0, nextLine.length() - 1);
					}
					if (nextLine.length() > 0) {
						problemText.add(nextLine);
					}
				} else if (count == 1) {
					if (nextLine.charAt(nextLine.length() - 1) == DELIMETER) {
						nextLine = nextLine.substring(0, nextLine.length() - 1);
						count++;
					}
					if (nextLine.length() > 0) {
						hypothText.add(nextLine);
					}
				} else if (count == 2) {
					if (nextLine.charAt(nextLine.length() - 1) == DELIMETER) {
						nextLine = nextLine.substring(0, nextLine.length() - 1);
						count++;
					}
					if (nextLine.length() > 0) {
						dataText.add(nextLine);
					}
				} else if (count == 3) {
					if (nextLine.charAt(nextLine.length() - 1) == DELIMETER) {
						nextLine = nextLine.substring(0, nextLine.length() - 1);
						count++;
					}
					if (nextLine.length() > 0) {
						genText.add(nextLine);
					}
				}
			}
		} catch (FileNotFoundException e) {  }*/

	//statementController.setText(problemText, hypothText, dataText, genText);
      statementController.setText(reader.getArg());

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