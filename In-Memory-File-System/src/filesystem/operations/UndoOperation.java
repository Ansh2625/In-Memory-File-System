package filesystem.operations;

import filesystem.Action;
import filesystem.core.FileSystemState;
import filesystem.helpers.Helper;
import node.Node;

// Undo method
public class UndoOperation implements FileSystemOperation
{
    @Override
    public void execute(FileSystemState state)
    {
        Helper helper = new Helper(state);

        if (state.getUndoStack().isEmpty())
        {
            System.out.println("Nothing to undo.");
            return;
        }

        Action action = state.getUndoStack().pop();
        String name = action.getName();

        switch(action.getType())
        {
            case TOUCH:
                Node touchedFile = state.getCurrent().getChild(name);
                if (touchedFile != null && touchedFile.isFile())
                {
                    state.getCurrent().removeChild(name);
                    state.getRedoStack().push(action);
                    System.out.println("Undo touch: " + name);
                }
                break;

            case MKDIR:
                Node dir = helper.resolvePath(name);
                if (dir != null && !dir.isFile() && dir.getChildren().isEmpty()) {
                    dir.getParent().removeChild(dir.getName());
                    state.getRedoStack().push(action);
                    System.out.println("Undo mkdir: " + name);
                }
                break;

            case RM:
                state.getCurrent().addChild(name, action.getNodeSnapshot());
                state.getRedoStack().push(action);
                System.out.println("Undo rm: " + name);
                break;

            case ECHO:
                Node file = state.getCurrent().getChild(name);
                if (file != null) file.setContent(action.getContentBefore());
                state.getRedoStack().push(action);
                System.out.println("Undo echo: " + name);
                break;
        }
    }    

}
