package ArgGen;

import GAIL.src.XMLHandler.ArgStructure;
import KB.KB_Arc.KB_Arc;
import KB.KB_Node.KB_Node;
import KB.KB_Node.Symptom;
import KB.KB_Node.Test;

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
    private final char DATA;
    private final char HYPO;
    private ArgStructure arg;


    public E2C(KB_Node rootNode, char data, char hypo, ArgStructure arg) {
        this.rootNode = rootNode;
        this.tempList = new ArrayList<KB_Node>();
        this.argList = new ArrayList<KB_Node>();
        this.pathList = new ArrayList<ArrayList<KB_Node>>();
        this.DATA = data;
        this.HYPO = hypo;
        this.arg = arg;
    }

    public ArrayList<ArrayList<KB_Node>> getPathList() {
        if (rootNode.getChildren().isEmpty()) {
            System.out.println("No children for node " + rootNode.getId() + " (E2C Scheme)");
        } else {
            traverseGraph(rootNode, tempList, pathList);
            pathList = getE2C(pathList);
        }
        return pathList;
    }

    private void traverseGraph(KB_Node root, ArrayList<KB_Node> tempList, ArrayList<ArrayList<KB_Node>> pathList) {

        argList.add(root);

        if (root.getChildren().size() > 1) {
            tempList = new ArrayList<KB_Node>(argList);
        }
        if (root.getType() == DATA) {
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
            boolean hold = ((Test) node).getResult();
            condition = hold;
        } else if (node instanceof Symptom) {
            String deg = "absent";
            String degree = ((Symptom) node).getDegree();
            if (!degree.equalsIgnoreCase(deg)) {
                condition = true;
            }
        }
        return condition;
    }


    /**
     * Check condition:
     * No two hypothesis should exist in an E2C arg scheme
     *
     * @param allPaths
     * @return
     */
    private ArrayList<ArrayList<KB_Node>> getE2C(ArrayList<ArrayList<KB_Node>> allPaths) {
        int i = 0;
        ArrayList<ArrayList<KB_Node>> tempList = new ArrayList<ArrayList<KB_Node>>();

        for (ArrayList<KB_Node> n : allPaths) {
            for (KB_Node k : n) {
                if (k.getType() == HYPO) {
                    i++;
                }
            }
            /**
             * If there are more than one hypothesis in one argument path,
             * it is not E2C argument
             */
            if (i == 1) {
                tempList.add(n);
            }
            i = 0;
        }
        return tempList;
    }

    /**
     * Check to see if the edge type is the influence arc
     *
     * @return true if influence arc exits between two nodes
     */
    private boolean checkArcType(KB_Node parent, KB_Node child) {
        boolean influence = false;
        int i = 0, n = 0;
        KB_Arc arc = null;

        if (parent.getChildren().contains(child)) {
            for (KB_Node m : parent.getChildren()) {
                if (m.getId() == child.getId()) {
                    n = i;
                }
                i++;
            }
            //str = parent.getArcs().get(n).getEdge_id();
            arc = parent.getArcs().get(n);
            if (arc.getType().equalsIgnoreCase("+")) {
                influence = true;
            }
        }
        return influence;
    }
}
