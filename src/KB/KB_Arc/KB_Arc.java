package KB.KB_Arc;

import KB.KB_Node.KB_Node;

import java.util.ArrayList;

/**
 * Author: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * gitHub: https://github.com/mahinshaw/msproject.git
 */
public class KB_Arc {

    private KB_Node child;
    private String edge_id;
    private ArrayList<KB_Node> parents;

    public KB_Arc(String edge_id, KB_Node child, ArrayList<KB_Node> parents) {
        this.edge_id = edge_id;
        this.child = child;
        this.parents = parents;
    }

    public KB_Node getChild() {
        return this.child;
    }

    public void setChild(KB_Node child) {
        this.child = child;
    }

    public void setParents(ArrayList<KB_Node> parents) {
        this.parents = parents;
    }

    public ArrayList<KB_Node> getParents(){
        return parents;
    }

    public String getEdge_id() {
        return this.edge_id;
    }

    public void setEdge_id(String edge_id) {
        this.edge_id = edge_id;
    }

    public String toString() {
        return "Child node ID: " + this.child.getId() + "\n" +
                "Edge_ID with parent_node: " + this.edge_id + "\n";
    }
}
