package KB_Arc;

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
    private KB_Person child;
    private List<KB_Person> parents;

    public Synergy_Arc() {
        parents = new ArrayList<KB_Person>();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public KB_Person getChild() {
        return child;
    }

    public void setChild(KB_Person child) {
        this.child = child;
    }

    public List<KB_Person> getParents() {
        return parents;
    }

    public void setParents(List<KB_Person> parents) {
        this.parents = parents;
    }
}
