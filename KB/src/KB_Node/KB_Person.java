package KB_Node;

import java.lang.String;
import java.util.ArrayList;

/**
 * Author:  Mark Hinshaw
 * email: mahinshaw@gmail.com
 * github: https://github.com/mahinshaw/msproject
 */

private class KB_Person {

    private int id;
    private String name;
    // 'm' for male; 'f' for female;
    private char gender;
    // list for children
    private ArrayList<KB_Person> children;

    private KB_Person{
        this.id = null;
        this.name = null;
        this.gender = null;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void addChildren(ArrayList<KB_Person> children) {
        children.addAll(children);
    }

    public void addChild(KB_Person child){
        children.add(child);
    }

    public KB_Person create(int id, String name, char gender) {
        KB_Person person = new KB_Person();
        person.id = id;
        person.name = name;
        person.gender = gender;

        return person;
    }
}