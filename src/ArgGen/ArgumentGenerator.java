package ArgGen;

import ArgumentStructure.Argument;
import KB.KB_Graph.KB_Graph;
import KB.KB_Node.KB_Node;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * Date: 7/30/13
 * github: https://github.com/mahinshaw/msproject
 */
public class ArgumentGenerator {

    private List<List<KB_Node>> pathArray;
    private List<KB_Node> argPath;
    private KB_Graph kbGraph;
    private KB_Node rootNode;
    private ArgBuilder argBuilder;
    private Argument arg;
    private final char HYPO;
    private final char DATA;

    public ArgumentGenerator(KB_Graph graph, KB_Node rootNode){
        this.pathArray = new ArrayList<List<KB_Node>>();
        this.argPath = new ArrayList<KB_Node>();
        this.kbGraph = graph;
        this.rootNode = rootNode;
        this.HYPO = 'H';
        this.DATA = 'D';
        this.argBuilder = new ArgBuilder(this.kbGraph, this.rootNode, this.HYPO, this.DATA);
    }

    public void addNode(KB_Node node){
        argPath.add(node);
    }

    public void addPath(List<KB_Node> path){
        pathArray.add(path);
    }

    public void addArgument(){
        /**
         * Test output
         */
        argBuilder.findArgument();
        System.out.println("Path for E2C");
        for (List<KB_Node> n : argBuilder.getPathList()) {
            for (KB_Node x : n) {
                System.out.print(x.getId()+" ");
            }
            System.out.println("\n-----------");
        }
    }
}
