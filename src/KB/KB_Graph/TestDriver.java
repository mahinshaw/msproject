package KB.KB_Graph;

/**
 * Created with IntelliJ IDEA.
 * User: mark
 * Date: 7/11/13
 * Time: 3:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestDriver {

    public static void main(String args[]) {
        KB_Graph graph = new KB_Graph();

        // People
        graph.createPerson(1, "Mom", 'f', 32);
        graph.createPerson(2, "Dad", 'm', 33);
        graph.createPerson(3, "JB", 'm', 2);
        graph.createPerson(4, "Brother", 'm', 3);


        // Nodes
        graph.createGenotype(1, 1, "1", "CF", "false", "recessive", "CF");
        graph.createGenotype(2, 2, "1", "CF", "false", "recessive", "CF");
        graph.createGenotype(3, 3, "1", "CF", "true", "recessive", "CF");
        graph.createGenotype(7, 4, "1", "CF", "true", "autosomeType", "CF");
        graph.createBiochemistry(4, 3, "2", "CF Protein", "false", "2");
        graph.createSymptom(5, 3, "3", "Fluid in Lungs", "severe", "abnormal");
        graph.createSymptom(8, 2, "3", "Upper Resp Inf", "none", "abnormal");
        graph.createSymptom(9, 1, "3", "Upper Resp Inf", "none", "abnormal");
        graph.createTest(10, 3, "SCT", "Abnormal", "Test");
        graph.createPhysiology(11, 3, "lungs", "thickened mucus", "abnormal");

        //arcs
        int[] ids = {1, 2};
        graph.createSynergyArc("S1", "X0", 3, ids);
        graph.createSynergyArc("S2", "X0", 7, ids);
        graph.createInfluenceArc("I1", "+", 3, 4);
        graph.createInfluenceArc("I2", "+", 4, 10);
        graph.createInfluenceArc("I3", "+", 4, 11);
        graph.createInfluenceArc("I4", "+", 11, 5);
        graph.createInfluenceArc("I5", "+", 2, 8);
        graph.createInfluenceArc("I7", "+", 1, 9);

        System.out.println(graph.toString());
    }
}
