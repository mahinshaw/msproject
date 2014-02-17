package ArgumentGenerator;

import ArgumentStructure.ArgumentFactory;
import ArgumentStructure.ArgumentObject;
import ArgumentStructure.ArgumentTree;
import ArgumentStructure.XMLWriter;
import KB.XMLinterface.ArgStruct;
import KB.KB_Arc.KB_Arc;
import KB.KB_Node.KB_Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Finding arguments with multiple schemes using elimination logic.
 * May result in conjugation (%).
 * <p/>
 * User: Tshering Tobgay
 * Date: 1/17/14
 */
public class Conjunction {
    private KB_Node rootNode;
    private ArrayList<KB_Node> graphNodes;
    private ArgStruct arg;
    private ArrayList<ArrayList<ArrayList<KB_Node>>> argTree;
    private ArgInfo argInfo;
    private boolean pro;
    private ArgumentTree currentTree;
    private String question;

    private E2C e2c;
    private JE2C je2c;


    public Conjunction(KB_Node rootNode, ArrayList<KB_Node> graphNodes, ArgStruct arg, String question) {
        this.rootNode = rootNode;
        this.argTree = new ArrayList<ArrayList<ArrayList<KB_Node>>>();
        this.graphNodes = graphNodes;
        this.arg = arg;
        this.pro = false;
        this.question = question;
        argInfo = new ArgInfo();

    }

    public void findConjunction() {

        if (rootNode.getChildren().isEmpty())
            System.out.println("No child(ren) for node " + rootNode.getId() + " (ArgumentGenerator/Conjunction)");
        else if (checkElimination(rootNode))
            traverseGraph(rootNode);
        else
            System.out.println("ArgGen did not produce any argument using elimination logic ~ ArgumentGenerator/Conjunction.java");
    }

    /**
     * Find all possible argument based on known schemes.
     *
     * @param rootNode is the parentNode
     */
    private void traverseGraph(KB_Node rootNode) {
        ArrayList<ArrayList<ArrayList<KB_Node>>> conjPathRight = new ArrayList<ArrayList<ArrayList<KB_Node>>>();
        ArrayList<ArgumentTree> treeList = new ArrayList<ArgumentTree>();

        for (KB_Node n : rootNode.getChildren())
            if (argInfo.findEdge(rootNode, n).getType().equalsIgnoreCase(String.valueOf(ArgInfo.ArcTYPE.CONJ.getType())))
                findArgument(n);

        /**
         * Build the tree with left child handling set(s) of argument from one
         * argument stream (from the first index of the argTree), and the rest are added to the right child array.
         * This is done so that we can have a many-to-many relation in terms of building
         * an argument tree.
         *
         */
        if (!getArgTree().isEmpty()) {
            ArrayList<ArrayList<KB_Node>> conjPathLeft = getArgTree().get(0);
            for (int i = 0; i < getArgTree().size(); i++)
                if (i != 0)
                    conjPathRight.add(getArgTree().get(i));
            Stack<KB_Node> stack = new Stack<KB_Node>();
            int argNo = 1;
            for (ArrayList<KB_Node> left : conjPathLeft) {
                stack.push(left);
                for (ArrayList<ArrayList<KB_Node>> right : conjPathRight) {
                    for (ArrayList<KB_Node> r : right) {
                        stack.push(r);
                        treeList.add(conjTree(rootNode, stack, argNo++));
                        stack.push(left);
                    }
                }
            }
            stack.pop();
            XMLWriter writer = new XMLWriter();
            writer.writeXML(treeList, question);//write the final tree
        }
    }

    /**
     * Call to different argument generators.
     * If not empty, get argument and argument type
     */
    private void findArgument(KB_Node node) {
        ArrayList<ArrayList<KB_Node>> argFound;
        /**
         * Check for arguments in terms of E2C and NE2C scheme
         * (argType = true for E2C "abnormal" and argType = false for NE2C "not abnormal.")
         *  First find NE2C (false) arguments, then E2C (true)
         */
        int argNo = 2;//the number of arguments, in this case, it's 2 (E2C + NE2C)
        for (int i = 0; i < argNo; i++) {
            e2c = new E2C(node, pro);
            argFound = e2c.getPathList();
            if (!argFound.isEmpty()) {
                addArgTree(argFound);
            }
            pro = true;//find E2C arguments
        }
        /**
         * Check for JE2C scheme argument
         */
        pro = false;//check for JE2C first
        je2c = new JE2C(node, graphNodes, pro);
        argFound = je2c.getPathList();
        if (!argFound.isEmpty()) {
            addArgTree(argFound);
        }
    }

    /**
     * Builds the tree for the argument(s) computed in this class.
     *
     * @param rootNode
     * @param conjPath
     * @param argNo
     * @return
     */
    private ArgumentTree conjTree(KB_Node rootNode, Stack<KB_Node> conjPath, int argNo) {
        ArgumentFactory argumentFactory = new ArgumentFactory();
        argumentFactory.setHypothesis(String.valueOf(rootNode.getId()), arg.getText(String.valueOf(rootNode.getId())));
        System.out.println(String.valueOf("Hypo: " + rootNode.getId()));

        //Check for all generalizations
        for (KB_Node n : rootNode.getChildren()) {
            KB_Arc arc = argInfo.findEdgeID(rootNode, n);
            if (arc.getType().equalsIgnoreCase(ArgInfo.ArcTYPE.CONJ.getType())) {
                argumentFactory.addGeneralization(arc.getEdge_id(), arg.getText(String.valueOf(arc.getEdge_id())));
                System.out.println(String.valueOf("Gen: " + arc.getEdge_id()));
            }
        }

        //if (conjPath.getSize() != 1)
        argumentFactory.setDatum(true);//conjunction is set

        ArgumentObject argumentObject = argumentFactory.createArgument(argNo);
        ArgumentTree tree = ArgumentTree.createArgumentTree(argumentObject);

        ArgumentFactory argumentFactory1 = null;
        while (!conjPath.isEmpty()) {
            int nodeIndex = 0;
            tree = createTree(conjPath.pop(), tree, argumentFactory1, argumentObject, argNo, nodeIndex);
            argNo++;//argument index
        }
        return tree;
    }

