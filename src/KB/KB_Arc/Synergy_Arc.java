package KB.KB_Arc;

import KB.KB_Node.KB_Node;

import java.util.ArrayList;

/**
 * Author: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * gitHub: https://github.com/mahinshaw/msproject.git
 */
public class Synergy_Arc extends KB_Arc {

    private String type;
    private ArrayList<KB_Node> parents;

    public Synergy_Arc(String syn_id, String type, ArrayList<KB_Node> parents, KB_Node child) {
        super(syn_id, child, parents);
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

    public void addParents(ArrayList<KB_Node> parents) {
        this.parents.addAll(parents);
    }

    public void addParent(KB_Node parent) {
        this.parents.add(parent);
    }


    @Override
    public void setParents(ArrayList<KB_Node> parents) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String toString() {
        String parentIDs = "";
        for (KB_Node parent : parents) {
            parentIDs += parent.getId() + " ";
        }
        return super.toString() + "\n" +
                "Synergy Type: " + this.type + "\n" +
                "Parent node IDs: " + parentIDs + "\n";
    }
}
