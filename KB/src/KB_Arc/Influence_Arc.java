package KB_Arc;

import KB_Node.KB_Node;
import KB_Node.KB_Person;

/**
 * Author: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * gitHub: https://github.com/mahinshaw/msproject.git
 */
public class Influence_Arc extends KB_Arc {

    private String type;
    private KB_Node parent;
    private KB_Node child;

    public Influence_Arc(String type, KB_Node parent, KB_Node child) {
        this.type = type;
        this.parent = parent;
        this.child = child;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public KB_Node getParent() {
        return parent;
    }

    public void setParent(KB_Node parent) {
        this.parent = parent;
    }

    public KB_Node getChild() {
        return child;
    }

    public void setChild(KB_Node child) {
        this.child = child;
    }
}
