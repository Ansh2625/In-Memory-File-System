package commands;
import filesystem.FileSystem;

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
        fileSystem.tree();
    }
}
