import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client
{
    // initialize socket and input output streams 
    private Socket socket            = null;
    private DataInputStream  input   = null;
    private DataOutputStream out     = null;
    private DataInputStream in       = null;

    // constructor to put ip address and port 
    public Client(String address, int port)
    {
        // establish a connection 
        try
        {
            socket = new Socket(address, port);
            out    = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));
            System.out.println("Connected");
            
            int selection = 1;
            String response = "";

            while (selection > 0 && selection < 7) {
                selection = displayMenu();
                switch (selection){
                    case 1 :
                        out.writeUTF("Date");
                        response = in.readUTF();
                        System.out.println("Server Date Response: " + response);
                        break;
                    case 2 :
                        out.writeUTF("Uptime");
                        response = in.readUTF();
                        System.out.println("Server Uptime Response: " + response + " seconds");
                        break;
                    case 3:
                        out.writeUTF("Memory");
                        response = in.readUTF();
                        System.out.println("Server Memory Use Response: " + response);
                        break;
                    case 4:
//                    netstat();
                        break;
                    case 5:
//                    currentUsers();
                        break;
                    case 6:
//                    processes();
                        break;
                    case 7:
                        // close the connection
                        try
                        {
//                            input.close();
                            out.close();
                            socket.close();
                        }
                        catch(IOException i)
                        {
                            System.out.println(i);
                        }
                        System.out.println("Connection Closed with Server.");
                        System.exit(0);
                        break;
                    default :
                        displayMenu();
                        break;
                }
            }

        }
        catch(UnknownHostException u)
        {
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

    private static int displayMenu() {

        int userInput;
        try {
            System.out.println("Please Choose an Option:\n");
            System.out.println("1.\tHost current Date and Time");
            System.out.println("2.\tHost uptime");
            System.out.println("3.\tHost memory use");
            System.out.println("4.\tHost Netstat");
            System.out.println("5.\tHost current users");
            System.out.println("6.\tHost running processes");
            System.out.println("7.\tQuit");
            System.out.print("\nEnter your selection number:\n");

            Scanner scan = new Scanner(System.in);
            userInput = scan.nextInt();
            if (userInput < 1 || userInput > 7) {
                System.out.println("Choice not Valid.");
                userInput = displayMenu();
            }
        } catch (Exception ex) {
            System.out.println("Choice not Valid.");
            userInput = displayMenu();
        }
        return userInput;
    }

    public static void main(String args[]) throws IOException, InterruptedException
    {
        if (args.length != 1) {
            System.out.println("Hostname of server computer must be supplied");
            return;
        }
        Client client = new Client(args[0], 9090);
    }
} 
