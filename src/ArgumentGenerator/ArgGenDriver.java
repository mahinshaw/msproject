package ArgumentGenerator;

import ArgumentGenerator.ArgGenerator.ArgGen;

/**
 * User: Tshering Tobgay
 * Date: 3/27/14
 * This driver can test ArgGen capabilities without going through GAIL.
 * A much faster approach to debugging and soultion check.
 */
public class ArgGenDriver {
    public static void main(String[] args){
        ArgGen argGen = new ArgGen(8, " TEST ");
        argGen.findArgument();
        System.out.println("No of argument tree(s): "+argGen.getArgument().size());
    }
}
