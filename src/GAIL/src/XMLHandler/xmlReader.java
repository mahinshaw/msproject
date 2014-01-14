package GAIL.src.XMLHandler;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

/**
 * User: Tshering Tobgay
 * Date: 7/19/13
 * Get data from XML file and add to ArgStructure
 * Date from ArgStructur to GAIL
 */
public class xmlReader {

    private String fileName;
    private ArgStructure arg = ArgStructure.create();
    final String nodeID = "node_id";

    public xmlReader(String fileName) {
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
            //setQuestion(list);
            /*
            for (int x = 0; x < list.getLength(); x++) {
                System.out.println(list.item(x).getAttributes().getNamedItem("node_id").getNodeValue());
            } */
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
                arg.insertNewNode(element.getAttribute(nodeID), element.getTextContent(), argType);
            }
        }
    }

    /*
    private void setQuestion(NodeList list) {
        for (int i = 0; i < list.getLength(); i++) {
            Node nodeN = list.item(i);

            if (nodeN.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nodeN;
                problemText.add(element.getTextContent());
            }
        }
        arg.insertQuestions(problemText);
    }
     */

    public ArgStructure getArg() {
        return arg;
    }


}
