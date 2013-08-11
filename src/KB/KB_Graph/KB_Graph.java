package KB.KB_Graph;

import java.util.ArrayList;

import KB.KB_Node.*;
import KB.KB_Arc.*;

/**
 * Author: Mark Hinshaw
 * email: mahinshaw@gmail.com
 * git: https://github.com/mahinshaw/msproject
 */

public class KB_Graph {

    private ArrayList<KB_Node> nodelist;
    private ArrayList<KB_Person> personlist;

    public KB_Graph() {
        this.nodelist = new ArrayList<KB_Node>();
        this.personlist = new ArrayList<KB_Person>();

    }

    public ArrayList<KB_Node> getNodelist() {
        return nodelist;
    }

    public void addNode(KB_Node node) {
        if (!nodelist.add(node)) {
            throw new IllegalArgumentException("The node was not added to the list of nodes");
        }
    }

    public void addPerson(KB_Person person) {
        if (!personlist.add(person)) {
            throw new IllegalArgumentException("The node was not added to the list of persons");
        }
    }

    public void createPerson(int id, String name, char gender, int age) {
        this.addPerson(new KB_Person(id, name, gender, age));
    }

    public void createGenotype(int id, int person_id, char type, String gene_id, String gene_name, String mutated, String autosomal_type, String disease) {
        this.addNode(new Genotype(id, person_id, type, gene_id, gene_name, mutated, autosomal_type, disease));
    }

    public void createBiochemistry(int id, int person_id, char type, String protein_id, String protein_name, boolean normal, String quantity) {
        this.addNode(new Biochemistry(id, person_id, type, protein_id, protein_name, normal, quantity));
    }

    public void createSymptom(int id, int person_id, char type, String symptom_id, String symptom_name, String degree) {
        this.addNode(new Symptom(id, person_id, type, symptom_id, symptom_name, degree));
    }

    public void createTest(int id, int person_id, char type, String testType, boolean result) {
        this.addNode(new Test(id, person_id, type, testType, result));
    }

    public void createPhysiology(int id, int person_id, char type, String location, String description) {
        this.addNode(new Physiology(id, person_id, type, location, description));
    }

    public void createInfluenceArc(String arc_id, String type, int parentNodeID, int childNodeID) {
        // will throw outOfBoundsException of findKB_NodeIndex returns -1.
        KB_Node parent = nodelist.get(findKB_NodeIndex(parentNodeID));
        KB_Node child = nodelist.get(findKB_NodeIndex(childNodeID));

        parent.addArc(new Influence_Arc(arc_id, type, parent, child));
    }

    /**
     * @param type
     * @param childNodeID
     * @param parentNodeIDs
     */
    public void createSynergyArc(String syn_id, String type, int childNodeID, int[] parentNodeIDs) {
        // will throw outOfBoundsException of findKB_NodeIndex returns -1.
        KB_Node child = nodelist.get(findKB_NodeIndex(childNodeID));
        KB_Node parent;
        ArrayList<KB_Node> parents = new ArrayList<KB_Node>();

        // find all the parent nodes by nodeID
        int ids = 0;
        for (int i = 0; i < parentNodeIDs.length; i++) {
            parent = nodelist.get(findKB_NodeIndex(parentNodeIDs[i]));
            parents.add(parent);
        }

        //add the arc to all the parent nodes
        for (KB_Node node : parents) {
            node.addArc(new Synergy_Arc(syn_id, type, parents, child));
        }
    }

    public int findKB_NodeIndex(int node_id) {
        // returns -1 if node is not in the list
        int index = -1;
        for (KB_Node node : nodelist) {
            if (node.getId() == node_id) {
                index = nodelist.indexOf(node);
                break;
            }
        }

        if (index == -1) {
            throw new IndexOutOfBoundsException("No indexes found.");
        }

        return index;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();

        //add people in the graph
        str.append("People in the graph:\n");
        str.append("---------------------\n");
        for (KB_Person person : personlist) {
            str.append(person.toString() + "\n");
        }

        //add space between items
        str.append("\n=================================================\n\n\n");

        // add nodes in the graph with adj list
        str.append("Nodes in the graph with child node(s):\n");
        str.append("---------------------------------------\n");
        for (KB_Node node : nodelist) {
            str.append(node.toString());
            str.append(node.adjList() + "\n");
        }

        return str.toString();
    }

    // this method is intended to clear flags in nodelist - set them to false
    public void clearFlags() {
        for (KB_Node node : nodelist) {
            node.setFlag(false);
        }
    }
}

