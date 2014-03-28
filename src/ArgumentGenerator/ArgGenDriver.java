package ArgumentGenerator;

import ArgumentGenerator.ArgGenerator.ArgGen;

/**
 * User: Tshering Tobgay
 * Date: 3/27/14
 * This driver can test ArgGen capabilities without going through comparator.
 * A much faster approach to debugging and solution check.
 */
public class ArgGenDriver {
    public static void main(String[] args){
        ArgGen argGen = new ArgGen(7, " TEST ");
        argGen.findArgument();
        System.out.println("No of argument tree(s): "+argGen.getArgument().size());
    }
}
