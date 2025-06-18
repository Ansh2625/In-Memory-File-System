package commands;
import filesystem.FileSystem;
import filesystem.operations.CdOperation;

public class CdCommand implements Command
{
    private FileSystem fileSystem;
    private String path;

    // Ctor for initialisation
    public CdCommand(FileSystem fileSystem, String path)
    {
        this.fileSystem = fileSystem;
        this.path = path;
    }

    @Override
    public void execute()
    {
        fileSystem.executeOperation(new CdOperation(path));
    }    
}
