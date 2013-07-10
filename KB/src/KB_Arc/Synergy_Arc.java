package KB_Arc;

import KB_Node.KB_Node;
import KB_Node.KB_Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * gitHub: https://github.com/mahinshaw/msproject.git
 */
public class Synergy_Arc extends KB_Arc {

    private String type;
    private KB_Node child;
    private ArrayList<KB_Node> parents;

    public Synergy_Arc(String type, ArrayList<KB_Node> parents, KB_Node child) {
        this.type = type;
        this.parents = parents;
        this.child = child;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public KB_Node getChild() {
        return child;
    }

    public void addChild(KB_Node child) {
        this.child = child;
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
}
