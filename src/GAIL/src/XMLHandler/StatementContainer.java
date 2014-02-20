package GAIL.src.XMLHandler;

import java.util.ArrayList;

/**
 * User: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * Date: 7/19/13
 * github: https://github.com/mahinshaw/msproject
 *
 * The intent of this class is to be a hub for data passed into GAIL from the schema.  This structure will be used to load
 * questions into gail as well as node data specific to the questions and argument information.
 *
 */
public class StatementContainer {

    /**
     * @questions - used to hold questions relative to active file.
     * @hypothesislist - list of all hypothesis nodes.
     * @datalist - list of all data nodes.
     * @generalizationlist - list of all generalization nodes.
     */
    private ArrayList<Node> questionList;
    private ArrayList<Node> hypothesisList;
    private ArrayList<Node> dataList;
    private ArrayList<Node> generalizationList;

    /**
     * initializes an empty ArgStructure
     *
     * @index - starts from 0.
     */
    private StatementContainer() {
        questionList = new ArrayList<Node>();
        hypothesisList = new ArrayList<Node>();
        dataList = new ArrayList<Node>();
        generalizationList = new ArrayList<Node>();
    }

    /**
     * This class adds nodes to proper list.
     *
     * @param node - the node to be added.
     */
    private void addNode(Node node) {
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
        return new Node(node_id, text, argType);
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
        this.addNode(n);
    }

    public ArrayList<Node> getQuestionList() {
        return questionList;
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
    public static StatementContainer create() {
        return new StatementContainer();
    }

    public void clear() {
        this.questionList.clear();
        this.hypothesisList.clear();
        this.dataList.clear();
        this.generalizationList.clear();
    }


    /**
     * This class is only intended for use by ArgStructure class.
     */
    public class Node {

        private final String node_id;
        private final String text;
        private final char argType;

        private Node(String node_id, String text, char argType) {
            this.node_id = node_id;
            this.text = text;
            this.argType = argType;
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
