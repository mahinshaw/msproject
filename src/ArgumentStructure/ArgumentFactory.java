package ArgumentStructure;

import java.util.ArrayList;
import ArgumentStructure.Argument.Hypothesis;
import ArgumentStructure.Argument.Generalization;
import ArgumentStructure.Argument.Datum;

/**
 * User: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * Date: 8/8/13
 * github: https://github.com/mahinshaw/msproject
 *
 * This class is intended to make creating argument objects easier.
 *
 * First add in the necessary objects for the Argument,
 * then call the create method with an argument ID to create a new argument.
 *
 */
public class ArgumentFactory {

    private Argument argument;
    private Hypothesis hypothesis;
    private ArrayList<Generalization> generalizations;
    private Datum datum;

    public ArgumentFactory(){
        this.hypothesis = null;
        this.generalizations = new ArrayList<Generalization>();
        this.datum = null;
    }

    public void setHypothesis(String kbNodeID, String text){
        this.hypothesis = new Hypothesis(kbNodeID, text);
    }

    public void setDatum(String kbNodeID, String text){
        this.datum = new Datum(kbNodeID, text);
    }

    public void addGeneralization(String kbArcID, String text){
        generalizations.add(new Generalization(kbArcID, text));
    }

    public Argument createArgument(int argID){
        this.argument = new Argument.Builder().argID(argID).hypothesis(this.hypothesis).generalizations(this.generalizations).datum(this.datum).build();
        return this.argument;
    }
}