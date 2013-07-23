package GAIL.src.XMLHandler;

import java.util.ArrayList;

/**
 *
 * User: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * Date: 7/19/13
 * github: https://github.com/mahinshaw/msproject
 *
 */
public class ArgStructure {

    private static int index;
    private ArrayList<String> questions;
    private ArrayList<Node> nodeList;
    private ArrayList<Node> hypothesisList;
    private ArrayList<Node> dataList;
    private ArrayList<Node> generalizationList;

    private ArgStructure(){
        this.index = 0;
        this.questions = new ArrayList<String>();
        nodeList = new ArrayList<Node>();
        hypothesisList = new ArrayList<Node>();
        dataList = new ArrayList<Node>();
        generalizationList = new ArrayList<Node>();
    }

    private void addNode(Node node){
        nodeList.add(node);
        switch (node.argType){
            case 'h':
                hypothesisList.add(node);
                break;
            case 'd':
                dataList.add(node);
                break;
            case 'g':
                generalizationList.add(node);
                break;
            default:
                System.out.println("The Node argType was incorrectly entered.");
        }
    }

    private Node createNode(int node_id, String text, char argType){
        return new Node(this.index++, node_id, text, argType);
    }

    public void insertNewNode(int node_id, String text, char argType){
        this.addNode(createNode(node_id, text, argType));
    }

    public void insertNewNode(Node n){
        this.addNode(n);
    }

    public void insertQuestions(ArrayList<String> questions){
        for (String s : questions){
            addQuestion(s);
        }
    }

    public void addQuestion(String q){
        this.questions.add(q);
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }

    public ArrayList<Node> getNodeList() {
        return nodeList;
    }

    public ArrayList<Node> getHypothesisList() {
        return hypothesisList;
    }

    public ArrayList<Node> getDataList() {
        return dataList;
    }

    public ArrayList<Node> getGeneralizationList() {
        return generalizationList;
    }

    public static ArgStructure create(){
        return new ArgStructure();
    }

    public void clear(){
        this.index = 0;
        this.questions.clear();
        this.nodeList.clear();
        this.questions.clear();
        this.hypothesisList.clear();
        this.dataList.clear();
        this.generalizationList.clear();
    }


    /*                                                                                       q
    * This class is only intended for use by ArgStructure class, and hence is private
    */
    public class Node {

        private final int id;
        private final int node_id;
        private final String text;
        private final char argType;

        private Node(int id, int node_id, String text, char argType){
            this.id = id;
            this.node_id = node_id;
            this.text = text;
            this.argType = argType;
        }

        public int getId(){
            return this.id;
        }

        public int getNode_id() {
            return node_id;
        }

        public String getText() {
            return text;
        }

        public char getArgType() {
            return argType;
        }
    }
}
