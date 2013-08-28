package GAIL.src.controller;

import ArgumentStructure.ArgumentObject;
import ArgumentStructure.ArgumentTree;
import GAIL.src.model.Branch;
import GAIL.src.model.Conjunction;
import GAIL.src.model.Statement;
import GAIL.src.model.Warrant;
import GAIL.src.model.Argument;

import java.util.ArrayList;

/**
 * User: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * Date: 8/27/13
 * github: https://github.com/mahinshaw/msproject
 *
 * This class is to decouple the loading of the Argument Tree Structure from the rest of the GAIL controllers.
 * This class will use the models in gail to load the tree structure.
 */
public class ArgTreeLoader {

    /**
     * These fields will hold objects related to the Argument tree
     */
    private ArrayList<ArgumentObject> argumentObjects = new ArrayList<ArgumentObject>();
    private ArrayList<ArgumentTree> argumentTrees = new ArrayList<ArgumentTree>();

    /**
     * These fields will hold objects related to GAIL itself
     */
    private ArrayList<Conjunction> conjunctions;
    private ArrayList<Branch> branches;
    private ArrayList<Statement> statements;
    private ArrayList<Warrant> warrants;
    private ArrayList<Argument> arguments;

    /**
     * Constructor.  Requires all of these parameters to be inserted
     * @param conjunctions - the arraylist of conjuctions created in GAIL.
     * @param branches - the arraylist of branches created in GAIL.
     * @param statements - the arraylist of statements created in GAIL.
     * @param warrants - the arraylist of warrants created in GAIL.
     * @param arguments - the arraylist of arguments created in GAIL.
     */
    public ArgTreeLoader(ArrayList<Conjunction> conjunctions, ArrayList<Branch> branches,
                         ArrayList<Statement> statements, ArrayList<Warrant> warrants,
                         ArrayList<Argument> arguments){
        this.conjunctions = conjunctions;
        this.branches = branches;
        this.statements = statements;
        this.warrants = warrants;
        this.arguments = arguments;
    }

    /**
     * This method is the main method of the class.  It will handle all the operations needed to load the Argument Trees.
     */
    public void loadTrees(){
        // check to see if there are any conjuctions.  If there are not move on to arguments.
        if (!conjunctions.isEmpty()){
            // here we will count the number of conjunctions to and pass them to a builder to handle loading
        }
        // Here we assume there are no conjuctions.  check the number of arguments.
        // If one, then operation is simple, just load it to the tree.
        else if (!arguments.isEmpty()){
            // if there are more than one it could be a nested argument, or multiple arguments
            if (arguments.size() > 1){

            }
            //if there is only one, then the work is easy, load it into a tree.
            else if (arguments.size() == 1){

            }
        }
    }

    /**
     * The following methods are private.  They handle the internal workings of the loader.
     */

    private void insertArgument(ArgumentTree argumentTree, Argument argument){
    }

}
