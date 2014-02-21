package GAIL.src.XMLHandler;

import java.util.ArrayList;

/**
 * User: Mark Hinshaw
 * Email: mahinshaw@gmail.com
 * Date: 7/19/13
 * github: https://github.com/mahinshaw/msproject
 *
 * The intent of this class is to be a hub for data passed into GAIL from the schema.  This structure will be used to load
 * questions into gail as well as node data specific to the questions and argument information.
 *
 */
public class StatementContainer {

    private final String node_id;
    private final String text;
    private final char argType;

    public StatementContainer(String node_id, String text, char argType) {
        this.node_id = node_id;
        this.text = text;
        this.argType = argType;
    }

    public String getNode_id() {
                             return node_id;
                                                             }

    public String getText() {
                          return text;
                                                    }

    public char getArgType() {
            return argType;
        }
}
