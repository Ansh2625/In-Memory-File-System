package commands;
import filesystem.FileSystem;

public class FindCommand implements Command
{
    private FileSystem fileSystem;
    private String pattern;

    // Ctor for initialisation 
    public FindCommand(FileSystem fileSystem, String pattern)
    {
        this.fileSystem = fileSystem;
        this.pattern = pattern;
    }

    @Override
    public void execute()
    {
        fileSystem.find(pattern);
    }
}
