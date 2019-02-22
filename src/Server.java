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

            // reads message from client until "Exit" is sent
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
                        out.flush();
                        break;
                    case "Uptime":
                        String currentUptime = getSystemUptime();
                        out.writeUTF(currentUptime);
                        out.flush();
                        break;
                    case "Memory":
                        StringBuilder memoryUse = getMemoryUse();
                        System.out.println(memoryUse);
                        out.writeUTF(String.valueOf(memoryUse));
                        out.flush();
                        break;
                    case "Netstat":
                        String netStat = getNetstat();
                        System.out.println(netStat);
                        out.writeUTF(netStat);
                        out.flush();
                        break;
                    case "Users":
                        String currentUsers = getCurrentUsers();
                        System.out.println(currentUsers);
                        out.writeUTF(currentUsers);
                        out.flush();
                        break;
                    case "Processes":
                        String currentProcesses = getCurrentProcesses();
                        System.out.println(currentProcesses);
                        out.writeUTF(currentProcesses);
                        out.flush();
                        break;
                    case "Exit":
                        System.out.println("Closing connection");
                        // close connection
                        socket.close();
                        in.close();
                        break;
                }
            }
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
    public static String getNetstat() {
        //
        String cmd = "netstat";
        return getString(cmd);
    }

    private static String getString(String cmd) {
        String s;
        String M = "";
        Process p;
        try{
            p = Runtime.getRuntime().exec(cmd);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while((s = br.readLine())!= null){
                M += s +"\n"; //store string
            }
            p.destroy();
        }catch(IOException e){
            e.printStackTrace();
        }

        return M;
    }

    public static String getCurrentUsers() {
        //
        String cmd = "w";
        String s;
        String message = "";
        Process p;
        try{
            p = Runtime.getRuntime().exec(cmd);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while((s = br.readLine())!= null){
                message = s;
            }
            p.destroy();
        }catch(IOException e){
            e.printStackTrace();
        }

        return message;
    }
    public static String getCurrentProcesses() {
        //
        String cmd = "ps -e";
        return getString(cmd);
    }
    public static void main(String args[])
    {
        Server server = new Server(9090);
    }
}
