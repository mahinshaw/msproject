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

    public Conjunction(KB_Node rootNode, ArrayList<KB_Node> graphNodes, ArgStructure arg, int argCount) {
        this.rootNode = rootNode;
        this.argList = new ArrayList<KB_Node>();
        this.pathList = new ArrayList<ArrayList<KB_Node>>();
        this.graphNodes = graphNodes;
        this.arg = arg;
        this.counter = false;
        this.argCount = argCount;
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

    private void traverseGraph(KB_Node n, KB_Node rootNode) {
    }
}
