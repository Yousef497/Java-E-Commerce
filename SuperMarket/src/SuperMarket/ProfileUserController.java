package SuperMarket;

import static SuperMarket.Client.c;
import static SuperMarket.LoginController.user_id;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;


public class ProfileUserController implements Initializable {
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    private Button BtnWallet;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnDeleteAccount;

    @FXML
    private Button btnEditPass;

    @FXML
    private Button btnShowOrders;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblFirstName;

    @FXML
    private Label lblLastName;

    @FXML
    private Label lblTotUserOrders;

    @FXML
    private Label lblUserName;

    @FXML
    private Label lblWalletBalance;

    @FXML
    private PasswordField passFieldNewPass;

    @FXML
    private PasswordField passFieldOldPass;

    @FXML
    private PasswordField passFieldPass;

    @FXML
    private PasswordField passFieldRetypeNewPass;

    @FXML
    private PasswordField passFieldRetypePass;
    
    
    @FXML
    public void btnBack(ActionEvent event) throws IOException{
        
        root = FXMLLoader.load(getClass().getResource("UserMainScreen.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(false);
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        
    }
    
    @FXML
    public void BtnWallet(ActionEvent event) throws IOException{  //may be changed must try
        
        // Open new stage of wallet screen without closing the main screen
        root = FXMLLoader.load(getClass().getResource("Wallet.fxml"));
        stage = new Stage();
        scene = new Scene(root);
        stage.setTitle("E-Mart");
        stage.setResizable(false);
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        
    }
    
    @FXML
    public void btnDeleteAccount(ActionEvent event) throws IOException{
        
        // Delete Account
        String oldPass = passFieldPass.getText();
        String retypePass = passFieldRetypePass.getText();
        
        if(oldPass.equalsIgnoreCase(retypePass)){
            String res = c.send(new String().format("%d:%d",Commands.GET_USER_PASSWORD,user_id));
            if(oldPass.equals(res)){
                c.send(new String().format("%d:%d",Commands.DELETE_USER,user_id));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("User has been Deleted");
                alert.show();
                
                root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setTitle("E-Mart");
                stage.setResizable(false);
                stage.resizableProperty().setValue(Boolean.FALSE);
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
            }
        }
        
    }
    
    @FXML
    public void btnEditPass(ActionEvent event) throws IOException{
        
        // Edit Pass
        String oldPass = passFieldOldPass.getText().trim();
        String newPass = passFieldNewPass.getText().trim();
        String retypeNew = passFieldRetypeNewPass.getText();
        
        if(newPass.equalsIgnoreCase(retypeNew)){
            String res = c.send(new String().format("%d:%d",Commands.GET_USER_PASSWORD,user_id));
            if(oldPass.equals(res)){
                c.send(new String().format("%d:%d;%s",Commands.CHANGE_PASSWORD,user_id,newPass));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Password Changed");
                alert.show();
            }
        }
    }
    
    @FXML
    public void btnShowOrders(ActionEvent event) throws IOException{
        
        // Open new stage of orders screen without closing the main screen
        root = FXMLLoader.load(getClass().getResource("OrdersUser.fxml"));
        stage = new Stage();
        scene = new Scene(root);
        stage.setTitle("E-Mart");
        stage.setResizable(false);
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        try { 
            // TODO
                
            lblFirstName.setText(c.send(new String().format("%d:%d",Commands.GET_USER_FIRSTNAME,user_id)));
            lblLastName.setText(c.send(new String().format("%d:%d",Commands.GET_USER_LASTNAME,user_id)));
            lblEmail.setText(c.send(new String().format("%d:%d",Commands.GET_USER_EMAIL,user_id)));
            lblUserName.setText(c.send(new String().format("%d:%d",Commands.GET_USER_NAME,user_id)));
            lblWalletBalance.setText(c.send(new String().format("%d:%d",Commands.GET_USER_BALANCE,user_id)));
            lblTotUserOrders.setText(c.send(new String().format("%d:%d",Commands.GET_ORDER_COUNT,user_id)));
            
        } catch (IOException ex) {
            Logger.getLogger(ProfileUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }  
    
}
