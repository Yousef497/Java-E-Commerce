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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class UserMainController implements Initializable {
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    private Button btnCart;

    @FXML
    private Button btnLogOut;

    @FXML
    private Button btnProfile;

    @FXML
    private Button btnShop;

    @FXML
    private Button btnUserOrders;

    @FXML
    private Button btnWallet;

    @FXML
    private Label lblFirstandLastNames;

    @FXML
    private Label lblNumProdCart;

    @FXML
    private Label lblTotUserOrders;

    @FXML
    private Label lblWalletBalance;
    
    
    @FXML
    public void btnLogOut(ActionEvent event) throws IOException{
        
        //close remote connection to database and release resources
        //to be implemented
        
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(false);
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        
    }
    
    @FXML
    public void btnProfile(ActionEvent event) throws IOException{
        
        root = FXMLLoader.load(getClass().getResource("ProfileUser.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(false);
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        
    }
    
    @FXML
    public void btnShop(ActionEvent event) throws IOException{
        
        root = FXMLLoader.load(getClass().getResource("ShopUser.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(false);
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        
    }
    
    @FXML
    public void btnWallet(ActionEvent event) throws IOException{
        
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
    public void btnCart(ActionEvent event) throws IOException{
        
        // Open new stage of cart screen without closing the main screen
        root = FXMLLoader.load(getClass().getResource("CartUser.fxml"));
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
    public void btnUserOrders(ActionEvent event) throws IOException{
        
        // Open new stage of User orders screen without closing the main screen
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
        // TODO
        try {
            // TODO
            String fname = c.send(new String().format("%d:%d",Commands.GET_USER_FIRSTNAME,user_id));
            String lname = c.send(new String().format("%d:%d",Commands.GET_USER_LASTNAME,user_id));
            
            
            // initialize labels from database
            lblFirstandLastNames.setText(fname + " " + lname);
            lblNumProdCart.setText(c.send(new String().format("%d:%d",Commands.COUNT_PRODUCTS_IN_CART,user_id)));
            lblTotUserOrders.setText(c.send(new String().format("%d:%d",Commands.GET_ORDER_COUNT,user_id)));
            lblWalletBalance.setText(c.send(new String().format("%d:%d",Commands.GET_USER_BALANCE,user_id)));
        } catch (IOException ex) {
            Logger.getLogger(AdminMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
}
