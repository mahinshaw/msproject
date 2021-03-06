package ArgumentGenerator.ArgLibrary;

import KB.KB_Arc.KB_Arc;
import KB.KB_Node.KB_Node;
import KB.KB_Node.Genotype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * ArgInfo.java has a collection of functions (a library of sorts) used multiple times
 * in the Argument Generator package.
 * User: Tshering Tobgay
 */
public class ArgInfo {
    /**
     * Enumerator: Collection of type of arguments found in the arcs
     */
    public enum ArcTYPE {
        INFLUENCE("+"), SYNERGY("X0"), CONJ("%"), MUTAL_EXCLUSIVE("X-"), NEGATIVE_INFL("-");
        private String type;

        ArcTYPE(String d) {
            type = d;
        }

        public String getType() {
            return type;
        }
    }

    /**
     * Enumerator: Collection of various mathematical representation of (certain) given strings
     */
    public enum mutationCheck {
        NOT_TWO("0...1"), ONE_OR_TWO("1...2"), ZERO("0"), ONE("1"), TWO("2");
        private String mutation;

        mutationCheck(String d) {
            mutation = d;
        }

        public String getMath() {
            return mutation;
        }
    }

    /**
     * Enumerator: Collection of different types of schemas
     */
    public enum schemaType {
        e2c("E2C"), ne2c("NE2C"), je2c("JE2C"), c2e("C2E"), nc2e("NC2E"), conj("Conjunction");
        private String schema;

        schemaType(String d) {
            schema = d;
        }

        public String getSchema() {
            return schema;
        }
    }

    /**
     * Enumerator: Collection of abnormalities: true, false, possible, or not possible
     */
    public enum abnormalType {
        TRUE("true"), FALSE("false"), POSSIBLE("possible"), NOT_POSSIBLE("not possible");
        private String abType;

        abnormalType(String d) {
            abType = d;
        }

        public String getAbType() {
            return abType;
        }
    }


    /**
     * Find the mutation type from KB/Genotype
     *
     * @param node node to check mutation
     * @return  the type of mutation
     */
    public String checkMutation(KB_Node node) {
        String str = "";
        if (node instanceof Genotype) {
            str = ((Genotype) node).getMutated();
        }
        return str;
    }

    /**
     * Find the parent(s) of the child
     *
     * @param child  the child whose parent(s) are to be found
     * @param graph  the graph of the KB
     * @return  parents of the given child
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
    public ArrayList<KB_Node> findRoots(ArrayList<KB_Node> graph) {
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
     * @param parent
     * @return
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
     * @param node_1
     * @param node_2
     * @return
     */
    public KB_Arc findEdgeID(KB_Node node_1, KB_Node node_2) {
        int i = 0, n = 0;
        KB_Arc arc = null;

        if (node_1.getChildren().contains(node_2)) {
            for (KB_Node m : node_1.getChildren()) {
                if (m.getId() == node_2.getId()) {
                    n = i;
                }
                i++;
            }
            arc = node_1.getArcs().get(n);
        } else if (node_2.getChildren().contains(node_1)) {
            for (KB_Node m : node_2.getChildren()) {
                if (m.getId() == node_1.getId()) {
                    n = i;
                }
                i++;
            }
            arc = node_2.getArcs().get(n);
        } else {
            System.out.println("No Arc ID found between " + node_1.getId() + " and " + node_2.getId() + " . ArgumentGenerator/ArgLibrary/ArgInfo");
        }
        return arc;
    }

    /**
     * Find the argument type based
     * on the passed int parameter
     *
     * @param i
     * @return
     */
    public String findArgType(int i) {
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

    /**
     * Check to see if the current argument is a question is pro or not pro.
     * For instance, if a question explores the possibilities of "possible" or "not possible" exhibition of certain aliment,
     * we add the "n" before the "p" in the question ID in the KB. This vital information will determine the course of the
     * argument generation.
     *
     * @param question          the current question
     * @param questionContainer the hash that stores all the questions with their quest ID
     * @return true - if question is "p", and false - if question is "np"
     */
    public boolean findCurrentQuestionNo(String question, HashMap<String, String> questionContainer) {
        boolean pro = true;
        if (questionContainer.containsKey(question)) {
            if (questionContainer.get(question).startsWith("n")) {
                pro = false;
            }
        }
        return pro;
    }
}
