package ArgumentStructure;

import java.util.ArrayList;

/**
 * This class is intended to make creating argument objects easier. It leverages the Builder Pattern to create ArgumentObjects.
 *
 * Example:
 *
 * ArgumentFactory factory = new ArgumentFactory();
 * factory.setHypothesis("1", "My Hypothesis");
 * factory.setDatum("2", "My Datum");
 * factory.addGeneralization("3", "My Generalization");
 * ArgumentObject argument = factory.createArgument(1);
 *
 *
 * This allows for a thread safe static object.
 */
public class ArgumentFactory {

    private ArgumentObject argumentObject;
    private Hypothesis hypothesis;
    private ArrayList<Generalization> generalizations;
    private Datum datum;

    /**
     * Constructs an empty ArgumentFactory.
     */
    public ArgumentFactory(){
        this.hypothesis = null;
        this.generalizations = new ArrayList<Generalization>();
        this.datum = null;
    }

    /**
     * Sets the Hypothesis for the ArgumentFactory.
     * @param kbNodeID - The node ID for the knowledge base.
     * @param text - The Hypothesis text.
     */
    public void setHypothesis(String kbNodeID, String text){
        this.hypothesis = new Hypothesis(kbNodeID, text);
    }

    /**
     * Sets the Datum for the ArgumentFactory.
     * @param kbNodeID - The node ID for the knowledge base.
     * @param text - the Data text.
     */
    public void setDatum(String kbNodeID, String text){
        this.datum = new Datum(kbNodeID, text);
    }

    /**
     * Sets the Datum for the ArgumentFactory as a Conjunction.
     * @param isConjunction - Sets the Datum as a conjuction.
     */
    public void setDatum(boolean isConjunction){
        this.datum = new Datum(isConjunction);
    }

    /**
     * Tells whether or not the Hypthesis has been set for the ArgumentFactory.
     * @return - whether or not the Hypothesis is set.
     */
    public boolean isHypothesisSet(){
        return this.hypothesis != null;
    }

    /**
     * Adds a generalization to the list of Generalizations.
     * @param kbArcID - The arc ID for the knowledge base.
     * @param text - the Generalization text.
     */
    public void addGeneralization(String kbArcID, String text){
        generalizations.add(new Generalization(kbArcID, text));
    }

    /**
     * Creates a new ArgumentObject based on values in the factory.  Gracefully handles null values.
     * @param argID - Argument ID number.
     * @return - A static ArgumentObject.
     */
    public ArgumentObject createArgument(int argID){
        if (this.hypothesis == null)
            this.hypothesis = new Hypothesis();
        if (this.datum == null)
            this.datum = new Datum(false);
        this.argumentObject = new ArgumentObject.Builder().argID(argID).hypothesis(this.hypothesis).generalizations(this.generalizations).datum(this.datum).build();
        return this.argumentObject;
    }
}
