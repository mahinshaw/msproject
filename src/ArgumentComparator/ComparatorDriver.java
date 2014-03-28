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
        factory.addGeneralization("I1", "Cystic fibrosis is the name of a disease caused by having two mutated copies of the CFTR gene. When both copies of CFTR are mutated, the body produces abnormal CFTR protein."); // this is what is wrong.
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
        factory.addGeneralization("I2", "In the absence of normal CFTR protein, sweat has an increased sodium chloride content. Most patients with cystic fibrosis show an elevated level of sweat chloride (more than 60 mmol/L).");
        factory.setDatum("14", "Test result: J.B.'s chloride level is 75 mmol/L.");
        genArg.addSubArgument(factory.createArgument(1), genArg.getRoot());
        genTrees.add(genArg);

        // build a generated argument.
        factory = new ArgumentFactory();
        factory.setHypothesis("7", "J. B. has cystic fibrosis (has 2 mutated copies of the CFTR gene).");
        factory.addGeneralization("I1", "Cystic fibrosis is the name of a disease caused by having two mutated copies of the CFTR gene. When both copies of CFTR are mutated, the body produces abnormal CFTR protein.");
        ArgumentTree genArg2 = ArgumentTree.createArgumentTree(factory.createArgument(1));
        factory = new ArgumentFactory();
        factory.setHypothesis("9", "J.B's CFTR protein is abnormal");
        factory.addGeneralization("I3", "People with CFTR protein often have viscous secretions in the lungs.");
        ArgumentObject two = factory.createArgument(2);
        genArg2.addSubArgument(two, genArg2.getRoot());
        factory = new ArgumentFactory();
        factory.setHypothesis("15", "J.B. has thick mucus secretion from her lungs.");
        factory.addGeneralization("I4", "Viscous secretion provides a medium for growth of bacteria, and which may result in frequent respiratory infections.");
        factory.setDatum("10", "History of respiratory problems: During her second year, J.B. developed a chronic cough and has had frequent upper respiratory infections.");
        genArg2.addSubArgument(factory.createArgument(2), two);
        genTrees.add(genArg2);


        List<ComparatorTree> output = hub.performComparison(userTrees, genTrees);

        writer.writeXML(output, "question 1");
    }
}
