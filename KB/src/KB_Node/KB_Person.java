package KB_Node;

import java.lang.String;
import java.util.ArrayList;

/**
 * Author:  Mark Hinshaw
 * email: mahinshaw@gmail.com
 * github: https://github.com/mahinshaw/msproject
 */

public class KB_Person {

    private static int id_generator = 0;

    private final int id;
    private String name;
    // 'm' for male; 'f' for female;
    private char gender;
    // list for children
    private ArrayList<KB_Person> children;

    public KB_Person(String name, char gender){
        this.id = id_generator;
        id_generator++;
        this.name = name;
        this.gender = gender;
        this.children = new ArrayList<KB_Person>();

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public char getGender() {
        return gender;
    }

    public ArrayList<KB_Person> getChildren() {
        return children;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void addChildren(ArrayList<KB_Person> children) {
        this.children.addAll(children);
    }

    public void addChild(KB_Person child){
        this.children.add(child);
    }
}