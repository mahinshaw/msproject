package ArgumentGenerator.XMLInterface;

import java.util.HashMap;

/**
 * User: Tshering Tobgay
 * Date: 3/14/14
 * The interface is between ArgGen and KB. ArgGen requires access to KB's set of questions and graph
 * to compute the arguments for both ArgStructure and XML outputs.
 */
public class Interface {
    HashMap<String, String> map;
    XmlReader reader;
    private final String FILE = "src/XMLInput/GailSchema.xml";

    public Interface(){
        map = new HashMap<String, String>();
    }

    public HashMap<String, String> getMap(){
        fileReader(FILE);
        return reader.getMap();
    }

    private void fileReader(String fileName){
        reader = new XmlReader(fileName);
        reader.readFile();
    }
}
