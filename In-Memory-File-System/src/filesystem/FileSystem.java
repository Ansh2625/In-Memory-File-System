package filesystem;

import java.util.Stack;

import filesystem.core.FileSystemState;
import filesystem.operations.FileSystemOperation;
import node.Node;

public class FileSystem 
{
    private final FileSystemState state;
    private FileSystemOperation operation;


    // Ctor for initialisation
    public FileSystem() 
    {
        this.state = new FileSystemState();
    }


    // Accessors
    public Node getRoot()
    {
        return state.getRoot();
    }

    public Node getCurrent()
    {
        return state.getCurrent();
    }

    public void setCurrent(Node node)
    {
        state.setCurrent(node);
    }

    public Stack<Action> getUndoStack()
    {
        return state.getUndoStack();
    }

    public Stack<Action> getRedoStack()
    {
        return state.getRedoStack();
    }

    public TrieNode getTrieRoot()
    {
        return state.getTrieRoot();
    }


    // Execute operation
    public void executeOperation(FileSystemOperation operation)
    {
        operation.execute(state);
    }

}
