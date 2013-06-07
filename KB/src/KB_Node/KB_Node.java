package KB_Node;

/**
 * Author: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * gitHub: https://github.com/mahinshaw/msproject.git
 */
public class KB_Node {

    private int id;
    private String person_id;
    private String certainty;

    public KB_Node(){

    }

    public KB_Node(int id, String person_id, String certainty) {
        this.id = id;
        this.person_id = person_id;
        this.certainty = certainty;
    }

    public int getId(){
        return id;
    }

    public String getPerson_id(){
        return person_id;
    }

    public String getCertainty(){
        return certainty;
    }

    public void setId(int i){
        this.id = i;
    }

    public void setPerson_id(String p){
        this.person_id = p;
    }

    public void setCertainty(String c){
        this.certainty = c;
    }
}
