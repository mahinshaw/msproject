package ArgumentStructure;

/**
 * User: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * Date: 8/2/13
 * github: https://github.com/mahinshaw/msproject
 */
public class ArgumentTree {
    private ArgumentObject root;
    private ArgumentTree leftChild;
    private ArgumentTree rightChild;

    private ArgumentTree(ArgumentObject argumentObject){
        this.root = argumentObject;
        this.leftChild = null;
        this.rightChild = null;
    }

    public ArgumentObject getRoot(){
        return this.root;
    }

    public ArgumentTree getLeftChild(){
        return this.leftChild;
    }

    public boolean hasLeftChild(){
        if (this.leftChild == null){
            return false;
        }
        else {
            return true;
        }
    }

    public ArgumentTree getRightChild(){
        return this.rightChild;
    }

    public boolean hasRightChild(){
        if (this.rightChild == null){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean isEmptyChild(ArgumentTree tree){
        if (tree == null){
            return true;
        } else {
            return false;
        }
    }

    public void addSubArgument(ArgumentObject argumentObject){
        if (isEmptyChild(leftChild) && !argumentObject.isHypothesis()){
            this.leftChild = createArgumentTree(argumentObject);
        }
        else if (!argumentObject.isHypothesis()){
            this.rightChild = createArgumentTree(argumentObject);
        }
        else {
            System.out.println("The argument is a full argument");
        }
    }

    public void addSubHypothesis(ArgumentObject argumentObject){
        if (leftChild.equals(null) && argumentObject.isHypothesis()){
            leftChild = createArgumentTree(argumentObject);
        }
        else if (argumentObject.isHypothesis()){
            rightChild = createArgumentTree(argumentObject);
        }
        else {
            System.out.println("The argument is only a hypothesis.");
        }
    }

    public static ArgumentTree createArgumentTree(ArgumentObject argumentObject){
        return new ArgumentTree(argumentObject);
    }
}
