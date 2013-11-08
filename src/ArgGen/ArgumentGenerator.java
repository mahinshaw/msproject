package ArgGen;

import ArgumentStructure.ArgumentObject;
import ArgumentStructure.ArgumentFactory;
import ArgumentStructure.ArgumentTree;
import ArgumentStructure.XMLWriter;
import GAIL.src.XMLHandler.ArgStructure;
import KB.KB_Arc.KB_Arc;
import KB.KB_Node.KB_Node;

import java.util.ArrayList;
import java.util.List;

import static ArgGen.ArgumentGenerator.TYPE.HYPO;

public class ArgumentGenerator {

    private List<List<KB_Node>> pathArray;
    private List<KB_Node> argPath;
    private KB_Node rootNode, hypo, data;
    private ArrayList<KB_Arc> gen;
    private ArgBuilder argBuilder;
    private ArgStructure arg;
    private String argType;
    private final String question;

    public enum TYPE {
        HYPO('H'), GEN('G'), DATA('D');
        private char type;

        TYPE(char d) {
            type = d;
        }

        public char getType() {
            return type;
        }
    }

    public ArgumentGenerator(KB_Node rootNode, ArgStructure arg, String question, ArrayList<KB_Node> graphNodes) {

        this.pathArray = new ArrayList<List<KB_Node>>();
        this.argPath = new ArrayList<KB_Node>();
        this.arg = arg;
        this.rootNode = rootNode;
        this.argBuilder = new ArgBuilder(graphNodes, this.rootNode, HYPO.getType(), TYPE.DATA.getType(), arg);
        this.hypo = null;
        this.gen = new ArrayList<KB_Arc>();
        this.data = null;
        this.question = question;
    }

    public void addNode(KB_Node node) {
        argPath.add(node);
    }

    public void addPath(List<KB_Node> path) {
        pathArray.add(path);
    }

    public void addArgument() {
        argBuilder.findArgument();
        ArrayList<ArgumentTree> treeList = new ArrayList<ArgumentTree>();
        int argNo = 1;

        if (!argBuilder.getPathList().isEmpty()) {

            for (List<KB_Node> n : argBuilder.getPathList()) {
                int nodeIndex = 0;
                argType = findArgType(argBuilder.getArgType().get(argNo - 1));
                ArgumentTree tree = null;
                ArgumentFactory argumentFactory = null;
                ArgumentObject argumentObject = null;
                treeList.add(createTree(n, tree, argumentFactory, argumentObject, argNo, nodeIndex));
                argNo++;
            }

            XMLWriter writer = new XMLWriter();
            writer.writeXML(treeList, question);
        }else{
            System.out.println("Empty pathList: No arguments generated ~ ArgymentGenerator.java");
        }
    }

    private ArgumentTree createTree(List<KB_Node> n, ArgumentTree tree, ArgumentFactory argumentFactory, ArgumentObject argumentObject, int argNo, int nodeIndex) {
        //if (n.get(nodeIndex).getType() == 'D') {
        if (nodeIndex == n.size() - 1) {
            argumentFactory.setDatum(String.valueOf(n.get(nodeIndex).getId()), arg.getText(String.valueOf(n.get(nodeIndex).getId())));
            if (n.size() == 2) {
                ArgumentObject argumentObject3 = argumentFactory.createArgument(argNo, argType);
                tree = ArgumentTree.createArgumentTree(argumentObject3);
            } else {
                ArgumentObject argumentObject2 = argumentFactory.createArgument(argNo, argType);
                tree.addSubArgument(argumentObject2, argumentObject);
            }
            System.out.println(String.valueOf("Data: " + n.get(nodeIndex).getId()) + "\n");
            return tree;
        } else {
            argumentFactory = new ArgumentFactory();
            argumentFactory.setHypothesis(String.valueOf(n.get(nodeIndex).getId()), arg.getText(String.valueOf(n.get(nodeIndex).getId())));
            System.out.println(String.valueOf("Hypo: " + n.get(nodeIndex).getId()));

            KB_Arc arc = findEdgeID(n.get(nodeIndex), n.get(++nodeIndex));
            System.out.println(String.valueOf("Gen: " + arc.getEdge_id()));
            argumentFactory.addGeneralization(arc.getEdge_id(), arg.getText(String.valueOf(arc.getEdge_id())));

           ArgumentObject argumentObject1 = null;
            if (n.size() > 2) {
                argumentObject1 = argumentFactory.createArgument(argNo, argType);
                if (nodeIndex == 1) {
                    tree = ArgumentTree.createArgumentTree(argumentObject1);
                } else if (n.get(nodeIndex).getType() != 'D') {
                    tree.addSubArgument(argumentObject1, argumentObject);
                } else {
                    argumentObject1 = argumentObject;
                }
            }
            createTree(n, tree, argumentFactory, argumentObject1, argNo, nodeIndex);
        }
        return tree;
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


