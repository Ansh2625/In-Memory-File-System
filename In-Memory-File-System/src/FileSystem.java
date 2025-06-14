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



    // Print the current directory path from root
    public void pwd()
    {
        printPath(root,current,"");
    }
    
    // Helper function for pwd
    public boolean printPath(Node node, Node target, String path)
    {
        if(node == target) // found current directory 
        {
            // if path equals "", it must be root so print /
            System.out.println(path.equals("")? "/" : path);
            return true;
        }

        if(node.isFile()) // Only folders are printed in pwd
            return false;

        for(Map.Entry<String,Node> entry : node.getChildren().entrySet())
        {
            // path can empty initially, if not append to it
            String newPath = path.equals("")? "/" + entry.getKey() : path + "/" + entry.getKey();

            // Recursively print append further nodes till get target
            if(printPath(entry.getValue(), target, newPath))
                return true;
        }

        return false;
    }



    // Create a file in current directory [Hashmap used for constant lookup]
    public void touch(String fileName)
    {
        // if file already exists 
        if(current.hasChild(fileName))
        {
            System.out.println("File already exists: " + fileName);
            return;
        }

        // else, create it and as child
        Node file = new Node(fileName, true); // Is a file
        current.addChild(fileName, file);
    }


    
    // List all nodes in current Directory
    /*
        If path is empty - list all items in current directory
        If path is a file - print the fileName only
        If path is a folder - print all its children names
    */
    public void ls(String path)
    {
        Node target;

        if(path==null || path.isEmpty())
        {
            target = current; // list all items in current
        }
        else
        {
            target = resolvePath(path); // to find the node user gave
            if(target == null) // Invalid path
            {
                System.out.println("Invalid Path: " + path);
                return;
            }
        }

        if(target.isFile()) // just print file name
        {
            System.out.println(target.getName());
        }
        else // folder
        {
            for(String childName : target.getChildren().keySet()) // print all its childrens
            {
                System.out.println(childName);
            }
        }
    }



    // Write into a file [overwrite(>) or append(>>)]
    public void echo(String content, String fileName, boolean append)
    {
        if(current.hasChild(fileName)) // existing file
        {
            Node fileNode = current.getChild(fileName);

            if(!fileNode.isFile()) // cannot write in a folder
            {
                System.out.println(fileName + " is a Directory");
                return;
            }

            if(append)
            {
                fileNode.setContent(fileNode.getContent() + content); // append
            }
            else
            {
                fileNode.setContent(content); // overwrite
            }
        }
        else // File not exists, so create it and add content
        {
            Node newFile = new Node(fileName,true); // is a file
            newFile.setContent(content);
            current.addChild(fileName, newFile);
        }
    }



    // Print the content of a file
    public void cat(String fileName)
    {
        if(!current.hasChild(fileName)) // current folder should have the input file as children
        {
            System.out.println("No such file: " + fileName);
            return;
        }

        Node node = current.getChild(fileName); 

        if(!node.isFile()) // directory cannot have content
        {
            System.out.println(fileName + " is a Directory");
            return;
        }

        System.out.println(node.getContent()); // print the content of file
    }


    
    // Remove a file
    /*
        File from current directory can only be removed
        Empty Folder can be removed, Non empty cannot be        
    */
    public void rm(String name)
    {
        if(!current.hasChild(name)) // File to be removed not in current folder
        {
            System.out.println("No such file or folder: " + name);
            return;
        }

        Node node = current.getChild(name);

        if(!node.isFile() && !node.getChildren().isEmpty()) // If a Folder is not empty
        {
            System.out.println("Cannot remove non-empty folder: " + name);
            return;
        }

        // Remove
        current.removeChild(name);
        System.out.println(name + " removed successfully.");
    }



    // Print the tree like structure from the current folder
    public void tree()
    {
        printTree(current,0); // 0 indicates initial depth to be 0
    }

    // Helper for tree command, recursively with indendation
    public void printTree(Node node, int depth)
    {
        // Indendation according to depth
        for(int i=0; i<depth; i++)
            System.out.println("    "); // 4 sapces per level

        // Print the node name
        System.out.println(node.getName());

        // If node is a Folder then recurse into its childrens
        if(!node.isFile())
        {
            for(Node child : node.getChildren().values())
                printTree(child, depth + 1);
        }
    }

}
