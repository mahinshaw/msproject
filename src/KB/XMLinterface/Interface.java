package KB.XMLinterface;

import KB.KB_Graph.KB_Graph;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;

import java.util.ArrayList;

/**
 * User: Tshering Tobgay
 * Date: 7/10/13
 */
public class Interface {
    private KB_Graph graph;

    protected Interface(String f){
       xmlInterface(f);
    }

    private void xmlInterface(String f){

        setGraph();

        try {
            SAXParserFactory parserFactory;
            SAXParser saxParser;
            parserFactory = SAXParserFactory.newInstance();
            saxParser = parserFactory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {
                ArrayList<String> holdValues;

                boolean person_name = false, gender = false, gene_id = false, gene_name = false, mutated = false, description = false;
                boolean autosomal_type = false, protein_id = false, protein_name = false, normal = false, quantity = false, location = false;
                boolean symptom_id = false, symptom_name = false, degree = false, influence_type = false, influence_parent = false, disease = false;
                boolean influence_child = false, type = false, child = false, parent = false, age = false, testName = false, testResult = false;
                int count = 0, size = 0;

                public void startElement(String str, String local, String name, Attributes attribute) {

                    if (name.equalsIgnoreCase("person")) {
                        setArray();
                        addVar(attribute.getValue("person_id"));
                    }
                    if (name.equalsIgnoreCase("person_name")) {
                        person_name = true;
                    }
                    if (name.equalsIgnoreCase("gender")) {
                        gender = true;
                    }
                    if (name.equalsIgnoreCase("age")){
                        age = true;
                    }

                    if (name.equalsIgnoreCase("genotype")) {
                        addVar(attribute.getValue("node_id"));
                        addVar(attribute.getValue("person_id"));
                    }
                    if (name.equalsIgnoreCase("gene_id")) {
                        gene_id = true;
                    }
                    if (name.equalsIgnoreCase("gene_name")) {
                        gene_name = true;
                    }
                    if (name.equalsIgnoreCase("mutated")) {
                        mutated = true;
                    }
                    if (name.equalsIgnoreCase("autosomal_type")) {
                        autosomal_type = true;
                    }

                    if (name.equalsIgnoreCase("disease")){
                        disease = true;
                    }

                    if (name.equalsIgnoreCase("biochemistry")) {
                        addVar(attribute.getValue("node_id"));
                        addVar(attribute.getValue("person_id"));
                    }
                    if (name.equalsIgnoreCase("protein_id")) {
                        protein_id = true;
                    }
                    if (name.equalsIgnoreCase("protein_name")) {
                        protein_name = true;
                    }
                    if (name.equalsIgnoreCase("normal")) {
                        normal = true;
                    }
                    if (name.equalsIgnoreCase("quantity")) {
                        quantity = true;
                    }
                    if (name.equalsIgnoreCase("symptom")) {
                        addVar(attribute.getValue("node_id"));
                        addVar(attribute.getValue("person_id"));
                    }
                    if (name.equalsIgnoreCase("symptom_id")) {
                        symptom_id = true;
                    }
                    if (name.equalsIgnoreCase("symptom_name")) {
                        symptom_name = true;
                    }
                    if (name.equalsIgnoreCase("degree")) {
                        degree = true;
                    }
                    if (name.equalsIgnoreCase("influence_type")) {
                        influence_type = true;
                    }
                    if (name.equalsIgnoreCase("influence_parent")) {
                        influence_parent = true;
                    }
                    if (name.equalsIgnoreCase("influence_child")) {
                        influence_child = true;
                    }

                    if (name.equalsIgnoreCase("type")) {
                        type = true;
                    }
                    if (name.equalsIgnoreCase("child")) {
                        child = true;
                    }
                    if (name.equalsIgnoreCase("parent")) {
                        parent = true;
                    }
                    if (name.equalsIgnoreCase("test")){
                        addVar(attribute.getValue("node_id"));
                        addVar(attribute.getValue("person_id"));
                    }

                    if (name.equalsIgnoreCase("testName")){
                        testName = true;
                    }

                    if (name.equalsIgnoreCase("testResult")){
                        testResult = true;
                    }

                    if (name.equalsIgnoreCase("physiology")){
                        addVar(attribute.getValue("node_id"));
                        addVar(attribute.getValue("person_id"));
                    }

                    if (name.equalsIgnoreCase("bodyLocation")){
                        location = true;
                    }

                    if (name.equalsIgnoreCase("description")){
                        description = true;
                    }

                }

                public void endElement(String str, String local, String name) {
                    // System.out.println("END tag: " + name);
                }

                public void characters(char c[], int start, int length) {


                    if (person_name) {
                        addVar(new String(c, start, length));
                        person_name = false;
                    }
                    if (gender) {
                        addVar(new String(c, start, length));
                        gender = false;
                    }
                    if (age){
                        addVar(new String(c, start, length));
                        age = false;
                        print(1);

                    }
                    if (gene_id) {
                        addVar(new String(c, start, length));
                        gene_id = false;
                    }
                    if (gene_name) {
                        addVar(new String(c, start, length));
                        gene_name = false;
                    }
                    if (mutated) {
                        addVar(new String(c, start, length));
                        mutated = false;
                    }
                    if (autosomal_type) {
                        addVar(new String(c, start, length));
                        autosomal_type = false;
                    }
                    if (disease){
                        addVar(new String(c, start, length));
                        disease = false;
                        print(2);
                    }
                    if (protein_id) {
                        addVar(new String(c, start, length));
                        protein_id = false;
                    }
                    if (protein_name) {
                        addVar(new String(c, start, length));
                        protein_name = false;
                    }
                    if (normal) {
                        addVar(new String(c, start, length));
                        normal = false;
                    }
                    if (quantity) {
                        addVar(new String(c, start, length));
                        quantity = false;
                        print(3);
                    }
                    if (symptom_id) {
                        addVar(new String(c, start, length));
                        symptom_id = false;
                    }
                    if (symptom_name) {
                        addVar(new String(c, start, length));
                        symptom_name = false;
                    }
                    if (degree) {
                        addVar(new String(c, start, length));
                        degree = false;
                        print(4);
                    }

                    if (influence_type) {
                        addVar(new String(c, start, length));
                        influence_type = false;
                    }
                    if (influence_parent) {
                        addVar(new String(c, start, length));
                        influence_parent = false;
                    }

                    if (influence_child) {
                        addVar(new String(c, start, length));
                        influence_child = false;
                        print(5);
                    }

                    if (type) {
                        addVar(new String(c, start, length));
                        type = false;
                    }

                    if (child) {
                        addVar(new String(c, start, length));
                        child = false;
                    }

                    if (parent) {
                        count++;
                        addVar(new String(c, start, length));
                        parent = false;
                        if (count == 2) {
                            count = 0;
                            print(6);
                        }

                    }
                    if (testName){
                        addVar(new String(c, start, length));
                        testName = false;
                    }

                    if (testResult){
                        addVar(new String(c, start, length));
                        testResult = false;
                        print(7);
                    }

                    if (location){
                        addVar(new String(c, start, length));
                        location = false;
                    }

                    if (description){
                        addVar(new String(c, start, length));
                        description = false;
                        print(8);
                    }

                }

                public void setArray() {
                    holdValues = new ArrayList<String>();
                }

                public void addVar(String var) {
                    holdValues.add(var);
                }

                public ArrayList<String> getArray() {
                    return holdValues;
                }

                public void print(int h) {
                        int size = 0;
                        switch (h){
                            case 1: graph.createPerson(Integer.parseInt(getArray().get(size)), getArray().get(++size), getArray().get(++size).charAt(0),Integer.parseInt(getArray().get(++size)));
                                size = 0;
                                break;
                            case 2: graph.createGenotype(Integer.parseInt(getArray().get(size)), Integer.parseInt(getArray().get(++size)),
                                    getArray().get(++size), getArray().get(++size), getArray().get(++size), getArray().get(++size), getArray().get(++size));
                                 size = 0;
                                break;
                            case 3: graph.createBiochemistry(Integer.parseInt(getArray().get(size)), Integer.parseInt(getArray().get(++size)),
                                    getArray().get(++size), getArray().get(++size), Boolean.parseBoolean(getArray().get(++size)), getArray().get(++size));
                                size =0;
                                break;
                            case 4: graph.createSymptom(Integer.parseInt(getArray().get(size)), Integer.parseInt(getArray().get(++size)),
                                    getArray().get(++size), getArray().get(++size), getArray().get(++size));
                                size = 0;
                                break;
                            case 5: graph.createInfluenceArc(getArray().get(size), Integer.parseInt(getArray().get(++size)), Integer.parseInt(getArray().get(++size)));
                                size = 0;
                                break;
                            case 6:
                                int[] parents = new int[getArray().size()-2];
                                for (int i =0; i< parents.length; i++){
                                    parents[i] = Integer.parseInt(getArray().get((getArray().size()-i)-1));
                                }
                                graph.createSynergyArc(getArray().get(size), Integer.parseInt(getArray().get(++size)), parents);
                                size = 0;
                                break;
                            case 7:
                                graph.createTest(Integer.parseInt(getArray().get(size)), Integer.parseInt(getArray().get(++size)), getArray().get(++size), Boolean.parseBoolean(getArray().get(++size)));
                                size = 0;
                                break;
                            case 8:
                                graph.createPhysiology(Integer.parseInt(getArray().get(size)), Integer.parseInt(getArray().get(++size)), getArray().get(++size), getArray().get(++size));
                                size = 0;
                                break;
                        }
                    setArray();
                }
            };

            saxParser.parse(f, handler);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setGraph() {
        graph = new KB_Graph();
    }

    protected KB_Graph getGraph(){
        return graph;
    }
}