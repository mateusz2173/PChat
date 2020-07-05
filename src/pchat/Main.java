package pchat;

import pchat.events.MessageEvent;

import java.io.IOException;
import java.util.Scanner;

public class Main
{

    private static boolean isNumber(String str)
    {
        try
        {
            Integer.parseInt(str);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public static void main(String[] args)
    {
        Console console = null;
        if(args.length == 0)
        {
            console = new Console();
        }
        else
        {
            if(!args[0].equalsIgnoreCase("nogui"))
            {
                console = new Console();
            }
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wybierz opcje:");
        System.out.println("1. Polacz");
        System.out.println("2. Hostuj");
        String input = scanner.nextLine();
        while(!isNumber(input))
        {
            System.out.println(input + " to nie liczba!");
            input = scanner.nextLine();
        }
        int num = Integer.parseInt(input);

        if(num == 1)
        {
            String ipAddress;
            System.out.println("Podaj nazwe hosta: ");
            input = scanner.nextLine();
            if(input.equals("")) ipAddress = "localhost";
            else ipAddress = input;

            while(!isNumber(input))
            {
                System.out.println("Podaj port: ");
                input = scanner.nextLine();
            }

            int port = Integer.parseInt(input);
            System.out.println("Lacze z " + ipAddress + ":" + port + "...");
            Client client = null;
            try
            {
                client = new Client(ipAddress, port);
            } catch (IOException e)
            {
                System.out.println("Nie udalo sie nawiazac polaczenia z " + ipAddress + ":" + port);
                main(new String[]{"nogui"});
                return;
            }
            new Thread(client).start();
            while(true)
            {
                String message = scanner.nextLine();
                client.send(message);
                if(client.getSocket().isClosed()) return;
            }
        }
        else if(num == 2)
        {
            input = "Input";
            while(!isNumber(input))
            {
                System.out.println("Podaj port: ");
                input = scanner.nextLine();
            }
            Server server = Server.getInstance();
            server.start(Integer.parseInt(input));
            while(true)
            {
                String message = scanner.nextLine();
                new MessageEvent(null, message).trigger();
            }
        }
        else
        {
            if(console != null)
                console.close();
        }
    }
}