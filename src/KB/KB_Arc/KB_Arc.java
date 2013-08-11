package KB.KB_Arc;

import KB.KB_Node.KB_Node;

/**
 * Author: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * gitHub: https://github.com/mahinshaw/msproject.git
 */
public class KB_Arc {

    private KB_Node child;
    private String edge_id;

    public KB_Arc(String edge_id, KB_Node child) {
        this.edge_id = edge_id;
        this.child = child;
    }

    public KB_Node getChild() {
        return this.child;
    }

    public void setChild(KB_Node child) {
        this.child = child;
    }

    public String getEdge_id(){
        return this.edge_id;
    }

    public void setEdge_id(String edge_id){
        this.edge_id = edge_id;
    }

    public String toString() {
        return "Child node ID: " + this.child.getId() + "\n"+
                "Edge_ID with parent_node: "+this.edge_id+"\n";
    }
}
