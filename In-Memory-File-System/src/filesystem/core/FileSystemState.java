package filesystem.core;
import java.util.Stack;
import filesystem.Action;
import filesystem.TrieNode;
import node.Node;

public class FileSystemState 
{
    private Node root = new Node("/",false); // root node is a folder
    private Node current = root;
    private Stack<Action> undoStack = new Stack<>();
    private Stack<Action> redoStack = new Stack<>();
    private TrieNode trieRoot = new TrieNode();

    // Accessors

    public Node getRoot()
    {
        return this.root;
    }

    public Node getCurrent()
    {
        return this.current;
    }

    public void setCurrent(Node node)
    {
        this.current = node;
    }

    public Stack<Action> getUndoStack()
    {
        return this.undoStack;
    }

    public Stack<Action> getRedoStack()
    {
        return this.redoStack;
    }

    public TrieNode getTrieRoot()
    {
        return this.trieRoot;
    }
}
