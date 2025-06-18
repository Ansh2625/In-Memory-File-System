package filesystem.helpers;

import filesystem.TrieNode;
import filesystem.core.FileSystemState;
import node.Node;

public class Helper 
{
    private FileSystemState state;

    public Helper(FileSystemState state)
    {
        this.state = state;
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
        Node temp = path.startsWith("/") ? state.getRoot() : state.getCurrent();
        String[] parts = path.split("/");

        for(String part : parts)
        {
            if(part.isEmpty() || part.equals("."))
                continue;

            if(part.equals(".."))
            {
                temp = temp.getParent();
                if(temp == null)
                    return null;
            }
            else
            {
                Node next = temp.getChild(part);
                if (next == null)
                {
                    System.out.println("Invalid directory: " + part);
                    return null;
                }
                temp = next;
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
