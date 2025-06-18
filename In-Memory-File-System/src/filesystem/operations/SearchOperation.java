package filesystem.operations;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import filesystem.core.FileSystemState;
import filesystem.helpers.Helper;
import node.Node;

// Search Command
public class SearchOperation implements FileSystemOperation
{
    private String pattern;

    public SearchOperation(String pattern)
    {
        this.pattern = pattern;
    }

    @Override
    public void execute(FileSystemState state)
    {
        Helper helper = new Helper(state);

        Set<Node> visited = new HashSet<>();
        dfsSearchContent(state.getCurrent(), pattern, visited, helper.getAbsolutePath(state.getCurrent()));
    }

    // DFS search
    public void dfsSearchContent(Node node, String pattern, Set<Node> visited, String pathSoFar) 
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
    public boolean kmpSearch(String text, String pattern) 
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
    public int[] buildLPS(String pattern) 
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
    
}
