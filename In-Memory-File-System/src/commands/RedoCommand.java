package commands;
import filesystem.FileSystem;

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
        fileSystem.redo();
    }
}
