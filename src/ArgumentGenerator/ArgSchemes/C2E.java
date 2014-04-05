package ArgumentGenerator.ArgSchemes;

import ArgumentGenerator.ArgLibrary.ArgInfo;
import KB.KB_Arc.KB_Arc;
import KB.KB_Node.KB_Node;

import java.util.ArrayList;
import java.util.Collections;

/**
 * User: Tshering Tobgay
 * Date: 3/14/14
 */
public class C2E {
    private KB_Node rootNode;
    private ArrayList<KB_Node> argList;
    private ArrayList<ArrayList<KB_Node>> pathList;
    private ArgInfo argInfo = new ArgInfo();
    private String pro;
    private E2C e2c;

    /**
     * @param rootNode the starting node where the argument is going to be generated.
     */
    public C2E(KB_Node rootNode, String pro) {
        this.rootNode = rootNode;
        this.argList = new ArrayList<KB_Node>();
        this.pathList = new ArrayList<ArrayList<KB_Node>>();
        this.pro = pro;
    }

    public ArrayList<ArrayList<KB_Node>> getPathList() {
        if (rootNode.getChildren().isEmpty()) {
            System.out.println("No children for node " + rootNode.getId() + " (ArgumentGenerator/ArgSchemes/C2E Scheme)");
            pathList.clear();
        } else {
            ArrayList<KB_Node> tempList = new ArrayList<KB_Node>();
            ArrayList<ArrayList<KB_Node>> tempPath = new ArrayList<ArrayList<KB_Node>>();
            tempPath = traverseGraph(rootNode, tempList, tempPath);
            if (!tempPath.isEmpty()) {
                for (ArrayList<KB_Node> path : tempPath)
                    checkE2C(path);

            }
        }
        return pathList;
    }

    private ArrayList<ArrayList<KB_Node>> traverseGraph(KB_Node root, ArrayList<KB_Node> tempList, ArrayList<ArrayList<KB_Node>> pathList) {
        argList.add(root);
        if (root.getChildren().size() > 1)
            tempList = new ArrayList<KB_Node>(argList);

        if (root.getChildren().isEmpty()) {
            if (checkConditions(root).equalsIgnoreCase(pro) && argList.size() != 1) {
                Collections.reverse(argList);
                pathList.add(argList);
            }
            argList = new ArrayList<KB_Node>(tempList);
        }
        for (KB_Node n : root.getChildren()) {
            if (!argList.contains(n) && checkArcType(root, n)) {
                if (checkConditions(n).equalsIgnoreCase(pro)) {
                    traverseGraph(n, tempList, pathList);
                }
            }
        }
        return pathList;
    }

    /**
     * Check to see if the abnormal field is
     * true for all node types.
     *
     * @param node
     * @return
     */
    private String checkConditions(KB_Node node) {
        String condition = node.getAbnormal();
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

    /**
     * Check if E2C and NE2C arguments exist
     *
     * @param path the path of C2E argument(s)
     * @return
     */
    private void checkE2C(ArrayList<KB_Node> path) {
        ArrayList<ArrayList<KB_Node>> hold;
        ArrayList<KB_Node> holdPathList = new ArrayList<KB_Node>(path);
        String argType = ArgInfo.abnormalType.FALSE.getAbType();
        KB_Node testNode = path.get(path.size() - 1);
        if (!testNode.getChildren().isEmpty()) {
            for (int i = 0; i < 2; i++) {
                e2c = new E2C(testNode, argType);
                hold = e2c.getPathList();
                if (!hold.isEmpty()) {
                    for (ArrayList<KB_Node> arryNodes : hold) {
                        for (KB_Node n : arryNodes)
                            if (!holdPathList.contains(n))
                                holdPathList.add(n);
                        pathList.add(holdPathList);
                        holdPathList = new ArrayList<KB_Node>(path);
                    }
                }
                argType = ArgInfo.abnormalType.TRUE.getAbType();//find E2C arguments
            }
        }
        //If a testNode's child
        if (!testNode.getChildren().isEmpty()) {
            for (KB_Node child : testNode.getChildren()) {
                if (path.contains(child) && testNode.getChildren().size() == 1) {
                    pathList.add(path);
                }
            }
        } else
            pathList.add(path);
    }
}
