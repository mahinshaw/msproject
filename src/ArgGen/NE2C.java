package ArgGen;

import KB.KB_Arc.KB_Arc;
import KB.KB_Node.KB_Node;
import KB.KB_Node.Symptom;
import KB.KB_Node.Test;

import java.util.ArrayList;

/**
 * User: Tshering Tobgay
 * Date: 8/9/13
 */
public class NE2C {
    private KB_Node rootNode;
    private ArrayList<KB_Node> tempList;
    private ArrayList<KB_Node> argList;
    private ArrayList<ArrayList<KB_Node>> pathList;
    private final char DATA;
    private final char HYPO;
    private ArgInfo argInfo = new ArgInfo();

    public NE2C(KB_Node rootNode, char data, char hypo) {
        this.rootNode = rootNode;
        this.tempList = new ArrayList<KB_Node>();
        this.argList = new ArrayList<KB_Node>();
        this.pathList = new ArrayList<ArrayList<KB_Node>>();
        this.DATA = data;
        this.HYPO = hypo;
    }

    public ArrayList<ArrayList<KB_Node>> getPathList() {
        if (rootNode.getChildren().isEmpty()) {
            System.out.println("No children for node " + rootNode.getId()+" (NE2C Scheme)");
        } else {
            traverseGraph(rootNode, tempList, pathList);
            pathList =pathList;
        }
        return pathList;
    }

    private void traverseGraph(KB_Node root, ArrayList<KB_Node> tempList, ArrayList<ArrayList<KB_Node>> pathList) {

        argList.add(root);

        if (root.getChildren().size() > 1) {
            tempList = new ArrayList<KB_Node>(argList);
        }
       // if (root.getType() == DATA) {
        if (root.getChildren().isEmpty()) {
            if (checkConditions(root)) {
                pathList.add(argList);
            }
            argList = new ArrayList<KB_Node>(tempList);
        }

        for (KB_Node n : root.getChildren()) {
            if (!argList.contains(n) && checkArcType(root, n)) {
                traverseGraph(n, tempList, pathList);
            }
        }
    }

    /**
     * Condition to check if DATA contains
     * affirmative values needed for E2C scheme.
     *
     * @param node
     * @return
     */

    private boolean checkConditions(KB_Node node) {
        //Class<? extends KB_Node> g = node.getClass();
        boolean condition = false;

        if (node instanceof Test) {
            // Test test = (Test) node;
            boolean hold = ((Test) node).getTestAbnormal();
            condition = !hold;
        } else if (node instanceof Symptom) {
            String deg = "absent";
            String degree = ((Symptom) node).getDegree();
            if (degree.equalsIgnoreCase(deg)) {
                condition = true;
            }
        }
        return condition;
    }

    /**
     * Check to see if the edge type is the influence arc
     *
     * @return true if influence arc exits between two nodes
     */
    private boolean checkArcType(KB_Node parent, KB_Node child) {
        KB_Arc arc = argInfo.findEdge(parent, child);
        boolean influence = false;

        if (arc.getType().equalsIgnoreCase(String.valueOf(ArgInfo.ArcTYPE.INFLUENCE.getType()))) {
            influence = true;
        }
        return influence;
    }
}
