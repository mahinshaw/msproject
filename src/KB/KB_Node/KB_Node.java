package KB.KB_Node;

import java.util.ArrayList;
import KB.KB_Arc.*;

/**
 * Author: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * gitHub: https://github.com/mahinshaw/msproject.git
 */
public class KB_Node {

    // values must be implemented in constructor.
    private final int id;
    private int person_id;
    private String certainty;
    private String abnormal;
    // list of Arcs(Edges) - only initialized in constructor.
    private ArrayList<KB_Arc> arcs;

    public KB_Node(int id, int person_id, String abnormal) {
        this.id = id;
        this.person_id = person_id;
        this.certainty = null;
        this.arcs = new ArrayList<KB_Arc>();
        this.abnormal = abnormal;
    }

    public int getId() {
        return id;
    }

    public int getPerson_id() {
        return person_id;
    }

    public String getCertainty() {
        return certainty;
    }

    public ArrayList<KB_Arc> getArcs() {
        return arcs;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public void setCertainty(String c) {
        this.certainty = c;
    }

    public void addArc(KB_Arc arc) {
        arcs.add(arc);
    }

    public void setAbnormal(String abnormal) {
        this.abnormal = abnormal;
    }

    public String getAbnormal() {
        return abnormal;
    }

    public ArrayList<KB_Node> getChildren() {
        ArrayList<KB_Node> children = new ArrayList<KB_Node>();
        for (KB_Arc arc : arcs) {
            children.add(arc.getChild());
        }
        return children;
    }

    public String toString() {
        return "Node ID: " + this.id + "\n" +
                "Person ID: " + this.person_id + "\n" +
                "Certainty: " + this.certainty + "\n" +
                "Abnormal: "+this.abnormal+"\n";
    }

    public String adjList() {
        String aList = "";
        for (KB_Arc arc : arcs) {
            aList += arc.getChild().getId() + " ";
        }
        return "List of child(ren): " + aList + "\n";
    }
}
