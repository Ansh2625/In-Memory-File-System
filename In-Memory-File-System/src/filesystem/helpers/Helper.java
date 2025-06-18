package filesystem.helpers;

import filesystem.TrieNode;
import filesystem.core.FileSystemState;
import node.Node;

public class Helper 
{
    private FileSystemState state;

    public Helper(FileSystemState state)
    {
        this.state = new FileSystemState();
    }
    
    // Helper to insert a word (file/folder name) into Trie
    public void insertToTrie(String word)
    {
        TrieNode node = state.getTrieRoot();

        for (char ch : word.toCharArray())
        {
            node.getChildren().putIfAbsent(ch, new TrieNode());
            node = node.getChildren().get(ch);
        }

        node.setEndOfWord(true);
    }    


    // Finds a node in the given path, handles both absolute and relative path
    public Node resolvePath(String path)
    {
        // path can start from root or any directory
        Node temp = path.startsWith("/")? state.getRoot() : state.getCurrent(); 

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


    // getAbsolutePath function
    public String getAbsolutePath(Node node) 
    {
        if (node == state.getRoot())
            return "";
        
        return getAbsolutePath(node.getParent()) + "/" + node.getName();
    }

}
