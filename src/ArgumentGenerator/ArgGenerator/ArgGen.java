package ArgumentGenerator.ArgGenerator;

import ArgumentGenerator.ArgLibrary.ArgInfo;
import ArgumentGenerator.ArgSchemes.Conjunction;
import ArgumentGenerator.ArgSchemes.*;
import ArgumentGenerator.XMLInterface.ArgInterface;
import ArgumentStructure.ArgumentTree;
import KB.KB_Node.KB_Node;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * ArgGen.java proceeds with the generation of the arguments made from KB.
 * User: Tshering Tobgay
 * Date: 1/5/14
 */
public class ArgGen {
    private KB_Node rootNode;
    private ArgInterface argInterface;
    private ArgInfo argInfo;
    private HashMap<String, String> map;
    private HashMap<String, String> questionMap;
    private ArrayList<ArgumentTree> treeList;
    private String argType;
    private final String question;
    private E2C e2c;
    private JE2C je2c;
    private Conjunction conj;
    private C2E c2e;
    private ArgGenWriter argGenWriter;
    private String extFileName;//this name is appended to the filename of the xml output

    private ArrayList<KB_Node> graphNodes;

    public ArgGen(int nodeID, String question, String fileName) {
        argInterface = new ArgInterface(fileName);
        argInfo = new ArgInfo();
        this.question = question;
        this.graphNodes = argInterface.getGraphNodes();
        this.map = argInterface.getMap();
        this.questionMap = argInterface.getQuestionMap();
        this.treeList = new ArrayList<ArgumentTree>();
        this.extFileName = this.getClass().getSimpleName();

        setRootNode(nodeID);
        setArgType(ArgInfo.abnormalType.FALSE.getAbType());//default abnormal type is false.
    }

    /**
     * Find the root node based on the ID passed
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
        /**
         * The argCounter keeps track of the number of arguments generated from different schemes.
         * It is appended to the output file. The reason: the file will be overwritten, if it finds a different schema.
         */
        int argCounter = 0;
        boolean np = argInfo.findCurrentQuestionNo(question, questionMap);
        if (np) {
            for (int i = 0; i < argNo; i++) {
                e2c = new E2C(this.rootNode, getArgType());
                hold = e2c.getPathList();
                if (!hold.isEmpty()) {
                    argGenWriter = new ArgGenWriter(map, hold, question, extFileName + "_" + argCounter++);
                    treeListHold = argGenWriter.getArgument();//add to argInterface tree and xml GAIL.output
                    if (!treeListHold.isEmpty())
                        addTreeList(treeListHold);
                    if (argType.equalsIgnoreCase(ArgInfo.abnormalType.TRUE.getAbType()))
                        printArgScheme(ArgInfo.schemaType.e2c.getSchema());
                    else
                        printArgScheme(ArgInfo.schemaType.ne2c.getSchema());
                }
                setArgType(ArgInfo.abnormalType.TRUE.getAbType());//find E2C arguments
            }
        }
        /**
         * Check for JE2C scheme argument
         */
        setArgType(ArgInfo.abnormalType.FALSE.getAbType());//check for JE2C first
        je2c = new JE2C(this.rootNode, graphNodes, getArgType());
        hold = je2c.getPathList();
        if (!hold.isEmpty()) {
            argGenWriter = new ArgGenWriter(map, hold, question, extFileName + "_" + argCounter++);
            treeListHold = argGenWriter.getArgument();//add to argInterface tree and xml GAIL.output
            if (!treeListHold.isEmpty())
                addTreeList(treeListHold);
            printArgScheme(ArgInfo.schemaType.je2c.getSchema());
        }

        /**
         * Check for arguments that can be found using conjunction (currently shown as % in KB)
         *
         */
        setArgType(ArgInfo.abnormalType.FALSE.getAbType());
        conj = new Conjunction(map, this.rootNode, graphNodes, question, extFileName + "_" + argCounter++);
        treeListHold = conj.findConjunction();
        if (!treeListHold.isEmpty()) {
            addTreeList(treeListHold);
            printArgScheme(ArgInfo.schemaType.conj.getSchema());
        }

        /**
         * Check for C2E arguments
         */
        if (!np) {
            setArgType(ArgInfo.abnormalType.POSSIBLE.getAbType());
            for (int i = 0; i < argNo; i++) {
                c2e = new C2E(this.rootNode, getArgType());
                hold = c2e.getPathList();
                if (!hold.isEmpty()) {
                    argGenWriter = new ArgGenWriter(map, hold, question, extFileName + "_" + argCounter++);
                    treeListHold = argGenWriter.getArgument();//add to argInterface tree and xml GAIL.output
                    if (!treeListHold.isEmpty())
                        addTreeList(treeListHold);
                    if (argType.equalsIgnoreCase(ArgInfo.abnormalType.POSSIBLE.getAbType()))
                        printArgScheme(ArgInfo.schemaType.c2e.getSchema());
                    else
                        printArgScheme(ArgInfo.schemaType.nc2e.getSchema());
                }
                setArgType(ArgInfo.abnormalType.NOT_POSSIBLE.getAbType());
            }
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
    private void printArgScheme(String r) {
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
