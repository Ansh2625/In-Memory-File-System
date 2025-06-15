package commandinvoker;
import commands.Command;

public class CommandInvoker 
{
    private Command command;

    // Set the command
    public void setCommand(Command command)
    {
        this.command = command;
    }

    // Execute the command
    public void executeCommand()
    {
        if(command != null)
        {
            command.execute();
        }
        else
        {
            System.out.println("No command set");
        }
    }
}
