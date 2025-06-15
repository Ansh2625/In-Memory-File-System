package node;
import java.util.HashMap;
import java.util.Map;

public class Node
{
    // Name of the File/Folder
    private String name;

    // A Node can be a File(true) or a Folder(false)
    private boolean isFile;

    // Only File can have content, Folder cannot
    private String content;

    // Only Folder can have children, File cannot
    private Map<String,Node> children;

    // A symbolic link that can point to any node in the file system
    private Node symbolicLink;

    // Parent pointer
    private Node parent;

    
    // Ctor for initialisation
    public Node(String name, boolean isFile)
    {
        this.name = name;
        this.isFile = isFile;
        this.symbolicLink = null;
        this.parent = null;

        // File
        if(isFile)
        {
            this.content = ""; // may have content
            this.children = null; // no children 
        }
        else // Folder
        {
            this.content = null; // no content
            this.children = new HashMap<>(); // may have children
        }
    }


    // Get Name
    public String getName()
    {
        return this.name;
    }

    // Get File or Not
    public boolean isFile()
    {
        return this.isFile;
    }

    // Get content of the file
    public String getContent()
    {
        return this.content;
    }

    // Get all Childrens
    public Map<String,Node> getChildren()
    {
        return this.children;
    }

    // Get the Symbolic Link
    public Node getSymbolicLink()
    {
        return this.symbolicLink;
    }

    // get parent
    public Node getParent()
    {
        return this.parent;
    }

    // Set the content of a file
    public void setContent(String content)
    {
        if(isFile)
        {
            this.content = content;
        }
    }

    // Set the Symbolic Link
    public void setSymbolicLink(Node target)
    {
        this.symbolicLink = target;
    }

    // Set parent
    public void setParent(Node parent)
    {
        this.parent = parent;
    }

    // Add a children to a folder
    public void addChild(String name, Node node)
    {
        if(!isFile) // a Folder [File cannot have children]
        {
            children.put(name, node);
        }
    }

    // Get a specific child by name
    public Node getChild(String name)
    {
        return (isFile)? null : children.get(name); // File cannot have children
    }

    // Check if a child exists
    public boolean hasChild(String name)
    {
        return !isFile && children.containsKey(name);
    }

    // Remove a child
    public void removeChild(String name)
    {
        if(!isFile)
        {
            children.remove(name);
        }
    }
}