package pchat.commands;

import pchat.Server;
import pchat.User;

import java.util.Arrays;

public class BanCommand extends ChatCommand
{
    public BanCommand()
    {
        super("ban", "/ban <nick>", "Banuje usera.", Arrays.asList("banuj", "block"));
    }

    @Override
    public void onCommand(CommandExecutor sender, String[] args)
    {
        if(!(sender instanceof Server))
        {
            sender.sendMessage("Ta komende moze wykonywac tylko serwer!");
            return;
        }
        if(args.length != 1)
        {
            sender.sendMessage("Poprawne uzycie: " + this.getUsage());
            return;
        }
        User target = User.getUsers().stream().filter(u -> u.getNick().equalsIgnoreCase(args[0])).findFirst().orElse(null);
        if(target == null)
        {
            sender.sendMessage("pchat.User o nicku " + args[0] + " nie istnieje.");
            return;
        }

        Server.getInstance().getBanList().add(target.getIP());
        sender.sendMessage(args[0] + " zostal zbanowany.");
        target.kick("Zostales zbanowany.");
    }
}
