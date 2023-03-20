package SuperMarket;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SuperMarketMain extends Application {
    
    public static Stage primaryStage;
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        this.primaryStage = primaryStage;
        
        Parent root = FXMLLoader.load(getClass().getResource("SplashScreen.fxml"));
        Scene scene = new Scene(root, 570, 299);
        primaryStage.setTitle("Getting Started");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.resizableProperty().setValue(Boolean.FALSE);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
        
        
        //Thread.sleep(3000);
        //if()
        //primaryStage.close();
        
        
        Parent root1 = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Stage mainStage = new Stage();
        Scene scene1 = new Scene(root1, 700, 450);
        mainStage.setTitle("E-Mart");
        mainStage.setScene(scene1);
        mainStage.setResizable(false);
        mainStage.resizableProperty().setValue(Boolean.FALSE);
        //mainStage.show();
        
        
        
        
        /*Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        */
    }
    
    
    public static void main(String[] args) throws IOException {  
        Client client = new Client();
        boolean connected = client.startConnection(args); 
        if(connected){
            launch(args); 
        }
    } 
    
}
