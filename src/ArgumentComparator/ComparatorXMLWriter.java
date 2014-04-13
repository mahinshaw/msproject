package ArgumentComparator;

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
import java.util.List;
import java.util.Calendar;
import java.util.Date;

/**
 * User: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * Date: 3/27/14
 * github: https://github.com/mahinshaw/msproject
 *
 * The Comparator XMLWriter outputs Comparator objects to XML.  It follows the tree structure defined by the
 * ComparatorTree itself.
 */
public class ComparatorXMLWriter {
    public static int iterationValue = 1;
    public static String previousQuestion = null;
    public void writeXML(List<ComparatorTree> trees, String q) {
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

            Element iteration = document.createElement("Trial");
            if (previousQuestion == null || previousQuestion != q) {
                previousQuestion = q;
                iterationValue = 1;
            }
            else
                iterationValue += 1;
            iteration.appendChild(document.createTextNode(Integer.toString(iterationValue)));
            session.appendChild(iteration);

            for (ComparatorTree tree : trees) {
                // get the correctness of the tree.
                Element correct = document.createElement("Correct");
                correct.appendChild(document.createTextNode(Boolean.toString(tree.isTreeCorrect())));
                session.appendChild(correct);

                // get the accuracy of the argument.
                Element accuracy = document.createElement("Accuracy");
                accuracy.appendChild(document.createTextNode(Double.toString(tree.getTreeAccuracy())));
                session.appendChild(accuracy);

                // append argument
                Element argument = document.createElement("Argument");
                Attr argIndex = document.createAttribute("arg");
                argument.setAttributeNode(argIndex);
                argIndex.setValue(Integer.toString(tree.getArgIndex()));
                session.appendChild(argument);

                // print arguments in each tree.
                addArgument(document, argument, tree);
            }

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult("src/XMLOutput/Comparator_" + getTimeStamp() + ".xml");
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method prints the agrument of the specified tree.
     *
     * @param document - the document to be printed to.
     * @param element  - The element to append to.
     * @param tree     - The tree (Single Argument/Node) to be printed
     */
    private void addArgument(Document document, Element element, ComparatorTree tree) {
        // append node correctness
        Element correct = document.createElement("Correct");
        correct.appendChild(document.createTextNode(Boolean.toString(tree.isNodeCorrect())));
        element.appendChild(correct);

        // append the hypothesis to element
        Element hypothesis = document.createElement("Hypothesis");
        Element hNode = document.createElement("UserNode");
        Element hText = document.createElement("UserText");
        Element hCorrect = document.createElement("Correct");
        Element hCorrectNode = document.createElement("CorrectNode");
        Element hCorrectText = document.createElement("CorrectText");

        if (tree.getUser() == null){
            hNode.appendChild(document.createTextNode("Missing"));
            hText.appendChild(document.createTextNode("Missing"));
            hCorrect.appendChild(document.createTextNode("False"));
        }
        else {
            hNode.appendChild(document.createTextNode("UserNode " + tree.getUser().getHypothesis().getKBNODEID()));
            hText.appendChild(document.createTextNode(tree.getUser().getHypothesis().getTEXT()));
            hCorrect.appendChild(document.createTextNode(Boolean.toString(tree.getRoot().compareHypothesis())));
        }
        if (tree.getGen() == null){
            hCorrectNode.appendChild(document.createTextNode("Extraneous"));
            hCorrectText.appendChild(document.createTextNode("Extraneous"));
        }
        else {
            hCorrectNode.appendChild(document.createTextNode("Node" + tree.getGen().getHypothesis().getKBNODEID()));
            hCorrectText.appendChild(document.createTextNode(tree.getGen().getHypothesis().getTEXT()));
        }

        hypothesis.appendChild(hCorrect);
        hypothesis.appendChild(hNode);
        hypothesis.appendChild(hText);
        // add correct answer if necessary.
        if (tree.getUser() == null || !tree.getRoot().compareHypothesis()){
            hypothesis.appendChild(hCorrectNode);
            hypothesis.appendChild(hCorrectText);
        }
        element.appendChild(hypothesis);

        // append the generalizations to the element - there should only be one generalization.
        Element generalization = document.createElement("Generalization");
        Element gArc = document.createElement("Arc");
        Element gText = document.createElement("UserText");
        Element gCorrect = document.createElement("Correct");
        Element gCorrectNode = document.createElement("CorrectArc");
        Element gCorrectText = document.createElement("CorrectText");

        // if the user is null or no generalization is found AND the gen is not null and has generalizations, text and node are missing.
        if ((tree.getUser() == null || tree.getUser().getGeneralizations().isEmpty()) && !(tree.getGen() == null || tree.getGen().getGeneralizations().isEmpty())) {
            gArc.appendChild(document.createTextNode("Missing"));
            gText.appendChild(document.createTextNode("Missing"));
            gCorrect.appendChild(document.createTextNode("False"));
            // append the generalization
            generalization.appendChild(gCorrect);
            generalization.appendChild(gArc);
            generalization.appendChild(gText);
        }
        else if(!tree.getUser().getGeneralizations().isEmpty()){
            if (tree.getRoot().compareGeneralization() && tree.getGen() != null)
                gArc.appendChild(document.createTextNode("Arc " + tree.getGen().getGeneralizations().get(0).getKBARCID()));
            else
                gArc.appendChild(document.createTextNode("Arc " + tree.getUser().getGeneralizations().get(0).getKBARCID()));
            gText.appendChild(document.createTextNode(tree.getUser().getGeneralizations().get(0).getTEXT()));
            gCorrect.appendChild(document.createTextNode(Boolean.toString(tree.getRoot().compareGeneralization())));
            // append the generalization.
            generalization.appendChild(gCorrect);
            generalization.appendChild(gArc);
            generalization.appendChild(gText);
        }
        if (tree.getGen() == null || tree.getGen().getGeneralizations().isEmpty()){
            gCorrectNode.appendChild(document.createTextNode("Extraneous"));
            gCorrectText.appendChild(document.createTextNode("Extraneous"));
        }
        else if(!tree.getGen().getGeneralizations().isEmpty()) {
            gCorrectNode.appendChild(document.createTextNode("Arc " + tree.getGen().getGeneralizations().get(0).getKBARCID()));
            gCorrectText.appendChild(document.createTextNode(tree.getGen().getGeneralizations().get(0).getTEXT()));
        }


        if (tree.getUser() == null || !tree.getRoot().compareGeneralization()){
            generalization.appendChild(gCorrectNode);
            generalization.appendChild(gCorrectText);
        }
        element.appendChild(generalization);

        // append the data to the element
        Element data = document.createElement("Data");
        if (tree.hasChildren()){
            // check and see if there is a conjunction
            if (tree.hasConjunction()) {
                // see if both the user and the data have a conjunction.
                Element conjCorrect = document.createElement("ConjunctionCorrect");
                conjCorrect.appendChild(document.createTextNode(Boolean.toString(tree.getUser().HasConjunction() && tree.getGen().HasConjunction())));
                data.appendChild(conjCorrect);
                if(!tree.getUser().HasConjunction() && tree.getGen().HasConjunction()){
                    // print the user Data in UserData Tag and gen in GenData tag
                    if (tree.getUser().getDatum().isChained()) {
                        Element uArc = document.createElement("UserNode");
                        Element uText = document.createElement("UserText");
                        Element gen = document.createElement("CorrectDatum");
                        uArc.appendChild(document.createTextNode("Arc " + tree.getUser().getDatum().getKBNODEID()));
                        uText.appendChild(document.createTextNode(tree.getUser().getDatum().getTEXT()));
                        gen.appendChild(document.createTextNode("Following Conjunction"));
                        data.appendChild(uArc);
                        data.appendChild(uText);
                        data.appendChild(gen);
                    }
                }
                if(!tree.getGen().HasConjunction() && tree.getUser().HasConjunction()){
                    // print the user Data in UserData tag, and gen in Gen Data tag
                    if (!tree.getGen().getDatum().isChained()) {
                        Element user = document.createElement("IncorrectUser");
                        user.appendChild(document.createTextNode("FollowingConjunction"));
                        Element genNode = document.createElement("CorrectNode");
                        Element genText = document.createElement("CorrectText");
                        genNode.appendChild(document.createTextNode("Arc " + tree.getGen().getDatum().getKBNODEID()));
                        genText.appendChild(document.createTextNode(tree.getGen().getDatum().getTEXT()));
                        data.appendChild(user);
                        data.appendChild(genNode);
                        data.appendChild(genText);
                    }
                }
                Element conjunction = document.createElement("Conjunction");
                //conjunctions should have a multiple children, so add them
                int i = 1;
                for (ComparatorTree child : tree.getChildren()) {
                    Element conjunct = document.createElement("Conjunct" + i);
                    addArgument(document, conjunct, child);
                    conjunction.appendChild(conjunct);
                    i++;
                }
                data.appendChild(conjunction);
            }
            else { // there is no conjunction, just one child.
                for (ComparatorTree child : tree.getChildren())
                    addArgument(document, data, child);
            }
        }
        //if no children print the data
        else if (!tree.hasChildren()) {
            addDatumNode(document, data, tree);
        }

        //append the data node
        element.appendChild(data);
    }

    private void appendUserDatum(Document document, Element data, ComparatorTree tree){
        Element dNode = document.createElement("UserNode");
        Element dText = document.createElement("UserText");

        if (tree.getUser() != null){
            dNode.appendChild(document.createTextNode("Node " + tree.getUser().getDatum().getKBNODEID()));
            dText.appendChild(document.createTextNode(tree.getUser().getDatum().getTEXT()));
        }

        data.appendChild(dNode);
        data.appendChild(dText);
    }

    private void addDatumNode(Document document, Element data, ComparatorTree tree){
        Element dNode = document.createElement("UserNode");
        Element dText = document.createElement("UserText");
        Element dCorrect = document.createElement("Correct");
        Element dCorrectNode = document.createElement("CorrectNode");
        Element dCorrectText = document.createElement("CorrectText");

        if (tree.getUser() == null){
            dNode.appendChild(document.createTextNode("Missing"));
            dText.appendChild(document.createTextNode("Missing"));
            dCorrect.appendChild(document.createTextNode("False"));
        }
        else {
            dNode.appendChild(document.createTextNode("Node " + tree.getUser().getDatum().getKBNODEID()));
            dText.appendChild(document.createTextNode(tree.getUser().getDatum().getTEXT()));
            dCorrect.appendChild(document.createTextNode(Boolean.toString(tree.getRoot().compareDatum())));
        }
        if (tree.getGen() == null){
            dCorrectNode.appendChild(document.createTextNode("Extraneous"));
            dCorrectText.appendChild(document.createTextNode("Extraneous"));
        }
        else {
            dCorrectNode.appendChild(document.createTextNode(tree.getGen().getDatum().getKBNODEID()));
            dCorrectText.appendChild(document.createTextNode(tree.getGen().getDatum().getTEXT()));
        }

        data.appendChild(dCorrect);
        data.appendChild(dNode);
        data.appendChild(dText);
        if (tree.getUser() == null || !tree.getRoot().compareDatum()){
            data.appendChild(dCorrectNode);
            data.appendChild(dCorrectText);
        }
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
