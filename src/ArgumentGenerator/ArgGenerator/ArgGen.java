package ArgumentGenerator.ArgGenerator;

import ArgumentGenerator.ArgSchemes.Conjunction;
import ArgumentGenerator.ArgSchemes.*;
import ArgumentGenerator.XMLInterface.ArgInterface;
import ArgumentStructure.ArgumentTree;
import KB.KB_Node.KB_Node;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * ArgGen.java proceeds with the generation of the arguments made from KB.
 * <p/>
 * User: Tshering Tobgay
 * Date: 1/5/14
 */
public class ArgGen {
    private KB_Node rootNode;
    private ArgInterface argInterface;
    private HashMap<String, String> map;
    private ArrayList<ArgumentTree> treeList;
    private String argType;
    private final String question;
    private E2C e2c;
    private JE2C je2c;
    private Conjunction conj;
    private C2E c2e;
    private ArgGenWriter argGenWriter;
    private String fileName = "CysticFibrosis.xml";//the name of the KB file name located in src/XMLInput/...
    private String extFileName = "ArgGen";//this name is appended to the filename
    final String defltVal = "false";

    private ArrayList<KB_Node> graphNodes;

    public ArgGen(int nodeID, String question) {
        argInterface = new ArgInterface(fileName);
        this.question = question;
        this.graphNodes = argInterface.getGraphNodes();
        this.map = argInterface.getMap();
        this.treeList = new ArrayList<ArgumentTree>();

        setRootNode(nodeID);
        setArgType(defltVal);//default abnormal type is false.
    }

    /**
     * Find the rootnode based on the ID passed
     *
     * @param nodeID
     */
    private void setRootNode(int nodeID) {
        for (int i = 0; i < graphNodes.size(); i++)
            if (graphNodes.get(i).getId() == nodeID)
                this.rootNode = graphNodes.get(i);
    }

    /**
     * Call to different argument generators.
     * If not empty, get argument and argument type
     */
    public void findArgument() {
        ArrayList<ArrayList<KB_Node>> hold;
        ArrayList<ArgumentTree> treeListHold;
        /**
         * Check for arguments in terms of E2C and NE2C scheme
         * (argType = true for E2C "abnormal" and argType = false for NE2C "not abnormal.")
         *  First find NE2C (false) arguments, then E2C (true)
         */
        int argNo = 2;//the number of arguments, in this case, it's 2 (E2C + NE2C)

        for (int i = 0; i < argNo; i++) {
            e2c = new E2C(this.rootNode, getArgType());
            hold = e2c.getPathList();
            if (!hold.isEmpty()) {
                argGenWriter = new ArgGenWriter(map, hold, question, extFileName);
                treeListHold = argGenWriter.addArgument();//add to argInterface tree and xml GAIL.output
                if (!treeListHold.isEmpty())
                    addTreeList(treeListHold);
            } else {
                if (argType.equalsIgnoreCase("true"))
                    printEmptyArg("E2C");
                else
                   printEmptyArg("NE2C");
            }
            setArgType("true");//find E2C arguments
        }
        /**
         * Check for JE2C scheme argument
         */
        setArgType("false");//check for JE2C first
        je2c = new JE2C(this.rootNode, graphNodes, getArgType());
        hold = je2c.getPathList();
        if (!hold.isEmpty()) {
            argGenWriter = new ArgGenWriter(map, hold, question, extFileName);
            treeListHold = argGenWriter.addArgument();//add to argInterface tree and xml GAIL.output
            if (!treeListHold.isEmpty())
                addTreeList(treeListHold);
            //printArgScheme(ArgInfo.schemaType.je2c.getSchema());
        } else {
            printEmptyArg("JE2C");
        }

        /**
         * Check for arguments that can be found using elimination (currently shown as % in KB)
         *
         */
        setArgType(defltVal);
        conj = new Conjunction(map, this.rootNode, graphNodes, question, extFileName);
        treeListHold = conj.findConjunction();
        if (!treeListHold.isEmpty())
            addTreeList(treeListHold);

        /**
         * Check for C2E arguments
         */
        c2e = new C2E(this.rootNode);
        hold = c2e.getPathList();
        if (!hold.isEmpty()) {
            argGenWriter = new ArgGenWriter(map, hold, question, extFileName);
            treeListHold = argGenWriter.addArgument();//add to argInterface tree and xml GAIL.output
            if (!treeListHold.isEmpty())
                addTreeList(treeListHold);
            //printArgScheme(ArgInfo.schemaType.c2e.getSchema());
        } else {
            printEmptyArg("C2E");
        }
    }

    private void addTreeList(ArrayList<ArgumentTree> treeListHolder) {
        for (ArgumentTree tree : treeListHolder)
            treeList.add(tree);
    }

    private void setArgType(String argType) {
        this.argType = argType;
    }

    private String getArgType() {
        return argType;
    }

    /**
     * Only called when no argument is found.
     * Prints to local console, not interface.
     *
     * @param r
     */
    private void printEmptyArg(String r) {
        System.out.println("ArgGen did not produce any argument using " + r + " schema ~ ArgumentGenerator/ArgGenerator/ArgGen.java");
    }

    /**
     * Prints out the type of schema used.
     *
     * @param r
     */
    private void printArgScheme(String r){
        System.out.println("ArgGen produced argument(s) using " + r + " schema ~ ArgumentGenerator/ArgGenerator/ArgGen.java");
    }

    /**
     * Return argument tree(s)
     *
     * @return
     */
    public ArrayList<ArgumentTree> getArgument() {
        return treeList;
    }
}
