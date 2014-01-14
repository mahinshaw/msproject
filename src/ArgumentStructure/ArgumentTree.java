package ArgumentStructure;

import GAIL.src.model.Argument;

import java.util.LinkedList;
import java.util.Stack;

/**
 * User: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * Date: 8/2/13
 * github: https://github.com/mahinshaw/msproject
 *
 * This tree structure is binary and unsorted.  There is no need to sort the tree, as it may intentionally be
 * unbalanced.  The tree is used to store arguments from both GAIL and the Argument Generator.
 *
 */
public class ArgumentTree {
    private ArgumentObject root;
    private LinkedList<ArgumentTree> children;

    private ArgumentTree parent;

    /**
     * These fields will be used for navigating large trees; ie. search and insertion
     * @param head - this is a reference to the uppermost root.
     */
    private ArgumentTree head;
    private ArgumentTree current;

    /**
     * This first constructor is used for creating a tree.  Here the head is set, and the parent is null.
     *
     * @param argumentObject - this is the object that will become the root of the head.
     */
    private ArgumentTree(ArgumentObject argumentObject){
        this.root = argumentObject;
        this.children = new LinkedList<ArgumentTree>();
        this.parent = null;
        this.head = this;
    }

    /**
     * This constructor is used for adding subtrees.  The head cannot be set here.
     * @param argumentObject - the object to be the root of this tree.
     * @param parent - the parent of this tree.
     */
    private ArgumentTree(ArgumentObject argumentObject, ArgumentTree parent){
        this.root = argumentObject;
        this.children = new LinkedList<ArgumentTree>();
        this.parent = parent;
    }

    public ArgumentObject getRoot(){
        return this.root;
    }

    public LinkedList<ArgumentTree> getChildren() { return this.children; }

    /**
     * This method returns whether or not there is a tree in the left child.
     * @return - boolean: true if has, false if has not.
     */
    public boolean hasLeftmostChild(){
        if (this.children.peek() == null){
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Are there any children for this tree.
     */
    public boolean hasChildren(){
        return !children.isEmpty();
    }

    /**
     * The set methods are for internal use only.
     * @param child
     */
    private void addChild(ArgumentTree child) { this.children.add(child); }

    private void setParent(ArgumentTree tree){
        this.parent = tree;
    }

    /**
     * This method checks to see if the child tree exists in the list of trees
     * @param child - child to check
     * @return - boolean: true if has, false if has not.
     */
    public boolean hasChild(ArgumentTree child){
        return children.contains(child);
    }

    public ArgumentTree getChild(ArgumentTree child){
        if(hasChild(child)){
            int index = this.children.indexOf(child);
            return this.children.get(index);
        }
        return null;
    }

    /**
     * This method is intended for adding sub-trees.  The user will pass an ArgumentObject which will in turn, create
     * a tree and then assign it to the proper child.  The trees can have multiple children, and since the parent is found,
     * just do the insertion.
     *
     * @param insert - ArgumentObject that will be passed and used to create the tree.
     * @param parent - ArgumentObject that will be passed and used to find where to insert into the tree.
     */
    public void addSubArgument(ArgumentObject insert, ArgumentObject parent){
        current = findArgumentObject(parent);
        current.addChild(new ArgumentTree(insert, current));
    }

    /**
     * This method is intended to create a new tree, which in turn will have subtrees.  If you wish to add subtrees to
     * an existing tree, then call a different method.  Else you will have a new tree with a null parent, creating a
     * tree root.  This method is also the only way to set the head of the tree.
     *
     * @param argumentObject
     * @return
     */
    public static ArgumentTree createArgumentTree(ArgumentObject argumentObject){
        return new ArgumentTree(argumentObject);
    }

    public ArgumentTree findArgumentObject(ArgumentObject argumentObject){
        // create a stack that tells us if we have trees left to check.
        Stack<ArgumentTree> hasLeft = new Stack<ArgumentTree>();
        current = head;

        //check to see if its the head
        if (current.getRoot() == argumentObject){
            return current;
        }
        // else start the search
        else {
            if (current.hasChildren()){
                // push on the children of current
                for(ArgumentTree child : current.children)
                   hasLeft.push(child);
            }
            // while the stack is not empty, look at the children
            while (!hasLeft.isEmpty()){
                // get what is on top.
                current = hasLeft.pop();
                // if we found it, return.
                if (argumentObject == current.getRoot()){
                    return current;
                }
                // add all of currents children.
                else if (current.hasChildren()){
                    for(ArgumentTree child : current.children)
                        hasLeft.push(child);
                }
            }
        }

        //else it does not exist and we should therefore return null
        return null;
    }
}
