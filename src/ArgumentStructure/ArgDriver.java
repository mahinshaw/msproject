package ArgumentStructure;

import java.util.ArrayList;

/**
 * User: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * Date: 8/2/13
 * github: https://github.com/mahinshaw/msproject
 */
public class ArgDriver {

    public static void main(String[] args){
        //hyp only
        ArgumentFactory argumentFactory1 = new ArgumentFactory();
        argumentFactory1.setHypothesis("1", "hypothesis 1");
        argumentFactory1.addGeneralization("2", "generalization");
        argumentFactory1.addGeneralization("3", "generalization");

        //full arg
        ArgumentFactory argumentFactory2 = new ArgumentFactory();
        argumentFactory2.setHypothesis("2", "hypothesis 2");
        argumentFactory2.addGeneralization("4", "generalization");
        argumentFactory2.addGeneralization("5", "generalization");
       // argumentFactory2.setDatum("4", "datum 4");

        ArgumentObject argumentObject1 = argumentFactory1.createArgument(1);
        ArgumentObject argumentObject2 = argumentFactory2.createArgument(2);
        ArgumentTree tree2 = ArgumentTree.createArgumentTree(argumentObject1);
        tree2.addSubArgument(argumentObject2, argumentObject1);


        //full arg
        ArgumentFactory argumentFactory3 = new ArgumentFactory();
        argumentFactory3.setHypothesis("3", "hypothesis 3");
        argumentFactory3.addGeneralization("6", "generalization");
        argumentFactory3.addGeneralization("7", "generalization");
        argumentFactory3.setDatum("5", "datum 5");


       // ArgumentObject argumentObject1 = argumentFactory1.createArgument(1);
        //ArgumentObject argumentObject2 = argumentFactory2.createArgument(2);
        ArgumentObject argumentObject3 = argumentFactory3.createArgument(2);

      //  System.out.println(argumentObject1.getTypeofArgument());
        //System.out.println(argumentObject2.getTypeofArgument());
        //System.out.println(argumentObject3.getTypeofArgument());

        // tree one is a single argument
        //ArgumentTree tree1 = ArgumentTree.createArgumentTree(argumentObject2);

        // tree 2 has a conjugated argument
      //  ArgumentTree tree2 = ArgumentTree.createArgumentTree(argumentObject1);
        tree2.addSubArgument(argumentObject3, argumentObject2);
        //tree2.addSubArgument(argumentObject3);

        ArrayList<ArgumentTree> treeList = new ArrayList<ArgumentTree>();
        //treeList.add(tree1);
        treeList.add(tree2);

        XMLWriter writer = new XMLWriter();
        writer.writeXML(treeList, "This question is a test.");
    }
}
