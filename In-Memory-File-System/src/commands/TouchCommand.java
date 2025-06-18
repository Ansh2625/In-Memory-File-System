package commands;
import filesystem.FileSystem;
import filesystem.operations.TouchOperation;

public class TouchCommand implements Command
{
    private FileSystem fileSystem;
    private String fileName;

    public TouchCommand(FileSystem fileSystem, String fileName)
    {
        this.fileSystem = fileSystem;
        this.fileName = fileName;
    }

    @Override
    public void execute()
    {
        fileSystem.executeOperation(new TouchOperation(fileName));
    } 
}
