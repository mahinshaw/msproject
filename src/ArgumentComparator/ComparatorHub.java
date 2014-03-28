package ArgumentComparator;

import ArgumentStructure.ArgumentTree;
import GAIL.src.model.Argument;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * User: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * Date: 3/26/14
 * github: https://github.com/mahinshaw/msproject
 *
 * This class handles communications between the user interface, the Argument Generator, and the Argument Comparator.
 */
public class ComparatorHub {
    public ComparatorHub(){
    }

    private ComparatorTree performComparison(ArgumentTree userTree,  List<ArgumentTree> generatorTrees){
        int indexCounter = 1;
        ExecutorService executor = Executors.newFixedThreadPool(2);
        List<Future<ComparatorTree>> futureComparatorTrees = new ArrayList<Future<ComparatorTree>>();
        List<ComparatorTree> comparatorTrees = new ArrayList<ComparatorTree>();
        for (ArgumentTree genTree : generatorTrees){
            Callable<ComparatorTree> task = new ArgumentComparator(userTree, genTree, indexCounter);
            Future<ComparatorTree> pledge = executor.submit(task);
            futureComparatorTrees.add(pledge);
        }

        // tell the executor to finish.
        executor.shutdown();
        // this is a hack, but it should work.

        for (Future<ComparatorTree> pledge : futureComparatorTrees){
            try {
                comparatorTrees.add(pledge.get());
            }
            catch (ExecutionException e) {
                e.printStackTrace();
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        return treeWithBestAccuracy(comparatorTrees);
    }

    public List<ComparatorTree> getBestAnswers(List<ArgumentTree> userTrees, List<ArgumentTree> generatorTrees){
        List<ComparatorTree> bestAnswers = new ArrayList<ComparatorTree>();
        for (ArgumentTree userTree : userTrees){
            bestAnswers.add(performComparison(userTree, generatorTrees));
        }
        return bestAnswers;
    }

    private ComparatorTree treeWithBestAccuracy(List<ComparatorTree> trees){
        ComparatorTree best = null;
        for (ComparatorTree tree : trees){
            if (best == null){
                best = tree;
            }
            else {
                if (tree.getTreeAccuracy() > best.getTreeAccuracy()){
                    best = tree;
                }
            }
        }
        return best;
    }
}