    /**
     * Similar to ArgGenWriter.java with minor adjustments to accommodate the conjunction
     * nature of the argument(s).
     *
     * @param n               the list of KB_Nodes for an argument
     * @param tree            the current tree where the root is already attached
     * @param argumentFactory a generic argumentFactory, not related to its counterpart in the calling method
     * @param argumentObject  the main object from the calling method
     * @param argNo           the index of the current argument
     * @param nodeIndex       the counter the adjust the recursive method. Initialized from calling method to zero.
     * @return
     */

    private ArgumentTree createTree(List<KB_Node> n, ArgumentTree tree, ArgumentFactory argumentFactory, ArgumentObject argumentObject, int argNo, int nodeIndex) {
        /**
         * This is the base case for the recursive call. Once it reaches the last two nodes,
         * it will append the data tag to the (last) appropriate node.
         */
        if (nodeIndex == n.size() - 1) {
            argumentFactory.setDatum(String.valueOf(n.get(nodeIndex).getId()), arg.getText(String.valueOf(n.get(nodeIndex).getId())));
            ArgumentObject argumentObject2 = argumentFactory.createArgument(argNo);
            tree.addSubArgument(argumentObject2, argumentObject);

            System.out.println(String.valueOf("C: Data: " + n.get(nodeIndex).getId()) + "\n");

            setCurrentTree(tree);
            return getCurrentTree();
        }
        /**
         * All calls to method should start from here onwards.
         */
        else {
            argumentFactory = new ArgumentFactory();
            argumentFactory.setHypothesis(String.valueOf(n.get(nodeIndex).getId()), arg.getText(String.valueOf(n.get(nodeIndex).getId())));
            System.out.println(String.valueOf("C: Hypo: " + n.get(nodeIndex).getId()));

            KB_Arc arc = argInfo.findEdgeID(n.get(nodeIndex), n.get(++nodeIndex));
            argumentFactory.addGeneralization(arc.getEdge_id(), arg.getText(String.valueOf(arc.getEdge_id())));
            System.out.println(String.valueOf("C: Gen: " + arc.getEdge_id()));

            ArgumentObject argumentObject1 = null;
            if (n.size() > 2) {
                argumentObject1 = argumentFactory.createArgument(argNo);
                if (!n.get(nodeIndex).getChildren().isEmpty()) {
                    tree.addSubArgument(argumentObject1, argumentObject);
                } else {
                    argumentObject1 = argumentObject;
                }
            } else {
                argumentObject1 = argumentObject;
            }
            //Recursive call
            createTree(n, tree, argumentFactory, argumentObject1, argNo, nodeIndex);
        }
        return getCurrentTree();
    }

    /**
     * Check to see if elimination is possible.
     */
    private boolean checkElimination(KB_Node rootNode) {
        ArrayList<String> checkStr = new ArrayList<String>();
        boolean value = false;

        for (KB_Node child : rootNode.getChildren()) {
            String str = argInfo.checkMutation(child);
            if (!str.isEmpty())
                checkStr.add(str);
        }

        for (int i = 0; i < checkStr.size(); i++)
            if (i != checkStr.size() - 1)
                value = checkLogic(checkStr.get(i), checkStr.get(++i));

        return value;
    }

    /**
     * Returns boolean based on logical AND.
     *
     * @param strL The value for one of the operands
     * @param strR The other operand.
     * @return
     */
    private boolean checkLogic(String strL, String strR) {
        boolean value = false;//if both are equal to each other, false. Else, true if it has values in Enum.
        boolean left = false, right = false;
        if (!strL.equalsIgnoreCase(strR)) {
            if (strL.equalsIgnoreCase(ArgInfo.mutationCheck.ONE_OR_TWO.getMath()) || strL.equalsIgnoreCase(ArgInfo.mutationCheck.NOT_TWO.getMath()))
                left = true;
            if (strR.equalsIgnoreCase(ArgInfo.mutationCheck.ONE_OR_TWO.getMath()) || strR.equalsIgnoreCase(ArgInfo.mutationCheck.NOT_TWO.getMath()))
                right = true;
            if (left && right)
                value = true;
        }
        return value;
    }

    private void addArgTree(ArrayList<ArrayList<KB_Node>> pathList) {
        argTree.add(pathList);
    }

    private ArrayList<ArrayList<ArrayList<KB_Node>>> getArgTree() {
        return argTree;
    }

    /**
     * Called only by createTree method to preserve
     * the tree data structure from changing during recursion.
     *
     * @param tree
     */
    private void setCurrentTree(ArgumentTree tree) {
        this.currentTree = tree;
    }

    /**
     * Return the tree that was stored during recursion.
     *
     * @return
     */
    private ArgumentTree getCurrentTree() {
        return currentTree;
    }

}
