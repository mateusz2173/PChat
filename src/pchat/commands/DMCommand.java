package pchat.commands;

import pchat.Server;
import pchat.User;

import java.util.Arrays;

public class DMCommand extends ChatCommand
{
    public DMCommand()
    {
        super("w", "/w <nick> <wiadomosc>", "Wysyla prywatna wiadomosc do usera.", Arrays.asList("dm", "msg"));
    }

    @Override
    public void onCommand(CommandExecutor sender, String[] args)
    {
        if(args.length < 2)
        {
            sender.sendMessage("Poprawne uzycie: " + this.getUsage());
            return;
        }

        String nick = args[0];
        User user = User.getUsers().stream()
                .filter(u -> u.getNick().equalsIgnoreCase(nick))
                .findFirst()
                .orElse(null);

        if(user == null)
        {
            sender.sendMessage("Nie ma nikogo o nicku " + nick + "!");
            return;
        }

        String message = Arrays.asList(args).stream()
                .skip(1)
                .reduce((s1, s2) -> s1 + " " + s2)
                .orElse("");
        if(sender instanceof Server)
        {
            user.sendMessage("<- [pchat.Server]: " + message);
        }
        else
        {
            User userSender = (User) sender;
            user.sendMessage("<- " + userSender.getNick() + ": " + message);
        }

        sender.sendMessage("-> " + user.getNick() + ": " + message);


    }
}
