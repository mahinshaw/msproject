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

    public ArgumentTree(Argument argument){
        this.root = argument;
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
}
