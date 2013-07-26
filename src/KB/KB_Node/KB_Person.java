package KB.KB_Node;

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
    private final String name;
    // 'm' for male; 'f' for female;
    private final char gender;
    private final int age;
    // list for parents
    private ArrayList<KB_Person> parents;

    public KB_Person(int id, String name, char gender, int age){
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.parents = new ArrayList<KB_Person>();
        this.age = age;
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

    public int getAge(){
        return age;
    }

    public ArrayList<KB_Person> getParents() {
        return parents;
    }

    public void addParents(ArrayList<KB_Person> parents) {
        this.parents.addAll(parents);
    }

    public void addParent(KB_Person parent){
        this.parents.add(parent);
    }

    public String toString() {
        String parentString = "";
        for (KB_Person parent : parents){
            parentString += parent.getName() + ", ";
        }
        return "Person ID: " + this.id + "\n" +
                "Name: " + this.name + "\n" +
                "Gender: " + this.gender + "\n" +
                "Parents: " + parentString +
                "\nAge: "+this.age+"\n";
    }
}