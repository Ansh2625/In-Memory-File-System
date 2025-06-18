package commands;
import filesystem.FileSystem;
import filesystem.operations.CatOperation;

public class CatCommand implements Command
{
    private FileSystem fileSystem;
    private String fileName;
    
    // Ctor for initialisation 
    public CatCommand(FileSystem fileSystem, String fileName)
    {
        this.fileSystem = fileSystem;
        this.fileName = fileName;
    }

    @Override
    public void execute()
    {
        fileSystem.executeOperation(new CatOperation(fileName));    
    }
}
