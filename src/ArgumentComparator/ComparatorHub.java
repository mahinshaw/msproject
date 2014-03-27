package ArgumentComparator;

import ArgumentStructure.ArgumentTree;

import java.util.ArrayList;
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

    public List<ComparatorTree> performComparison(List<ArgumentTree> userTrees,  List<ArgumentTree> generatorTrees){
        int indexCounter = 1;
        ExecutorService executor = Executors.newFixedThreadPool(2);
        List<Future<ComparatorTree>> futureComparatorTrees = new ArrayList<Future<ComparatorTree>>();
        List<ComparatorTree> comparatorTrees = new ArrayList<ComparatorTree>();
        for (ArgumentTree userTree : userTrees){
            for (ArgumentTree genTree : generatorTrees){
                Callable<ComparatorTree> task = new ArgumentComparator(userTree, genTree, indexCounter);
                Future<ComparatorTree> pledge = executor.submit(task);
                futureComparatorTrees.add(pledge);
            }
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

        return comparatorTrees;
    }
}
