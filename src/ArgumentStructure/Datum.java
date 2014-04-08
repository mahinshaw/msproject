package ArgumentStructure;

/**
 * This class is a Datum object to be contained within the ArgumentObject.
 */
public class Datum {
    private final boolean isConjunction;
    private final String KBNODEID;
    private final String TEXT;
    private final char TYPE = 'D';

    /**
     * Construct a Datum that is a conjuntion if true, and empty if false.
     * @param isConjunction - true if Datum will be a conjunction.
     */
   public Datum(boolean isConjunction){
        this.isConjunction = isConjunction;
        this.KBNODEID = "";
        this.TEXT = "";
    }

    /**
     * Construct a Datum.
     * @param kbNodeID - node ID from knowledge base.
     * @param text - Datum text.
     */
    public Datum(String kbNodeID, String text){
        this.isConjunction = false;
        this.KBNODEID = kbNodeID;
        this.TEXT = text;
    }

    /**
     * Returns if Datum is a conjunction.
     * @return - true if Datum is a conjunction.
     */
    public boolean isConjunction() { return isConjunction; }

    /**
     * Returns true if Datum is empty.
     * @return - true if Datum is empty.
     */
    public boolean isEmptyDatum() {
        return !isConjunction && (getKBNODEID() == "" && getTEXT() == "");
    }

    /**
     * Return node ID.
     * @return - node ID.
     */
    public String getKBNODEID() {
        return KBNODEID;
    }

    /**
     * Return Datum text.
     * @return - text
     */
    public String getTEXT() {
        return TEXT;
    }

    /**
     * Compare datum with other datum.
     * @param other - Datum to compare with.
     * @return - true if Datum are equal.
     */
    public boolean equals(Datum other){
        if (this.isConjunction() || other.isConjunction())
            return this.isConjunction() && other.isConjunction();
        else
            return this.getKBNODEID().equals(other.getKBNODEID());
    }
}
