package filesystem.operations;

import filesystem.core.FileSystemState;
import filesystem.helpers.Helper;
import node.Node;

// Change Directory
/*
    It can be
    cd /a/b  - Absolute path (move from root)
    cd b  - Relative path (move from current)
    cd ..  - Move to parent directory
    cd /  - Move to root 
*/
public class CdOperation implements FileSystemOperation
{
    private String path;

    public CdOperation(String path)
    {
        this.path = path;
    }

    @Override
    public void execute(FileSystemState state)
    {
        Helper helper = new Helper(state);
        Node target = helper.resolvePath(path); // helper method to get the folder

        if(target==null || target.isFile())
        {
            System.out.println("Invalid directory: " + path);
            return;
        }

        state.setCurrent(target); // Move the current pointer
    }
}
