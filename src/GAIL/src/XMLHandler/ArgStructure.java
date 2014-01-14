package GAIL.src.XMLHandler;

import KB.KB_Node.KB_Node;

import java.util.ArrayList;

/**
 * User: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * Date: 7/19/13
 * github: https://github.com/mahinshaw/msproject
 * <p/>
 * The intent of this class is to be a hub for data passed in and out of GAIL.  This structure will be used to load
 * questions into gail as well as node data specific to the questions and argument information.
 */
public class ArgStructure {

    /**
     * @index - this is a static integer used for indexing nodes within the ArgStructure
     * @questions - used to hold questions relative to active file.
     * @nodelist - complete collection of all nodes in data structure
     * @hypothesislist - list of all hypothesis nodes.
     * @datalist - list of all data nodes.
     * @generalizationlist - list of all generalization nodes.
     */
    private static int index;
    private ArrayList<String> questions;
    private ArrayList<Node> questionList;
    private ArrayList<Node> nodeList;
    private ArrayList<Node> hypothesisList;
    private ArrayList<Node> dataList;
    private ArrayList<Node> generalizationList;

    /**
     * initializes an empty ArgStructure
     *
     * @index - starts from 0.
     */
    private ArgStructure() {
        this.index = 0;
        //this.questions = new ArrayList<String>();
        questionList = new ArrayList<Node>();
        nodeList = new ArrayList<Node>();
        hypothesisList = new ArrayList<Node>();
        dataList = new ArrayList<Node>();
        generalizationList = new ArrayList<Node>();
    }

    /**
     * This class adds node to nodelist as well as the
     *
     * @param node
     */
    private void addNode(Node node) {
        nodeList.add(node);
        switch (node.argType) {
            case 'q':
                questionList.add(node);
                break;
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

    //create a new node
    private Node createNode(String node_id, String text, char argType) {
        return new Node(this.index++, node_id, text, argType);
    }

    //create and insert a new node
    public void insertNewNode(String node_id, String text, char argType) {
        this.addNode(createNode(node_id, text, argType));
    }

    //insert a created node.  Must be passed from another structure.
    public void insertNewNode(Node n) {
        this.addNode(n);
    }

    // load the questions array with an arraylist.
    public void insertQuestions(Node n) {
    //public void insertQuestions(ArrayList<String> questions) {
        //this.questions.addAll(questions);
        this.addNode(n);
    }

    // add an individual question.
    public void addQuestion(String q) {
        this.questions.add(q);
    }

    public ArrayList<String> getQuestions() {
        return questions;}

    public ArrayList<Node> getQuestionList(){
        return questionList;
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

    //create a static data structure.
    public static ArgStructure create() {
        return new ArgStructure();
    }

    public String getText(String s) {
        String str = " ";
        for (Node n : nodeList) {
            if (n.getNode_id().equalsIgnoreCase(s)) {
                str = n.getText();
            }
        }
        return str;
    }

    public void clear() {
        this.index = 0;
        this.questionList.clear();
        this.nodeList.clear();
        //this.questions.clear();
        this.hypothesisList.clear();
        this.dataList.clear();
        this.generalizationList.clear();
    }


    /**
     * This class is only intended for use by ArgStructure class.
     */
    public class Node {

        private final int id;
        private final String node_id;
        private final String text;
        private final char argType;

        private Node(int id, String node_id, String text, char argType) {
            this.id = id;
            this.node_id = node_id;
            this.text = text;
            this.argType = argType;
        }

        public int getId() {
            return this.id;
        }

        public String getNode_id() {
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
