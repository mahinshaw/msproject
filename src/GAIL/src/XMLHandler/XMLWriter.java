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
 */
public class XMLWriter {

    public void writeXML(ArrayList<ArgStructure> argArray){
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
            question.appendChild(document.createTextNode(argArray.get(index).getQuestions().get(index)));

            Element argument = document.createElement("Argument");
            Attr argIndex = document.createAttribute("arg");
            Element hypothesis = document.createElement("Hypothesis");
            Element data = document.createElement("Data");
            Element generalization = document.createElement("Generalization");

            for (ArgStructure arg : argArray){
                // append argument
                argIndex.setValue(Integer.toString(index));
                argument.setAttributeNode(argIndex);
                session.appendChild(argument);

                // append hypothesis to argument as children
                for (ArgStructure.Node hyp : arg.getHypothesisList()){
                    hypothesis.appendChild(document.createTextNode("Node " + hyp.getNode_id()));
                    argument.appendChild(hypothesis);
                }

                // append data to argument as children
                for (ArgStructure.Node dat : arg.getDataList()){
                    data.appendChild(document.createTextNode("Node " + dat.getNode_id()));
                    argument.appendChild(data);
                }

                // append generalizations to argument as children
                for (ArgStructure.Node gen : arg.getGeneralizationList()){
                    generalization.appendChild(document.createTextNode("Node " + gen.getNode_id()));
                    argument.appendChild(generalization);
                }
            }

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult("src/output/session_" + getTimeStamp());

            transformer.transform(source, result);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

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
