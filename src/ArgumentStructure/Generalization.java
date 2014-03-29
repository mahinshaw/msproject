package ArgumentStructure;

/**
 * User: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * Date: 8/28/13
 * github: https://github.com/mahinshaw/msproject
 */
public class Generalization {
    private final String KBARCID;
    private final String TEXT;
    private final char TYPE = 'G';

    public Generalization(){
        this.KBARCID = "";
        this.TEXT = "";
    }

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

    public boolean equals(Generalization other){
        return this.getKBARCID().contains(other.getKBARCID() + ",");
    }
}
