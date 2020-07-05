package pchat.commands;

import java.util.Arrays;

public class PingCommand extends ChatCommand
{
    public PingCommand()
    {
        super("ping", "/ping", "Sprawdza ping.", Arrays.asList("lag"));
    }

    @Override
    public void onCommand(CommandExecutor sender, String[] args)
    {
        sender.sendMessage("PONG!");
    }
}
