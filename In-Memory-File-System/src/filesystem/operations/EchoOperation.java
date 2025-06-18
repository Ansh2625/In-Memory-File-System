package filesystem.operations;

import filesystem.Action;
import filesystem.core.FileSystemState;
import node.Node;

// Write into a file [overwrite(>) or append(>>)]
public class EchoOperation implements FileSystemOperation
{
    private String content;
    private String fileName;
    private boolean append;  
    
    public EchoOperation(String content, String fileName, boolean append)
    {
        this.content = content;
        this.fileName = fileName;
        this.append = append;
    }   

    public void execute(FileSystemState state)
    {
        Node file = state.getCurrent().getChild(fileName);
        String oldContent = "";

        if (file == null) {
            file = new Node(fileName, true); // create new file
            state.getCurrent().addChild(fileName, file);
        }

        if (!file.isFile()) {
            System.out.println(fileName + " is not a file.");
            return;
        }

        oldContent = file.getContent(); // before writing

        if (append)
            file.setContent(oldContent + content);
        else
            file.setContent(content);

        // Push action to undo stack (with full constructor)
        state.getUndoStack().push(new Action(Action.ActionType.ECHO, fileName, oldContent, null));
        state.getRedoStack().clear();
    }
}
