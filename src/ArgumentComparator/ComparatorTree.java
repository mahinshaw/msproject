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
    }

    private ComparatorTree(ComparatorObject child, ComparatorTree parent, int index){
        this.root = child;
        this.parent = parent;
    }

    public static ComparatorTree createComparatorTree(ComparatorObject root, int index){
        return new ComparatorTree(root, index);
    }

    public void addSubTree(ComparatorObject child, ComparatorTree parent){
        addChild(new ComparatorTree(child, parent, parent.getArgIndex()));
    }

    public void addSubTree(ArgumentTree user, ArgumentTree gen, ComparatorTree parent){
        addChild(new ComparatorTree(new ComparatorObject(user.getRoot(), gen.getRoot()), parent, parent.getArgIndex()));
    }

    public boolean hasChildren(){
        if (this.children == null)
            return false;
        return !this.children.isEmpty();
    }

    public static ComparatorTree buildTree(ArgumentTree gen, ArgumentTree user, int index){
        return  new ComparatorTree(new ComparatorObject(gen.getRoot(), user.getRoot()), index);
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
}
