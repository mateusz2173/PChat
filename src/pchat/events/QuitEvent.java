package pchat.events;

import pchat.Server;
import pchat.User;

public class QuitEvent extends PChatEvent
{
    private final User user;

    public QuitEvent(User user)
    {
        this.user = user;
    }

    @Override
    public void trigger()
    {
        user.setHandler(null);
        Server.getInstance().broadcast("> " + user.getNick() + " opuscil chat.");
    }

    public User getUser()
    {
        return this.user;
    }
}
