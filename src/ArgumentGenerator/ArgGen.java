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
         * Check for both pro and con arguments
         * in terms of E2C scheme (NE2C)
         */
        for (int i = 0; i < 2; i++) {
            if (i != 0)
                argType = true;
            e2c = new E2C(this.rootNode, argType);
            hold = e2c.getPathList();
            if (!hold.isEmpty()) {
                argGenWriter = new ArgGenWriter(arg, hold, argType, question);
                argGenWriter.addArgument();//add to arg tree and xml output
            }
        }

        /**
         * JE2C
         */
        for (int i = 0; i < 2; i++) {
            if (i != 0)
                argType = true;
            je2c = new JE2C(this.rootNode, graphNodes, arg, argType);
            hold = je2c.getPathList();
            if (!hold.isEmpty()) {
                argGenWriter = new ArgGenWriter(arg, hold, argType, question);
                //argGenWriter.addArgument();//add to arg tree and xml output
            }
        }

    }

}
