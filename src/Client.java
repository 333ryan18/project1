import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client
{
    // initialize socket and input output streams 
    private Socket socket            = null;
    private PrintWriter out     = null;
    private BufferedReader in       = null;

    // constructor to put ip address and port 
    public Client(String address, int port)
    {
        // establish a connection 
        try
        {
            socket = new Socket(address, port);
            out    = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            System.out.println("Connected");
            
            int selection = displayMenu();

            while (selection > 0 && selection < 7) {
                selection = displayMenu();
                switch (selection){
                    case 1 :
                        String date = "Date";
                        out.println(date);
                        String dateResponse = in.readLine();
                        System.out.println("Server Date Response: " + dateResponse);
                        break;
                    case 2 :
                        out.write("Uptime");
                        String uptimeResponse = in.readLine();
                        System.out.println("Server Uptime Response: " + uptimeResponse + " seconds");
                        break;
                    case 3:
                        out.write("Memory");
                        String memoryResponse = in.readLine();
                        System.out.println("Server Memory Use Response: " + memoryResponse);
                        break;
                    case 4:
                        out.write("Netstat");
                        String netstatResponse = in.readLine();
                        System.out.println("Server Netstat Response: " + netstatResponse);
                        break;
                    case 5:
                        out.write("Users");
                        String usersResponse = in.readLine();
                        System.out.println("Server Current Users Response: " + usersResponse);
                        break;
                    case 6:
                        out.write("Processes");
                        String processesResponse = in.readLine();
                        System.out.println("Server Current Processes Response: " + processesResponse);
                        break;
                    case 7:
                        out.write("Exit");
                        try
                        {
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

    public static void main(String args[]) {
        if (args.length != 1) {
            System.out.println("Hostname of server computer must be supplied");
            return;
        }
        Client client = new Client(args[0], 9090);
    }
} 
