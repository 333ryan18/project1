import java.net.*;
import java.io.*;
import java.util.Date;
import java.util.Scanner;

public class Server
{
    //initialize socket and input stream 
    private Socket          socket   = null;
    private ServerSocket    server   = null;
    private DataInputStream in       = null;
    private DataOutputStream out     = null;

    // constructor with port 
    public Server(int port)
    {
        // starts server and waits for a connection 
        try
        {
            server = new ServerSocket(port);
            System.out.println("Server started");

            System.out.println("Waiting for a client ...");

            socket = server.accept();
            System.out.println("Client accepted");

            // takes input from the client socket 
            in = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));

            out = new DataOutputStream(socket.getOutputStream());

            String line = "";

            // reads message from client until "Over" is sent 
            while (!line.equals("Exit"))
            {
                try
                {
                    line = in.readUTF();
                    System.out.println(line);

                }
                catch(IOException i)
                {
                    System.out.println(i);
                    System.exit(0);
                }
                switch (line){
                    case "Date":
                        String currentDate = new Date().toString();
                        out.writeUTF(currentDate);
                    case "Uptime":
                        String currentUptime = getSystemUptime();
                        out.writeUTF(currentUptime);
                }
            }
            System.out.println("Closing connection");

            // close connection 
            socket.close();
            in.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getSystemUptime() throws Exception {
        String uptime = new Scanner(new FileInputStream("/proc/uptime")).next();
        return uptime;
    }
    public static void main(String args[]) throws IOException, InterruptedException
    {
        Server server = new Server(9090);
    }
} 
