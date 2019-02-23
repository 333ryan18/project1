import java.net.*;
import java.io.*;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) throws IOException {
        int portNumber = 9090;
        try {
            ServerSocket server = new ServerSocket(9090);
            System.out.println("(Type CTRL+C to end the server program)");
            while (true) {
                try (
                        Socket client = server.accept();
                        PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                )
                {
                        System.out.println("Connection successful with user: " + client.getInetAddress());
                        String line;
                        line = in.readLine();
                        int selection = Integer.valueOf(line.charAt(0));
                        System.out.println(selection);
                        switch (selection) {
                            case 49:
                                String currentDate = new Date().toString();
                                System.out.println(currentDate);
                                out.println(currentDate);
//                                out.flush();
                                break;
                            case 50:
                                String currentUptime = getSystemUptime();
                                out.println(currentUptime + " seconds");
//                                out.flush();
                                break;
                            case 51:
                                StringBuilder memoryUse = getMemoryUse();
                                System.out.println(memoryUse);
                                out.println(memoryUse);
//                                out.flush();
                                break;
                            case 52:
                                String netStat = getNetstat();
                                System.out.println(netStat);
                                out.println(netStat);
//                                out.flush();
                                break;
                            case 53:
                                String currentUsers = getCurrentUsers();
                                System.out.println(currentUsers);
                                out.println(currentUsers);
//                                out.flush();
                                break;
                            case 54:
                                String currentProcesses = getCurrentProcesses();
                                System.out.println(currentProcesses);
                                out.println(currentProcesses);
//                                out.flush();
                                break;
                            case 55:
                                System.out.println("Closing connection");
                                // close connection
                                client.close();
                                in.close();
                                break;
                        }
                        System.out.println("Closing connection for user " + client.getInetAddress());
                        in.close();
                        out.close();
                        client.close();

                } catch (IOException i) {
                    System.out.println(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        catch(IOException e) {
            System.out.println("Exception caught when trying to listen on port " + portNumber);
            System.out.println(e.getMessage());
        }
    }

        public static String getSystemUptime () throws Exception {
            String uptime = new Scanner(new FileInputStream("/proc/uptime")).next();
            return uptime;
        }
        public  static StringBuilder getMemoryUse () throws Exception {
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
        public static String getNetstat () {
            //
            String cmd = "netstat";
            return getString(cmd);
        }

        private static String getString (String cmd){
            String s;
            String M = "";
            Process p;
            try {
                p = Runtime.getRuntime().exec(cmd);
                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                while ((s = br.readLine()) != null) {
                    M += s + "\n"; //store string
                }
                p.destroy();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return M;
        }

        public static String getCurrentUsers () {
            //
            String cmd = "w";
            String s;
            String message = "";
            Process p;
            try {
                p = Runtime.getRuntime().exec(cmd);
                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                while ((s = br.readLine()) != null) {
                    message = s;
                }
                p.destroy();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return message;
        }
        public static String getCurrentProcesses () {
            //
            String cmd = "ps -e";
            return getString(cmd);
        }
}
