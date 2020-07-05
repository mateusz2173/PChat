package pchat.commands;

import java.util.ArrayList;
import java.util.List;

public abstract class ChatCommand
{
    private static List<ChatCommand> commands = new ArrayList<>();

    private final String name;
    private final List<String> aliases;
    private final String description;
    private final String usage;

    public ChatCommand(String name, String usage, String description, List<String> aliases)
    {
        this.name = name;
        this.aliases = aliases;
        this.description = description;
        this.usage = usage;

        commands.add(this);
    }

    public abstract void onCommand(CommandExecutor sender, String[] args);

    public static List<ChatCommand> getCommands()
    {
        return commands;
    }

    public static void registerCommands()
    {
        new PingCommand();
        new HelpCommand();
        new NickCommand();
        new ListCommand();
        new DMCommand();
        new BanCommand();
    }

    public String getName()
    {
        return this.name;
    }

    public List<String> getAliases()
    {
        return this.aliases;
    }

    public String getDescription()
    {
        return this.description;
    }

    public String getUsage()
    {
        return this.usage;
    }
}
