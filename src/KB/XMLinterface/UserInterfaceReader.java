package KB.XMLinterface;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Stack;

/**
 * User: Tshering Tobgay
 * Date: 2/6/14
 */
public class UserInterfaceReader {
    private String fileName;
    private ArgStruct arg = ArgStruct.create();
    final String NODEID = "node_id";
    final char DELIMINATOR = ',';

    public UserInterfaceReader(String fileName) {
        this.fileName = fileName;
    }

    public void readFile() {
        char argType = ' ';
        try {

            File xmlFile = new File(fileName);
            DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = fac.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("question");
            argType = 'q';

            setArgStructure(list, argType);

            NodeList list2 = doc.getElementsByTagName("hypothesis");
            argType = 'h';
            setArgStructure(list2, argType);

            NodeList list3 = doc.getElementsByTagName("data");
            argType = 'd';
            setArgStructure(list3, argType);

            NodeList list4 = doc.getElementsByTagName("generalizations");
            argType = 'g';
            setArgStructure(list4, argType);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setArgStructure(NodeList list, char argType) {
        for (int i = 0; i < list.getLength(); i++) {
            Node nodeN = list.item(i);
            if (nodeN.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nodeN;
                String node_id = String.valueOf(element.getAttributeNode(NODEID).getValue());
                Stack<String> nodeStack = checkNodeID(node_id);
                while (!nodeStack.isEmpty())
                    arg.insertNewNode(nodeStack.pop(), element.getTextContent(), argType);
            }

        }
    }

    private Stack<String> checkNodeID(String node_id) {
        Stack<String> nodeStack = new Stack<String>();
        StringBuffer str = new StringBuffer(node_id);
        StringBuffer strHold = new StringBuffer();
        //System.out.println(node_id);

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != DELIMINATOR) {
                if (str.charAt(i) != ' ')
                    strHold.append(str.charAt(i));
            }

            if (i == str.length() - 1 || str.charAt(i) == DELIMINATOR) {
                nodeStack.push(String.valueOf(strHold));
                strHold.delete(0, strHold.length());//clear the temporary str buffer
            }
        }
        return nodeStack;
    }

    public ArgStruct getArg() {
        return arg;
    }
}
