package KB_Node;

/**
 * Author: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * gitHub: https://github.com/mahinshaw/msproject.git
 */
public class KB_Node {

    private final int id;
    private int person_id;
    private String certainty;

    public KB_Node(int id, int person_id) {
        this.id = id;
        this.person_id = person_id;
        this.certainty = null;
    }

    public int getId(){
        return id;
    }

    public int getPerson_id(){
        return person_id;
    }

    public String getCertainty(){
        return certainty;
    }

    public void setPerson_id(int person_id){
        this.person_id = person_id;
    }

    public void setCertainty(String c){
        this.certainty = c;
    }
}
