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


public class AdminMainController implements Initializable {
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
     @FXML
    private Button btnCategory;

    @FXML
    private Button btnLogOut;

    @FXML
    private Button btnProducts;

    @FXML
    private Button btnUsers;

    @FXML
    private Label lblName;

    @FXML
    private Label lblTotCategory;

    @FXML
    private Label lblTotOrders;

    @FXML
    private Label lblTotProd;

    @FXML
    private Label lblTotUsers;
    
    
    @FXML
    public void btnCategory(ActionEvent event) throws IOException{
        
        root = FXMLLoader.load(getClass().getResource("CategoriesAdmin.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(false);
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        
    }
    
    @FXML
    public void btnProducts(ActionEvent event) throws IOException{
        
        root = FXMLLoader.load(getClass().getResource("ProductsAdmin.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(false);
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        
    }
    
    @FXML
    public void btnUsers(ActionEvent event) throws IOException{
        
        root = FXMLLoader.load(getClass().getResource("UsersAdminView.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(false);
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        
    }
    
    @FXML
    public void btnLogOut(ActionEvent event) throws IOException{
        
        
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
    

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            String fname = c.send(new String().format("%d:%d",Commands.GET_ADMIN_FIRSTNAME,user_id));
            String lname = c.send(new String().format("%d:%d",Commands.GET_ADMIN_FIRSTNAME,user_id));
            String totCat = c.send(new String().format("%d:v",Commands.COUNT_CATEGORIES));
            String totProd = c.send(new String().format("%d:v",Commands.COUNT_PRODUCTS));
            String totUsers = c.send(new String().format("%d:v",Commands.COUNT_USERS));
            String totOrders = c.send(new String().format("%d:v",Commands.COUNT_ORDERS));
            
            // initialize labels from database
            lblName.setText(fname + " " + lname);
            lblTotCategory.setText(totCat);
            lblTotOrders.setText(totOrders);
            lblTotProd.setText(totProd);      
            lblTotUsers.setText(totUsers);
        } catch (IOException ex) {
            Logger.getLogger(AdminMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
}
