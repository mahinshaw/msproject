package ArgumentGenerator;

import ArgumentGenerator.ArgInfo;
import GAIL.src.XMLHandler.ArgStructure;
import KB.KB_Node.KB_Node;

import java.util.ArrayList;

/**
 * Finding arguments with multiple schemes using conjugation (%).
 * User: Tshering Tobgay
 * Date: 1/17/14
 */
public class Conjunction {
    private KB_Node rootNode;
    private ArrayList<KB_Node> graphNodes;
    private ArgStructure arg;
    private ArrayList<KB_Node> argList;
    private ArrayList<ArrayList<KB_Node>> pathList;
    private ArgInfo argInfo;
    private boolean counter; //check to see if argument exists: true, if conjunction exists, and false, if not.
    private int argCount;//the number of arguments in a single tree
    private boolean pro;

    private E2C e2c;
    private JE2C je2c;

    public Conjunction(KB_Node rootNode, ArrayList<KB_Node> graphNodes, ArgStructure arg, int argCount) {
        this.rootNode = rootNode;
        this.argList = new ArrayList<KB_Node>();
        this.pathList = new ArrayList<ArrayList<KB_Node>>();
        this.graphNodes = graphNodes;
        this.arg = arg;
        this.counter = false;
        this.argCount = argCount;
        this.pro = false;
        argInfo = new ArgInfo();

    }

    public void findConjunction() {
        int argTrack = 0;
        if (rootNode.getChildren().isEmpty()) {
            System.out.println("No child(ren) for node " + rootNode.getId() + " (ArgumentGenerator/Conjunction)");
        } else {
            for (KB_Node n : rootNode.getChildren())
                if (argInfo.findEdge(rootNode, n).getType().equalsIgnoreCase(String.valueOf(ArgInfo.ArcTYPE.CONJ.getType()))) {
                    traverseGraph(n, rootNode);
                    argTrack++;
                }
        }

        if (argTrack!=argCount){
            System.out.println("No arguments in (ArgumentGenerator/Conjunction)");
        }
    }

    /**
     * Find all possible argument based on known schemes.
     *
     * @param n is child of rootNode
     * @param rootNode is the parentNode
     */
    private void traverseGraph(KB_Node n, KB_Node rootNode) {

    }

    /**
     * Call to different argument generators.
     * If not empty, get argument and argument type
     */
    private ArrayList<ArrayList<KB_Node>> findArgument(KB_Node node) {
        ArrayList<ArrayList<KB_Node>> argFound;
        /**
         * Check for arguments in terms of E2C and NE2C scheme
         * (argType = true for E2C "abnormal" and argType = false for NE2C "not abnormal.")
         *  First find NE2C (false) arguments, then E2C (true)
         */
        int argNo = 2;//the number of arguments, in this case, it's 2 (E2C + NE2C)
        for (int i = 0; i < argNo; i++) {
            e2c = new E2C(node, pro);
            argFound = e2c.getPathList();
            if (!argFound.isEmpty()) {

            }
            pro = true;//find E2C arguments
        }
        /**
         * Check for JE2C scheme argument
         */
        pro = false;//check for JE2C first
        je2c = new JE2C(node, graphNodes, arg,pro);
        argFound = je2c.getPathList();
        if (!argFound.isEmpty()) {

        }
        return argFound;
    }

}
