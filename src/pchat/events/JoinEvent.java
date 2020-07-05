package pchat.events;

import pchat.Server;
import pchat.User;
import pchat.events.PChatEvent;

public class JoinEvent extends PChatEvent
{
    private final User user;

    public JoinEvent(User user)
    {
        this.user = user;
    }

    @Override
    public void trigger()
    {
        if(user.isBanned())
        {
            user.kick("Jestes zbanowany!");
            return;
        }
        Server.getInstance().broadcast("> " + user.getNick() + " dolaczyl do chatu!");
        user.sendMessage("Witaj na serwerze, wpisz /? aby wyswietlic liste komend.");
    }

    public User getUser()
    {
        return this.user;
    }
}
