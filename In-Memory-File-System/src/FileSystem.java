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
    public void mkdir(String path){}

    // Change Directory
    public void cd(String path){}

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
