package KB_Graph;

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

        graph.createPerson(1, "Mom", 'f');
        graph.createPerson(2, "Dad", 'm');
        graph.createPerson(3, "JB", 'm');
        graph.createPerson(4, "Brother", 'm');
        graph.createGenotype(1, 1, "1", "CF", "false", "autosomeType");
        graph.createGenotype(2, 2, "1", "CF", "false", "autosomeType");
        graph.createGenotype(3, 3, "1", "CF", "true", "autosomeType");
        graph.createGenotype(4, 4, "1", "CF", "true", "autosomeType");
        graph.createBiochemistry(5, 3, "2", "CF Protein", false, "2");
        graph.createBiochemistry(6, 4, "2", "CF Protein", false, "2");
        graph.createSymptom(7, 3, "3", "Fluid in Lungs", "severe");
        int[] ids = {1, 2};
        graph.createSynergyArc("X+", 3, ids);
        graph.createSynergyArc("X+", 4, ids);
        graph.createInfluenceArc("Y+", 3, 5);
        graph.createInfluenceArc("Y+", 3, 7);
        graph.createInfluenceArc("Y+", 4, 6);

        System.out.println(graph.toString());
    }
}