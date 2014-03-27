package ArgumentComparator;

import ArgumentStructure.ArgumentFactory;
import ArgumentStructure.ArgumentObject;
import ArgumentStructure.ArgumentTree;
import GAIL.src.model.Argument;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * Date: 3/26/14
 * github: https://github.com/mahinshaw/msproject
 *
 * This is a test.
 */
public class ComparatorDriver {
    public static void main(String[] args){
        ComparatorHub hub = new ComparatorHub();
        List<ArgumentTree> userTrees = new ArrayList<ArgumentTree>();
        List<ArgumentTree> genTrees = new ArrayList<ArgumentTree>();
        ArgumentFactory factory = new ArgumentFactory();

        // build a user argument.
        factory.setHypothesis("7", "");
        factory.addGeneralization("I1", "");
        factory.setDatum(true);
        ArgumentTree userArg = ArgumentTree.createArgumentTree(factory.createArgument(1));
        factory = new ArgumentFactory();
        factory.setHypothesis("9", "");
        factory.addGeneralization("I1", ""); // this is what is wrong.
        factory.setDatum("14", "");
        userArg.addSubArgument(factory.createArgument(1), userArg.getRoot());
        userTrees.add(userArg);

        // build a generated argument.
        factory = new ArgumentFactory();
        factory.setHypothesis("7", "");
        factory.addGeneralization("I1", "");
        factory.setDatum(true);
        ArgumentTree genArg = ArgumentTree.createArgumentTree(factory.createArgument(1));
        factory = new ArgumentFactory();
        factory.setHypothesis("9", "");
        factory.addGeneralization("I1", "");
        factory.setDatum("14", "");
        genArg.addSubArgument(factory.createArgument(1), genArg.getRoot());
        genTrees.add(genArg);

        hub.performComparison(userTrees, genTrees);
    }
}
