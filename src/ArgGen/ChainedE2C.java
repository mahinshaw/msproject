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
        int argNo = 1;

        ArrayList<ArgumentTree> treeList = new ArrayList<ArgumentTree>();

        for (List<KB_Node> n : pathList) {
            int nodeIndex = 0;
            ArgumentTree tree = null;
            ArgumentFactory argumentFactory = null;
            ArgumentObject argumentObject = null;
            treeList.add(createTree(n, arg, tree, argumentFactory, argumentObject, argNo, nodeIndex));
            argNo++;
        }

        XMLWriter writer = new XMLWriter();
        writer.writeXML(treeList, "Chained E2C");

    }

    private ArgumentTree createTree(List<KB_Node> n, ArgStructure arg, ArgumentTree tree, ArgumentFactory argumentFactory, ArgumentObject argumentObject, int argNo, int nodeIndex) {
        if (n.get(nodeIndex).getType() == 'D') {
            argumentFactory.setDatum(String.valueOf(n.get(nodeIndex).getId()), arg.getText(String.valueOf(n.get(nodeIndex).getId())));
            ArgumentObject argumentObject2 = argumentFactory.createArgument(argNo);
            tree.addSubArgument(argumentObject2, argumentObject);
            System.out.println(String.valueOf("Data: " + n.get(nodeIndex).getId()) + "\n");
            return tree;
        } else {
            argumentFactory = new ArgumentFactory();
            argumentFactory.setHypothesis(String.valueOf(n.get(nodeIndex).getId()), arg.getText(String.valueOf(n.get(nodeIndex).getId())));
            System.out.println(String.valueOf("Hypo: " + n.get(nodeIndex).getId()));

            KB_Arc arc = findEdgeID(n.get(nodeIndex), n.get(++nodeIndex));
            System.out.println(String.valueOf("Gen: " + arc.getEdge_id()));
            argumentFactory.addGeneralization(arc.getEdge_id(), arg.getText(String.valueOf(arc.getEdge_id())));

            ArgumentObject argumentObject1 = argumentFactory.createArgument(argNo);

            if (nodeIndex == 1) {
                tree = ArgumentTree.createArgumentTree(argumentObject1);
            }
            else if (n.get(nodeIndex).getType() != 'D') {
                tree.addSubArgument(argumentObject1, argumentObject);
            } else{
                argumentObject1 = argumentObject;
            }
            createTree(n, arg, tree, argumentFactory, argumentObject1, argNo, nodeIndex);
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
