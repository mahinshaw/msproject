package ArgGen;

import KB.KB_Graph.KB_Graph;
import KB.KB_Node.KB_Node;

import java.util.ArrayList;

/**
 * User: Tshering Tobgay
 * Date: 8/2/13
 */
public class ArgBuilder {

    private KB_Graph graph;
    private KB_Node rootNode;
    private KB_Node dataNode;
    private ArrayList<KB_Node> genNodes;
    private ArrayList<KB_Node> argList;
    private final char HYPO;
    private final char DATA;


    public ArgBuilder(KB_Graph graph, KB_Node rootNode) {
        this.graph = graph;
        this.rootNode = rootNode;
        this.genNodes = new ArrayList<KB_Node>();
        this.argList = new ArrayList<KB_Node>();
        this.HYPO = 'H';
        this.DATA = 'D';
        findArgument();
    }

    public void findArgument() {
        if (checkHypo()) {
            argList.add(E2C());
        }
    }

    private KB_Node E2C() {
     /*   if (rootNode.getChildren().isEmpty()) {
            System.out.println("No children for:\n "+rootNode.toString());
        } else{
            for (KB_Node k : rootNode.getChildren()){
                System.out.println("Children:\n "+k.toString());
            }
        }         */

        traverseGraph(rootNode, argList);
        for (KB_Node n : argList) {
            System.out.println("--> " + n.toString() + "\n");
        }


        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    private void traverseGraph(KB_Node root, ArrayList<KB_Node> pathList) {
        pathList.add(root);
        for (KB_Node n : root.getChildren()) {
            if (!pathList.contains(n)) {
                traverseGraph(n, pathList);
            }
        }

    }

    private boolean checkHypo() {
        boolean hold = true;
        if (rootNode.getType() != HYPO) {
            hold = false;
        }
        return hold;
    }

}
