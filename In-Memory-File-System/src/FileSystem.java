import java.util.Map;

public class FileSystem 
{
    // Root Node
    private Node root;

    // Current Node
    private Node current;



    // Ctor for initialisation
    public FileSystem()
    {
        this.root = new Node("/", false); // root is a Folder
        this.current = root; // Starting from root node
    }



    // Create a Directory
    public void mkdir(String path)
    {
        // path can be given from root or any other directory
        Node temp = path.startsWith("/")? root : current;

        // Break path into folder names
        String[] parts = path.split("/");

        for(String part : parts)
        {
            if(part.isEmpty()) // skip "", due to leading /
                continue;

            // if folder not exists create it and add it 
            if(!temp.hasChild(part))
            {
                Node folder = new Node(part, false); // it is a Folder
                temp.addChild(part, folder);
            }

            // Move in to the next folder
            temp = temp.getChild(part);
        }
    }



    // Change Directory
    /*
        It can be
        cd /a/b  - Absolute path (move from root)
        cd b  - Relative path (move from current)
        cd ..  - Move to parent directory
        cd /  - Move to root 
    */
    public void cd(String path)
    {
        Node target = resolvePath(path); // helper method to get the folder

        if(target==null || target.isFile())
        {
            System.out.println("Invalid directory: " + path);
            return;
        }

        current = target; // Move the current pointer
    }

    // Finds a node in the given path, handles both absolute and relative path
    public Node resolvePath(String path)
    {
        // path can start from root or any directory
        Node temp = path.startsWith("/")? root : current; 

        // Split the path into folder names
        String[] parts = path.split("/");

        for(String part : parts)
        {
            // ignore "" made via leading /
            // ignore . , as we need .. for parent directory
            if(part.isEmpty() || part.equals("."))
                continue;

            if(part.equals(".."))
            {
                // As we do not store parent reference, we need helper to get it
                temp = findParent(root,temp);

                if(temp == null) // no parent
                    return null;
            }
            else
            {
                if(!temp.hasChild(part)) // if no child
                    return null;
                temp = temp.getChild(part); // Move to next folder
            }
        }

        return temp;
    }

    // Helper function to get parent for a target node [DFS in Tree]
    public Node findParent(Node root, Node target)
    {
        // a file cannot be a parent
        if(root==null || root.isFile())
            return null;

        // Go to each child of current root
        for(Map.Entry<String,Node> entry : root.getChildren().entrySet())
        {
            // if any child of root is same object as target, we found it
            if(entry.getValue() == target)
                return root;

            // Recursively find the target
            Node found = findParent(entry.getValue(), target);
            
            if(found != null)
                return found;
        }

        return null;
    }


    
    // List all the Folders and File in current Directory
    public void ls(String path){}

    // Print the current directory path from root
    public void pwd(){}

    // Create a file in current directory
    public void touch(String fileName){}

    // Write into a file
    public void echo(String fileName){}

    // Print the content of a file
    public void cat(String fileName){}

    // Remove a file
    public void remove(String name){}

    // Print the tree structure of all folders
    public void tree(){}

}
