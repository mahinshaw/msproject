package ArgGen;

import KB.KB_Arc.KB_Arc;
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
    private NE2C ne2c;
    private final char HYPO;
    private final char DATA;


    public ArgBuilder(KB_Graph graph, KB_Node rootNode, char hypo, char data) {
        this.graph = graph;
        this.rootNode = rootNode;
        this.pathList = new ArrayList<ArrayList<KB_Node>>();
        this.HYPO = hypo;
        this.DATA = data;
        e2c = new E2C(this.rootNode, this.DATA, this.HYPO);
        ne2c = new NE2C(this.rootNode, this.DATA, this.HYPO);
    }

    /**
     * Method to find different arguments
     */
    public void findArgument() {
        if (checkHypo()) {
            setPathList(e2c.getPathList());//E2C
            setPathList(ne2c.getPathList());//NE2C
        }

    }

    private boolean checkHypo() {
        boolean hold = true;
        if (rootNode.getType() != HYPO) {
            hold = false;
        }
        return hold;
    }

    public void setPathList(ArrayList<ArrayList<KB_Node>> path) {
        for (ArrayList<KB_Node> k : path) {
            pathList.add(k);
        }
    }

    public ArrayList<ArrayList<KB_Node>> getPathList() {
        return pathList;
    }
}
