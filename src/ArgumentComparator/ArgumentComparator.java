package ArgumentComparator;

import ArgumentStructure.ArgumentObject;
import ArgumentStructure.ArgumentTree;

import java.util.LinkedList;
import java.util.Stack;

/**
 * User: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * Date: 1/1/14
 * github: https://github.com/mahinshaw/msproject
 *
 * The Argument Comparator is used to compare ArgumentTree(s) data structures.
 * The comparator will look at nodes in the trees which are passed recursively.
 * The feedback will be based on the Argument objects at the tree roots.
 */

public class ArgumentComparator {
    private LinkedList<ArgumentTree> userTrees, generatorTrees;
    private ArgumentObject currentUserObject;

    public ArgumentComparator(){
        userTrees = new LinkedList<ArgumentTree>();
        generatorTrees = new LinkedList<ArgumentTree>();
    }

    private void loadTrees(LinkedList<ArgumentTree> userTrees, LinkedList<ArgumentTree> generatorTrees){
        userTrees.addAll(userTrees);
        generatorTrees.addAll(generatorTrees);
    }

    /**
     * This is the main method for which the user will call to execute the comparison.
     * @param userTrees - List of ArgumentTrees from the user.
     * @param generatorTrees - List of ArgumentTrees from the generator.
     */
    public void CompareTreeLists(LinkedList<ArgumentTree> userTrees, LinkedList<ArgumentTree> generatorTrees){
        loadTrees(userTrees, generatorTrees);

        // TODO: Compare the two lists one by one.  First find out if there is one that matches the user attempt.
    }
    /**
     * This method compares two ArgumentTrees with one another.  This should be called from another method that will
     * handle the lists of ArgumentTrees that may be passed to the Comparator.
     * @param user - ArgumentTree from the user.
     * @param generated - ArgumentTree from the generator.
     */
    private ArgumentObject CompareArgumentTrees(ArgumentTree user, ArgumentTree generated){
        ArgumentTree currentUserTree = user, currentGeneratedTree = generated;
        Stack<ArgumentTree> userStack = new Stack<ArgumentTree>();
        Stack<ArgumentTree> generatedStack = new Stack<ArgumentTree>();

        userStack.push(currentUserTree);
        generatedStack.push(currentGeneratedTree);

        while(!userStack.isEmpty() || !generatedStack.isEmpty()){
            if (currentUserTree.hasChildren())
                for (ArgumentTree child : currentUserTree.getChildren())
                    userStack.push(child);
            if (currentGeneratedTree.hasChildren())
                for (ArgumentTree child : currentGeneratedTree.getChildren())
                    generatedStack.push(child);

            // compare the root nodes of the two trees.
            currentUserObject = CompareTreeRoots(currentUserTree.getRoot(), currentGeneratedTree.getRoot());

            // break if we find a discrepancy.  This will be used for feedback.
            if (currentUserObject != null)
                break;

            currentUserTree = userStack.pop();
            currentGeneratedTree = generatedStack.pop();

        }

        return currentUserObject;
    }

    /**
     * Return the ArgumentObject of the User that is not correct.
     * @param user - ArgumentObject of the user tree.
     * @param generated - ArgumentObject of the generated tree.
     * @return - return the Users ArgumentObject that does not match the generated, else return null.
     */
    private ArgumentObject CompareTreeRoots(ArgumentObject user, ArgumentObject generated){
        return (user.getARGID() != generated.getARGID()) ? user : null;
    }

    public void clearTrees(){
        userTrees.clear();
        generatorTrees.clear();
    }

}
