package KB.KB_Node;

/**
 * User: Tshering Tobgay
 * Date: 7/21/13
 */
public class Physiology extends KB_Node {

    private String location;
    private String description;
    private boolean abnormal;

    public Physiology(int id, int person_id, char type, String location, String description, boolean phyAbnormal){
        super(id, person_id, type);
        this.location = location;
        this.description = description;
        this.abnormal = phyAbnormal;
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

    public boolean getPhyAbnormal(){
        return abnormal;
    }

    public void setPhyAbnormal(boolean phyAbnormal){
        this.abnormal=phyAbnormal;
    }

    public String toString(){
        return super.toString()+"Type: "+this.location+"\nDescription: "+this.description+"\n"+
                "Abnormal: "+abnormal+"\n";
    }

}
