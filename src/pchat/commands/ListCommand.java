package pchat.commands;

import pchat.User;

import java.util.Arrays;

public class ListCommand extends ChatCommand
{

    public ListCommand()
    {
        super("lista", "/lista", "Wyswietla liste wszystkich userow na chacie.", Arrays.asList("list", "users", "online"));
    }

    @Override
    public void onCommand(CommandExecutor sender, String[] args)
    {
        sender.sendMessage("Lista osob online:");
        for(User user : User.getUsers())
        {
            if(user.isOnline())
                sender.sendMessage("- " + user.getNick());
        }
    }
}
