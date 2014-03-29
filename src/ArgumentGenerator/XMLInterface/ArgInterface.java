package ArgumentGenerator.XMLInterface;

import java.util.ArrayList;
import java.util.HashMap;

import KB.KB_Node.KB_Node;
import KB.XMLinterface.Interface;

/**
 * User: Tshering Tobgay
 * Date: 3/14/14
 * The interface is between ArgGen and KB. ArgGen requires access to KB's set of questions and graph
 * to compute the arguments for both ArgStructure and XML outputs.
 */
public class ArgInterface {
    private HashMap<String, String> map;
    private XmlReader reader;
    private String fileName;
    private Interface KBInterface;

    public ArgInterface(String fileName){
        this.fileName = fileName;
        this.map = new HashMap<String, String>();
        KBInterface = new Interface(fileName);
    }

    public HashMap<String, String> getMap(){
        fileReader(fileName);
        return reader.getMap();
    }

    public ArrayList<KB_Node> getGraphNodes(){
        return KBInterface.getGraph().getNodelist();
    }

    private void fileReader(String fileName){
        reader = new XmlReader(fileName);
        reader.readFile();
    }
}
