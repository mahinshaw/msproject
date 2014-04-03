package ArgumentStructure;

/**
 * This class is the Hypothesis object that is contained within the ArgumentObject.
 */
public class Hypothesis {
    private final String KBNODEID;
    private final String TEXT;
    private final char TYPE = 'H';

    /**
     * Construct an empty hypothesis.
     */
    public Hypothesis(){
        this.KBNODEID = "";
        this.TEXT = "";
    }

    /**
     * Construct a Hypothesis.
     * @param kbNodeID - node ID.
     * @param text - text.
     */
    public Hypothesis(String kbNodeID, String text){
        this.KBNODEID = kbNodeID;
        this.TEXT = text;
    }

    /**
     * Return the node ID.
     * @return the node ID.
     */
    public String getKBNODEID(){
        return KBNODEID;
    }

    /**
     * Return the text.
     * @return - the text.
     */
    public String getTEXT(){
        return TEXT;
    }

    /**
     * Compare this Hypothesis with another hypothesis.
     * @param other - other hypothesis to compare.
     * @return - true if the hypotheses are equal.
     */
    public boolean equals(Hypothesis other){
        return this.getKBNODEID().equals(other.getKBNODEID());
    }
}
