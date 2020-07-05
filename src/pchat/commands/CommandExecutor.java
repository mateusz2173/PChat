package pchat.commands;

import pchat.Server;
import pchat.User;

import java.util.Arrays;

public abstract class CommandExecutor
{
    public void executeCommand(String commandString)
    {
        String[] split = commandString.split(" ");
        String name = split[0].substring(1);
        ChatCommand command = ChatCommand.getCommands()
                .stream()
                .filter(c -> c.getName().equalsIgnoreCase(name) || c.getAliases().contains(name))
                .findFirst()
                .orElse(null);
        if(command == null)
        {
            System.out.println("Komenda /" + name + " nie istnieje.");
            return;
        }
        String[] args = Arrays.copyOfRange(split, 1, split.length);
        command.onCommand(this, args);
    }

    public void sendMessage(String message)
    {
        if(this instanceof Server)
        {
            System.out.println(message);
        }
        else
        {
            User user = (User) this;
            user.sendMessage(message);
        }
    }
}
