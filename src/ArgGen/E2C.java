package ArgGen;

import KB.KB_Graph.KB_Graph;
import KB.KB_Node.KB_Node;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Tshering Tobgay
 * Date: 8/5/13
 */
public class E2C {
    private KB_Node rootNode;
    private ArrayList<KB_Node> tempList;
    private ArrayList<KB_Node> argList;
    private ArrayList<ArrayList<KB_Node>> pathList;
    private final char DATA;


    public E2C(KB_Node rootNode, char data) {
        this.rootNode = rootNode;
        this.tempList = new ArrayList<KB_Node>();
        this.argList = new ArrayList<KB_Node>();
        this.pathList = new ArrayList<ArrayList<KB_Node>>();
        this.DATA = data;
    }

    public ArrayList<ArrayList<KB_Node>> getPathList(){
        if (rootNode.getChildren().isEmpty()) {
            System.out.println("No children for node " + rootNode.getId());
        } else {
            traverseGraph(rootNode, tempList, pathList);
        }
        return pathList;
    }

    private void traverseGraph(KB_Node root, ArrayList<KB_Node> tempList, ArrayList<ArrayList<KB_Node>> pathList) {

        argList.add(root);

        if (root.getChildren().size() > 1) {
            tempList = new ArrayList<KB_Node>(argList);
        }
        if (root.getType() == DATA) {
            pathList.add(argList);
            argList = new ArrayList<KB_Node>(tempList);
        }

        for (KB_Node n : root.getChildren()) {
            if (!argList.contains(n)) {
                traverseGraph(n, tempList, pathList);
            }
        }
    }
}
