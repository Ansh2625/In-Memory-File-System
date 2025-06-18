package commands;
import filesystem.FileSystem;
import filesystem.operations.SearchOperation;

public class SearchContentCommand implements Command
{
    private FileSystem fileSystem;
    private String pattern;

    // Ctor for initialisation 
    public SearchContentCommand(FileSystem fileSystem, String pattern)
    {
        this.fileSystem = fileSystem;
        this.pattern = pattern;
    }

    @Override
    public void execute()
    {
        fileSystem.executeOperation(new SearchOperation(pattern));
    }    
}
