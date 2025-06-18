package filesystem.operations;

import java.util.Stack;

import filesystem.Action;
import filesystem.core.FileSystemState;
import filesystem.helpers.Helper;
import node.Node;

// Create a Directory
public class MkdirOperation implements FileSystemOperation
{
    private String path;

    public MkdirOperation(String path)
    {
        this.path = path;
    }

    @Override
    public void execute(FileSystemState state)
    {
        Helper helper = new Helper(state);
        // path can be given from root or any other directory
        Node temp = path.startsWith("/")? state.getRoot() : state.getCurrent();

        // Break path into folder names
        String[] parts = path.split("/");

        Stack<String> created = new Stack<>(); // track what we create

        for(String part : parts)
        {
            if(part.isEmpty()) // skip "", due to leading /
                continue;

            // if folder not exists create it and add it 
            if(!temp.hasChild(part))
            {
                Node folder = new Node(part, false); // it is a Folder
                folder.setParent(temp);
                temp.addChild(part, folder);
                helper.insertToTrie(part);
                created.push(part); // track for undo
            }

            // Move in to the next folder
            temp = temp.getChild(part);
        }

        if (!created.isEmpty())
        {
            state.getUndoStack().push(new Action(Action.ActionType.MKDIR, path));
            state.getRedoStack().clear();
        }
    }
}
