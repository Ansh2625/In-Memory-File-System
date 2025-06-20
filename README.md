# In-Memory File System <br>
A console-based in-memory file system built using core Data Structures like Tree, Trie, HashMap and Algorithms like String Parsing, Recursion, DFS. <br>
Simulates Unix-like file operations such as `mkdir`, `cd`, `ls`, `touch`, etc. **Without using the actual file system**.<br><br>

## Problem Statement <br>
Design and implement a file system that: <br>
- Maintains a virtual folder/file structure in memory. <br>
- Allows users to create, delete, and navigate directories. <br>
- Stores file metadata and simulates basic content handling. <br>
- Lookup and Traversal should be fast. <br>
- Allows Unix-Like file operations such as `mkdir`, `cd`, `cat`, `echo`. <br>
- Built using OOP principle. <br><br>

## Project Structure <br>

### commandinvoker pacakage <br>
- `CommandInvoker.java`: Implements the invoker part of the Command Pattern. It stores and executes commands provided by the user interface (like mkdir, cd, ls, etc.). <br>

### commands packge <br>
- `Command.java`:  Interface for all user commands in the system. <br>
- `MkdirCommand.java`,`CdCommand.java`,`LsCommand.java`,etc: Concrete command classes implementing the `Command.java` interface. Each class represents a specific file system command and delegates the logic to the corresponding `FileSystemOperation.java`.<br>

### filesystem package <br>
- **core package** <br>
    - `FileSystemState.java`: Maintains the current state of the file system, including the root node, current working directory, undo/redo stacks, and Trie root. Acts as a shared context passed to all file system operations. <br>
- **helper package** <br>
    - `Helper.java`: Provides utility functions to resolve file paths (both absolute and relative), compute absolute paths, and insert entries into the Trie for auto-completion support. <br>
- **operations package** <br>
    - `FileSystemOperation.java`: Interface representing a file system operation. <br>
    - `MkdirOperation.java`, `CdOperation.java`, `TouchOperation.java`, etc: Each class implements the `FileSystemOperation.java` interface to encapsulate a specific file system behavior (like creating directories, changing directories, file creation, content writing, deletion, etc.). These operations modify the file system state and support undo/redo functionality. <br>
    - `FileSystem.java`: Core class that maintains the reference to the current file system state. Provides methods to get/set current directory, root node, and manages undo/redo stacks. Executes operations via the `FileSystemOperation.java` interface, acting as the central orchestrator of all file system changes. <br>
    - `Action.java`: Represents a reversible operation for supporting undo/redo functionality.
    - `TrieNode.java`: Represents a node in a trie (prefix tree) structure used for efficient file/folder name search. Each node maintains its children and a flag to mark the end of a word. Supports auto-complete and fast lookup features within the file system. <br>

### node package <br>
- `Node.java`: Core building block of the in-memory file system. Represents both files and folders using a unified structure. Each node stores metadata such as name, content (for files), child nodes (for directories), parent reference, and optional symbolic link support. <br>

### `Main.java` <br>
- Entry point of the application. Initializes the `FileSystem.java`, reads user commands from input, and routes them through the `CommandInvoker.java` for execution. Simulates a terminal-like interface for interacting with the in-memory file system. <br><br>

## Class Diagram <br>
![Class Diagram](In-Memory-File-System/diagrams/Class_Diagram.png) <br><br>

## Concepts Used <br>

### DSA Algorithms & Techniques <br>
- **KMP (Knuth-Morris-Pratt) Pattern Matching** <br>
    Used in the `search` command to efficiently match substrings inside file contents. Built from scratch with custom LPS (Longest Prefix Suffix) construction for optimal linear-time performance. <br>
- **DFS (Depth-First Search)** <br>
    Used in operations like `tree`, `find`, and `search` to recursively traverse the file system hierarchy, exploring all nested directories and files. <br>
- **Trie (Prefix Tree)** <br>
    Used to index file and folder names for fast autocomplete/search functionality (future extension ready). Trie insertion logic is handled during file/folder creation. <br>
- **Stack (Undo/Redo Functionality)** <br>
    Two stacks are used to implement `undo` and `redo` operations, allowing users to reverse or reapply changes with precision. <br>
- **HashSet / HashMap** <br>
    Used for visited tracking in DFS (to prevent infinite cycles) and fast lookups of child nodes in directories. <br>

### Object-Oriented Design Principles <br>
- **Command Pattern** <br>
    Every command (`mkdir`, `cd`, `touch`, `echo`, etc.) implements a common interface (`Command`) and delegates logic to operation classes like `MkdirOperation`, `CdOperation`, etc., encapsulating request execution and promoting extensibility. <br>
- **Separation of Concerns (SOC)** <br>
    Clear separation between command parsing (`CommandInvoker`), execution logic (`FileSystemOperation` classes), and state management (`FileSystemState`). <br>
- **Single Responsibility Principle (SRP)** <br>
    Each class has one focused responsibility. e.g., `Helper` only deals with path resolution and trie updates, `Action` only represents undo/redo state, etc. <br>
- **Open-Closed Principle (OCP)** <br>
    New commands can be added without modifying existing code – just create a new `Command` + corresponding `Operation` class. <br>
- **Immutability & Encapsulation** <br>
    Classes like `Node` restrict direct external modification through proper getter/setter encapsulation. <br><br>

## How to Run <br>

1. **Prerequisites**: <br>
- Java 17 or later installed. <br>
- VS Code (recommended) or any Java-supported IDE. <br>

2. **Clone the Repository**: <br>
```bash
git clone https://github.com/Ansh2625/In-Memory-File-System 
cd In-Memory-File-System
``` 

3. **Import the Project**: <br>
- Open VS Code. <br>
- Select ```File > Open``` and choose the project's root folder (In-Memory-File-System). <br>

4. **Build the Project**: <br>
- Open ```Main.java``` file inside src. <br>
- Click the Run button on top, or right-click and choose Run java. <br>

5. **Run the Application**: <br>
- Right-click on ```Main.java```. <br>
- Alternatively, use the terminal: <br>
```bash
javac -d bin src/Main.java 
java -cp bin Main
```

6. **Interact with the System**: <br>
The system simulates in-memory file system operations.
Check console logs for actions like file/folder creation, navigation, content manipulation, search, and undo/redo execution. <br><br>

## Test Run <br>
- **Input File**: [TestCase.txt](./TestCase.txt)<br>
- **Output File**: [SampleOutput.txt](./SampleOutput.txt)<br><br>