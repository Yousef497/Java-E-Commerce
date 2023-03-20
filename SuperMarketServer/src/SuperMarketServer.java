import java.io.IOException;


public class SuperMarketServer {

    
    public static void main(String[] args) {
        Database.create();
        int port = 10000;
            Server server = new Server();
            while (true){
                try{
                    server.start(port);
                    break;
                }catch (IOException e){
                    port++;
                }
            }

    }
    
}
