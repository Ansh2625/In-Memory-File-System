package commands;

import filesystem.FileSystem;
import filesystem.operations.LnOperation;

public class LnCommand implements Command
{
    private FileSystem fileSystem;
    private String linkName;
    private String targetPath;
    
    // Ctor for initialisation
    public LnCommand(FileSystem fileSystem, String linkName, String targetPath)
    {
        this.fileSystem = fileSystem;
        this.linkName = linkName;
        this.targetPath = targetPath;
    }

    @Override
    public void execute()
    {
        fileSystem.executeOperation(new LnOperation(linkName, targetPath));
    }
}
