package SuperMarket;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;


public class MainController implements Initializable{
    
    @FXML
    private Label percentageLabel;
    
    @FXML
    private ProgressBar progressBar;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        progressBar.setStyle("-fx-accent: #1DFE7F");  //to change color of progressbar
        
        startProcess();
        
    }
    
    
    private void startProcess() {

        // Create a background Task
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                // Set the total number of steps in our process
                int steps = 102;

                // Simulate a long running task
                for (int i = 0; i < steps; i++) {

                    Thread.sleep(30); // Pause briefly

                    // Update our progress and message properties
                    updateProgress(i, steps);
                    updateMessage(String.valueOf(i));
                }
                return null;
            }
        };

        // This method allows us to handle any Exceptions thrown by the task
        task.setOnFailed(wse -> {
            wse.getSource().getException().printStackTrace();
        });

        // If the task completed successfully, perform other updates here
        task.setOnSucceeded(wse -> {
            //System.out.println("Done!");
            SuperMarketMain.primaryStage.close();
            Parent root1 = null;
            
            try {
                root1 = FXMLLoader.load(getClass().getResource("Login.fxml"));
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            Stage mainStage = new Stage();
            Scene scene1 = new Scene(root1);
            mainStage.setTitle("E-Mart");
            mainStage.setScene(scene1);
            mainStage.setResizable(false);
            mainStage.resizableProperty().setValue(Boolean.FALSE);
            mainStage.show();
        });

        // Before starting our task, we need to bind our UI values to the properties on the task
        progressBar.progressProperty().bind(task.progressProperty());
        percentageLabel.textProperty().bind(task.messageProperty());

        // Now, start the task on a background thread
        new Thread(task).start();
    }
    
    
}
