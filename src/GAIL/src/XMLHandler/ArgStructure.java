package GAIL.src.XMLHandler;

import java.util.ArrayList;

/**
 *
 * User: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * Date: 7/19/13
 * github: https://github.com/mahinshaw/${PROJECT_NAME}
 *
 */
public class ArgStructure {

    //question
    private ArrayList<String> questions;
    private ArrayList<Node> nodeList;

    private ArgStructure(String question){
        this.questions = new ArrayList<String>();
        this.questions.add(question);
        nodeList = new ArrayList<Node>();
    }

    private void addNode(Node node){
        nodeList.add(node);
    }

    private Node createNode(int id, int node_id, String text, String argType){
        return new Node(id, node_id, text, argType);
    }

    public void insertNewNode(int id, int node_id, String text, String argType){
        this.addNode(createNode(id, node_id, text, argType));
    }

    public static ArgStructure create(String question){
        return new ArgStructure(question);
    }


    /*
    * This class is only intended for use by ArgStructure class, and hence is private
    */
    private class Node {

        private final int id;
        private final int node_id;
        private final String text;
        private final String argType;

        private Node(int id, int node_id, String text, String argType){
            this.id = id;
            this.node_id = node_id;
            this.text = text;
            this.argType = argType;
        }

        public int getId(){
            return this.id;
        }

        private int getNode_id() {
            return node_id;
        }

        private String getText() {
            return text;
        }

        private String getArgType() {
            return argType;
        }
    }
}
