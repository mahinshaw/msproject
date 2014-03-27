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
        ComparatorXMLWriter writer = new ComparatorXMLWriter();
        ComparatorHub hub = new ComparatorHub();
        List<ArgumentTree> userTrees = new ArrayList<ArgumentTree>();
        List<ArgumentTree> genTrees = new ArrayList<ArgumentTree>();
        ArgumentFactory factory = new ArgumentFactory();

        // build a user argument.
        factory.setHypothesis("7", "J. B. has cystic fibrosis (has 2 mutated copies of the CFTR gene).");
        factory.addGeneralization("I1", "Cystic fibrosis is the name of a disease caused by having two mutated copies of the CFTR gene. When both copies of CFTR are mutated, the body produces abnormal CFTR protein.");
        ArgumentTree userArg = ArgumentTree.createArgumentTree(factory.createArgument(1));
        factory = new ArgumentFactory();
        factory.setHypothesis("9", "J.B's CFTR protein is abnormal");
        factory.addGeneralization("I2", "In the absence of normal CFTR protein, sweat has an increased sodium chloride content. Most patients with cystic fibrosis show an elevated level of sweat chloride (more than 60 mmol/L)."); // this is what is wrong.
        factory.setDatum("12", "J.B.'s mother does not have a history of respiratory problems."); // this is also wrong.
        userArg.addSubArgument(factory.createArgument(1), userArg.getRoot());
        userTrees.add(userArg);

        // build a generated argument.
        factory = new ArgumentFactory();
        factory.setHypothesis("7", "J. B. has cystic fibrosis (has 2 mutated copies of the CFTR gene).");
        factory.addGeneralization("I1", "Cystic fibrosis is the name of a disease caused by having two mutated copies of the CFTR gene. When both copies of CFTR are mutated, the body produces abnormal CFTR protein.");
        ArgumentTree genArg = ArgumentTree.createArgumentTree(factory.createArgument(1));
        factory = new ArgumentFactory();
        factory.setHypothesis("9", "J.B's CFTR protein is abnormal");
        factory.addGeneralization("I1", "Cystic fibrosis is the name of a disease caused by having two mutated copies of the CFTR gene. When both copies of CFTR are mutated, the body produces abnormal CFTR protein.");
        factory.setDatum("14", "Test result: J.B.'s chloride level is 75 mmol/L.");
        genArg.addSubArgument(factory.createArgument(1), genArg.getRoot());
        genTrees.add(genArg);

        List<ComparatorTree> output = hub.performComparison(userTrees, genTrees);

        writer.writeXML(output, "question 1");
    }
}
