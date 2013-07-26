package KB.KB_Node;

/**
 * User: Tshering Tobgay
 * Date: 7/21/13
 */
public class Physiology extends KB_Node {

    private String location;
    private String description;

    public Physiology(int id, int person_id, String location, String description){
        super(id, person_id);
        this.location = location;
        this.description = description;
    }

    public String getLocation(){
        return location;
    }

    public String getDescription(){
        return description;
    }

    public void setType(String type){
        this.location = type;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String toString(){
        return super.toString()+"Type: "+this.location+"\nDescription: "+this.description+"\n";
    }

}
