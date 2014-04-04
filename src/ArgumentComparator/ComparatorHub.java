package ArgumentComparator;

import ArgumentStructure.ArgumentTree;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * This class handles communications between the user interface, the Argument Generator, and the Argument Comparator.
 */
public class ComparatorHub {
    private ExecutorService executor;
    private int num_threads;

    public ComparatorHub(){
        num_threads = 1;//Runtime.getRuntime().availableProcessors();
        executor = Executors.newFixedThreadPool(num_threads);
    }

    private List<ComparatorTree> performComparison(ArgumentTree userTree,  List<ArgumentTree> generatorTrees){
        int indexCounter = 1;
        List<Future<ComparatorTree>> futureComparatorTrees = new ArrayList<Future<ComparatorTree>>();
        List<ComparatorTree> comparatorTrees = new ArrayList<ComparatorTree>();
        for (ArgumentTree genTree : generatorTrees){
            Callable<ComparatorTree> task = new ArgumentComparator(userTree, genTree, indexCounter);
            Future<ComparatorTree> pledge = executor.submit(task);
            futureComparatorTrees.add(pledge);
        }

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

        return comparatorTrees;
    }

    public List<ComparatorTree> getBestAnswers(List<ArgumentTree> userTrees, List<ArgumentTree> generatorTrees){
        List<ComparatorTree> bestAnswers = new ArrayList<ComparatorTree>();
        for (ArgumentTree userTree : userTrees){
            bestAnswers.addAll(performComparison(userTree, generatorTrees));
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
