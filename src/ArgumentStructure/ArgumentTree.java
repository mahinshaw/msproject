package ArgumentStructure;

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
    private ArgumentTree leftChild;
    private ArgumentTree rightChild;

    private ArgumentTree parent;

    /**
     * These fields will be used for navigating large trees; ie. search and insertion
     * @head - this is a reference to the uppermost root.
     */
    private ArgumentTree head;
    private ArgumentTree current;

    private boolean conjuction;

    private ArgumentTree(ArgumentObject argumentObject, ArgumentTree parent){
        this.root = argumentObject;
        this.leftChild = null;
        this.rightChild = null;
        this.conjuction = false;
        this.parent = parent;
        head = this;
    }

    public ArgumentObject getRoot(){
        return this.root;
    }

    public ArgumentTree getLeftChild(){
        return this.leftChild;
    }

    public ArgumentTree getRightChild(){
        return this.rightChild;
    }

    public void setConjuction(boolean c){
        this.conjuction = c;
    }

    /**
     * This method returns whether or not there is a tree in the left child.
     * @return - boolean: true if has, false if has not.
     */
    public boolean hasLeftChild(){
        if (this.leftChild == null){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean hasRightChild(){
        if (this.rightChild == null){
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * The set methods are for internal use only.
     * @param tree
     */
    private void setLeftChild(ArgumentTree tree){
        this.leftChild = tree;
    }

    private void setRightChild(ArgumentTree tree){
        this.rightChild = tree;
    }

    private void setParent(ArgumentTree tree){
        this.parent = tree;
    }

    /**
     *
     * @param tree - child to check
     * @return - boolean: true if has, false if has not.
     */
    public boolean isEmptyChild(ArgumentTree tree){
        if (tree == null){
            return true;
        } else {
            return false;
        }
    }

    /**
     * This mehtod is intended for adding sub-trees.  The user will pass an ArgumentObject which will in turn, create
     * a tree and then assign it to the proper child.  At the time of writitng, trees were only intended to have two
     * levels.  If there wer no children, add to the left, if there is a left, then it must be a conjuction, and add
     * to the right.
     *
     * @param insert - ArgumentObject that will be passed and used to create the tree.
     * @param parent - ArgumentObject that will be passed and used to find where to insert into the tree.
     */
    public void addSubArgument(ArgumentObject insert, ArgumentObject parent){
        current = findArgumentObject(parent);
        if (isEmptyChild(current.getLeftChild()) /*&& !insert.isHypothesis()*/){
            current.setLeftChild(new ArgumentTree(insert, current));
            current.getLeftChild().setParent(current);
        }
        else if (!insert.isHypothesis()){
            current.setRightChild(new ArgumentTree(insert, current));
        }
        else {
            System.out.println("The argument is a full argument");
        }
    }

    /**
     * This method is similar addSubArgument, except it was intended for hypotheses rather than full arguments.
     * However they have the same functionality.
     *
     * @param insert - ArgumentObject that will start the new tree.
     * @param parent - ArgumentObject that will find the parent of the new tree..
     */
    public void addSubHypothesis(ArgumentObject insert, ArgumentObject parent){
        current = findArgumentObject(parent);
        if (isEmptyChild(current.getLeftChild()) && insert.isHypothesis()){
            current.setLeftChild(new ArgumentTree(insert, current));
        }
        else if (insert.isHypothesis()){
            current.setRightChild(new ArgumentTree(insert, current));

        }
        else {
            System.out.println("The argument is only a hypothesis.");
        }
    }

    /**
     * This method is intended to create a new tree, which in turn will have subtrees.  If you wish to add subtrees to
     * an existing tree, then call a different method.  Else you will have a new tree with a null parent, creating a
     * tree root.
     *
     * @param argumentObject
     * @return
     */
    public static ArgumentTree createArgumentTree(ArgumentObject argumentObject){
        return new ArgumentTree(argumentObject, null);
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
            if (current.hasLeftChild()){
                hasLeft.push(current.getLeftChild());
            }
            if (current.hasRightChild()){
                hasLeft.push(current.getRightChild());
            }
            // while both stack is not empty, look at the children
            while (!hasLeft.isEmpty()){
                /**
                 * while the stack is not empty, pop off the top and check if its good.  If it is return it.
                 * else check for left and right and push on right then left.
                 */

                current = hasLeft.pop();
                if (argumentObject == current.getRoot()){
                    return current;
                }
                else if (current.hasRightChild()){
                    hasLeft.push(current.getRightChild());
                }
                else if (current.hasLeftChild()){
                    hasLeft.push(current.getLeftChild());
                }
            }
        }

        //else it does not exist and we should therefore return null
        return null;
    }
}
