package ArgumentStructure;

/**
 * User: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * Date: 8/28/13
 * github: https://github.com/mahinshaw/msproject
 */
public class Datum {
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
