package KB.KB_Node;

/**
 * Author: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * gitHub: https://github.com/mahinshaw/msproject.git
 */
public class Biochemistry extends KB_Node {

    private String protein_id;
    private String protein_name;
    private boolean normal;
    private String quantity;

    public Biochemistry(int id, int person_id, String protein_id, String protein_name, boolean normal, String quantity) {
        super(id, person_id);
        this.protein_id = protein_id;
        this.protein_name = protein_name;
        this.normal = normal;
        this.quantity = quantity;
    }

    public String getProtein_id() {
        return protein_id;
    }

    public void setProtein_id(String protein_id) {
        this.protein_id = protein_id;
    }

    public String getProtein_name() {
        return protein_name;
    }

    public void setProtein_name(String protein_name) {
        this.protein_name = protein_name;
    }

    public boolean isNormal() {
        return normal;
    }

    public void setNormal(boolean normal) {
        this.normal = normal;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String toString() {
        return super.toString() +
                "Protein ID: " + this.protein_id + "\n" +
                "Protein name: " + this.protein_name + "\n" +
                "Normal: " + this.normal + "\n" +
                "quantity: " + this.quantity + "\n";
    }
}
