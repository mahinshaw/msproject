package KB_Arc;

import KB_Node.KB_Person;

/**
 * Author: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * gitHub: https://github.com/mahinshaw/msproject.git
 */
public class Influence_Arc extends KB_Arc {

    private String type;
    private KB_Person parent;
    private KB_Person child;

    public Influence_Arc() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public KB_Person getParent() {
        return parent;
    }

    public void setParent(KB_Person parent) {
        this.parent = parent;
    }

    public KB_Person getChild() {
        return child;
    }

    public void setChild(KB_Person child) {
        this.child = child;
    }
}
