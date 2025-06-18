package filesystem.operations;

import filesystem.core.FileSystemState;
import filesystem.helpers.Helper;
import node.Node;

// List all nodes in current Directory
/*
    If path is empty - list all items in current directory
    If path is a file - print the fileName only
    If path is a folder - print all its children names
*/
public class LsOperation implements FileSystemOperation
{
    private String path;

    public LsOperation(String path)
    {
        this.path = path;
    }    

    @Override
    public void execute(FileSystemState state)
    {
        Helper helper = new Helper(state);

        Node target;

        if(path==null || path.isEmpty())
        {
            target = state.getCurrent(); // list all items in current
        }
        else
        {
            target = helper.resolvePath(path); // to find the node user gave
            if(target == null) // Invalid path
            {
                System.out.println("Invalid Path: " + path);
                return;
            }
        }

        if(target.isFile()) // just print file name
        {
            System.out.println(target.getName());
        }
        else // folder
        {
            for(String childName : target.getChildren().keySet()) // print all its childrens
            {
                System.out.println(childName);
            }
        }
    }
    
}
