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

    public Node resolvePath(String path)
    {
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
