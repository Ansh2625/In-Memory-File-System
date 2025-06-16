package filesystem;
import node.Node;

public class Action 
{
    public enum ActionType {TOUCH, MKDIR, RM, ECHO}    

    private ActionType type;
    private String name;
    private String contentBefore; // for echo overwrite/append
    private Node nodeSnapshot; // for restoring deleted node

    // Ctor for initialisation
    private Action(ActionType type, String name)
    {
        this.type = type;
        this.name = name;
    }

    public Action(ActionType type, String name, String contentBefore, Node nodeSnapshot)
    {
        this.type = type;
        this.name = name;
        this.contentBefore = contentBefore;
        this.nodeSnapshot = nodeSnapshot;
    }

     // Getters
    public ActionType getType()
    {
        return type; 
    }

    public String getName() 
    {
        return name;
    }

    public String getContentBefore() 
    { 
        return contentBefore; 
    }

    public Node getNodeSnapshot()
    { 
        return nodeSnapshot; 
    }
}
