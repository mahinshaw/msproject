package ArgumentGenerator;

import ArgumentStructure.ArgumentFactory;
import ArgumentStructure.ArgumentObject;
import ArgumentStructure.ArgumentTree;
import ArgumentStructure.XMLWriter;
import GAIL.src.XMLHandler.ArgStructure;
import KB.KB_Arc.KB_Arc;
import KB.KB_Node.KB_Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Joint effect to case argument(s) calculated in JE2C.java
 * User: Tshering Tobgay
 * Date: 9/26/13
 */
public class JE2C {
    private KB_Node rootNode;
    private ArrayList<KB_Node> argList;
    private ArrayList<ArrayList<KB_Node>> pathList;
    private ArrayList<KB_Node> graphNodes;
    private ArrayList<KB_Node> rootParents;
    private ArgStructure arg;
    private ArgInfo argInfo;
    private E2C e2c;
    private boolean pro;

    public JE2C(KB_Node rootNode, ArrayList<KB_Node> graphNodes, ArgStructure arg, boolean pro) {
        this.rootNode = rootNode;
        this.argList = new ArrayList<KB_Node>();
        this.pathList = new ArrayList<ArrayList<KB_Node>>();
        this.graphNodes = graphNodes;
        this.arg = arg;
        this.pro = pro;
        argInfo = new ArgInfo();
    }

    /**
     * Find the JE2C argument by finding a synergy relation or S relation with its child(ren)
     * and the other parent node.
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
        return pathList;
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
        e2c = new E2C(argPath.get(argPath.size() - 1), pro);
        hold = e2c.getPathList();
        if (!hold.isEmpty()) {
            for (ArrayList<KB_Node> n : hold) {
                for (KB_Node k : n) {
                    argPath.add(k);
                }
            }
        }
        setJE2C(argPath);
        //TODO add the other parent's(parentNo 2) argument to the pathList
       /*
        if (parentNo==2){
            traverseGraph(child, otherParent, 2);
        }
         */
    }

    private void setParents(ArrayList<KB_Node> parents) {
        rootParents = parents;
    }

    private ArrayList<KB_Node> getParents() {
        return rootParents;
    }

    private void argGenerator(ArrayList<ArrayList<KB_Node>> pathList) {
        int i = 0;
        ArrayList<ArgumentTree> treeList = new ArrayList<ArgumentTree>();
        for (List<KB_Node> n : pathList) {
            ArgumentFactory argumentFactory = new ArgumentFactory();
            argumentFactory.setHypothesis(Integer.toString(n.get(0).getId()), arg.getText(Integer.toString(n.get(0).getId())));
            KB_Arc arc = argInfo.findEdgeID(n.get(0), n.get(1));
            argumentFactory.addGeneralization(arc.getEdge_id(), arg.getText(String.valueOf(arc.getEdge_id())));

            //TODO: Change it so that multiple data can be added
            // for (KB_Node d : getParents()) {
            argumentFactory.setDatum(Integer.toString(n.get(1).getId()), arg.getText(Integer.toString(n.get(1).getId())));
            ArgumentObject argumentObject = argumentFactory.createArgument(i);
            ArgumentTree tree = ArgumentTree.createArgumentTree(argumentObject);
            treeList.add(tree);
            i++;
        }
        XMLWriter writer = new XMLWriter();
        writer.writeXML(treeList, "JE2C");
    }

    private void setJE2C(ArrayList<KB_Node> argList) {
        pathList.add(argList);
    }

    private ArrayList<ArrayList<KB_Node>> getJE2C() {
        return pathList;
    }
}
