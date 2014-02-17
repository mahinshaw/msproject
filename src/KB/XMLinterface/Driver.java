package KB.XMLinterface;

/**
 * User: Tshering Tobgay
 * Date: 7/11/13
 */
public class Driver {
    public static void main(String arg[]) {
        Interface dri = new Interface("src/XMLInput/GailSchema.xml");
        UserInterfaceReader reader = new UserInterfaceReader("src/XMLInput/GailSchema.xml");
        reader.readFile();
        for (ArgStruct.Node r : reader.getArg().getGeneralizationList()) {
            System.out.println(r.getText());
        }
    }
}
