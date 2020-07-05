package pchat.commands;

import java.util.Arrays;

public class HelpCommand extends ChatCommand
{
    public HelpCommand()
    {
        super("help", "/help", "Wyswietla wszystkie komendy.", Arrays.asList("pomoc", "pomocy", "komendy", "cmd", "cmds", "?"));
    }

    @Override
    public void onCommand(CommandExecutor sender, String[] args)
    {
        sender.sendMessage("==============");
        sender.sendMessage("Lista komend:");
        for(ChatCommand cmd : ChatCommand.getCommands())
        {
            sender.sendMessage(cmd.getUsage() + " - " + cmd.getDescription());
        }
        sender.sendMessage("==============");
    }
}
