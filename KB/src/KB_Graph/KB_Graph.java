package KB_Graph;

import java.util.ArrayList;

import KB_Node.*;
import KB_Arc.*;

/**
 * Author: Mark Hinshaw
 * email: mahinshaw@gmail.com
 * git: https://github.com/mahinshaw/msproject
 */

public class KB_Graph {

    private ArrayList<KB_Node> nodelist;
    private ArrayList<KB_Arc> arclist;
    private ArrayList<KB_Person> personlist;

    private KB_Graph() {
        this.nodelist = new ArrayList<KB_Node>();
        this.arclist = new ArrayList<KB_Arc>();
        this.personlist = new ArrayList<KB_Person>();

    }

    public ArrayList<KB_Node> getNodelist() {
        return nodelist;
    }

    public ArrayList<KB_Arc> getArclist() {
        return arclist;
    }

    public void addNode(KB_Node node) {
        if (!nodelist.add(node)){
            throw new IllegalArgumentException("The node was not added to the list of nodes");
        }
    }

    public void addArc(KB_Arc arc) {
        if (!arclist.add(arc)){
            throw new IllegalArgumentException("The node was not added to the list of arcs");
        }
    }

    public void addPerson(KB_Person person) {
        if (!personlist.add(person)){
            throw new IllegalArgumentException("The node was not added to the list of persons");
        }
    }

    public void createPerson(int id, String name, char gender) {
        this.addPerson(new KB_Person(id, name, gender));
    }

    public void createGenotype(int id, int person_id, String gene_id, String gene_name, String mutated, String autosomal_type) {
        this.addNode(new Genotype(id, person_id, gene_id, gene_name, mutated, autosomal_type));
    }

    public void createBiochemistry(int id, int person_id, String protein_id, String protein_name, boolean normal, String quantity) {
        this.addNode(new Biochemistry(id, person_id, protein_id, protein_name, normal, quantity));
    }

    public void createSymptom(int id, int person_id, String symptom_id, String symptom_name, String degree) {
        this.addNode(new Symptom(id, person_id, symptom_id, symptom_name, degree));
    }

    public void createInfluenceArc(String type, int parentNodeID, int childNodeID) {
        // will throw outOfBoundsException of findKB_NodeIndex returns -1.
        KB_Node parent = nodelist.get(findKB_NodeIndex(parentNodeID));
        KB_Node child = nodelist.get(findKB_NodeIndex(childNodeID));

        this.addArc(new Influence_Arc(type, parent, child));
    }

    public void createSynergyArc(String type, int childNodeID, int[] parentNodeIDs) {
        // will throw outOfBoundsException of findKB_NodeIndex returns -1.
        KB_Node child = nodelist.get(findKB_NodeIndex(childNodeID));
        KB_Node parent;
        ArrayList<KB_Node> parents = new ArrayList<KB_Node>();
        for(int i = 0; i < parentNodeIDs.length; i++){
            parent = nodelist.get(findKB_NodeIndex(parentNodeIDs[i]));
            parents.add(parent);
        }

        this.addArc(new Synergy_Arc(type, parents, child));
    }

    public int findKB_NodeIndex(int node_id){
        // returns -1 if fail
        int index = -1;
        for (int i = 0; i < nodelist.size(); i++) {
            if (nodelist.get(i).getId() == node_id){
                index = i;
                break;
            }
        }

        return index;
    }
}

