package filesystem.operations;

import filesystem.core.FileSystemState;
import filesystem.helpers.Helper;
import node.Node;

// Print the current directory path from root
public class PwdOperation implements FileSystemOperation
{
    
    @Override
    public void execute(FileSystemState state)
    {
        Helper helper = new Helper(state);
        printPath(state,state.getCurrent());
    }

    // Helper function for pwd
    public void printPath(FileSystemState state, Node node)
    {
        if(node == state.getRoot()) // found current directory 
        {
            // if path equals "", it must be root so print /
            System.out.println("/");
        }
        else
        {
            printPath(state,node.getParent());
            System.out.println("/" + node.getName());
        }

        if(node == state.getCurrent())
            System.out.println();
    }
}
