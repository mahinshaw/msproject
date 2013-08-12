package GAIL.src.file;

import java.io.File;
import java.util.ArrayList;

import ArgGen.ArgBuilder;
import ArgGen.ArgumentGenerator;
import GAIL.src.XMLHandler.xmlReader;
import GAIL.src.controller.StatementController;
import KB.KB_Node.KB_Node;
import KB.XMLinterface.Interface;

public class StatementFileReader {

    /*
	private final char DELIMETER = '@';      */
	private final String FOLDER = "src/XMLInput/";
	StatementController statementController;
    ArgBuilder argBuilder;
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
        reader = new xmlReader(FOLDER+fileName);
        reader.readFile();
        xmlInterface = new Interface(FOLDER+fileName);

        //TODO Test for ArgGen- Tobey 8/2/2013
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
        ArgumentGenerator argGen = new ArgumentGenerator(xmlInterface.getGraph().getNodelist().get(6), reader.getArg());
        argGen.addArgument();

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