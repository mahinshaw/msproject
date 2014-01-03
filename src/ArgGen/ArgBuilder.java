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
    private boolean argType;
    private E2C e2c;
    private NE2C ne2c;
    private JE2C je2c;


    public ArgBuilder(ArrayList<KB_Node> graphNodes, KB_Node rootNode, ArgStructure arg) {
        this.graphNodes = graphNodes;
        this.rootNode = rootNode;
        this.pathList = new ArrayList<ArrayList<KB_Node>>();
        e2c = new E2C(this.rootNode);
        ne2c = new NE2C(this.rootNode);
        je2c = new JE2C(this.rootNode, this.graphNodes, arg);
        argType = false;
    }

    /**
     * Call to different argument generators
     * If not empty, get argument and argument type
     */
    public void findArgument() {
        ArrayList<ArrayList<KB_Node>> hold;

        hold = e2c.getPathList();
        if (!hold.isEmpty()) {
            pathList.addAll(hold);//E2C
            argType = true;
        }
            /*
            hold = ne2c.getPathList();
            if (!hold.isEmpty()) {
                pathList.addAll(hold);//NE2C
            }
*/

        hold = je2c.getPathList();
        if (!hold.isEmpty()) {
            pathList.addAll(hold);//NE2C
            for (int y = 0; y < hold.size(); y++)
                argType = true;
        }

    }

    public ArrayList<ArrayList<KB_Node>> getPathList() {
        return pathList;
    }

    public boolean getArgType() {
        return argType;
    }
}
