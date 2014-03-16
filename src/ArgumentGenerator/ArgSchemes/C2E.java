package ArgumentGenerator.ArgSchemes;

import ArgumentGenerator.ArgLibrary.ArgInfo;
import KB.KB_Arc.KB_Arc;
import KB.KB_Node.KB_Node;

import java.util.ArrayList;

/**
 * User: Tshering Tobgay
 * Date: 3/14/14
 */
public class C2E {
    private KB_Node rootNode;
    private ArrayList<KB_Node> tempList;
    private ArrayList<KB_Node> argList;
    private ArrayList<ArrayList<KB_Node>> pathList;
    private ArgInfo argInfo = new ArgInfo();
    private boolean pro;
    private final String UNKNOWN = "possible";

    /**
     * @param rootNode the starting node where the argument is going to be generated.
     */
    public C2E(KB_Node rootNode) {
        this.rootNode = rootNode;
        this.tempList = new ArrayList<KB_Node>();
        this.argList = new ArrayList<KB_Node>();
        this.pathList = new ArrayList<ArrayList<KB_Node>>();
        this.pro = true;
    }

    public ArrayList<ArrayList<KB_Node>> getPathList() {
        if (rootNode.getChildren().isEmpty()) {
            System.out.println("No children for node " + rootNode.getId() + " (ArgumentGenerator/ArgSchemes/C2E Scheme)");
            pathList.clear();
        } else {
            ArrayList<ArrayList<KB_Node>> tempPath = new ArrayList<ArrayList<KB_Node>>();
            if (checkConditions(rootNode))
                pathList = traverseGraph(rootNode, tempList, tempPath);
        }
        return pathList;
    }

    private ArrayList<ArrayList<KB_Node>> traverseGraph(KB_Node root, ArrayList<KB_Node> tempList, ArrayList<ArrayList<KB_Node>> pathList) {
        argList.add(root);
        if (root.getChildren().size() > 1)
            tempList = new ArrayList<KB_Node>(argList);

        if (root.getChildren().isEmpty()) {
            if (checkConditions(root) == pro && argList.size() != 1) {
                pathList.add(argList);
            }
            argList = new ArrayList<KB_Node>(tempList);
        }
        for (KB_Node n : root.getChildren()) {
            if (!argList.contains(n) && checkArcType(root, n)) {
                if (checkConditions(n) == pro) {
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
    private boolean checkConditions(KB_Node node) {
        boolean condition = false;
        if (UNKNOWN.equalsIgnoreCase(node.getAbnormal()))
            condition = true;
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
