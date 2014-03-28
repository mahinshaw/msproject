package ArgumentStructure;

import GAIL.src.controller.ConjunctionController;
import GAIL.src.controller.EdgeController;
import GAIL.src.controller.StatementController;
import GAIL.src.model.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * User: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * Date: 10/10/13
 * github: https://github.com/mahinshaw/msproject
 *
 * This class was implemented with the desire to not have to modify the core GAIL code.  Because of this it has
 * inherent inefficiencies.
 *
 * The purpose of this method is to load the unstructured objects created by the gui into a graph structure.
 * This will allow for differentiation in the case where there are multiple chained arguments created within GAIL.
 * This also supports loading into the Argument Structure.
 */
public class GraphBuilder {

    // model data objects
    private ArrayList<MultiGeneralizationModel> multiGeneralizations;
    private ArrayList<Branch> branches;
    private ArrayList<Conjunction> conjunctions;
    private ArrayList<Warrant> warrants;
    private ArrayList<Argument> arguments;

    //object containers for the Graph
    // heads are the beginnings of an argument.  This is necessary as there may be multiple arguments.
    private LinkedList<Statement> heads;
    private LinkedList<Statement> hypotheses;
    private LinkedList<Statement> data;
    private LinkedList<Statement> generalizations;
    private LinkedHashMap<Node, LinkedList<Edge>> adjacencyMap;

    // object container for ArgumentTree(s)
    private ArrayList<ArgumentTree> argumentTrees;
    private int currentArgID;
    private ArgumentTree tree;
    private ArgumentFactory factory;

    public GraphBuilder(StatementController statementController,
                        EdgeController edgeController,
                        ConjunctionController conjunctionController,
                        ArrayList<MultiGeneralizationModel> multiGeneralizations) {
        this.multiGeneralizations = multiGeneralizations;
        this.branches = edgeController.getBranches();
        this.conjunctions = conjunctionController.getConjunctions();
        this.warrants = edgeController.getWarrants();
        this.arguments = edgeController.getArguments();
        this.adjacencyMap = new LinkedHashMap<Node, LinkedList<Edge>>();
        this.hypotheses = new LinkedList<Statement>();
        this.generalizations = new LinkedList<Statement>();
        this.data = new LinkedList<Statement>();
        this.heads = new LinkedList<Statement>();
        this.argumentTrees = new ArrayList<ArgumentTree>(3);
        this.currentArgID = 0;
        findTypes(statementController.getStatements());
        buildAdjacencyMap();
        findHeads();
        loadTrees();
    }

    /**
     * WHAT TO DO - Need to find the hypothesis with only one edge connecting it.  if it is a data statement, or a
     * generalization, then it cannot be the head. First thing would be to sort the statements.
     */

    private void addHead(Statement head) {
        this.heads.push(head);
    }

    public ArrayList<ArgumentTree> getArgumentTrees() {
        return argumentTrees;
    }

    private void findTypes(ArrayList<Statement> statements) {
        for (Statement statement : statements) {
            if (statement.getType() == Statement.StatementType.HYPOTHESIS) {
                this.hypotheses.push(statement);
            } else if (statement.getType() == Statement.StatementType.DATA) {
                this.data.push(statement);
            } else if (statement.getType() == Statement.StatementType.GENERALIZATION) {
                this.generalizations.push(statement);
            }
        }
    }

    /**
     * This method builds an adjacency map
     */
    private void buildAdjacencyMap() {
        for (Statement hypothesis : hypotheses) {
            // check to see if the hypothesis is attached to any arguments
            for (Argument a : arguments)
                if (a.getSource() == hypothesis || a.getTarget() == hypothesis)
                    addToAdjacencyMap(hypothesis, a);
            // check to see if the statement is attached to any branches.
            for (Branch b : branches)
                if (b.getSource() == hypothesis)
                    addToAdjacencyMap(hypothesis, b);
        }
        for (Statement datum : data) {
            // check to see if the statement is attached to any arguments
            for (Argument a : arguments)
                if (a.getSource() == datum || a.getTarget() == datum)
                    addToAdjacencyMap(datum, a);
            // check to see if the statement is attached to any branches.
            for (Branch b : branches)
                if (b.getSource() == datum)
                    addToAdjacencyMap(datum, b);
        }
        for (Conjunction conjunction : conjunctions) {
            // check to see if the statement is attached to any arguments
            for (Argument a : arguments)
                if (a.getSource() == conjunction)
                    addToAdjacencyMap(conjunction, a);
            // check to see if the statement is attached to any branches.
            for (Branch b : branches)
                if (b.getSource() == conjunction || b.getTarget() == conjunction)
                    addToAdjacencyMap(conjunction, b);
        }
    }

