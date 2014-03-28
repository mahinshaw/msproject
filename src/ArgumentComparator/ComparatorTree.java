package ArgumentComparator;

import ArgumentStructure.ArgumentObject;
import ArgumentStructure.ArgumentTree;
import GAIL.src.model.Argument;

import java.util.LinkedList;
import java.util.List;

/**
 * User: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * Date: 3/12/14
 * github: https://github.com/mahinshaw/msproject
 *
 * This method creates a tree that should be used for ComparatorObject structure, and to simplify xml output.
 * This tree will be rooted with a ComparatorObject which in turn contains two ArgumentObjects.  This tree will
 * follow the structure of the ArgumentObject from the ArgumentGenerator.
 */
public class ComparatorTree {
    private final ComparatorObject root;
    private LinkedList<ComparatorTree> children;
    private ComparatorTree parent;
    private int argIndex;

    private ComparatorTree(ComparatorObject root, int index){
        this.root = root;
        this.parent = null;
        this.argIndex = index;
        this.children = new LinkedList<ComparatorTree>();
    }

    private ComparatorTree(ComparatorObject child, ComparatorTree parent, int index){
        this.root = child;
        this.parent = parent;
        this.argIndex = index;
        this.children = new LinkedList<ComparatorTree>();
    }

    public ComparatorTree createComparatorTree(ComparatorObject root, int index){
        return new ComparatorTree(root, index);
    }

    public ComparatorTree addSubTree(ComparatorObject child, ComparatorTree parent){
        ComparatorTree comparatorTree = new ComparatorTree(child, parent, parent.getArgIndex());
        addChild(comparatorTree);
        return comparatorTree;
    }

    public ComparatorTree addSubTree(ArgumentTree user, ArgumentTree gen, ComparatorTree parent){
        ComparatorTree comparatorTree = new ComparatorTree(new ComparatorObject(user.getRoot(), gen.getRoot()), parent, parent.getArgIndex());
        addChild(comparatorTree);
        return comparatorTree;
    }

    public boolean hasChildren(){
        if (this.children == null)
            return false;
        return !this.children.isEmpty();
    }

    public static ComparatorTree buildTree(ArgumentTree user, ArgumentTree gen, int index){
        return  new ComparatorTree(new ComparatorObject(user.getRoot(), gen.getRoot()), index);
    }

    private void addChild(ComparatorTree child){
        if (this.children == null)
            this.children = new LinkedList<ComparatorTree>();
        this.children.add(child);
    }

    public void setArgIndex(int i){
        this.argIndex = i;
    }

    public int getArgIndex(){
        return this.argIndex;
    }

    public LinkedList<ComparatorTree> getChildren(){
        return this.children;
    }

    public ComparatorObject getRoot(){
        return this.root;
    }

    public ArgumentObject getUser(){
        return this.root.getUserObject();
    }

    public ArgumentObject getGen(){
        return this.getRoot().getGeneratorObject();
    }

    public boolean hasConjunction(){
        return (this.getUser() != null) ? this.getUser().HasConjunction() : this.getGen().HasConjunction();
    }

    public boolean isNodeCorrect(){
        return this.root.isEquivalent();
    }

    public boolean isTreeCorrect(){
        boolean correct = this.root.isEquivalent();

        //recursively call children, if one is false, then return false, else true
        for (ComparatorTree child : this.children){
            if (child.hasChildren())
                correct = child.isTreeCorrect();
            else
                correct = child.isNodeCorrect();
            if (!correct)
                break;
        }
        return correct;
    }

    public double getRootAccuracy(){
        return this.root.getAccuracy();
    }

    public double getTreeAccuracy(){
        double totalAccuracy = this.getRootAccuracy();

        for (ComparatorTree child : this.children){
            // sum this and the child.
            totalAccuracy += child.getTreeAccuracy();
            // then divide by 2.
            totalAccuracy /= 2.0;
        }
        // return the calculated accuracy.
        return totalAccuracy;
    }
}
