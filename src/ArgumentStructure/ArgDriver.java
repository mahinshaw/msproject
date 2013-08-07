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
        //full argument
        Argument argument3 = new Argument.Builder()
                .setHypothesis("1", "let")
                .addGeneralization("2", "us")
                .addGeneralization("3", "test")
                .setDatum("4", "this")
                .setArgID(1)
                .build();

        //full argument
        Argument argument2 = new Argument.Builder()
                .setHypothesis("5", "this")
                .addGeneralization("6", "is")
                .addGeneralization("7", "a")
                .setDatum("8", "test")
                .setArgID(1)
                .build();

        //hypothesis only
        Argument argument1 = new Argument.Builder()
                .setArgID(2)
                .setHypothesis("1", "Test please")
                .build();

        System.out.println(argument1.getTypeofArgument());
        System.out.println(argument2.getTypeofArgument());
        System.out.println(argument3.getTypeofArgument());

        // tree one is a single argument
        ArgumentTree tree1 = ArgumentTree.createArgumentTree(argument2);

        // tree 2 has a conjugated argument
        ArgumentTree tree2 = ArgumentTree.createArgumentTree(argument1);
        tree2.addSubArgument(argument2);
        tree2.addSubArgument(argument3);

        ArrayList<ArgumentTree> treeList = new ArrayList<ArgumentTree>();
        treeList.add(tree1);
        treeList.add(tree2);

        XMLWriter writer = new XMLWriter();
        writer.writeXML(treeList, "This question is a test.");
    }
}
