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

        public Argument build(){
            return new Argument(this);
        }
    }

    public static class Hypothesis {
        private final String KBNODEID;
        private final String TEXT;
        private final char TYPE = 'H';

        public Hypothesis(String kbNodeID, String text){
            this.KBNODEID = kbNodeID;
            this.TEXT = text;
        }

        public String getKBNODEID(){
            return KBNODEID;
        }

        public String getTEXT(){
            return TEXT;
        }

        public char getTYPE(){
            return TYPE;
        }
    }

    public static class Generalization {
        private final String KBARCID;
        private final String TEXT;
        private final char TYPE = 'G';

        public Generalization(String kbArcID, String text){
            this.KBARCID = kbArcID;
            this.TEXT = text;
        }

        public String getKBARCID() {
            return KBARCID;
        }

        public String getTEXT() {
            return TEXT;
        }

        public char getTYPE() {
            return TYPE;
        }
    }

    public static class Datum {
        private final String KBNODEID;
        private final String TEXT;
        private final char TYPE = 'D';

        public Datum(String kbNodeID, String text){
            this.KBNODEID = kbNodeID;
            this.TEXT = text;
        }

        public String getKBNODEID() {
            return KBNODEID;
        }

        public String getTEXT() {
            return TEXT;
        }

        public char getTYPE() {
            return TYPE;
        }
    }
}
