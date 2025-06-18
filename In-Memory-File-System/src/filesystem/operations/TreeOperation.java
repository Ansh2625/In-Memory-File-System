package filesystem.operations;

import java.util.HashSet;
import java.util.Set;

import filesystem.core.FileSystemState;
import node.Node;

// Print the tree like structure from the current folder
public class TreeOperation implements FileSystemOperation
{
    @Override
    public void execute(FileSystemState state)
    {

        Set<Node> visited = new HashSet<>(); // to avoid infinite cycle loop
        printTree(state.getCurrent(),0,visited); // 0 indicates initial depth to be 0
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
    
}
