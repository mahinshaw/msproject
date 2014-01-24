package ArgumentGenerator;

import ArgumentGenerator.ArgInfo;
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
 * Finding arguments with multiple schemes using conjugation (%).
 * User: Tshering Tobgay
 * Date: 1/17/14
 */
public class Conjunction {
    private KB_Node rootNode;
    private ArrayList<KB_Node> graphNodes;
    private ArgStructure arg;
    private ArrayList<KB_Node> argList;
    private ArrayList<ArrayList<KB_Node>> pathList;
    private ArrayList<ArrayList<ArrayList<KB_Node>>> argTree;
    private ArgInfo argInfo;
    private boolean counter; //check to see if argument exists: true, if conjunction exists, and false, if not.
    private int argCount;//the number of arguments in a single tree
    private boolean pro;
    private ArgumentObject currentObject;

    private E2C e2c;
    private JE2C je2c;


    public Conjunction(KB_Node rootNode, ArrayList<KB_Node> graphNodes, ArgStructure arg, int argCount) {
        this.rootNode = rootNode;
        this.argList = new ArrayList<KB_Node>();
        this.pathList = new ArrayList<ArrayList<KB_Node>>();
        this.argTree = new ArrayList<ArrayList<ArrayList<KB_Node>>>();
        this.graphNodes = graphNodes;
        this.arg = arg;
        this.counter = false;
        this.argCount = argCount;
        this.pro = false;
        argInfo = new ArgInfo();

    }

    public void findConjunction() {

        if (rootNode.getChildren().isEmpty()) {
            System.out.println("No child(ren) for node " + rootNode.getId() + " (ArgumentGenerator/Conjunction)");
        } else {
            System.out.println("Made it to conjunction");
            traverseGraph(rootNode);
        }

       /* if (argTrack != argCount) {
            System.out.println("No arguments in (ArgumentGenerator/Conjunction)");
        }*/
    }

    /**
     * Find all possible argument based on known schemes.
     *
     * @param rootNode is the parentNode
     */
    private void traverseGraph(KB_Node rootNode) {
        ArrayList<ArrayList<KB_Node>> conjPath = new ArrayList<ArrayList<KB_Node>>();
        ArrayList<ArrayList<ArrayList<KB_Node>>> conjPathRight = new ArrayList<ArrayList<ArrayList<KB_Node>>>();
        ArrayList<ArgumentTree> treeList = new ArrayList<ArgumentTree>();

        int argTrack = 0;

        for (KB_Node n : rootNode.getChildren())
            if (argInfo.findEdge(rootNode, n).getType().equalsIgnoreCase(String.valueOf(ArgInfo.ArcTYPE.CONJ.getType()))) {
                findArgument(n);
                argTrack++;
            }
        /**
         * Build the tree with left child handling set(s) of argument from one
         * argument stream (from the first index of the argTree), and the rest are added to the right child array.
         * This is done so that we can have a many-to-many relation in terms of building
         * an argument tree.
         *
         */
        ArrayList<ArrayList<KB_Node>> conjPathLeft = getArgTree().get(0);
        for (int i = 0; i < getArgTree().size(); i++)
            if (i != 0)
                conjPathRight.add(getArgTree().get(i));
        Stack<KB_Node> stack = new Stack<KB_Node>();
        Stack<KB_Node> tempStack = new Stack<KB_Node>();
        int argNo = 1;
        for (ArrayList<KB_Node> left : conjPathLeft) {
           //conjPath.add(left);
            stack.push(left);
            for (ArrayList<ArrayList<KB_Node>> right : conjPathRight) {
                for (ArrayList<KB_Node> r : right) {
                    stack.push(r);
                    tempStack.addAll(stack);
                    //conjPath.add(r);
                    treeList.add(conjTree(rootNode, tempStack, argNo++));
                    stack.pop();
                }
            }
        }

        XMLWriter writer = new XMLWriter();
        writer.writeXML(treeList, "Conjunction test");
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
        je2c = new JE2C(node, graphNodes, arg, pro);
        argFound = je2c.getPathList();
        if (!argFound.isEmpty()) {
            addArgTree(argFound);
        }
    }

    private void addArgTree(ArrayList<ArrayList<KB_Node>> pathList) {
        argTree.add(pathList);
    }

    private ArrayList<ArrayList<ArrayList<KB_Node>>> getArgTree() {
        return argTree;
    }

