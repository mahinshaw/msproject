package ArgGen;

import ArgumentStructure.ArgumentObject;
import ArgumentStructure.ArgumentFactory;
import ArgumentStructure.ArgumentTree;
import ArgumentStructure.XMLWriter;
import GAIL.src.XMLHandler.ArgStructure;
import KB.KB_Arc.KB_Arc;
import KB.KB_Graph.KB_Graph;
import KB.KB_Node.KB_Node;

import java.util.ArrayList;
import java.util.List;

import static ArgGen.ArgumentGenerator.TYPE.HYPO;

/**
 * User: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * Date: 7/30/13
 * github: https://github.com/mahinshaw/msproject
 */
public class ArgumentGenerator {

    private List<List<KB_Node>> pathArray;
    private List<KB_Node> argPath;
    private KB_Graph kbGraph;
    private KB_Node rootNode, hypo, data;
    private ArrayList<KB_Arc> gen;
    private ArgBuilder argBuilder;
    private ArgStructure arg;
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
        int i = 1;
        ArrayList<ArgumentTree> treeList = new ArrayList<ArgumentTree>();

        for (List<KB_Node> n : argBuilder.getPathList()) {
            int z = 1;
            for (KB_Node x : n) {
                initVariable(x);
                if (z != n.size()) {
                    gen.add(findEdgeID(x, n.get(z++)));
                }
            }
            treeList.add(createArguments(i));
            gen.clear();
            i++;
        }

        XMLWriter writer = new XMLWriter();
        //  writer.writeXML(treeList, question);
    }

    private ArgumentTree createArguments(int i) {

        ArgumentFactory argumentFactory = new ArgumentFactory();
        argumentFactory.setHypothesis(String.valueOf(hypo.getId()), arg.getText(String.valueOf(hypo.getId())));
        if (data != null)
            argumentFactory.setDatum(String.valueOf(data.getId()), arg.getText(String.valueOf(data.getId())));
        for (KB_Arc k : gen) {
            argumentFactory.addGeneralization(k.getEdge_id(), arg.getText(String.valueOf(k.getEdge_id())));
        }
        ArgumentObject argumentObject = argumentFactory.createArgument(i);
        ArgumentTree tree = ArgumentTree.createArgumentTree(argumentObject);

        return tree;
    }

    private void initVariable(KB_Node x) {
        switch (x.getType()) {
            case 'H':
                hypo = x;
                break;
            case 'D':
                data = x;
                break;
        }
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
}


