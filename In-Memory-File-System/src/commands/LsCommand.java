package commands;
import filesystem.FileSystem;
import filesystem.operations.LsOperation;

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
        fileSystem.executeOperation(new LsOperation(path));
    }  
}
