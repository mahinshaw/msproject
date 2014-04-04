package ArgumentGenerator.XMLInterface;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * User: Tshering Tobgay
 * Date: 3/14/14
 */
public class XmlReader {

    private HashMap<String, String> map;
    private String fileName;
    final String NODE_ID = "node_id";
    final char DELIMITER = ',';

    protected XmlReader(String fileName) {
        this.fileName = fileName;
        this.map = new HashMap<String, String>();
    }

    protected void readFile() {
        try {
            File xmlFile = new File(fileName);
            DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = fac.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

           // NodeList list = doc.getElementsByTagName("question");
            //setMap(list);

            NodeList list2 = doc.getElementsByTagName("hypothesis");
            setMap(list2);

            NodeList list3 = doc.getElementsByTagName("data");
            setMap(list3);

            NodeList list4 = doc.getElementsByTagName("generalizations");
            setMap(list4);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setMap(NodeList list) {
        for (int i = 0; i < list.getLength(); i++) {
            Node nodeN = list.item(i);
            if (nodeN.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nodeN;
                String node_id = String.valueOf(element.getAttributeNode(NODE_ID).getValue());
                Stack<String> nodeStack = checkNodeID(node_id);
                while (!nodeStack.isEmpty())
                   map.put(nodeStack.pop(), element.getTextContent());
            }

        }
    }

    private Stack<String> checkNodeID(String node_id) {
        Stack<String> nodeStack = new Stack<String>();
        StringBuffer str = new StringBuffer(node_id);
        StringBuffer strHold = new StringBuffer();
        //System.out.println(node_id);

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != DELIMITER) {
                if (str.charAt(i) != ' ')
                    strHold.append(str.charAt(i));
            }

            if (i == str.length() - 1 || str.charAt(i) == DELIMITER) {
                nodeStack.push(String.valueOf(strHold));
                strHold.delete(0, strHold.length());//clear the temporary str buffer
            }
        }
        return nodeStack;
    }

    protected HashMap<String, String> getMap() {
        return map;
    }

}