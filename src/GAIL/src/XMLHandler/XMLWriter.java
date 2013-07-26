package GAIL.src.XMLHandler;

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
 * Date: 7/22/13
 * github: https://github.com/mahinshaw/msproject
 *
 * This class is used to output the information passed from an array of ArgStructures and print them as
 * arguments in XML.
 */
public class XMLWriter {

    /**
     * This class handles the writing of the xml.
     * @param argArray - arraylist of ArgStructures to be written.
     * @param qIndex - Index of the question being answered in the questions arraylist.
     */
    public void writeXML(ArrayList<ArgStructure> argArray, int qIndex){
        int argCount = argArray.size();
        int index = 0;

        try{
            DocumentBuilderFactory docFac = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = docFac.newDocumentBuilder();

            Document document = builder.newDocument();
            Element session = document.createElement("Session");
            //set root element
            document.appendChild(session);

            // append the question to the session
            Element question = document.createElement("Question");
            session.appendChild(question);
            if (!argArray.get(index).getQuestions().isEmpty()){
                question.appendChild(document.createTextNode(argArray.get(index).getQuestions().get(qIndex)));
            }
            else {
                question.appendChild(document.createTextNode("No question was passed."));
            }

            for (int i =0; i < argCount; i++){
                // append argument
                Element argument = document.createElement("Argument");
                Attr argIndex = document.createAttribute("arg");
                argument.setAttributeNode(argIndex);
                argIndex.setValue(Integer.toString(i));
                session.appendChild(argument);

                // append hypothesis to argument as children
                for (ArgStructure.Node hyp : argArray.get(i).getHypothesisList()){
                    Element hypothesis = document.createElement("Hypothesis");
                    System.out.println("Hyp node id: "+hyp.getNode_id());
                    hypothesis.appendChild(document.createTextNode("Node " + hyp.getNode_id()));
                    argument.appendChild(hypothesis);
                }

                // append data to argument as children
                for (ArgStructure.Node dat : argArray.get(i).getDataList()){
                    Element data = document.createElement("Data");
                    System.out.println("Dat node id: "+dat.getNode_id());
                    data.appendChild(document.createTextNode("Node " + dat.getNode_id()));
                    argument.appendChild(data);
                }

                // append generalizations to argument as children
                for (ArgStructure.Node gen : argArray.get(i).getGeneralizationList()){
                    Element generalization = document.createElement("Generalization");
                    System.out.println("Gen node id: "+gen.getNode_id());
                    generalization.appendChild(document.createTextNode("Node " + gen.getNode_id()));
                    argument.appendChild(generalization);
                }
            }

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult("src/XMLOutput/session_" + getTimeStamp() + ".xml");

            transformer.transform(source, result);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This method was reused from the GAIL src code.
     * @return - A timestamp that signifies the individual xml file.
     */
    public String getTimeStamp() {
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
