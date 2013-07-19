package GAIL.src.file;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

/**
 * User: Tshering Tobgay
 * Date: 7/19/13
 */
public class xmlReader {

    private String fileName;
    private ArrayList<String> problemText = new ArrayList<String>();
    private ArrayList<String> hypothText = new ArrayList<String>();
    private ArrayList<String> dataText = new ArrayList<String>();
    private ArrayList<String> genText = new ArrayList<String>();

    public xmlReader(String fileName){
        this.fileName = fileName;
    }
    public void readFile() {
        try {

            File xmlFile = new File(fileName);
            DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = fac.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            NodeList list1 = doc.getElementsByTagName("question");
            setQuestion(list1);

            NodeList list2 = doc.getElementsByTagName("hypothesis");
            setHypothesis(list2);

            NodeList list3 = doc.getElementsByTagName("data");
            setData(list3);

            NodeList list4 = doc.getElementsByTagName("generalizations");
            setGen(list4);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setData(NodeList list3) {
        for (int i = 0; i < list3.getLength(); i++) {
            Node nodeN = list3.item(i);

            if (nodeN.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nodeN;
                dataText.add(element.getTextContent());
            }
        }
    }

    private void setGen(NodeList list4) {
        for (int i = 0; i < list4.getLength(); i++) {
            Node nodeN = list4.item(i);

            if (nodeN.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nodeN;
                genText.add(element.getTextContent());
            }
        }

    }

    private void setHypothesis(NodeList list) {
        for (int i = 0; i < list.getLength(); i++) {
            Node nodeN = list.item(i);

            if (nodeN.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nodeN;
                hypothText.add(element.getTextContent());
            }
        }

    }

    private void setQuestion(NodeList list) {
        for (int i = 0; i < list.getLength(); i++) {
            Node nodeN = list.item(i);

            if (nodeN.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nodeN;
                problemText.add(element.getTextContent());
            }
        }

    }

    public ArrayList<String> getProblemText() {
        return problemText;
    }

    public ArrayList<String> getHypothText() {
        return hypothText;
    }

    protected ArrayList<String> getDataText() {
        return dataText;
    }

    protected ArrayList<String> getGenText() {
        return genText;
    }

}
