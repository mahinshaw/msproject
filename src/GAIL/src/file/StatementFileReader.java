package GAIL.src.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import GAIL.src.controller.StatementController;

public class StatementFileReader {

<<<<<<< HEAD
    /*
	private final char DELIMETER = '@';      */
	private final String FOLDER = "src/XMLInput/";
=======
	private final char DELIMETER = '@';
	private final String FOLDER = "src/GAIL/text/input/";
>>>>>>> 5546243775cf55e7b078475726940223d5a3756e
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

        ArrayList<String> problemText = new ArrayList<String>(reader.getProblemText());
		ArrayList<String> hypothText = new ArrayList<String>(reader.getHypothText());
		ArrayList<String> dataText = new ArrayList<String>(reader.getDataText());
		ArrayList<String> genText = new ArrayList<String>(reader.getGenText());

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

		statementController.setText(problemText, hypothText, dataText, genText);
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