package filesystem.operations;

import java.util.Map;

import filesystem.TrieNode;
import filesystem.core.FileSystemState;

// Autocomplete method using Trie
public class AutoCompleteOperation implements FileSystemOperation
{
    private String prefix;
    
    public AutoCompleteOperation(String prefix)
    {
        this.prefix = prefix;
    }

    @Override
    public void execute(FileSystemState state)
    {
        TrieNode node = state.getTrieRoot();

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
}
