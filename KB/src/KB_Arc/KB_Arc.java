package KB_Arc;

import KB_Node.KB_Node;

/**
 * Author: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * gitHub: https://github.com/mahinshaw/msproject.git
 */
public class KB_Arc {

    private KB_Node child;

    public KB_Arc(KB_Node child) {
        this.child = child;
    }

    public KB_Node getChild() {
        return this.child;
    }

    public void setChild(KB_Node child) {
        this.child = child;
    }

    public String toString() {
        return "Child node ID: " + this.child.getId() + "\n";
    }
}
