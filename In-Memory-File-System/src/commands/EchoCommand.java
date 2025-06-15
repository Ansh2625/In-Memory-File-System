package commands;
import filesystem.FileSystem;

public class EchoCommand implements Command
{
    private FileSystem fileSystem;
    private String content;
    private String fileName;
    private boolean append;

    // Ctor for initialisation
    public EchoCommand(FileSystem fileSystem, String content, String fileName, boolean append)
    {
        this.fileSystem = fileSystem;
        this.content = content;
        this.fileName = fileName;
        this.append = append;
    }

    @Override
    public void execute()
    {
        fileSystem.echo(content, fileName, append);
    }
}
