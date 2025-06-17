package commands;
import filesystem.FileSystem;

public class UndoCommand implements Command
{
    private FileSystem fileSystem;  
    
    // Ctor for initialisation
    public UndoCommand(FileSystem fileSystem)
    {
        this.fileSystem = fileSystem;
    }

    @Override
    public void execute()
    {
        fileSystem.undo();
    }
}
