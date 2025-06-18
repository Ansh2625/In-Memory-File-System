package commands;
import filesystem.FileSystem;
import filesystem.operations.RmOperation;

public class RmCommand implements Command
{
    private FileSystem fileSystem;    
    private String name;

    // Ctor for initialisation
    public RmCommand(FileSystem fileSystem, String name)
    {
        this.fileSystem = fileSystem;
        this.name = name;
    }

    @Override
    public void execute()
    {
        fileSystem.executeOperation(new RmOperation(name));
    }
}
