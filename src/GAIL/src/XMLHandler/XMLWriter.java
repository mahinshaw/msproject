package GAIL.src.XMLHandler;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;

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

                //append hyp, dat, gen, to argument as children

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
