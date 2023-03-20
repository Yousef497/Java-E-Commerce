package SuperMarket;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import static javafx.application.Application.launch;
import javafx.application.Platform;

public class Client {
    private Socket clientSocket;
    private DataOutputStream out;
    private DataInputStream in;
    private static String serverIP;
    public static Client c;
    
    private final static String DNS = "http://192.168.25.24/dns.php?s=get";

    public Client() {
            c = this;
    }
    
    private String getServerIP() throws IOException {
        URL obj = new URL(DNS);
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
            if("null".equalsIgnoreCase(response.toString())) {
                return null;
            }else{
                System.out.println("Found server @ " + response.toString());
                return response.toString();
            }
        }else return null;
    }
    public boolean startConnection(String[] args) throws IOException {
        serverIP = getServerIP();
        
        if(serverIP == null) {
            System.out.println("No servers found");
            return false;
        }else{
            this.connect(serverIP);
            return true; 
        }
    }
    
    public String send(String msg) throws IOException{
        String res = c.sendMessage(msg);
        if("err".equalsIgnoreCase(res)){
            serverIP = getServerIP();
            this.stopConnection();
            if(serverIP == null) Platform.exit();
            else {
                this.connect(serverIP);
                res = this.sendMessage(msg);
            }
        }
        if("die".equalsIgnoreCase(res)) {
            this.stopConnection();
            Platform.exit();
        }
        return res;
    }
    
    public void connect(String serverIP) throws IOException {
        String ip = serverIP.split(":")[0];
        int port = Integer.parseInt(serverIP.split(":")[1]);
        clientSocket = new Socket(ip, port);
        System.out.println("Connected to " + ip + ":" + port);
        out = new DataOutputStream(clientSocket.getOutputStream());
        in = new DataInputStream(clientSocket.getInputStream());
    }

    public String sendMessage(String msg) throws IOException {
        String res;
        try {
            out.writeUTF(msg);
            res = in.readUTF(); 
        }catch (IOException e){
            res = "err";
        }
        return res;
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}
