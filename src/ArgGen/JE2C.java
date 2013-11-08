package ArgGen;

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
    private ArgInfo argInfo = new ArgInfo();

    public JE2C(KB_Node rootNode, ArrayList<KB_Node> graphNodes, ArgStructure arg) {
        this.rootNode = rootNode;
        this.argList = new ArrayList<KB_Node>();
        this.pathList = new ArrayList<ArrayList<KB_Node>>();
        this.graphNodes = graphNodes;
        this.arg = arg;
    }

    public ArrayList<ArrayList<KB_Node>> getPathList() {
        if (rootNode.getChildren().isEmpty()) {
            System.out.println("No child(ren) for node " + rootNode.getId() + " (JE2C Scheme)");
        } else {
            for (KB_Node n : rootNode.getChildren())
                if (argInfo.findEdge(rootNode, n).getType().equalsIgnoreCase(String.valueOf(ArgInfo.ArcTYPE.SYNERGY.getType())))
                    traverseGraph(n, rootNode, 1);
        }
       // argGenerator(pathList);

        return pathList;
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

            KB_Arc arc = findEdgeID(n.get(0), n.get(1));
            argumentFactory.addGeneralization(arc.getEdge_id(), arg.getText(String.valueOf(arc.getEdge_id())));

            //TODO: Change it so that multiple data can be added
            // for (KB_Node d : getParents()) {
            argumentFactory.setDatum(Integer.toString(n.get(1).getId()), arg.getText(Integer.toString(n.get(1).getId())));
            //}

            ArgumentObject argumentObject = argumentFactory.createArgument(i, "JE2C");

            ArgumentTree tree = ArgumentTree.createArgumentTree(argumentObject);

            treeList.add(tree);
            i++;
        }
        XMLWriter writer = new XMLWriter();
        writer.writeXML(treeList, "JE2C");
    }

    /**
     * Since the root has a X0 relation with its parent, check its other parent
     *
     * @param child
     */
    private void traverseGraph(KB_Node child, KB_Node knownParent, int parentNo) {
        ArrayList<KB_Node> argPath = new ArrayList<KB_Node>();
        KB_Node otherParent = null;
        String holdInflunce = "";
        argPath.add(knownParent);
        String arcID = argInfo.findEdge(knownParent, child).getEdge_id();

        for (KB_Node p : argInfo.getParents(child, graphNodes)) {
            //Find all parents other than rootNode
            if (knownParent != p) {
                holdInflunce = argInfo.findEdge(p, child).getType();
                //Find parents with only synergy relation to child
                if (holdInflunce.equalsIgnoreCase(String.valueOf(ArgInfo.ArcTYPE.SYNERGY.getType()))) {
                    //Find the other parent for rootNode by edge ID among the synergy-related parents
                    if (arcID.equalsIgnoreCase(argInfo.findEdge(p, child).getEdge_id())){
                        otherParent = p;
                        argPath.add(child);
                    }
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

    private void setJE2C(ArrayList<KB_Node> argList) {
        pathList.add(argList);
    }

    private ArrayList<ArrayList<KB_Node>> getJE2C() {
        return pathList;
    }

    /**
     * Returns the ArcID between two nodes
     *
     * @param parent
     * @param child
     * @return
     */
    public KB_Arc findEdgeID(KB_Node parent, KB_Node child) {
        String str = " ";
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

        } else {
            System.out.println("No ID found.");
        }
        return arc;
    }
}
