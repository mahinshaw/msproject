package ArgGen;

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

    private ArgumentGenerator(KB_Graph graph){
        this.pathArray = new ArrayList<List<KB_Node>>();
        this.argPath = new ArrayList<KB_Node>();
        this.kbGraph = graph;
    }

    public void addNode(KB_Node node){
        argPath.add(node);
    }

    public void addPath(List<KB_Node> path){
        pathArray.add(path);
    }
}
