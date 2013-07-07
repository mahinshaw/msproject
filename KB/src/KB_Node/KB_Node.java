package KB_Node;

/**
 * Author: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * gitHub: https://github.com/mahinshaw/msproject.git
 */
public class KB_Node {

    private int id;
    private KB_Person person;
    private String certainty;

    public KB_Node(){

    }

    public KB_Node(int id, KB_Person person, String certainty) {
        this.id = id;
        this.person = person;
        this.certainty = certainty;
    }

    public int getId(){
        return id;
    }

    public String getPerson_id(){
        return person;
    }

    public String getCertainty(){
        return certainty;
    }

    public void setId(int i){
        this.id = i;
    }

    public void setPerson(KB_Person person){
        this.person = person;
    }

    public void setCertainty(String c){
        this.certainty = c;
    }
}
