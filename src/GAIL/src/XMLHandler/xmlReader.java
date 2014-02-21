package GAIL.src.XMLHandler;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * User: Tshering Tobgay
 * Date: 7/19/13
 * Get data from XML file and add to ArgStructure
 * Date from ArgStructur to GAIL
 */
public class xmlReader {

    private HashMap<String, ArrayList<StatementContainer>> container;
    private String fileName;
    final String nodeID = "node_id";

    public xmlReader(String fileName) {
        this.fileName = fileName;
        this.container = new HashMap<String, ArrayList<StatementContainer>>(4);
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
            setArrayLists(list, argType);

            NodeList list2 = doc.getElementsByTagName("hypothesis");
            argType = 'h';
            setArrayLists(list2, argType);

            NodeList list3 = doc.getElementsByTagName("data");
            argType = 'd';
            setArrayLists(list3, argType);

            NodeList list4 = doc.getElementsByTagName("generalizations");
            argType = 'g';
            setArrayLists(list4, argType);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setArrayLists(NodeList list, char argType) {
        ArrayList<StatementContainer> arrayList = new ArrayList<StatementContainer>(list.getLength());
        for (int i = 0; i < list.getLength(); i++) {
            Node nodeN = list.item(i);

            if (nodeN.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nodeN;
                arrayList.add(new StatementContainer(element.getAttribute(nodeID), element.getTextContent(), argType));
            }
        }
        switch (argType){
            case 'q':
                this.container.put("Questions", arrayList);
                break;
            case 'h':
                this.container.put("Hypotheses", arrayList);
                break;
            case 'g':
                this.container.put("Generalizations", arrayList);
                break;
            case 'd':
                this.container.put("Data", arrayList);
                break;
        }
    }

    public HashMap<String, ArrayList<StatementContainer>> getContainer() {
        return container;
    }


}
