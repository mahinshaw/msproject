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
    private ComparatorTree comparatorTree;

    public ArgumentComparator(ArgumentTree gen, ArgumentTree user){
        userTree = user;
        generatorTree = gen;
        // we know that the roots need comparing, so start the tree here, then we can advance.
        comparatorTree = ComparatorTree.buildTree(generatorTree, userTree);
    }

    /**
     * This method compares two ArgumentTrees with one another.  This should be called from another method that will
     * handle the lists of ArgumentTrees that may be passed to the Comparator.
     * @return - ComparatorTree based on finished comparison.
     */
    private ComparatorTree CompareArgumentTrees(){
        ArgumentTree currentUserTree = userTree, currentGeneratedTree = generatorTree;
        Stack<ArgumentTree> userStack = new Stack<ArgumentTree>();
        Stack<ArgumentTree> generatedStack = new Stack<ArgumentTree>();

        userStack.push(currentUserTree);
        generatedStack.push(currentGeneratedTree);


        return comparatorTree;
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
        return CompareArgumentTrees();
    }
}
