package ArgGen;

import KB.KB_Graph.KB_Graph;
import KB.KB_Node.KB_Node;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Tshering Tobgay
 * Date: 8/2/13
 */
public class ArgBuilder {

    private KB_Graph graph;
    private KB_Node rootNode;
    private ArrayList<ArrayList<KB_Node>> pathList;
    private E2C e2c;
    private final char HYPO;
    private final char DATA;


    public ArgBuilder(KB_Graph graph, KB_Node rootNode) {
        this.graph = graph;
        this.rootNode = rootNode;
        this.pathList = new ArrayList<ArrayList<KB_Node>>();
        this.HYPO = 'H';
        this.DATA = 'D';
        e2c = new E2C(this.rootNode, this.DATA);
        findArgument();
    }

    public void findArgument() {
        if (checkHypo()) {
            pathList = new ArrayList<ArrayList<KB_Node>>(e2c.getPathList());
        }
        /**
         * Test output
         */
        System.out.println("Path for E2C");
        for (List<KB_Node> n : pathList) {
            for (KB_Node x : n) {
                System.out.print(x.getId()+" ");
            }
            System.out.println("\n-----------");
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
