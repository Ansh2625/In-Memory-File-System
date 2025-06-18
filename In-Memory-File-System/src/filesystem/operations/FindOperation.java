package filesystem.operations;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import filesystem.core.FileSystemState;
import filesystem.helpers.Helper;
import node.Node;

// Find command - to find a file or folder in current directory [Pattern Matching]
public class FindOperation implements FileSystemOperation
{
    private String pattern;

    public FindOperation(String pattern)
    {
        this.pattern = pattern;
    }

    @Override
    public void execute(FileSystemState state)
    {
        Helper helper = new Helper(state);

        Set<Node> visited = new HashSet<>(); // to avoid infinite loops
        dfsFind(state.getCurrent(), pattern, visited, helper.getAbsolutePath(state.getCurrent()));
    }

    // dfsFind function
    public void dfsFind(Node node, String pattern, Set<Node> visited, String pathSoFar)
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
    public boolean match(String text, String pattern) 
    {
        return matchHelper(text, pattern, 0, 0);
    }

     // Match helper function
    public boolean matchHelper(String text, String pattern, int i, int j)
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
}
