package pchat;

import pchat.events.MessageEvent;
import pchat.events.QuitEvent;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable
{
    private final Socket socket;
    private final BufferedReader input;
    private final PrintWriter output;
    private final User user;

    public ClientHandler(Socket socket, User user) throws IOException
    {
        this.socket = socket;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.output = new PrintWriter(new DataOutputStream(socket.getOutputStream()), true);
        this.user = user;
        user.setHandler(this);
    }

    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                String message = input.readLine();
                new MessageEvent(user, message).trigger();
            } catch (IOException e)
            {
                new QuitEvent(user).trigger();
                return;
            }
        }
    }

    public void send(String message)
    {
        output.write(message + "\n");
        output.flush();
    }

    public Socket getSocket()
    {
        return this.socket;
    }

    public BufferedReader getInput()
    {
        return this.input;
    }

    public PrintWriter getOutput()
    {
        return this.output;
    }

    public User getUser()
    {
        return this.user;
    }
}
