package ArgumentGenerator;

import KB.KB_Node.KB_Node;
import KB.XMLinterface.ArgStruct;

import java.util.ArrayList;

/**
 * ArgGen.java proceeds with the generation of the arguments made from KB.
 * <p/>
 * User: Tshering Tobgay
 * Date: 1/5/14
 */
public class ArgGen {
    private KB_Node rootNode;
    private ArgStruct arg;
    private boolean argType;
    private final String question;
    private E2C e2c;
    private JE2C je2c;
    private Conjunction conj;
    private ArgGenWriter argGenWriter;
    ArrayList<KB_Node> graphNodes;

    public ArgGen(int nodeID, ArgStruct arg, String question, ArrayList<KB_Node> graphNodes) {
        this.arg = arg;
        this.question = question;
        this.graphNodes = graphNodes;
        setRootNode(nodeID);
        setArgType(false);
    }

    /**
     * Find the rootnode based on the ID passed
     *
     * @param nodeID
     */
    private void setRootNode(int nodeID) {
        for (int i = 0; i < graphNodes.size(); i++)
            if (graphNodes.get(i).getId() == nodeID)
                this.rootNode = graphNodes.get(i);
    }

    /**
     * Call to different argument generators.
     * If not empty, get argument and argument type
     */
    public void findArgument() {
        ArrayList<ArrayList<KB_Node>> hold;
        /**
         * Check for arguments in terms of E2C and NE2C scheme
         * (argType = true for E2C "abnormal" and argType = false for NE2C "not abnormal.")
         *  First find NE2C (false) arguments, then E2C (true)
         */
        int argNo = 2;//the number of arguments, in this case, it's 2 (E2C + NE2C)

        for (int i = 0; i < argNo; i++) {
            e2c = new E2C(this.rootNode, getArgType());
            hold = e2c.getPathList();
            if (!hold.isEmpty()) {
                argGenWriter = new ArgGenWriter(arg, hold, getArgType(), question);
                argGenWriter.addArgument();//add to arg tree and xml output
            } else {
                if (argType)
                    printEmptyArg("E2C");
                else
                    printEmptyArg("NE2C");
            }
            setArgType(true);//find E2C arguments
        }
        /**
         * Check for JE2C scheme argument
         */
        setArgType(false);//check for JE2C first
        je2c = new JE2C(this.rootNode, graphNodes, getArgType());
        hold = je2c.getPathList();
        if (!hold.isEmpty()) {
            argGenWriter = new ArgGenWriter(arg, hold, getArgType(), question);
            argGenWriter.addArgument();//add to arg tree and xml output
        } else {
            printEmptyArg("JE2C");
        }

        /**
         * Check for arguments that can be found using elimination (currently shown as % in KB)
         *
         */
        setArgType(false);
        conj = new Conjunction(this.rootNode, graphNodes, arg, question);
        conj.findConjunction();
    }

    private void setArgType(boolean argType) {
        this.argType = argType;
    }

    private boolean getArgType() {
        return argType;
    }

    /**
     * Only called when no argument is found.
     * Prints to local console, not interface.
     *
     * @param r
     */
    private void printEmptyArg(String r) {
        System.out.println("ArgGen did not produce any argument using " + r + " schema ~ ArgumentGenerator/ArgGen.java");
    }
}
