package KB_Graph;

import java.util.ArrayList;
import KB_Node.*;
import KB_Arc.*;

/**
 *
 * Author: Mark Hinshaw
 * email: mahinshaw@gmail.com
 * git: https://github.com/mahinshaw/msproject
 *
 */

public class KB_Graph {

    private ArrayList<KB_Node> nodelist;
    private ArrayList<KB_Arc> arclist;

    private KB_Graph() {
        this.nodelist = new ArrayList<KB_Node>();
        this.arclist = new ArrayList<KB_Arc>();

    }

    public ArrayList<KB_Node> getNodelist() {
        return nodelist;
    }

    public ArrayList<KB_Arc> getArclist() {
        return arclist;
    }

    public void addNode(KB_Node node) {
        nodelist.add(node);
    }

    public void addArc(KB_Arc arc) {
        arclist.add(arc);
    }
}

