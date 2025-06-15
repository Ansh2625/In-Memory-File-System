package commands;
import filesystem.FileSystem;

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
        fileSystem.cd(path);
    }    
}