    private ArgumentTree conjTree(KB_Node rootNode, Stack<KB_Node> conjPath, int argNo) {

        ArgumentFactory argumentFactory = null;
        int nodeIndex = 0;

        argumentFactory = new ArgumentFactory();
        argumentFactory.setHypothesis(String.valueOf(rootNode.getId()), arg.getText(String.valueOf(rootNode.getId())));
        System.out.println(String.valueOf("Hypo: " + rootNode.getId()));

        for (KB_Node n : rootNode.getChildren()) {
            KB_Arc arc = argInfo.findEdgeID(rootNode, n);
            argumentFactory.addGeneralization(arc.getEdge_id(), arg.getText(String.valueOf(arc.getEdge_id())));
            System.out.println(String.valueOf("Gen: " + arc.getEdge_id()));
        }
        argumentFactory.setDatum(true);

        ArgumentObject argumentObject = argumentFactory.createArgument(argNo);
        ArgumentTree tree = ArgumentTree.createArgumentTree(argumentObject);

        ArgumentTree tree1 = null;
        ArgumentFactory argumentFactory1 = null;
        ArgumentObject argumentObject1 = null;

        while(!conjPath.isEmpty()) {
       // for (ArrayList<KB_Node> m : conjPath) {
            nodeIndex = 0;
            tree.addSubArgument(createTree(conjPath.pop(), tree1, argumentFactory1, argumentObject1, argNo, nodeIndex), argumentObject);
            argNo++;
        }

        return tree;
    }

    private ArgumentObject createTree(List<KB_Node> n, ArgumentTree tree, ArgumentFactory argumentFactory, ArgumentObject argumentObject, int argNo, int nodeIndex) {
        //if (n.get(nodeIndex).getType() == 'D') {
        /**
         * This is the base case for the recursive call. Once it reaches the last two nodes,
         * it will append the data tag to the (last) appropriate node.
         */
        if (nodeIndex == n.size() - 1) {
            argumentFactory.setDatum(String.valueOf(n.get(nodeIndex).getId()), arg.getText(String.valueOf(n.get(nodeIndex).getId())));
            if (n.size() == 2) {
                argumentObject = argumentFactory.createArgument(argNo);
                //argumentObject = argumentFactory.createArgument(argNo);
                //tree = ArgumentTree.createArgumentTree(argumentObject2);
            } else {
                ArgumentObject argumentObject2 = argumentFactory.createArgument(argNo);
                tree.addSubArgument(argumentObject2, argumentObject);
            }
            System.out.println(String.valueOf("Data: " + n.get(nodeIndex).getId()) + "\n");

            setCurrentObject(argumentObject);
            return argumentObject;
        }
        /**
         * All calls to method should start from here onwards.
         */
        else {
            argumentFactory = new ArgumentFactory();
            argumentFactory.setHypothesis(String.valueOf(n.get(nodeIndex).getId()), arg.getText(String.valueOf(n.get(nodeIndex).getId())));
            System.out.println(String.valueOf("Hypo: " + n.get(nodeIndex).getId()));

            KB_Arc arc = argInfo.findEdgeID(n.get(nodeIndex), n.get(++nodeIndex));
            argumentFactory.addGeneralization(arc.getEdge_id(), arg.getText(String.valueOf(arc.getEdge_id())));
            System.out.println(String.valueOf("Gen: " + arc.getEdge_id()));

            ArgumentObject argumentObject1 = null;
            if (n.size() > 2) {
                argumentObject1 = argumentFactory.createArgument(argNo);
                if (nodeIndex == 1) {
                    tree = ArgumentTree.createArgumentTree(argumentObject1);
                } //else if (n.get(nodeIndex).getType() != 'D') {
                else if (!n.get(nodeIndex).getChildren().isEmpty()) {
                    tree.addSubArgument(argumentObject1, argumentObject);
                } else {
                    argumentObject1 = argumentObject;
                }
            }
            //Recursive call
            createTree(n, tree, argumentFactory, argumentObject1, argNo, nodeIndex);
        }
        return getCurrentObject();
    }

    /**
     * Called only by createTree method to preserve
     * the tree data structure from changing during recursion.
     *
     * @param currentObject
     */
    private void setCurrentObject(ArgumentObject currentObject) {
        this.currentObject = currentObject;
    }

    /**
     * Return the tree that was stored during recursion.
     *
     * @return
     */
    private ArgumentObject getCurrentObject() {
        return currentObject;
    }

}
