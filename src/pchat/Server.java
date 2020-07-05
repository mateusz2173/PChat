package pchat;

import pchat.commands.ChatCommand;
import pchat.commands.CommandExecutor;
import pchat.events.JoinEvent;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server extends CommandExecutor implements Runnable

{
    public static final int DEFAULT_PORT = 6624;
    public static final int MAX_CONNECTIONS = 100;

    private static Server instance;

    private ServerSocket listener;
    private int port;
    private final ExecutorService pool;
    private List<String> banList;

    private Server()
    {
        banList = new ArrayList<>();
        pool = Executors.newFixedThreadPool(MAX_CONNECTIONS);
        port = DEFAULT_PORT;
        ChatCommand.registerCommands();
    }

    public static Server getInstance()
    {
        if (instance == null)
        {
            instance = new Server();
        }

        return instance;
    }

    public void start(int port)
    {
        this.port = port;
        new Thread(this).start();
    }

    public void run()
    {
        try
        {
            this.listener = new ServerSocket(this.port);
        } catch (IOException e)
        {
            System.out.println("Nie mozna utworzyc serwra na porcie " + this.port);
            e.printStackTrace();
        }
        System.out.println("Utworzono serwer na " + this.listener.getInetAddress().getHostAddress() + ":" + this.port);
        System.out.println("Nasluchuje na polaczenia...");
        while (true)
        {
            try
            {
                Socket client = listener.accept();
                User user = User.getUser(client.getInetAddress().getHostAddress());
                ClientHandler handler = new ClientHandler(client, user);
                pool.execute(handler);
                user.setHandler(handler);
                new JoinEvent(user).trigger();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public boolean broadcast(String message)
    {
        if (listener.isClosed()) return false;

        System.out.println(message);
        User.getUsers().forEach(c -> c.sendMessage(message));

        return true;
    }

    public List<String> getBanList()
    {
        return this.banList;
    }
}
