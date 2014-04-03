package ArgumentStructure;

/**
 * This class is a Generalizaion object to be contained with an ArgumentObject.
 */
public class Generalization {
    private final String KBARCID;
    private final String TEXT;
    private final char TYPE = 'G';

    /**
     * Construct a generalization.
     * @param kbArcID - arc ID.
     * @param text - text.
     */
    public Generalization(String kbArcID, String text){
        this.KBARCID = kbArcID;
        this.TEXT = text;
    }

    /**
     * Return the arc ID.
     * @return - arc ID.
     */
    public String getKBARCID() {
        return KBARCID;
    }

    /**
     * Return the text.
     * @return - text.
     */
    public String getTEXT() {
        return TEXT;
    }

    /**
     * Compare generalization with other generalization.
     * @param other - generalization to compare with.
     * @return - true if the generalizations are equal.
     */
    public boolean equals(Generalization other){
        return this.getKBARCID().contains(other.getKBARCID() + ",");
    }
}
