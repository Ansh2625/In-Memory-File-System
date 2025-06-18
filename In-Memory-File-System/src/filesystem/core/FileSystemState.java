package filesystem.core;
import java.util.Stack;
import filesystem.Action;
import node.Node;

public class FileSystemState 
{
    public Node root = new Node("/",false); // root node is a folder
    public Node current = root;
    public Stack<Action> undoStack = new Stack<>();
    public Stack<Action> redoStack = new Stack<>();
}
