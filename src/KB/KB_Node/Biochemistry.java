package KB.KB_Node;

/**
 * Author: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * gitHub: https://github.com/mahinshaw/msproject.git
 */
public class Biochemistry extends KB_Node {

    private String protein_id;
    private String protein_name;
    private boolean abnormal;
    private String quantity;

    public Biochemistry(int id, int person_id, char type, String protein_id, String protein_name, boolean bioAbnormal, String quantity) {
        super(id, person_id, type);
        this.protein_id = protein_id;
        this.protein_name = protein_name;
        this.abnormal = bioAbnormal;
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

    public boolean getbioAbnormal() {
        return abnormal;
    }

    public void setbioAbnormal(boolean bioAbnormal) {
        this.abnormal = bioAbnormal;
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
                "Abnormal: " + this.abnormal + "\n" +
                "quantity: " + this.quantity + "\n";
    }
}
