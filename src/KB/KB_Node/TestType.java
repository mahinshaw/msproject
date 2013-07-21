package KB.KB_Node;

/**
 * User: Tshering Tobgay
 * Date: 7/21/13
 */
public class TestType extends KB_Node{

    private String testType;

    public TestType(int id, int person_id, String testType){
        super(id, person_id);
        this.testType = testType;
    }

    public String getTestType(){
        return testType;
    }

    public void setTestType(String testType){
        this.testType = testType;
    }

    public String toString(){
        return super.toString()+"Test type: "+this.testType+"\n";
    }

}

