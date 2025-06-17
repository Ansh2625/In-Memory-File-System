import java.util.Scanner;
import commandinvoker.CommandInvoker;
import commands.*;
import filesystem.FileSystem;

public class Main 
{
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        FileSystem fileSystem = new FileSystem();
        CommandInvoker invoker = new CommandInvoker();

        // Continuosly Take input
        while(true)
        {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if(input.equalsIgnoreCase("exit")) // Exit on user input
            {
                System.out.println("Exiting...");
                break;
            }

            if(input.isEmpty()) // ignore empty input
                continue;

            String[] parts = input.split("\\s+",2); // Separate command and argument
            String command = parts[0];
            String argument = (parts.length > 1)? parts[1] : "";

            switch (command) 
            {
                case "mkdir":
                    invoker.setCommand(new MkdirCommand(fileSystem, argument));
                    break;

                case "cd":
                    invoker.setCommand(new CdCommand(fileSystem, argument));
                    break;

                case "ls":
                    invoker.setCommand(new LsCommand(fileSystem,argument));
                    break;

                case "touch":
                    invoker.setCommand(new TouchCommand(fileSystem, argument));
                    break;

                case "cat":
                    invoker.setCommand(new CatCommand(fileSystem, argument));
                    break;

                case "echo":
                    String[] echoParts = argument.split(">>|>", 2);
                    boolean append = argument.contains(">>");

                    if (echoParts.length == 2) 
                    {
                        String content = echoParts[0].trim();
                        String filename = echoParts[1].trim();

                        // Correct argument order
                        invoker.setCommand(new EchoCommand(fileSystem, content, filename, append));
                    } 
                    else 
                    {
                        System.out.println("Invalid echo usage. Use: echo content > filename");
                        continue;
                    }
                    break;

                case "rm":
                    invoker.setCommand(new RmCommand(fileSystem, argument));
                    break;

                case "pwd":
                    invoker.setCommand(new PwdCommand(fileSystem));
                    break;

                case "tree":
                    invoker.setCommand(new TreeCommand(fileSystem));
                    break;

                case "ln":
                    String[] lnParts = argument.split("\\s+");
                    if (lnParts.length != 2) 
                    {
                        System.out.println("Usage: ln linkName targetPath");
                        continue;
                    }
                    invoker.setCommand(new LnCommand(fileSystem, lnParts[0], lnParts[1]));
                    break;

                case "find":
                    invoker.setCommand(new FindCommand(fileSystem, argument));
                    break;

                case "search":
                    invoker.setCommand(new SearchContentCommand(fileSystem, argument));
                    break;

                case "autocomplete":
                    invoker.setCommand(new AutoCompleteCommand(fileSystem, argument));
                    break;

                case "undo":
                    invoker.setCommand(new UndoCommand(fileSystem));
                    break;

                case "redo":
                    invoker.setCommand(new RedoCommand(fileSystem));
                    break;

                default:
                    System.out.println("Unknown command: " + command);
                    continue;
            }

            invoker.executeCommand();
        }

    }    
}
