package filesystem.operations;

import filesystem.core.FileSystemState;

public interface FileSystemOperation 
{
    void execute(FileSystemState state);    
}
