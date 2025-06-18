package filesystem.operations;

import filesystem.Action;
import filesystem.core.FileSystemState;
import filesystem.helpers.Helper;
import node.Node;

// Create a file in current directory [Hashmap used for constant lookup]
public class TouchOperation implements FileSystemOperation
{
    private String fileName;
    
    public TouchOperation(String fileName)
    {
        this.fileName = fileName;
    }

    @Override
    public void execute(FileSystemState state)
    {
        Helper helper = new Helper(state);

        // if file already exists  
        if(state.getCurrent().hasChild(fileName))
        {
            System.out.println("File already exists: " + fileName);
            return;
        }

        // else, create it and as child
        Node file = new Node(fileName, true); // Is a file
        helper.insertToTrie(fileName);
        state.getCurrent().addChild(fileName, file);

        // Push to undo stack
        state.getUndoStack().push(new Action(Action.ActionType.TOUCH, fileName));
        state.getRedoStack().clear(); // Clear redo stack on new action
    }
}
