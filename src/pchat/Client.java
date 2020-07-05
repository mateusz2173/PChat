package pchat;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable
{
    private final Socket socket;
    private final BufferedReader input;
    private final PrintWriter output;

    public Client(String ipString, int port) throws IOException
    {
        this.socket = new Socket(ipString, port);
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.output = new PrintWriter(new DataOutputStream(socket.getOutputStream()));
    }

    @Override
    public void run()
    {
        System.out.println("Polaczono!");
        while(true)
        {
            try
            {
                String message = input.readLine();
                if(message == null) break;
                System.out.println(message);
            } catch (IOException e)
            {
                System.out.println("Rozlaczono z serwerem.");
                break;
            }
        }
        output.close();
        try
        {
            input.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        Main.main(new String[]{"nogui"});

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
}
