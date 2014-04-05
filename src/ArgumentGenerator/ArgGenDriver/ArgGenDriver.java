package ArgumentGenerator.ArgGenDriver;

import ArgumentGenerator.ArgGenerator.ArgGen;

/**
 * User: Tshering Tobgay
 * Date: 3/27/14
 * This driver can test ArgGen capabilities without going through comparator.
 * A much faster approach to debugging and solution check.
 */
public class ArgGenDriver {
    public static void main(String[] args){
        ArgGen argGen = new ArgGen(1, " TEST ", "src/XMLInput/HereditaryColonCancer.xml");
        //ArgGen argGen = new ArgGen(1, " TEST ", "src/XMLInput/CysticFibrosis.xml");
        argGen.findArgument();
        System.out.println("No of argument tree(s): "+argGen.getArgument().size());
    }
}
