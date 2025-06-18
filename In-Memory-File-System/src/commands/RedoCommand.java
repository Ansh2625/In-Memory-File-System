package commands;
import filesystem.FileSystem;
import filesystem.operations.RedoOperation;

public class RedoCommand implements Command
{
    private FileSystem fileSystem;  
    
    // Ctor for initialisation
    public RedoCommand(FileSystem fileSystem)
    {
        this.fileSystem = fileSystem;
    }

    @Override
    public void execute()
    {
        fileSystem.executeOperation(new RedoOperation());
    }
}
