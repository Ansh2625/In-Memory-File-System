package filesystem.operations;

import filesystem.Action;
import filesystem.core.FileSystemState;
import node.Node;

// Remove a file
/*
    File from current directory can only be removed
    Empty Folder can be removed, Non empty cannot be        
*/
public class RmOperation implements FileSystemOperation
{
    private String name;
    
    public RmOperation(String name)
    {
        this.name = name;
    }

    @Override
    public void execute(FileSystemState state)
    {
        if(!state.getCurrent().hasChild(name)) // File to be removed not in current folder
        {
            System.out.println("No such file or folder: " + name);
            return;
        }

        Node node = state.getCurrent().getChild(name);

        if(!node.isFile() && !node.getChildren().isEmpty()) // If a Folder is not empty
        {
            System.out.println("Cannot remove non-empty folder: " + name);
            return;
        }

        // Remove
        state.getCurrent().removeChild(name);
        state.getUndoStack().push(new Action(Action.ActionType.RM, name, null, node));
        state.getRedoStack().clear();
        System.out.println(name + " removed successfully.");
    }
}
