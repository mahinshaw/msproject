package ArgGen;

import GAIL.src.XMLHandler.ArgStructure;
import KB.KB_Arc.KB_Arc;
import KB.KB_Graph.KB_Graph;
import KB.KB_Node.KB_Node;
import com.sun.xml.internal.bind.v2.TODO;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Tshering Tobgay
 * Date: 8/2/13
 */
public class ArgBuilder {

    private ArrayList<KB_Node> graphNodes;
    private KB_Node rootNode;
    private ArrayList<ArrayList<KB_Node>> pathList;
    private ArrayList<Integer> argType;
    private E2C e2c;
    private NE2C ne2c;
    private JE2C je2c;
    private final char HYPO;
    private final char DATA;


    public ArgBuilder(ArrayList<KB_Node> graphNodes, KB_Node rootNode, char hypo, char data, ArgStructure arg) {
        this.graphNodes = graphNodes;
        this.rootNode = rootNode;
        this.pathList = new ArrayList<ArrayList<KB_Node>>();
        this.HYPO = hypo;
        this.DATA = data;
        e2c = new E2C(this.rootNode, this.DATA, this.HYPO, arg);
        ne2c = new NE2C(this.rootNode, this.DATA, this.HYPO);
        je2c = new JE2C(this.rootNode, this.graphNodes, arg);
        this.argType = new ArrayList<Integer>();
    }

    /**
     * Call to different argument generators
     * If not empty, get argument and argument type
     */
    public void findArgument() {
        ArrayList<ArrayList<KB_Node>> hold;
        if (checkHypo()) {

            hold = e2c.getPathList();
            if (!hold.isEmpty()) {
                pathList.addAll(hold);//E2C
                for (int y = 0; y < hold.size(); y++)
                    argType.add(1);
            }

            hold = ne2c.getPathList();
            if (!hold.isEmpty()) {
                pathList.addAll(hold);//NE2C
                for (int y = 0; y < hold.size(); y++)
                    argType.add(2);
            }

            //pathList.addAll(je2c.getPathList());//JE2C

            je2c.findPath();
        }
    }

    private boolean checkHypo() {
        boolean hold = true;
        if (rootNode.getType() != HYPO) {
            hold = false;
        }
        return hold;
    }

    public ArrayList<ArrayList<KB_Node>> getPathList() {
        return pathList;
    }

    public ArrayList<Integer> getArgType() {
        return argType;
    }
}
