package commands;
import filesystem.FileSystem;

public class AutoCompleteCommand implements Command
{
    private FileSystem fileSystem;
    private String prefix;

    public AutoCompleteCommand(FileSystem fileSystem, String prefix)
    {
        this.fileSystem = fileSystem;
        this.prefix = prefix;
    }

    @Override
    public void execute()
    {
        fileSystem.autocomplete(prefix);
    }
}
