package KB.KB_Node;

/**
 * User: Tshering Tobgay
 * Date: 7/21/13
 */
public class Physiology extends KB_Node {

    private String type;

    public Physiology(int id, int person_id, String type){
           super(id, person_id);
           this.type = type;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String toString(){
        return super.toString()+"Physiology: "+this.type+"\n";
    }

}
