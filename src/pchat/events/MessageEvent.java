package pchat.events;

import pchat.Server;
import pchat.User;

public class MessageEvent extends PChatEvent
{
     private final User user;
     private String message;
     private static final String format = "<NICK>: <MESSAGE>";

    public MessageEvent(User user, String message)
    {
        this.user = user;
        this.message = message;
    }

    public void trigger()
    {
        if(message.startsWith("/"))
        {
            if(user == null)
                Server.getInstance().executeCommand(message);
            else
                user.executeCommand(message);
        }
        else
        {
            if(user == null)
                Server.getInstance().broadcast(format.replaceAll("<NICK>", "[pchat.Server]").replaceAll("<MESSAGE>", message));
            else
                Server.getInstance().broadcast(format.replaceAll("<NICK>", user.getNick()).replaceAll("<MESSAGE>", message));

        }
    }

    public User getUser()
    {
        return this.user;
    }

    public String getMessage()
    {
        return this.message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
