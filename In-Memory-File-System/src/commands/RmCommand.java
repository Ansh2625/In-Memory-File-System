package commands;
import filesystem.FileSystem;

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
        fileSystem.rm(name);
    }
}
