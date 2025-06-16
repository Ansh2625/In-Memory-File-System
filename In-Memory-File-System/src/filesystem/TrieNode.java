package filesystem;
import java.util.HashMap;
import java.util.Map;

public class TrieNode 
{
    private Map<Character,TrieNode> children = new HashMap<>();
    private boolean isEndOfWord = false;

    public TrieNode() 
    {
        this.children = new HashMap<>();
        this.isEndOfWord = false;
    }

    public Map<Character, TrieNode> getChildren() 
    {
        return children;
    }

    public boolean isEndOfWord() 
    {
        return isEndOfWord;
    }

    public void setEndOfWord(boolean isEndOfWord) 
    {
        this.isEndOfWord = isEndOfWord;
    }
}