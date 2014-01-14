package ArgumentStructure;

/**
 * User: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * Date: 8/28/13
 * github: https://github.com/mahinshaw/msproject
 */
public class Datum {
    private final boolean isConjunction;
    private final String KBNODEID;
    private final String TEXT;
    private final char TYPE = 'D';

    /**
     * This constructor is used to create a conjunction datum.
     * This type of datum will be used to denote the availability of multiple children.
     */
    public Datum(boolean isConjunction){
        this.isConjunction = isConjunction;
        this.KBNODEID = null;
        this.TEXT = null;
    }

    public Datum(String kbNodeID, String text){
        this.isConjunction = false;
        this.KBNODEID = kbNodeID;
        this.TEXT = text;
    }

    public boolean isConjunction() { return isConjunction; }

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
