package ArgumentGenerator;

import KB.KB_Arc.KB_Arc;
import KB.KB_Node.KB_Node;

import java.util.ArrayList;
import java.util.Stack;

/**
 * User: Tshering Tobgay
 * Date: 10/24/13
 */
public class ArgInfo {
    public enum ArcTYPE{
        INFLUENCE("+"), SYNERGY("X0"), CONJ("%");
        private String type;
        ArcTYPE(String d) {
            type = d;
        }
        public String getType() {
            return type;
        }
    }

    /**
     * Find the parent(s) of the child
     *
     * @param child
     * @param graph
     * @return
     */
    public ArrayList<KB_Node> getParents(KB_Node child, ArrayList<KB_Node> graph) {
        ArrayList<KB_Node> parents = new ArrayList<KB_Node>();
        ArrayList<KB_Node> findParents = new ArrayList<KB_Node>();
        KB_Node parent = null;

        for (KB_Node g : findRoots(graph)) {
            parents = findParents(child, g, findParents, parent);
        }

        return parents;
    }

    /**
     * Search the graph for the root(s)
     *
     * @param graph
     * @return
     */
    private ArrayList<KB_Node> findRoots(ArrayList<KB_Node> graph) {
        ArrayList<KB_Node> roots = new ArrayList<KB_Node>();
        ArrayList<KB_Node> children = new ArrayList<KB_Node>();
        Stack parents = new Stack();
        KB_Node parent;

        //add parents in one stack and children in an arrayList
        for (KB_Node n : graph) {
            parents.push(n);
            if (!n.getChildren().isEmpty()) {
                for (KB_Node c : n.getChildren()) {
                    children.add(c);
                }
            }
        }
        //compare if parents are in the children stack
        while (!parents.isEmpty()) {
            parent = (KB_Node) parents.pop();
            if (!children.contains(parent)) {
                roots.add(parent);
            }
        }
        return roots;
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
     * Returns the ArcID between two nodes
     *
     * @param parent
     * @param child
     * @return
     */
    public KB_Arc findEdge(KB_Node parent, KB_Node child) {
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
            str = "No ID found.";
        }
        return arc;
    }

    /**
     * Find the argument type based
     * on the passed int parameter
     * @param i
     * @return
     */
    private String findArgType(int i) {
        String argT = "";
        switch (i) {
            case 1:
                argT = "E2C";
                break;
            case 2:
                argT = "NE2C";
                break;
            case 3:
                argT = "JE2C";
                break;
        }
        return argT;
    }
}
