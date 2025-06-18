package commands;
import filesystem.FileSystem;
import filesystem.operations.AutoCompleteOperation;

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
        fileSystem.executeOperation(new AutoCompleteOperation(prefix));
    }
}
