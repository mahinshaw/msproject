package ArgumentGenerator;

import GAIL.src.XMLHandler.ArgStructure;
import KB.KB_Node.KB_Node;

import java.util.ArrayList;

/**
 * User: Tshering Tobgay
 * Date: 1/5/14
 */
public class ArgGen {
    private KB_Node rootNode;
    private ArgStructure arg;
    private boolean argType;
    private final String question;
    private E2C e2c;
    private JE2C je2c;
    private ArgGenWriter argGenWriter;
    ArrayList<KB_Node> graphNodes;

    public ArgGen(KB_Node rootNode, ArgStructure arg, String question, ArrayList<KB_Node> graphNodes) {
        this.arg = arg;
        this.rootNode = rootNode;
        this.question = question;
        this.argType = false;
        this.graphNodes = graphNodes;
    }

    /**
     * Call to different argument generators
     * If not empty, get argument and argument type
     */
    public void findArgument() {
        ArrayList<ArrayList<KB_Node>> hold;
        /**
         * Check for arguments
         * in terms of E2C scheme
         */
        e2c = new E2C(this.rootNode, argType);
        hold = e2c.getPathList();
        if (!hold.isEmpty()) {
            argGenWriter = new ArgGenWriter(arg, hold, argType, question);
            argGenWriter.addArgument();//add to arg tree and xml output
        } else{
           printEmptyArg("E2C");
        }

        /**
         * JE2C
         */
        je2c = new JE2C(this.rootNode, graphNodes, arg, argType);
        hold = je2c.getPathList();
        if (!hold.isEmpty()) {
            argGenWriter = new ArgGenWriter(arg, hold, argType, question);
            //argGenWriter.addArgument();//add to arg tree and xml output
        } else {
            printEmptyArg("JE2C");
        }
    }

    public void printEmptyArg(String r){
        System.out.println("ArgGen did not produce any argument using "+r+" schema ~ ArgumentGenerator/ArgGen.java");
    }

}
