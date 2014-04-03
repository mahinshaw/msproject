package ArgumentStructure;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a container for Arguments. Each class contains a Hypothesis, a Datum, and Generalizations.
 * This class is the building block for the ArgumentTree.
 *
 * Creation of ArgumentObjects should be handled through the ArgumentFactory.
 */
public class ArgumentObject {

    private final int ARGID;
    private final Hypothesis hypothesis;
    private final List<Generalization> generalizations;
    private final Datum datum;

    private ArgumentObject(Builder builder){
        this.ARGID = builder.argID;
        this.hypothesis = builder.hypothesis;
        this.generalizations = builder.generalizations;
        this.datum = builder.datum;
    }

    /**
     * Returns the argument ID.
     * @return - the argument ID.
     */
    public int getARGID() {
        return this.ARGID;
    }

    /**
     * Returns the Hypothesis.
     * @return - the Hypothesis.
     */
    public Hypothesis getHypothesis(){
        return this.hypothesis;
    }

    /**
     * Returns the Generalizations.
     * @return - the generalizations.
     */
    public List<Generalization> getGeneralizations() {
        return this.generalizations;
    }

    /**
     * Returns the Datum.
     * @return - the Datum.
     */
    public Datum getDatum() {
        return this.datum;
    }

    /**
     * Returns true if the passed generalization is contained in this Argument.
     * @param generalization - generalization to search for.
     * @return - if the passed generalization is contained in this Argument.
     */
    public boolean containsGeneralization(Generalization generalization){
        for (Generalization g : this.getGeneralizations()){
            return (g.equals(generalization));
        }
        return false;
    }

    /**
     * Returns if the Datum is empty.
     * @return - if the Datum is empty.
     */
    public boolean isHypothesis(){
        if (datum == null || datum.isEmptyDatum()){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Returns true if the Datum is a conjuction.
     * @return - true if the Datum is a conjunction.
     */
    public boolean HasConjunction(){
        return (datum != null) && datum.isConjunction();
    }

    /**
     * Returns true if the other ArgumentObject is equal.
     * @param other - ArgumentObject to compare with.
     * @return - true if the other ArgumentObject is equal.
     */
    public boolean equals(ArgumentObject other){
        return hypothesisEqual(other) && datumEqual(other) && generalizationsEqual(other);
    }

    /**
     * Returns true if the other Argument's Hypothesis is equal.
     * @param other - ArgumentObject to compare with.
     * @return - true if the other Argument's Hypothesis is equal.
     */
    public boolean hypothesisEqual(ArgumentObject other){
        return this.getHypothesis().equals(other.getHypothesis());
    }

    /**
     * Returns true if the other Argument's Datum is equal.
     * @param other - ArgumentObject to compare with.
     * @return - true if the other Argument's Datum is equal.
     */
    public boolean datumEqual(ArgumentObject other){
        return this.getDatum().equals(other.getDatum());
    }

    /**
     * Returns true fi teh other Argument's Generalizations are equal.
     * @param other - ArgumentObject to compare with.
     * @return - true ig the other Argument's Generalizations are equal.
     */
    public boolean generalizationsEqual(ArgumentObject other){
        if (this.getGeneralizations().size() != other.getGeneralizations().size())
            return false;
        else {
            for (Generalization g : other.getGeneralizations()){
               if (!this.containsGeneralization(g))
                   return false;
            }
            return true;
        }
    }

    /**
     * This class allows chained building of Argument objects.
     */
    public static class Builder {
        private int argID;
        private Hypothesis hypothesis;
        private List<Generalization> generalizations = new ArrayList<Generalization>();
        private Datum datum = null;

        /**
         * Creates a builder.
         * @param argID - Argument ID.
         * @return - this.
         */
        public Builder argID(int argID){
            this.argID = argID;
            return this;
        }

        /**
         * Sets the hypothesis.
         * @param hypothesis - Hypothesis to store.
         * @return - this.
         */
        public Builder hypothesis(Hypothesis hypothesis){
            this.hypothesis = hypothesis;
            return this;
        }

        /**
         * Sets the generalizaions
         * @param generalizations - List of generalizations.
         * @return - this.
         */
        public Builder generalizations(ArrayList<Generalization> generalizations){
            this.generalizations = generalizations;
            return this;
        }

        /**
         * Sets the datum.
         * @param datum - Datum to store.
         * @return - this.
         */
        public Builder datum(Datum datum){
            this.datum = datum;
            return this;
        }

        /**
         * Build the new ArgumentObject.
         * @return - new ArgumentObject.
         */
        public ArgumentObject build(){
            return new ArgumentObject(this);
        }
    }
}
