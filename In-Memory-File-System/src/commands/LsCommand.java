package commands;
import filesystem.FileSystem;

public class LsCommand implements Command
{
    private FileSystem fileSystem;
    private String path;    

    // Ctor for initialisation
    public LsCommand(FileSystem fileSystem, String path)
    {
        this.fileSystem = fileSystem;
        this.path = path;
    }

    @Override
    public void execute()
    {
        fileSystem.ls(path);
    }  
}
