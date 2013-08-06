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
public class Argument {

    private final int ARGID;
    private final Hypothesis hypothesis;
    private final List<Generalization> generalizations;
    private final Datum datum;

    private Argument(Builder builder){
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

    public void addGeneralization(int arcID, String text){
        this.generalizations.add(new Generalization(arcID, text));
    }

    public String getTypeofArgument(){
        if(datum == null && generalizations.isEmpty()){
            return "Hypothesis only";
        }
        else {
            return "Full Argument";
        }
    }

    public boolean isHypothesis(){
        if (datum.equals(null) && generalizations.isEmpty()){
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

        public Builder setArgID(int argID){
            this.argID = argID;
            return this;
        }

        public Builder setHypothesis(int kbNodeID, String text){
            this.hypothesis = new Hypothesis(kbNodeID, text);
            return this;
        }

        public Builder addGeneralization(int kbArcID, String text){
            this.generalizations.add(new Generalization(kbArcID, text));
            return this;
        }

        public Builder setDatum(int kbNodeID, String text){
            this.datum = new Datum(kbNodeID, text);
            return this;
        }

        public Argument build(){
            return new Argument(this);
        }
    }

    public static class Hypothesis {
        private final int KBNODEID;
        private final String TEXT;
        private final String TYPE = "Hypothesis";

        public Hypothesis(int kbNodeID, String text){
            this.KBNODEID = kbNodeID;
            this.TEXT = text;
        }

        public int getKBNODEID(){
            return KBNODEID;
        }

        public String getTEXT(){
            return TEXT;
        }

        public String getTYPE(){
            return TYPE;
        }
    }

    public static class Generalization {
        private final int KBARCID;
        private final String TEXT;
        private final String TYPE = "Generalization";

        public Generalization(int kbArcID, String text){
            this.KBARCID = kbArcID;
            this.TEXT = text;
        }

        public int getKBARCID() {
            return KBARCID;
        }

        public String getTEXT() {
            return TEXT;
        }

        public String getTYPE() {
            return TYPE;
        }
    }

    public static class Datum {
        private final int KBNODEID;
        private final String TEXT;
        private final String TYPE = "Datum";

        public Datum(int kbNodeID, String text){
            this.KBNODEID = kbNodeID;
            this.TEXT = text;
        }

        public int getKBNODEID() {
            return KBNODEID;
        }

        public String getTEXT() {
            return TEXT;
        }

        public String getTYPE() {
            return TYPE;
        }
    }
}
