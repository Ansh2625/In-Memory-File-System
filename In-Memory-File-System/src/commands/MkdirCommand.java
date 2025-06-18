package commands;
import filesystem.FileSystem;
import filesystem.operations.MkdirOperation;

public class MkdirCommand implements Command
{
    private FileSystem fileSystem;
    private String path;

    // Ctor for initialisation
    public MkdirCommand(FileSystem fileSystem, String path)
    {
        this.fileSystem = fileSystem;
        this.path = path;
    }

    @Override
    public void execute()
    {
        fileSystem.executeOperation(new MkdirOperation(path));
    }
}
