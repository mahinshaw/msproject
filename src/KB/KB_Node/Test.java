package KB.KB_Node;

/**
 * User: Tshering Tobgay
 * Date: 7/18/13
 */
public class Test extends KB_Node{

    private String testType;
    private boolean result;

    public Test(int id, int person_id, char type, String testType, boolean result) {
        super(id, person_id, type);
        this.testType = testType;
        this.result = result;
    }

    public String getTestType(){
        return testType;
    }

    public boolean getResult(){
        return result;
    }

    public void getTestType(String testType){
        this.testType = testType;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String toString() {
        return super.toString() +
                "Test Type: " + this.testType + "\nTest Result: "+this.result+"\n";
    }
}
