import java.net.*;
import java.io.*;
import java.text.NumberFormat;
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
                    case "Memory":
                        StringBuilder memoryUse = getMemoryUse();
                        System.out.println(memoryUse);
                        out.writeUTF(String.valueOf(memoryUse));
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
    public StringBuilder getMemoryUse() throws Exception{
        Runtime runtime = Runtime.getRuntime();

        NumberFormat format = NumberFormat.getInstance();

        StringBuilder sb = new StringBuilder();
        long maxMemory = runtime.maxMemory();
        long allocatedMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();

        sb.append("Free Memory: " + format.format(freeMemory / 1024));
        sb.append("\n");
        sb.append("Allocated Memory: " + format.format(allocatedMemory / 1024));
        sb.append("\n");
        sb.append("Max Memory: " + format.format(maxMemory / 1024));
        sb.append("\n");
        sb.append("Total Free Memory: " + format.format((freeMemory + (maxMemory - allocatedMemory)) / 1024));

        return sb;
    }
    public static void main(String args[])
    {
        Server server = new Server(9090);
    }
}
