package filesystem.operations;

import filesystem.core.FileSystemState;
import node.Node;

// Print the content of a file
public class CatOperation implements FileSystemOperation
{
    private String fileName;

    public CatOperation(String fileName)
    {
        this.fileName = fileName;
    }

    @Override
    public void execute(FileSystemState state)
    {
        if(!state.getCurrent().hasChild(fileName)) // current folder should have the input file as children
        {
            System.out.println("No such file: " + fileName);
            return;
        }

        Node node = state.getCurrent().getChild(fileName); 

        if(!node.isFile()) // directory cannot have content
        {
            System.out.println(fileName + " is a Directory");
            return;
        }

        System.out.println(node.getContent()); // print the content of file
    }
}
