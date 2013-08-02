package ArgumentStructure;

/**
 * User: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * Date: 8/2/13
 * github: https://github.com/mahinshaw/msproject
 */
public class ArgDriver {

    public static void main(String[] args){
        //full argument
        Argument argument = new Argument.Builder()
                .setHypothesis(1, "hello")
                .addGeneralization(2, "world")
                .addGeneralization(3, "this")
                .setDatum(4, "sucks")
                .setArgID(1)
                .build();

        //hypothesis only
        Argument argument1 = new Argument.Builder()
                .setArgID(2)
                .setHypothesis(1, "what?")
                .build();

        System.out.println(argument.getTypeofArgument());
        System.out.println(argument1.getTypeofArgument());
    }
}
