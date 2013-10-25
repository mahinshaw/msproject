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

    public JE2C(KB_Node rootNode, ArrayList<KB_Node> graphNodes, ArgStructure arg) {
        this.rootNode = rootNode;
        this.argList = new ArrayList<KB_Node>();
        this.pathList = new ArrayList<ArrayList<KB_Node>>();
        this.graphNodes = graphNodes;
        this.arg = arg;
    }

    public void findPath() {

        ArrayList<KB_Node> findParents = new ArrayList<KB_Node>();
        ArrayList<KB_Node> graphRoots = new ArrayList<KB_Node>();
        ArrayList<KB_Node> rootParents = new ArrayList<KB_Node>();
        KB_Node parent = null;
        /**
         * TODO: FIND ways to find all the roots of the graph
         */
        graphRoots.add(graphNodes.get(0));
        graphRoots.add(graphNodes.get(3));
        for (KB_Node g : graphRoots) {
            rootParents = findParents(rootNode, g, findParents, parent);
        }

        setParents(rootParents);

        if (getParents().isEmpty()) {
            System.out.println("No parent(s) for node " + rootNode.getId() + " (JE2C Scheme)");
        } else {
            traverseGraph(rootNode, getParents());
        }

        for (KB_Node n : argList) {
            System.out.println(n.getId());
        }

        argGenerator(getJE2C());
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

            KB_Arc arc = findEdgeID(n.get(1), n.get(0));
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
     * Find the parents of the child in the parameter
     * using BFS algorithm.
     *
     * @param child
     * @param root
     * @param parents
     * @param parent  @return
     */
    private ArrayList<KB_Node> findParents(KB_Node child, KB_Node root, ArrayList<KB_Node> parents, KB_Node parent) {
        if (root == child) {
            parents.add(parent);
        }
        for (KB_Node n : root.getChildren()) {
            parent = root;
            findParents(child, n, parents, parent);
        }
        return parents;
    }

    /**
     * @param root
     * @param rootParents
     */
    private void traverseGraph(KB_Node root, ArrayList<KB_Node> rootParents) {
        argList.add(root);
        for (KB_Node n : rootParents)
            argList.add(n);
        setJE2C(argList);
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
