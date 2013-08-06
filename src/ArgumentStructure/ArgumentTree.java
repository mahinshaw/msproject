package ArgumentStructure;

/**
 * User: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * Date: 8/2/13
 * github: https://github.com/mahinshaw/msproject
 */
public class ArgumentTree {
    private Argument root;
    private ArgumentTree leftChild;
    private ArgumentTree rightChild;

    private ArgumentTree(Argument argument){
        this.root = argument;
        this.leftChild = null;
        this.rightChild = null;
    }

    public Argument getRoot(){
        return this.root;
    }

    public ArgumentTree getLeftChild(){
        return this.leftChild;
    }

    public ArgumentTree getRightChild(){
        return this.rightChild;
    }

    public void addSubArgument(Argument argument){
        if (leftChild.equals(null) && !argument.isHypothesis()){
            leftChild = createArgumentTree(argument);
        }
        else if (!argument.isHypothesis()){
            rightChild = createArgumentTree(argument);
        }
        else {
            System.out.println("The argument is a full argument");
        }
    }

    public void addSubHypothesis(Argument argument){
        if (leftChild.equals(null) && argument.isHypothesis()){
            leftChild = createArgumentTree(argument);
        }
        else if (argument.isHypothesis()){
            rightChild = createArgumentTree(argument);
        }
        else {
            System.out.println("The argument is only a hypothesis.");
        }
    }

    public static ArgumentTree createArgumentTree(Argument argument){
        return new ArgumentTree(argument);
    }
}
