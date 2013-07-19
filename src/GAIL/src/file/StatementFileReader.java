package GAIL.src.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import GAIL.src.controller.StatementController;

public class StatementFileReader {

	private final char DELIMETER = '@';
	private final String FOLDER = "text/input/";
	StatementController statementController;

	public StatementFileReader(StatementController statementController) {
		this.statementController = statementController;
	}

	public void readStatements(String fileName) {
		ArrayList<String> problemText = new ArrayList<String>();
		ArrayList<String> hypothText = new ArrayList<String>();
		ArrayList<String> dataText = new ArrayList<String>();
		ArrayList<String> genText = new ArrayList<String>();

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
		} catch (FileNotFoundException e) {
		}
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