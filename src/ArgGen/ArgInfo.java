package ArgGen;

import KB.KB_Arc.KB_Arc;
import KB.KB_Node.KB_Node;

import java.util.ArrayList;

/**
 * User: Tshering Tobgay
 * Date: 10/24/13
 */
public class ArgInfo {

    public enum TYPE {
        HYPO('H'), GEN('G'), DATA('D');
        private char type;

        TYPE(char d) {
            type = d;
        }

        public char getType() {
            return type;
        }

        /**
         * Check to see if the edge type is the influence arc
         *
         * @return true if influence arc exits between two nodes
         */
        private boolean checkArcType(KB_Node parent, KB_Node child) {
            boolean influence = false;
            int i = 0, n = 0;
            KB_Arc arc = null;

            if (parent.getChildren().contains(child)) {
                for (KB_Node m : parent.getChildren()) {
                    if (m.getId() == child.getId()) {
                        n = i;
                    }
                    i++;
                }
                //str = parent.getArcs().get(n).getEdge_id();
                arc = parent.getArcs().get(n);
                if (arc.getType().equalsIgnoreCase("+")) {
                    influence = true;
                }
            }
            return influence;
        }

        /**
         * Returns the ArcID between two nodes
         *
         * @param parent
         * @param child
         * @return
         */
        public KB_Arc findEdgeID(KB_Node parent, KB_Node child) {
            String str = " ";
            int i = 0, n = 0;
            KB_Arc arc = null;

            if (parent.getChildren().contains(child)) {
                for (KB_Node m : parent.getChildren()) {
                    if (m.getId() == child.getId()) {
                        n = i;
                    }
                    i++;
                }
                //str = parent.getArcs().get(n).getEdge_id();
                arc = parent.getArcs().get(n);

            } else {
                System.out.println("No ID found.");
            }
            return arc;
        }

        /**
         * Check condition:
         * No two hypothesis should exist in an E2C arg scheme
         *
         * @param allPaths
         * @return
         */
        private ArrayList<ArrayList<KB_Node>> getE2C(ArrayList<ArrayList<KB_Node>> allPaths) {
            int i = 0;
            ArrayList<ArrayList<KB_Node>> tempList = new ArrayList<ArrayList<KB_Node>>();

            for (ArrayList<KB_Node> n : allPaths) {
                for (KB_Node k : n) {
                    if (k.getType() == 'H') {
                        i++;
                    }
                }
                /**
                 * If there are more than one hypothesis in one argument path,
                 * it is not E2C argument
                 */
                if (i == 1) {
                    tempList.add(n);
                }
                i = 0;
            }
            return tempList;
        }
    }
}
