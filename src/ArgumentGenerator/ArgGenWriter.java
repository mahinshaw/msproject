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
 * User: Tshering Tobgay
 * Date: 1/5/14
 */
public class ArgGenWriter {
    private  ArrayList<ArrayList<KB_Node>> pathList;
    private boolean argType;
    private String question;
    private ArgStructure arg;
    private ArgInfo argInfo;

    public ArgGenWriter(ArgStructure arg, ArrayList<ArrayList<KB_Node>> pathList, boolean argType, String question){
        this.arg = arg;
        this.pathList = pathList;
        this.argType = argType;
        this.question = question;
        argInfo = new ArgInfo();
    }
    /**
     * This method builds the argument
     * from ArgBuilder. Arguments are called from ArgBuilder
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
        }else{
            System.out.println("\nEmpty pathList: No arguments generated ~ ArgumentGenerator.java");
        }
    }

    /**
     * This builds the actual argument fro the tree
     * @param n
     * @param tree
     * @param argumentFactory
     * @param argumentObject
     * @param argNo
     * @param nodeIndex
     * @return
     */
    private ArgumentTree createTree(List<KB_Node> n, ArgumentTree tree, ArgumentFactory argumentFactory, ArgumentObject argumentObject, int argNo, int nodeIndex) {
        //if (n.get(nodeIndex).getType() == 'D') {
        if (nodeIndex == n.size() - 1) {
            argumentFactory.setDatum(String.valueOf(n.get(nodeIndex).getId()), arg.getText(String.valueOf(n.get(nodeIndex).getId())));
            if (n.size() == 2) {
                ArgumentObject argumentObject3 = argumentFactory.createArgument(argNo);
                tree = ArgumentTree.createArgumentTree(argumentObject3);
            } else {
                ArgumentObject argumentObject2 = argumentFactory.createArgument(argNo);
                tree.addSubArgument(argumentObject2, argumentObject);
            }
            System.out.println(String.valueOf("Data: " + n.get(nodeIndex).getId()) + "\n");
            return tree;
        } else {
            argumentFactory = new ArgumentFactory();
            argumentFactory.setHypothesis(String.valueOf(n.get(nodeIndex).getId()), arg.getText(String.valueOf(n.get(nodeIndex).getId())));
            System.out.println(String.valueOf("Hypo: " + n.get(nodeIndex).getId()));

            KB_Arc arc = argInfo.findEdgeID(n.get(nodeIndex), n.get(++nodeIndex));
            System.out.println(String.valueOf("Gen: " + arc.getEdge_id()));
            argumentFactory.addGeneralization(arc.getEdge_id(), arg.getText(String.valueOf(arc.getEdge_id())));

            ArgumentObject argumentObject1 = null;
            if (n.size() > 2) {
                argumentObject1 = argumentFactory.createArgument(argNo);
                if (nodeIndex == 1) {
                    tree = ArgumentTree.createArgumentTree(argumentObject1);
                } //else if (n.get(nodeIndex).getType() != 'D') {
                else if (!n.get(nodeIndex).getChildren().isEmpty()){
                    tree.addSubArgument(argumentObject1, argumentObject);
                } else {
                    argumentObject1 = argumentObject;
                }
            }
            createTree(n, tree, argumentFactory, argumentObject1, argNo, nodeIndex);
        }
        return tree;
    }
}
