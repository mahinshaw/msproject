package ArgumentComparator;

import ArgumentStructure.ArgumentTree;
import ArgumentStructure.Hypothesis;
import sun.awt.image.ImageWatched;

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
    private ComparatorTree comparatorTree;
    private final int argIndex;

    public ArgumentComparator(ArgumentTree user, ArgumentTree gen, int index){
        userTree = user;
        generatorTree = gen;
        comparatorTree = null;
        argIndex = index;
    }

    /**
     * This method compares two ArgumentTrees with one another.  This should be called from another method that will
     * handle the lists of ArgumentTrees that may be passed to the Comparator.
     * @return - ComparatorTree based on finished comparison.
     */
    private ComparatorTree CompareArgumentTrees(){
        ComparatorTree parentComparatorTree;
        ArgumentTree currentUserTree, currentGeneratedTree;
        ArrayDeque<ArgumentTree> userQueue = new ArrayDeque<ArgumentTree>();
        ArrayDeque<ArgumentTree> genQueue = new ArrayDeque<ArgumentTree>();
        ArrayDeque<ComparatorTree> parentQueue = new ArrayDeque<ComparatorTree>();
        HashMap<ArgumentTree, ArgumentTree> matchMap;

        // begin with the root, load them and then order and load the children.
        do {
            if (comparatorTree == null) {
                comparatorTree = ComparatorTree.buildTree(userTree, generatorTree, argIndex);
                parentQueue.push(comparatorTree);
            }
            parentComparatorTree = parentQueue.poll();
            // TODO: check into possible null value states.
            if (parentComparatorTree.getUser() != null)
                currentUserTree = userTree.findArgumentObject(parentComparatorTree.getUser());
            else
                currentUserTree = null;
            if (parentComparatorTree.getGen() != null)
                currentGeneratedTree = generatorTree.findArgumentObject(parentComparatorTree.getGen());
            else
                currentGeneratedTree = null;

            if(currentUserTree != null && currentUserTree.hasChildren())
                userQueue.addAll(currentUserTree.getChildren());
            if(currentGeneratedTree != null && currentGeneratedTree.hasChildren())
                genQueue.addAll(currentGeneratedTree.getChildren());

            while (!userQueue.isEmpty() || !genQueue.isEmpty()) {
                matchMap = mapLikeChildren(currentUserTree, currentGeneratedTree);

                for (ArgumentTree user : matchMap.keySet()) {
                    parentQueue.push(comparatorTree.addSubTree(user, matchMap.get(user), parentComparatorTree));
                    genQueue.removeFirstOccurrence(matchMap.get(user));
                    userQueue.removeFirstOccurrence(user);
                }
                if (!genQueue.isEmpty()) {
                    for (ArgumentTree genArg : genQueue) {
                        parentQueue.push(comparatorTree.addSubTree(new ComparatorObject(null, genArg.getRoot()), parentComparatorTree));
                        genQueue.removeFirstOccurrence(genArg);
                    }
                    genQueue.clear();
                }
            }
        } while(!parentQueue.isEmpty());

        return comparatorTree;
    }

    private static HashMap<ArgumentTree,ArgumentTree> mapLikeChildren(ArgumentTree currentUserTree, ArgumentTree currentGenTree){
        HashMap<ArgumentTree, ArgumentTree> matchMap = new HashMap<ArgumentTree, ArgumentTree>();
        if(currentUserTree == null)
            return matchMap;
        LinkedList<ArgumentTree> userArgs = currentUserTree.getChildren();
        LinkedList<ArgumentTree> genArgs = new LinkedList<ArgumentTree>();
        if (currentGenTree != null)
            genArgs.addAll(currentGenTree.getChildren());


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
