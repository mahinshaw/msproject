package ArgumentComparator;

import ArgumentStructure.ArgumentTree;
import GAIL.src.model.Argument;

import java.util.LinkedList;

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
    private ComparatorObject parent;

    private ComparatorTree(ComparatorObject root){
        this.root = root;
        this.parent = null;
    }

    private ComparatorTree(ComparatorObject child, ComparatorObject parent){
        this.root = child;
        this.parent = parent;
    }

    public static ComparatorTree createComparatorTree(ComparatorObject root){
        return new ComparatorTree(root);
    }

    public void addSubTree(ComparatorObject child){
        addChild(new ComparatorTree(child, root));
    }

    public boolean hasChildren(){
        return !this.children.isEmpty();
    }

    public static ComparatorTree buildTree(ArgumentTree gen, ArgumentTree user){
        return  new ComparatorTree(new ComparatorObject(gen.getRoot(), user.getRoot()));
    }

    private void addChild(ComparatorTree child){
        this.children.add(child);
    }
}
