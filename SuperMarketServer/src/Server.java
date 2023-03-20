

import java.io.*;
import java.net.*;

public class Server {
    private final static String DNS = "http://localhost/dns.php?";
    private ServerSocket serverSocket;
    private static String serverIP;
    private final static int START = 0;
    private final static int STOP = 1;
    private final static int CLOST = 2;

    public static boolean notifyDNS(int op) throws IOException {
        String opString;
        switch (op){
            case STOP:
                opString = "s=stop&ip=";
                break;
            case CLOST:
                opString = "s=clost&ip=";
                break;
            default:
                opString = "s=start&ip=";
                break;
        }

        URL obj = new URL(DNS + opString + serverIP);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        if(responseCode == 200){
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println("DNS notified successfully");
            return true;
        }else return false;
    }
    public void start(int port) throws IOException {
        System.out.println("Starting server ....");
        serverSocket = new ServerSocket(port);
        String ip = String.valueOf(InetAddress.getLocalHost()).split("/")[1];
        serverIP = ip + ":" + port;

        //Notify the DNS
        if(notifyDNS(START)) System.out.println("Server started at " + serverIP + "\n");

        while (true)
            new ClientHandler(serverSocket.accept()).start();
    }

    public void stop() throws IOException {
        serverSocket.close();
        notifyDNS(STOP);
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private DataOutputStream out;
        private DataInputStream in;
        private String clientIP;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                out = new DataOutputStream(clientSocket.getOutputStream());
                in = new DataInputStream(clientSocket.getInputStream());
                clientIP = clientSocket.getRemoteSocketAddress().toString().substring(1);
                if(!clientIP.split(":")[0].equals(serverIP.split(":")[0])){
                    System.out.println(clientIP + " connected");
                }

                String input;
                while ((input = in.readUTF()) != null) {
                    input = input.replace("%20"," ");
                    //Disconnect
                    if("end".equalsIgnoreCase(input)) {
                        System.out.println("Client " + clientIP + " disconnected");
                        notifyDNS(CLOST);
                        out.writeUTF("die");
                        break;
                    }else if("ping".equalsIgnoreCase(input)){
                        System.out.println("Client " + clientIP + " is pinging");
                        out.writeUTF("alive");
                    }else if("whois".equalsIgnoreCase(input)){
                        System.out.println("Client " + clientIP + " is asking for his ip");
                        out.writeUTF(clientIP);
                    }else{
                        System.out.println("Client" + clientIP + " said : " + input);
                        out.writeUTF("Received your message : " + input);
                    }
                    System.out.print("\n");
                }
                in.close();
                out.close();
                clientSocket.close();

            } catch (IOException e) {
                try {
                    if(!clientIP.split(":")[0].equals(serverIP.split(":")[0])){
                        Server.notifyDNS(Server.CLOST);
                        System.out.println("Client " + clientIP + " disconnected");
                    }
                } catch (IOException ex) {
                    System.out.println("Failed to connect to DNS");
                }


            }
        }
    }
}
