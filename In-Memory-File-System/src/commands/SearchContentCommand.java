package commands;
import filesystem.FileSystem;

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
        fileSystem.searchContent(pattern);
    }    
}