    /**
     * The method looks through the adjacency list and finds any heads of an argument.
     * A head must be a hypothesis, and it must have only one connected edge.
     */
    private void findHeads() {
        // search the adjacency map for the first instance of a hypothesis that only has one edge.
        for (Statement h : hypotheses)
            if (adjacencyMap.containsKey(h))
                if (adjacencyMap.get(h).size() == 1)
                    addHead(h);
    }

    /**
     * This method will look at each head, traverse it's edges and load the argument trees.
     */
    private void loadTrees() {
        ArgumentObject parent = null;
        // placeholders
        Statement current;
        LinkedList<Edge> edges;
        Node next;

        //make sure that there are heads to start with
        if (heads.isEmpty()) return;

        while (!heads.isEmpty()) {
            // increment currentArgID
            currentArgID++;
            // initialize factory
            factory = new ArgumentFactory();
            // assign the next head to be the start of a new tree. Add to tree
            current = heads.pop();
            factory.setHypothesis(current.getNode_id(), current.getText());
            // get the edges of the current, then pull the head off the adjacency map.
            edges = adjacencyMap.get(current);
            adjacencyMap.remove(current);
            // the head should only have one edge
            // getNextNode will add generalizations if needed.
            next = getNextNode(current, edges);
            // if next is null then we need to go to the next head.
            if (next.equals(null)) continue;

            // create a tree for the new head
            tree = null;
            tree = treeBuilder(next, parent);
            argumentTrees.add(tree);

        }
    }

    /****************** Helper Methods ******************/

    /**
     * This method adds to the adjacencyMap based on the state of the key(s) that is passed.  It important to look to
     * see if the key exists, or if the map is empty to know how to add to the list value.
     *
     * @param s - is the key(statement)
     * @param e - is the value to add to the list of edges.
     */
    private void addToAdjacencyMap(Node s, Edge e) {
        LinkedList<Edge> edges;
        // if the map is empty
        if (adjacencyMap.isEmpty()) {
            edges = new LinkedList<Edge>();
            edges.add(e);
            adjacencyMap.put(s, edges);
        }
        // if the key already exists
        else if (adjacencyMap.containsKey(s)) {
            // check to see if the edge is already in the list.
            if (adjacencyMapContainsEdge(s, e))
                return;
                // if the edge is not there
            else {
                edges = adjacencyMap.get(s);
                edges.add(e);
                adjacencyMap.remove(s);
                adjacencyMap.put(s, edges);
            }
            // if the key does not exist, add it and a edge list with e as its value.
        } else {
            edges = new LinkedList<Edge>();
            edges.add(e);
            adjacencyMap.put(s, edges);
        }
    }

    /**
     * This method checks to see if there is an Edge e contained within the list of Edges for key s.
     *
     * @param s - the key to check the adjacency map.
     * @param e - the value to check within the list of Edges.
     * @return
     */
    private boolean adjacencyMapContainsEdge(Node s, Edge e) {
        return adjacencyMap.get(s).contains(e) ? true : false;
    }

    /**
     * This method returns the next node in the list of edges.  It will return null if there are no edges in the list.
     * This method also calls cleanEdges(). removing and already viewed edge if necessary.
     *
     * @param current - The node that is currently being searched.
     * @param edges   - The list of edges attached to the current node.
     * @return
     */
    private Node getNextNode(Node current, LinkedList<Edge> edges) {
        Node next = null;
        Edge edge = edges.poll(); //edges is decremented.
        // make sure the edge exists.  If not, then we return null.  This is important for Conjunctions.
        if (edge == null) {
            return next;
        }
        switch (edge.getEdgeType()) {
            // find out if it is a source or a target
            case ARGUMENT:
                Argument a = (Argument) edge;
                // add generalizations to factory
                addGeneraliztionsToFactory(a);
                if (a.getTarget() == current)
                    next = a.getSource();
                else
                    next = a.getTarget();
                break;
            // warrants should not exist in this list - Target is always an argument.
            case WARRANT:
                break;
            // Target for a branch is always a conjunction - The Statement is the source.
            case BRANCH:
                Branch b = (Branch) edge;
                if (b.getTarget() == current)
                    next = b.getSource();
                else
                    next = b.getTarget();
                break;
        }
        //now that we have next, lets clean its edges.
        cleanEdges(current, next);

        return next;
    }

