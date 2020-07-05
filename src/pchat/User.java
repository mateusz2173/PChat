package pchat;

import pchat.commands.CommandExecutor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User extends CommandExecutor
{
    private final static List<User> users = new ArrayList<>();
    private final String IP;
    private String nick;
    private ClientHandler handler;

    private User(String ip)
    {
        this.IP = ip;
        this.nick = IP;

        users.add(this);
    }

    public static User getUser(String ipString)
    {
        return users.stream()
                .filter(Objects::nonNull)
                .filter(u -> u.getIP().equalsIgnoreCase(ipString))
                .findFirst()
                .orElse(new User(ipString));
    }

    public static List<User> getUsers()
    {
        return User.users;
    }

    public boolean isOnline()
    {
        return handler != null;
    }

    public void sendMessage(String message)
    {
        if(isOnline())
            handler.send(message);
    }

    public boolean isBanned()
    {
        return Server.getInstance().getBanList().contains(getIP());
    }

    public void kick(String reason)
    {
        sendMessage(reason);
        try
        {
            getHandler().getSocket().close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public String getIP()
    {
        return this.IP;
    }

    public String getNick()
    {
        return this.nick;
    }

    public ClientHandler getHandler()
    {
        return this.handler;
    }

    public void setNick(String nick)
    {
        this.nick = nick;
    }

    public void setHandler(ClientHandler handler)
    {
        this.handler = handler;
    }
}
