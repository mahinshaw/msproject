package ArgumentComparator;

import ArgumentStructure.ArgumentObject;
import ArgumentStructure.ArgumentTree;
import ArgumentStructure.Hypothesis;
import GAIL.src.model.Argument;

import java.util.*;
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

    public ArgumentComparator(ArgumentTree user, ArgumentTree gen){
        userTree = user;
        generatorTree = gen;
        comparatorTree = null;
    }

    /**
     * This method compares two ArgumentTrees with one another.  This should be called from another method that will
     * handle the lists of ArgumentTrees that may be passed to the Comparator.
     * @return - ComparatorTree based on finished comparison.
     */
    private ComparatorTree CompareArgumentTrees(){
        ComparatorTree parentComparatorTree = null;
        ArgumentTree currentUserTree = userTree, currentGeneratedTree = generatorTree;
        ArrayDeque<ArgumentTree> userQueue = new ArrayDeque<ArgumentTree>();
        ArrayDeque<ArgumentTree> genQueue = new ArrayDeque<ArgumentTree>();
        HashMap<ArgumentTree, ArgumentTree> matchMap;

        // begin with the root, load them and then order and load the children.
        comparatorTree = ComparatorTree.buildTree(generatorTree, userTree);
        parentComparatorTree = comparatorTree;

        userQueue.addAll(userTree.getChildren());
        genQueue.addAll(generatorTree.getChildren());

        // TODO: This currently only handles the row beneath the parent.  Needs to be optimized.
        while (!userQueue.isEmpty() || !genQueue.isEmpty()){
            matchMap = mapLikeChildren(currentUserTree.getChildren(), currentGeneratedTree.getChildren());

            for (ArgumentTree user : matchMap.keySet()){
                comparatorTree.addSubTree(user, matchMap.get(user), parentComparatorTree);
                genQueue.removeFirstOccurrence(matchMap.get(user));
                userQueue.removeFirstOccurrence(user);
            }
            if (!genQueue.isEmpty()){
                for (ArgumentTree genArg : matchMap.values()) {
                    comparatorTree.addSubTree(new ComparatorObject(null, genArg.getRoot()), parentComparatorTree);
                    genQueue.removeFirstOccurrence(genArg);
                }
            }
        }

        return comparatorTree;
    }

    private static HashMap<ArgumentTree,ArgumentTree> mapLikeChildren(LinkedList<ArgumentTree> userArgs, LinkedList<ArgumentTree> genArgs){
        HashMap<ArgumentTree, ArgumentTree> matchMap = new HashMap<ArgumentTree, ArgumentTree>();

        for (ArgumentTree userArg : userArgs){
            matchMap.put(userArg, FindSimilarArgument(userArg, genArgs));
        }

        return matchMap;
    }

    private static ArgumentTree FindSimilarArgument(ArgumentTree userArg, LinkedList<ArgumentTree> genArgs){
        boolean hasChildren = userArg.hasChildren();

        ArrayDeque<ArgumentTree> validTrees = new ArrayDeque<ArgumentTree>();
        ArrayDeque<ArgumentTree> matches = new ArrayDeque<ArgumentTree>();
        // put everything in a queue. First see if the user has children, and add all the genArgs that have children
        // to the queue.  Otherwise, push on the childless ones.
        // first lets check against the hypotheses
        Hypothesis userHyp = userArg.getRoot().getHypothesis();
        for (ArgumentTree genArg : genArgs){
            if (genArg.getRoot().getHypothesis().getKBNODEID().equals(userHyp.getKBNODEID()))
                validTrees.push(genArg);
        }

        if (validTrees.size() == 1)
            return validTrees.pop();

        if (validTrees.size() > 1) {
            for (ArgumentTree genArg : validTrees){
                // pop off invalid ArgumentTrees.
                if (((genArg.hasChildren() && hasChildren) || !(genArg.hasChildren() || hasChildren)))
                    matches.push(genArg);
            }
            validTrees.removeAll(matches);
        }
        else { // there are no matching hypotheses
            for (ArgumentTree genArg : genArgs) {
                // see if there are children, if so, its possible.
                if (((genArg.hasChildren() && hasChildren) || !(genArg.hasChildren() || hasChildren)))
                    validTrees.push(genArg);
            }
        }

        // Finally, test what is in validTrees, and see if it can be widdled down with Generalizations.
        if (validTrees.size() == 1)
            return validTrees.pop();
        else{
            if (validTrees.size() > 1){
                for (ArgumentTree genArg : validTrees){
                    if (userArg.getRoot().generalizationsEqual(genArg.getRoot()))
                        matches.push(genArg);
                }
                if (matches.size() > 0)
                    validTrees = matches;
            }
            else{
                for (ArgumentTree genArg : genArgs){
                    if (userArg.getRoot().generalizationsEqual(genArg.getRoot()))
                        validTrees.push(genArg);
                }
            }
        }

        // get the best result out of the queue.
        return validTrees.poll();
    }

    @Override
    public ComparatorTree call() throws Exception {
        return CompareArgumentTrees();
    }
}
