package XMLinterface;

/**
 * User: Tshering Tobgay
 * Date: 7/11/13
 */
public class Driver {
    public static void main(String arg[]){
         Interface dri = new Interface("KB/src/GailSchema.xml");

       System.out.print("TEST: "+dri.getGraph().getNodelist().get(9));
    }
}
