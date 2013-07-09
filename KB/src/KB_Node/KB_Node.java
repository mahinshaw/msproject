package KB_Node;

/**
 * Author: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * gitHub: https://github.com/mahinshaw/msproject.git
 */
public class KB_Node {

    private final int id;
    private KB_Person person;
    private String certainty;

    public KB_Node(KB_Person person, int id) {
        this.id = id;
        this.person = person;
        this.certainty = null;
    }

    public int getId(){
        return id;
    }

    public KB_Person getPerson(){
        return person;
    }

    public String getCertainty(){
        return certainty;
    }

    public void setPerson(KB_Person person){
        this.person = person;
    }

    public void setCertainty(String c){
        this.certainty = c;
    }
}
