package ArgumentStructure;

import GAIL.src.XMLHandler.ArgStructure;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * User: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * Date: 8/6/13
 * github: https://github.com/mahinshaw/msproject
 * <p/>
 * This class handles writitng xml documents based upon the Argument Tree.
 */
public class XMLWriter {


    public void writeXML(ArrayList<ArgumentTree> trees, String q) {
        int argCount = trees.size();

        try {
            DocumentBuilderFactory docFac = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = docFac.newDocumentBuilder();

            Document document = builder.newDocument();
            Element session = document.createElement("Session");
            //set root element
            document.appendChild(session);

            // append the question to the session
            Element question = document.createElement("Question");
            session.appendChild(question);
            if (q != null) {
                question.appendChild(document.createTextNode(q));
            } else {
                question.appendChild(document.createTextNode("No question was passed."));
            }

            for (ArgumentTree tree : trees) {
                System.out.println("Tree1 " + tree.getRoot().getARGID());

                // append argument
                Element argument = document.createElement("Argument");
                Attr argIndex = document.createAttribute("arg");
                argument.setAttributeNode(argIndex);
                argIndex.setValue(Integer.toString(tree.getRoot().getARGID()));
                session.appendChild(argument);

                // find out if the root is a hypothesis
                if (tree.getRoot().isHypothesis()) {
                    // if the root is a hypothesis, print hypothesis, then print left and right children as sub-arguments
                    Element hypothesis = document.createElement("Hypothesis");
                    Element subarg1 = document.createElement("SubArgument");
                    Element subarg2 = document.createElement("SubArgument");
                    Element node = document.createElement("Node");
                    Element text = document.createElement("Text");
                    node.appendChild(document.createTextNode("Node " + tree.getRoot().getHypothesis().getKBNODEID()));
                    text.appendChild(document.createTextNode("Text " + tree.getRoot().getHypothesis().getTEXT()));
                    hypothesis.appendChild(node);
                    hypothesis.appendChild(text);
                    argument.appendChild(hypothesis);
                    // append left child
                    addArgument(document, subarg1, tree.getLeftChild());
                    // append right child
                    addArgument(document, subarg2, tree.getRightChild());
                    hypothesis.appendChild(subarg1);
                    hypothesis.appendChild(subarg2);

                } else {
                    // there should be no children.  Print the argument
                    addArgument(document, argument, tree);
                }

            }

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult("src/XMLOutput/session_" + getTimeStamp() + ".xml");

            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void addArgument(Document document, Element element, ArgumentTree tree) {

        //append the hypothesis to element
        Element hypothesis = document.createElement("Hypothesis");
        Element hNode = document.createElement("Node");
        Element hText = document.createElement("Text");
        hNode.appendChild(document.createTextNode("Node " + tree.getRoot().getHypothesis().getKBNODEID()));
        hText.appendChild(document.createTextNode(tree.getRoot().getHypothesis().getTEXT()));
        hypothesis.appendChild(hNode);
        hypothesis.appendChild(hText);
        element.appendChild(hypothesis);

        // append the generalizations to the element
        for (Argument.Generalization g : tree.getRoot().getGeneralizations()){
            Element generalization = document.createElement("Generalization");
            Element gNode = document.createElement("Node");
            Element gText = document.createElement("Text");
            gNode.appendChild(document.createTextNode("Arc " + g.getKBARCID()));
            gText.appendChild(document.createTextNode(g.getTEXT()));
            generalization.appendChild(gNode);
            generalization.appendChild(gText);
            element.appendChild(generalization);
        }

        // append the data to the element
        Element data = document.createElement("Data");
        Element dNode = document.createElement("Node");
        Element dText = document.createElement("Text");
        dNode.appendChild(document.createTextNode("Node " + tree.getRoot().getDatum().getKBNODEID()));
        dText.appendChild(document.createTextNode(tree.getRoot().getDatum().getTEXT()));
        data.appendChild(dNode);
        data.appendChild(dText);
        element.appendChild(data);

    }

    /**
     * This method was reused from the GAIL src code.
     *
     * @return - A timestamp that signifies the individual xml file.
     */
    private String getTimeStamp() {
        Calendar ca = Calendar.getInstance();
        String hour = "" + ca.get(Calendar.HOUR_OF_DAY);
        String minute = "" + ca.get(Calendar.MINUTE);
        String second = "" + ca.get(Calendar.SECOND);
        if (hour.length() < 2) {
            hour = 0 + hour;
        }
        if (minute.length() < 2) {
            minute = 0 + minute;
        }
        if (second.length() < 2) {
            second = 0 + second;
        }
        return new SimpleDateFormat("MMddyy").format(new Date()) + "_" + hour
                + "" + minute + "" + second;
    }
}
