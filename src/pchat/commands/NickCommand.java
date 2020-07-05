package pchat.commands;

import pchat.Server;
import pchat.User;

import java.util.Arrays;

public class NickCommand extends ChatCommand
{
    public NickCommand()
    {
        super("nick", "/nick <text>", "Zmienia twój nick.", Arrays.asList("name", "imie", "nazwa"));
    }

    @Override
    public void onCommand(CommandExecutor sender, String[] args)
    {
        if(sender instanceof Server)
        {
            sender.sendMessage("Ta komenda dostepna jest tylko dla userow!");
            return;
        }

        User user = (User) sender;
        if(args.length != 1)
        {
            user.sendMessage("Poprawne uzycie: " + this.getUsage());
            return;
        }

        user.setNick(args[0]);
        user.sendMessage("Zmieniles swoj nick na " + args[0]);
    }
}
