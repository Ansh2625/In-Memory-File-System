package commands;
import filesystem.FileSystem;
import filesystem.operations.PwdOperation;

public class PwdCommand implements Command
{
    private FileSystem fileSystem;

    // Ctor for initialisation
    public PwdCommand(FileSystem fileSystem)
    {
        this.fileSystem = fileSystem;
    }
    
    @Override
    public void execute()
    {
        fileSystem.executeOperation(new PwdOperation());
    } 
}
