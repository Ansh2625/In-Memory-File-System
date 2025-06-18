package filesystem.operations;

import filesystem.core.FileSystemState;
import filesystem.helpers.Helper;
import node.Node;

// Link - creates a shortcut (symbolic link) to another file or folder.
public class LnOperation implements FileSystemOperation
{
    private String linkName;
    private String targetPath;

    public LnOperation(String linkName, String targetPath)
    {
        this.linkName = linkName;
        this.targetPath = targetPath;
    }

    @Override
    public void execute(FileSystemState state)
    {
        Helper helper = new Helper(state);

        // Find the node 
        Node target = helper.resolvePath(targetPath);

        if(target == null) // no such folder exists 
        {
            System.out.println("Invalid target path: " + targetPath);
            return;
        }

        if(state.getCurrent().hasChild(linkName)) // if current folder already has it
        {
            System.out.println("A file/folder with name " + linkName + " already exists");
            return;
        }

        Node link = new Node(linkName, target.isFile()); // mimic it
        link.setSymbolicLink(target);
        state.getCurrent().addChild(linkName, link); // add it as child in current folder

        System.out.println("Symbolic link " + linkName + " created to " + targetPath);
    }
}
