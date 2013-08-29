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
 * Date: 8/27/13
 */
public class ChainedE2C {
    private ArrayList<KB_Arc> gen;
    private KB_Node hypo, data;

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

    public void addArgument(ArrayList<ArrayList<KB_Node>> pathList, ArgStructure arg) {
        KB_Node hyp = null;
        KB_Arc arc = null;
        KB_Node datum = null;
        gen = new ArrayList<KB_Arc>();
        int i = 1;

        ArrayList<ArgumentTree> treeList = new ArrayList<ArgumentTree>();

        for (List<KB_Node> n : pathList) {
            int z = 1;
            ArgumentTree tree = null;
            ArgumentFactory argumentFactory = new ArgumentFactory();
            treeList.add(createChainedE2C(n, n.get(0), i, z, arg, tree, argumentFactory));
            i++;
        }

        //XMLWriter writer = new XMLWriter();
        //writer.writeXML(treeList, "Test");

    }

    private ArgumentTree createChainedE2C(List<KB_Node> n, KB_Node kb_node, int i, int z, ArgStructure arg, ArgumentTree tree, ArgumentFactory argumentFactory) {
        KB_Arc arc = null;

        if (kb_node.getType() != 'D') {
            argumentFactory.setHypothesis(String.valueOf(kb_node.getId()), arg.getText(String.valueOf(kb_node.getId())));
            System.out.println(String.valueOf("Hypo: " + kb_node.getId()));

            arc = findEdgeID(kb_node, n.get(z++));
            System.out.println(String.valueOf("Gen: " + arc.getEdge_id()));
            argumentFactory.addGeneralization(arc.getEdge_id(), arg.getText(String.valueOf(arc.getEdge_id())));
        }

        if (kb_node.getType() == 'D') {
            argumentFactory.setDatum(String.valueOf(kb_node.getId()), arg.getText(String.valueOf(kb_node.getId())));
            ArgumentObject argumentObject = argumentFactory.createArgument(i);
            tree.addSubArgument(argumentObject);
            System.out.println(String.valueOf("Data: " + kb_node.getId()) + "\n");
            return tree;
        } else {
            ArgumentObject argumentObject = argumentFactory.createArgument(i);
            if (z == 2) {
                System.out.println("HERE!");
                tree = ArgumentTree.createArgumentTree(argumentObject);
            } else {
                tree.addSubArgument(argumentObject);
            }

            kb_node = n.get(z - 1);

            createChainedE2C(n, kb_node, i, z, arg, tree, argumentFactory);
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
}
