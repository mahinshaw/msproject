package ArgumentStructure;

import java.util.LinkedList;
import java.util.Stack;

/**
 * This tree structure is unsorted.  There is no need to sort the tree, as it may intentionally be
 * unbalanced.  The tree is used to store arguments from both GAIL and the Argument Generator.
 */
public class ArgumentTree {
    private ArgumentObject root;
    private LinkedList<ArgumentTree> children;
    private int level;
    private ArgumentTree parent;

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
        this.level = 1;
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
        this.level = this.parent.getLevel() + 1;
    }

    /**
     * Get the ArgumentObject at the root of this tree.
     * @return
     */
    public ArgumentObject getRoot(){
        return this.root;
    }

    /**
     * Get the level of the tree.
     * @return - the level of the tree.
     */
    public int getLevel(){
        return this.level;
    }

    /**
     * Return the list of children.
     * @return - the list of children.
     */
    public LinkedList<ArgumentTree> getChildren() { return this.children; }

    /**
     * Returns true if this tree has children.
     * @return - true if this tree has children.
     */
    public boolean hasChildren(){
        return !children.isEmpty();
    }

    /**
     * The set methods are for internal use only.
     * @param child - the child tree to add to list of children.
     */
    private void addChild(ArgumentTree child) { this.children.add(child); }

    /**
     * This method checks to see if the child tree exists in the list of trees
     * @param child - child to check
     * @return - boolean: true if has, false if has not.
     */
    public boolean hasChild(ArgumentTree child){
        return children.contains(child);
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
     * @param argumentObject - root Argument for the tree.
     * @return - new ArgumentTree.
     */
    public static ArgumentTree createArgumentTree(ArgumentObject argumentObject){
        return new ArgumentTree(argumentObject);
    }

    /**
     * This method finds a specified ArgumentObject in the subtrees of this tree, and returns the tree it is head of.
     * @param argumentObject - ArgumentObject to search for.
     * @return - ArgumentTree that is rooted by the passed ArgumentObject.
     */
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
