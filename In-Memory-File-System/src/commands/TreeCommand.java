package commands;
import filesystem.FileSystem;
import filesystem.operations.TreeOperation;

public class TreeCommand implements Command
{
    private FileSystem fileSystem;    

    // Ctor for initialisation
    public TreeCommand(FileSystem fileSystem)
    {
        this.fileSystem = fileSystem;
    }

    @Override
    public void execute()
    {
        fileSystem.executeOperation(new TreeOperation());
    }
}
