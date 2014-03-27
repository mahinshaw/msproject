package ArgumentGenerator.ArgGenerator;

import ArgumentGenerator.ArgLibrary.ArgInfo;
import ArgumentStructure.ArgumentFactory;
import ArgumentStructure.ArgumentObject;
import ArgumentStructure.ArgumentTree;
import ArgumentStructure.XMLWriter;
import KB.KB_Arc.KB_Arc;
import KB.KB_Node.KB_Node;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * ArgJenWriter.java is where the argument(s) found is added to the tree data structure and
 * its subsequent addition onto an XML tree.
 *
 * User: Tshering Tobgay
 * Date: 1/5/14
 */
public class ArgGenWriter {
    private ArrayList<ArrayList<KB_Node>> pathList;
    private String question;
    private HashMap<String, String> map;
    private ArgInfo argInfo;
    private ArgumentTree currentTree;

    public ArgGenWriter(HashMap<String, String> map, ArrayList<ArrayList<KB_Node>> pathList, String question) {
        this.map = map;
        this.pathList = pathList;
        this.question = question;
        argInfo = new ArgInfo();
    }

    /**
     * This method builds the argument from ArgBuilder. Arguments are called from ArgBuilder
     * based on the selected question.
     */
    public void addArgument() {
        ArrayList<ArgumentTree> treeList = new ArrayList<ArgumentTree>();
        int argNo = 1;

        if (!pathList.isEmpty()) {
            for (List<KB_Node> n : pathList) {
                int nodeIndex = 0;
                //argType = findArgType(argBuilder.getArgType().get(argNo - 1));
                ArgumentTree tree = null;
                ArgumentFactory argumentFactory = null;
                ArgumentObject argumentObject = null;
                treeList.add(createTree(n, tree, argumentFactory, argumentObject, argNo, nodeIndex));
                argNo++;
            }

            XMLWriter writer = new XMLWriter();
            writer.writeXML(treeList, question);
        } else {
            System.out.println("\nEmpty pathList: No arguments generated ~ ArgumentGenerator.java");
        }
    }

    /**
     * This builds the actual argument for the tree using a recursive method.
     *
     * @param n               list of argument nodes
     * @param tree            argument tree
     * @param argumentFactory argument tree function
     * @param argumentObject  argument tree object
     * @param argNo           argument number
     * @param nodeIndex       index/pointer to keep track of nodes in n list
     * @return
     */
    private ArgumentTree createTree(List<KB_Node> n, ArgumentTree tree, ArgumentFactory argumentFactory, ArgumentObject argumentObject, int argNo, int nodeIndex) {
        //if (n.get(nodeIndex).getType() == 'D') {
        /**
         * This is the base case for the recursive call. Once it reaches the last two nodes,
         * it will append the data tag to the (last) appropriate node.
         */
        if (nodeIndex == n.size() - 1) {
            argumentFactory.setDatum(String.valueOf(n.get(nodeIndex).getId()), map.get(String.valueOf(n.get(nodeIndex).getId())));
            if (n.size() == 2) {
                ArgumentObject argumentObject2 = argumentFactory.createArgument(argNo);
                //argumentObject = argumentFactory.createArgument(argNo);
                tree = ArgumentTree.createArgumentTree(argumentObject2);
            } else {
                ArgumentObject argumentObject2 = argumentFactory.createArgument(argNo);
                tree.addSubArgument(argumentObject2, argumentObject);
            }
            //System.out.println(String.valueOf("Data: " + n.get(nodeIndex).getId()) + "\n");
            setCurrentTree(tree);
            //return getCurrentTree();
            return tree;
        }
        /**
         * All calls to method should start from here onwards.
         */
        else {
            argumentFactory = new ArgumentFactory();
            argumentFactory.setHypothesis(String.valueOf(n.get(nodeIndex).getId()), map.get(String.valueOf(n.get(nodeIndex).getId())));
            System.out.println(String.valueOf("Hypo: " + n.get(nodeIndex).getId()));

            KB_Arc arc = argInfo.findEdgeID(n.get(nodeIndex), n.get(++nodeIndex));
            argumentFactory.addGeneralization(arc.getEdge_id(), map.get(String.valueOf(arc.getEdge_id())));
            System.out.println(String.valueOf("Gen: " + arc.getEdge_id()));

            ArgumentObject argumentObject1 = null;
            if (n.size() > 2) {
                argumentObject1 = argumentFactory.createArgument(argNo);
                if (nodeIndex == 1) {
                    tree = ArgumentTree.createArgumentTree(argumentObject1);
                }
                else if (nodeIndex != n.size() - 1) {
                    tree.addSubArgument(argumentObject1, argumentObject);
                } else {
                    argumentObject1 = argumentObject;
                }
            }
            //Recursive call
            tree = createTree(n, tree, argumentFactory, argumentObject1, argNo, nodeIndex);
        }
        //return getCurrentTree();
        return tree;
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
