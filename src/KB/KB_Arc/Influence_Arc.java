package KB.KB_Arc;

import KB.KB_Node.KB_Node;
import KB.KB_Node.KB_Person;

import java.util.ArrayList;

/**
 * Author: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * gitHub: https://github.com/mahinshaw/msproject.git
 */
public class Influence_Arc extends KB_Arc {

    private String type;
    private ArrayList<KB_Node> parents;

    public Influence_Arc(String arc_id, String type, ArrayList<KB_Node> parents, KB_Node child) {
        super(arc_id, child, parents);
        this.type = type;
        this.parents = parents;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<KB_Node> getParents() {
        return parents;
    }

    public void setParent(ArrayList<KB_Node> parent) {
        this.parents = parent;
    }

    @Override
    public void setParents(ArrayList<KB_Node> parents) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String toString() {
        return super.toString() + "\n" +
                "Type: " + this.type + "\n";
                //+"Parent node ID: " + this.parent.getId() + "\n";
    }
}
