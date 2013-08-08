package ArgGen;

import ArgumentStructure.Argument;
import ArgumentStructure.ArgumentFactory;
import ArgumentStructure.ArgumentTree;
import ArgumentStructure.XMLWriter;
import GAIL.src.XMLHandler.ArgStructure;
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
    private ArrayList<KB_Node> gen;
    private ArgBuilder argBuilder;
    private ArgStructure arg;

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

    public ArgumentGenerator(KB_Node rootNode, ArgStructure arg) {
        this.pathArray = new ArrayList<List<KB_Node>>();
        this.argPath = new ArrayList<KB_Node>();
        this.arg = arg;
        this.rootNode = rootNode;
        this.argBuilder = new ArgBuilder(this.kbGraph, this.rootNode, HYPO.getType(), TYPE.DATA.getType());
        this.hypo = null;
        this.gen = new ArrayList<KB_Node>();
        this.data = null;
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
            for (KB_Node x : n) {
                initVariable(x);
            }
            treeList.add(createArguments(i));
            gen.clear();
            i++;
        }

        XMLWriter writer = new XMLWriter();
        writer.writeXML(treeList, "TEST");
    }

    private ArgumentTree createArguments(int i) {

        ArgumentFactory argumentFactory = new ArgumentFactory();
        argumentFactory.setHypothesis(String.valueOf(hypo.getId()), arg.getText(String.valueOf(hypo.getId())));
        argumentFactory.setDatum(String.valueOf(data.getId()), arg.getText(String.valueOf(data.getId())));
        for (KB_Node k : gen) {
            argumentFactory.addGeneralization(String.valueOf(k.getId()), arg.getText(String.valueOf(k.getId())));
        }
        Argument argument = argumentFactory.createArgument(i);
        ArgumentTree tree = ArgumentTree.createArgumentTree(argument);

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
            case 'G':
                gen.add(x);
                break;
        }
    }
}


