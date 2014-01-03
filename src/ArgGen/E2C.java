package ArgGen;

import KB.KB_Arc.KB_Arc;
import KB.KB_Node.*;

import java.util.ArrayList;

/**
 * User: Tshering Tobgay
 * Date: 8/5/13
 */
public class E2C {
    private KB_Node rootNode;
    private ArrayList<KB_Node> tempList;
    private ArrayList<KB_Node> argList;
    private ArrayList<ArrayList<KB_Node>> pathList;
    private ArgInfo argInfo = new ArgInfo();

    public E2C(KB_Node rootNode) {
        this.rootNode = rootNode;
        this.tempList = new ArrayList<KB_Node>();
        this.argList = new ArrayList<KB_Node>();
        this.pathList = new ArrayList<ArrayList<KB_Node>>();
    }

    public ArrayList<ArrayList<KB_Node>> getPathList() {
        if (rootNode.getChildren().isEmpty()) {
            System.out.println("No children for node " + rootNode.getId() + " (E2C Scheme)");
        } else {
            ArrayList<ArrayList<KB_Node>> tempPath = new ArrayList<ArrayList<KB_Node>>();
            traverseGraph(rootNode, tempList, tempPath);
            pathList = tempPath;
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
     * Check to see if the abnormal field is true for all node types.
     *
     * @param node
     * @return
     */
    private boolean checkConditions(KB_Node node) {
        boolean condition = false;
        if (node instanceof Test) {
            condition = ((Test) node).getTestAbnormal();
            System.out.println("Test Abnormal: " + condition);
        } else if (node instanceof Symptom) {
            condition = ((Symptom) node).getSymAbnormal();
            System.out.println("Symptom Abnormal: " + condition);
        } else if (node instanceof Physiology) {
            System.out.println("Physiology Abnormal: " + condition);
            condition = ((Physiology) node).getPhyAbnormal();
        } else if (node instanceof Biochemistry) {
            System.out.println("Biochemistry Abnormal: " + condition);
            condition = ((Biochemistry) node).getbioAbnormal();
        }
        return condition;
    }

    /**
     * Check to see if the edge type is the influence arc.
     * True, if influence arc exits between two nodes.
     *
     * @return
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
