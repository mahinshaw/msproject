package KB_Node;

/**
 * User: Tshering Tobgay
 * Date: 7/18/13
 */
public class Test extends KB_Node{

    private boolean result;

    public Test(int id, int person_id, boolean result) {
        super(id, person_id);
        this.result = result;
    }

    public boolean getResult(){
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String toString() {
        return super.toString() +
                "Result: " + this.result + "\n";
    }
}
