package ArgumentGenerator;

import KB.KB_Node.KB_Node;

import java.util.ArrayList;

/**
 * Joint effect to case argument(s) calculated in JE2C.java
 * User: Tshering Tobgay
 * Date: 9/26/13
 */
public class JE2C {
    private KB_Node rootNode;
    private ArrayList<ArrayList<KB_Node>> pathList;
    private ArrayList<ArrayList<KB_Node>> argFound;
    private ArrayList<KB_Node> graphNodes;
    private ArrayList<KB_Node> rootParents;
    private ArgInfo argInfo;
    private E2C e2c;
    private boolean pro;

    public JE2C(KB_Node rootNode, ArrayList<KB_Node> graphNodes, boolean pro) {
        this.rootNode = rootNode;
        this.pathList = new ArrayList<ArrayList<KB_Node>>();
        this.argFound = new ArrayList<ArrayList<KB_Node>>();
        this.graphNodes = graphNodes;
        this.pro = pro;
        argInfo = new ArgInfo();
    }

    /**
     * Find the JE2C argument by finding a synergy relation or S relation with its child(ren)
     * and the other parent node.
     *
     * @return
     */
    public ArrayList<ArrayList<KB_Node>> getPathList() {
        if (rootNode.getChildren().isEmpty()) {
            System.out.println("No child(ren) for node " + rootNode.getId() + " (ArgumentGenerator/JE2C Scheme)");
        } else {
            for (KB_Node n : rootNode.getChildren())
                if (argInfo.findEdge(rootNode, n).getType().equalsIgnoreCase(String.valueOf(ArgInfo.ArcTYPE.SYNERGY.getType())))
                    traverseGraph(n, rootNode, 1);
        }
        //if (!pathList.isEmpty()) {argGenerator(pathList);}
        return getJE2C();
    }

    /**
     * Since the root has a X0 relation with its parent, check the other parent
     *
     * @param child
     */
    private void traverseGraph(KB_Node child, KB_Node knownParent, int parentNo) {
        ArrayList<KB_Node> argPath = new ArrayList<KB_Node>();
        ArrayList<ArrayList<KB_Node>> hold;
        KB_Node otherParent = null;
        String holdInfluence = "";
        argPath.add(knownParent);
        String arcID = argInfo.findEdge(knownParent, child).getEdge_id();

        for (KB_Node p : argInfo.getParents(child, graphNodes)) {
            //Find all parents other than rootNode
            if (knownParent != p) {
                holdInfluence = argInfo.findEdge(p, child).getType();
                //Find parents with only synergy relation to child
                if (holdInfluence.equalsIgnoreCase(String.valueOf(ArgInfo.ArcTYPE.SYNERGY.getType()))) {
                    //Find the other parent for rootNode by edge ID among the synergy-related parents
                    if (arcID.equalsIgnoreCase(argInfo.findEdge(p, child).getEdge_id())) {
                        otherParent = p;
                        argPath.add(child);
                    }
                }
            }
        }
        KB_Node leafChild = argPath.get(argPath.size() - 1);
        hold = checkE2C(leafChild);
        ArrayList<KB_Node> pathHolder = new ArrayList<KB_Node>(argPath);

        /**
         * Find E2C relation from the leaf node.
         * If no arg found, skip this step.
         */
        if (!hold.isEmpty()) {
            for (ArrayList<KB_Node> n : hold) {
                for (KB_Node k : n) {
                    if (k != argPath.get(argPath.size() - 1))
                        argPath.add(k);
                }
                setJE2C(argPath);
                argPath = new ArrayList<KB_Node>(pathHolder);
            }
        } else {
            setJE2C(argPath);
        }
        hold.clear();

        //TODO add the other parent's(parentNo 2) argument to the pathList
       /*] if (parentNo==2){ traverseGraph(child, otherParent, 2);}*/
    }

    private ArrayList<ArrayList<KB_Node>> checkE2C(KB_Node subRootNode) {
        /**
         * Check for arguments in terms of E2C and NE2C scheme
         * (argType = true for E2C "abnormal" and argType = false for NE2C "not abnormal.")
         *  First find NE2C (false) arguments, then E2C (true)
         */

        int argNo = 2;//the number of arguments, in this case, it's 2 (E2C + NE2C)
        for (int i = 0; i < argNo; i++) {
            e2c = new E2C(subRootNode, pro);
            ArrayList<ArrayList<KB_Node>> hold = e2c.getPathList();
            if (!hold.isEmpty()) {
                for (ArrayList<KB_Node> k : hold)
                    addArgE2C(k);
            }
            pro = true;//find E2C arguments
        }
        return getArgE2C();
    }

    private void addArgE2C(ArrayList<KB_Node> k) {
        this.argFound.add(k);
    }

    private ArrayList<ArrayList<KB_Node>> getArgE2C() {
        return argFound;
    }

    private void setJE2C(ArrayList<KB_Node> argList) {
        pathList.add(argList);
    }

    private ArrayList<ArrayList<KB_Node>> getJE2C() {
        return pathList;
    }

    private void setParents(ArrayList<KB_Node> parents) {
        rootParents = parents;
    }

    private ArrayList<KB_Node> getParents() {
        return rootParents;
    }
}
