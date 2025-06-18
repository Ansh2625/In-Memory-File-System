package filesystem.operations;

import filesystem.Action;
import filesystem.core.FileSystemState;
import node.Node;

// Redo Method
public class RedoOperation implements FileSystemOperation
{
    @Override
    public void execute(FileSystemState state)
    {
        if (state.getRedoStack().isEmpty())
        {
            System.out.println("Nothing to redo.");
            return;
        }

        Action action = state.getRedoStack().pop();
        String name = action.getName();

        switch(action.getType())
        {
            case TOUCH:
                if (!state.getCurrent().hasChild(name))
                {
                    Node file = new Node(name, true);
                    state.getCurrent().addChild(name, file);
                    state.getUndoStack().push(action);
                    System.out.println("Redo touch: " + name);
                }
                break;
            
            case MKDIR:
                FileSystemOperation op = new MkdirOperation(name);
                op.execute(state);
                break;

            case RM:
                state.getCurrent().removeChild(name);
                state.getUndoStack().push(action);
                System.out.println("Redo rm: " + name);
                break;

            case ECHO:
                Node echoFile = state.getCurrent().getChild(name);
                if (echoFile != null) echoFile.setContent(action.getContentBefore());
                state.getUndoStack().push(action);
                System.out.println("Redo echo: " + name);
                break;
        }
    }    
}
