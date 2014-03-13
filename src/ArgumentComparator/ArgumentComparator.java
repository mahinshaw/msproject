package ArgumentComparator;

import ArgumentStructure.ArgumentObject;
import ArgumentStructure.ArgumentTree;
import GAIL.src.model.Argument;

import java.util.LinkedList;
import java.util.Stack;
import java.util.concurrent.Callable;

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

public class ArgumentComparator implements Callable {
    private ArgumentTree userTree, generatorTree;
    private ArgumentObject currentUserObject;

    public ArgumentComparator(ArgumentTree user, ArgumentTree gen){
        userTree = user;
        generatorTree = gen;
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

    @Override
    public ComparatorTree call() throws Exception {
        return null;
    }
}