    /**
     * This method is used to append any generalizations attatched to an edge related to a hypothesis.
     * It is called from the getNextNode() method.
     *
     * @param a - The Argument (edge) that is being inspected by getNextNode().
     */
    private void addGeneraliztionsToFactory(Argument a) {
        ArrayList<Warrant> aWarrants = a.getWarrants();
        for (Warrant w : aWarrants) {
            if (w.getSource() instanceof MultiGeneralizationModel) {
                for (Statement s : ((MultiGeneralizationModel) w.getSource()).getGeneralizations()) {
                    factory.addGeneralization(s.getNode_id(), s.getText());
                }
            } else if (w.getSource() instanceof Statement && ((Statement) w.getSource()).getType() == Statement.StatementType.GENERALIZATION) {
                Statement s = (Statement) w.getSource();
                factory.addGeneralization(s.getNode_id(), s.getText());
            }
        }
    }

    /**
     * This method does the heavy lifting.  Based upon what type the next node is, we load finish the factory and
     * load the tree.  This method is recursive by nature.
     *
     * @param next   - the next node (Statement) to look at.
     * @param parent - The parent ArgumentObject.
     * @return
     */
    private ArgumentTree treeBuilder(Node next, ArgumentObject parent) {
        Node current = next;
        LinkedList<Edge> edges = adjacencyMap.get(current);
        // now remove current from adjacency list
        adjacencyMap.remove(current);
        ArgumentObject nextParent;

        // base case: the next node is a data, and we return it
        if (current instanceof Statement && ((Statement) current).getType() == Statement.StatementType.DATA) {
            factory.setDatum(((Statement) current).getNode_id(), ((Statement) current).getText());
            if (tree == null)
                return ArgumentTree.createArgumentTree(factory.createArgument(currentArgID));
            else {
                tree.addSubArgument(factory.createArgument(currentArgID), parent);
                return tree;
            }
        } else if (current instanceof Statement && ((Statement) current).getType() == Statement.StatementType.HYPOTHESIS) {
            // The hypothesis is special.  we need to set the hyporthesis, but not change the parent since the object
            // may not be completed.
            if (factory.isHypothesisSet()) {
                // if the hypothesis is already set, then we know that we have a chained argument.
                if (tree == null) {
                    tree = ArgumentTree.createArgumentTree(factory.createArgument(currentArgID));
                    parent = tree.getRoot();
                } else {
                    nextParent = factory.createArgument(currentArgID);
                    tree.addSubArgument(nextParent, parent);
                    parent = nextParent;
                }
                factory = new ArgumentFactory();
            }
            factory.setHypothesis(((Statement) current).getNode_id(), ((Statement) current).getText());

            next = getNextNode(current, edges);
            return treeBuilder(next, parent);
        } else if (current instanceof Conjunction) {
            // Conjunctions are loaded into the Datum Object.
            factory.setDatum(true); // passing True Denotes that this Datum is a Conjunction.

            // if the tree is null, we must create the tree, because there is no parent. and the parent is the root
            if (tree == null) {
                tree = ArgumentTree.createArgumentTree(factory.createArgument(currentArgID));
                parent = tree.getRoot();
            } else {
                nextParent = factory.createArgument(currentArgID);
                tree.addSubArgument(nextParent, parent);
                parent = nextParent;
            }
            // loop over the edges and attach them to the tree.
            while (!edges.isEmpty()) {
                next = getNextNode(current, edges);

                factory = new ArgumentFactory();

                tree = treeBuilder(next, parent);
            }

            return tree;
        } else {
            // if we made it here, then next is null and the answer is wrong, hence we return the tree
            // if the tree is null, we must create the tree, because there is no parent. and the parent is the root
            if (tree == null) {
                tree = ArgumentTree.createArgumentTree(factory.createArgument(currentArgID));
                parent = tree.getRoot();
            } else {
                tree.addSubArgument(factory.createArgument(currentArgID), parent);
            }
            return tree;
        }
    }

    /**
     * This method cleans edges that may have already been used by previous node.
     *
     * @param current
     * @param next
     * @return
     */
    private void cleanEdges(Node current, Node next) {
        LinkedList<Edge> edges = new LinkedList<Edge>();
        for (Edge e : adjacencyMap.get(next)) {
            switch (e.getEdgeType()) {

                // find out if it is a source or a target
                case ARGUMENT:
                    if (((Argument) e).getTarget() == current)
                        edges.add(e);
                    else if (e.getSource() == current)
                        edges.add(e);
                    break;
                // warrants should not exist in this list - Target is always an argument.
                case WARRANT:
                    break;
                // Target for a branch is always a conjunction - The Statement is the source.
                case BRANCH:
                    if (e.getSource() == current)
                        edges.add(e);
                    break;
            }
        }
        for (Edge e : edges) {
            removeEdge(next, e);
        }
    }

    private void removeEdge(Node n, Edge e) {
        LinkedList<Edge> edges = adjacencyMap.get(n);
        edges.remove(e);
        adjacencyMap.put(n, edges);
    }
}
