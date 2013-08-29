package ArgumentStructure;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * Date: 8/2/13
 * github: https://github.com/mahinshaw/msproject
 *
 * This Structure will be used to compare student arguments to arguments created from the Argument Generator.
 *
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

    public int getARGID() {
        return ARGID;
    }

    public Hypothesis getHypothesis(){
        return hypothesis;
    }

    public List<Generalization> getGeneralizations() {
        return generalizations;
    }

    public Datum getDatum() {
        return datum;
    }

    public void addGeneralization(String arcID, String text){
        this.generalizations.add(new Generalization(arcID, text));
    }

    public String getTypeofArgument(){
        if(datum == null){
            return "Hypothesis only";
        }
        else {
            return "Full Argument";
        }
    }

    public boolean isHypothesis(){
        if (datum == null){
            return true;
        }
        else {
            return false;
        }
    }

    public static class Builder {
        private int argID;
        private Hypothesis hypothesis;
        private List<Generalization> generalizations = new ArrayList<Generalization>();
        private Datum datum = null;

        public Builder argID(int argID){
            this.argID = argID;
            return this;
        }

        public Builder hypothesis(Hypothesis hypothesis){
            this.hypothesis = hypothesis;
            return this;
        }

        public Builder generalizations(ArrayList<Generalization> generalizations){
            this.generalizations = generalizations;
            return this;
        }

        public Builder datum(Datum datum){
            this.datum = datum;
            return this;
        }

        public ArgumentObject build(){
            return new ArgumentObject(this);
        }
    }
}
