package filesystem;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import node.Node;

public class FileSystem 
{
    // Root Node
    private Node root;

    // Current Node
    private Node current;

    // Trie root for autocomplete
    private TrieNode trieRoot;

    // Stack for undo
    private Stack<Action> undoStack = new Stack<>();

    // Stack for redo
    private Stack<Action> redoStack = new Stack<>();


    // Ctor for initialisation
    public FileSystem()
    {
        this.root = new Node("/", false); // root is a Folder
        this.current = root; // Starting from root node
        this.trieRoot = new TrieNode(); // Initialise Trie root
    }



    // Create a Directory
    public void mkdir(String path)
    {
        // path can be given from root or any other directory
        Node temp = path.startsWith("/")? root : current;

        // Break path into folder names
        String[] parts = path.split("/");

        Stack<String> created = new Stack<>(); // track what we create

        for(String part : parts)
        {
            if(part.isEmpty()) // skip "", due to leading /
                continue;

            // if folder not exists create it and add it 
            if(!temp.hasChild(part))
            {
                Node folder = new Node(part, false); // it is a Folder
                folder.setParent(temp);
                temp.addChild(part, folder);
                insertToTrie(part);
                created.push(part); // track for undo
            }

            // Move in to the next folder
            temp = temp.getChild(part);
        }

        if (!created.isEmpty())
        {
            undoStack.push(new Action(Action.ActionType.MKDIR, path));
            redoStack.clear();
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
                // We have parent reference
                temp = temp.getParent();

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



    // Print the current directory path from root
    public void pwd()
    {
        printPath(current);
    }
    
    // Helper function for pwd
    public void printPath(Node node)
    {
        if(node == root) // found current directory 
        {
            // if path equals "", it must be root so print /
            System.out.println("/");
        }
        else
        {
            printPath(node.getParent());
            System.out.println("/" + node.getName());
        }

        if(node == current)
            System.out.println();
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
        insertToTrie(fileName);
        current.addChild(fileName, file);

        // Push to undo stack
        undoStack.push(new Action(Action.ActionType.TOUCH, fileName));
        redoStack.clear(); // Clear redo stack on new action
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
        String beforeContent = "";
        if(current.hasChild(fileName)) // existing file
        {
            Node fileNode = current.getChild(fileName);

            if(!fileNode.isFile()) // cannot write in a folder
            {
                System.out.println(fileName + " is a Directory");
                return;
            }

            beforeContent = fileNode.getContent();

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

        undoStack.push(new Action(Action.ActionType.ECHO, fileName, beforeContent, null));
        redoStack.clear();
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
        undoStack.push(new Action(Action.ActionType.RM, name, null, node));
        redoStack.clear();
        System.out.println(name + " removed successfully.");
    }



    // Print the tree like structure from the current folder
    public void tree()
    {
        Set<Node> visited = new HashSet<>(); // to avoid infinite cycle loop
        printTree(current,0,visited); // 0 indicates initial depth to be 0
    }

    // Helper for tree command, recursively with indendation
    public void printTree(Node node, int depth, Set<Node> visited)
    {
        // Detect cycle using visited set
        if(visited.contains(node))
        {
            System.out.println("    ".repeat(depth) + node.getName() + " SymbolicLink loop detected");
            return;
        }

        visited.add(node);

        // Indendation according to depth
        System.out.println("    ".repeat(depth) + node.getName());

        // If node is a Folder then recurse into its childrens
        if(!node.isFile())
        {
            for(Node child : node.getChildren().values())
            {
                // If child is a symbolic link, follow the target
                Node target = (child.getSymbolicLink() != null) ? child.getSymbolicLink() : child;

                // recursive call
                printTree(child, depth + 1, visited);
            }
        }
    }



    // Link - creates a shortcut (symbolic link) to another file or folder.
    public void ln(String linkName, String targetPath)
    {
        // Find the node 
        Node target = resolvePath(targetPath);

        if(target == null) // no such folder exists 
        {
            System.out.println("Invalid target path: " + targetPath);
            return;
        }

        if(current.hasChild(linkName)) // if current folder already has it
        {
            System.out.println("A file/folder with name " + linkName + " already exists");
            return;
        }

        Node link = new Node(linkName, target.isFile()); // mimic it
        link.setSymbolicLink(target);
        current.addChild(linkName, link); // add it as child in current folder

        System.out.println("Symbolic link " + linkName + " created to " + targetPath);
    }



    // Find command - to find a file or folder in current directory [Pattern Matching]
    public void find(String pattern)
    {
        Set<Node> visited = new HashSet<>(); // to avoid infinite loops
        dfsFind(current,pattern,visited,getAbsolutePath(current));
    }

    // dfsFind function
    private void dfsFind(Node node, String pattern, Set<Node> visited, String pathSoFar)
    {
        if (visited.contains(node)) return;
        visited.add(node);

        String fullPath = pathSoFar.equals("/") ? "/" + node.getName() : pathSoFar + "/" + node.getName();

        // Match current node
        if (match(node.getName(), pattern)) 
            System.out.println(fullPath);

        // If its a folder, go deeper
        if(!node.isFile())
        {
            for (Map.Entry<String, Node> entry : node.getChildren().entrySet())
            {
                Node child = entry.getValue();
                Node target = (child.getSymbolicLink() != null) ? child.getSymbolicLink() : child;
                dfsFind(target, pattern, visited, fullPath);
            }
        }
    }

    // Wildcard pattern match: * means any sequence
    private boolean match(String text, String pattern) 
    {
        return matchHelper(text, pattern, 0, 0);
    }

    // Match helper function
    private boolean matchHelper(String text, String pattern, int i, int j)
    {
        if (i == text.length() && j == pattern.length())
            return true;

        if (j == pattern.length())
             return false;

        if (pattern.charAt(j) == '*') 
        {
             // Match zero or more characters: two recursive choices
            return (i < text.length() && matchHelper(text, pattern, i + 1, j)) || matchHelper(text, pattern, i, j + 1);
        }
        else if (i < text.length() && (pattern.charAt(j) == text.charAt(i)))
        {
            // Match current character
            return matchHelper(text, pattern, i + 1, j + 1);
        }

        return false;
    }

    // getAbsolutePath function
    private String getAbsolutePath(Node node) 
    {
        if (node == root)
            return "";
        
        return getAbsolutePath(node.getParent()) + "/" + node.getName();
    }



    // Search Command
    public void searchContent(String pattern) 
    {
        Set<Node> visited = new HashSet<>();
        dfsSearchContent(current, pattern, visited, getAbsolutePath(current));
    }

    // DFS search
    private void dfsSearchContent(Node node, String pattern, Set<Node> visited, String pathSoFar) 
    {
        if (visited.contains(node)) return;
        visited.add(node);

        String fullPath = pathSoFar.equals("/") ? "/" + node.getName() : pathSoFar + "/" + node.getName();

        if (node.isFile()) 
        {
            if (kmpSearch(node.getContent(), pattern))
                System.out.println(fullPath);
        } 
        else 
        {
            for (Map.Entry<String, Node> entry : node.getChildren().entrySet())
            {
                Node child = entry.getValue();
                Node target = (child.getSymbolicLink() != null) ? child.getSymbolicLink() : child;
                dfsSearchContent(target, pattern, visited, fullPath);
            }
        }
    }

    // Kmp Search Algorithm
    private boolean kmpSearch(String text, String pattern) 
    {
        if (pattern == null || pattern.isEmpty()) return true;
        if (text == null || text.isEmpty()) return false;

        int[] lps = buildLPS(pattern);
        int i = 0, j = 0;

        while (i < text.length())
        {
            if (text.charAt(i) == pattern.charAt(j))
            {
                i++;
                j++;
                if (j == pattern.length())
                    return true;
            } 
            else 
            {
                if (j != 0)
                    j = lps[j - 1];
                else
                    i++;
            }
        }

        return false;
    }

    // helper to build LPS 
    private int[] buildLPS(String pattern) 
    {
        int[] lps = new int[pattern.length()];
        int len = 0;
        int i = 1;

        while (i < pattern.length())
        {
            if (pattern.charAt(i) == pattern.charAt(len))
            {
                len++;
                lps[i] = len;
                i++;
            } 
            else 
            {
                if (len != 0) 
                {
                    len = lps[len - 1];
                } else 
                {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }



    // Autocomplete method using Trie
    public void autocomplete(String prefix)
    {
        TrieNode node = trieRoot;

        // Traverse to the node matching the prefix
        for (char ch : prefix.toCharArray())
        {
            if (!node.getChildren().containsKey(ch))
            {
                System.out.println("No suggestions found.");
                return;
            }
            node = node.getChildren().get(ch);
        }

        // DFS from the prefix node
        dfsTrie(node, prefix);
    }

    // DFS helper to collect suggestions from the trie
    private void dfsTrie(TrieNode node, String word)
    {
        if (node.isEndOfWord())
        {
            System.out.println(word);
        }

        for (Map.Entry<Character, TrieNode> entry : node.getChildren().entrySet())
        {
            dfsTrie(entry.getValue(), word + entry.getKey());
        }
    }



    // Helper to insert a word (file/folder name) into Trie
    private void insertToTrie(String word)
    {
        TrieNode node = trieRoot;

        for (char ch : word.toCharArray())
        {
            node.getChildren().putIfAbsent(ch, new TrieNode());
            node = node.getChildren().get(ch);
        }

        node.setEndOfWord(true);
    }



    // Undo method
    public void undo()
    {
        if (undoStack.isEmpty())
        {
            System.out.println("Nothing to undo.");
            return;
        }

        Action action = undoStack.pop();
        String name = action.getName();

        switch(action.getType())
        {
            case TOUCH:
                Node touchedFile = current.getChild(name);
                if (touchedFile != null && touchedFile.isFile())
                {
                    current.removeChild(name);
                    redoStack.push(action);
                    System.out.println("Undo touch: " + name);
                }
                break;

            // others
        }
    }



    // Redo Method
    public void redo()
    {
        if (redoStack.isEmpty())
        {
            System.out.println("Nothing to redo.");
            return;
        }

        Action action = redoStack.pop();
        String name = action.getName();

        switch(action.getType())
        {
            case TOUCH:
                if (!current.hasChild(name))
                {
                    Node file = new Node(name, true);
                    current.addChild(name, file);
                    undoStack.push(action);
                    System.out.println("Redo touch: " + name);
                }
                break;
            
            // others
        }
    }
}
