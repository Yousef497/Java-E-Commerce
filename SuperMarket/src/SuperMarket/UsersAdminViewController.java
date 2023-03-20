package SuperMarket;

import static SuperMarket.Client.c;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class UsersAdminViewController implements Initializable {
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    private Button btnBack;

    @FXML
    private TableColumn<Users, String> columnEmail;

    @FXML
    private TableColumn<Users, String> columnFirstName;

    @FXML
    private TableColumn<Users, String> columnLastName;

    // should be in new table view to show the all data related to specific order for user
    @FXML
    private TableColumn<?, ?> columnNumOfOrdersOfUser;

    @FXML
    private TableColumn<?, ?> columnOrderDate;

    @FXML
    private TableColumn<?, ?> columnOrderPrice;

////////////////////////////////////////////

    @FXML
    private TableColumn<Users, Integer> columnUserOrderID;

    @FXML
    private TableColumn<Users, String> columnUsername;

    @FXML
    private TableColumn<Users, Integer> columnWalletBalanceOfUser;

    @FXML
    private Label lblSelectedEmail;

    @FXML
    private Label lblSelectedUsername;

    @FXML
    private Label lblTotOrdersInSystem;

    @FXML
    private Label lblTotUsersInSystem;

    @FXML
    private TableView<?> tableSelectedUserOrders;

    @FXML
    private TableView<Users> tableUsers;
    
    
    @FXML
    public void btnBack(ActionEvent event) throws IOException{
        
        root = FXMLLoader.load(getClass().getResource("AdminMainScreen.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(false);
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        
    }
    private void get_Users() throws IOException{
        UsersInfo = new ArrayList<>();
        //FIX next lines
        // not important but it is useful for loop
        String[] ids = c.send(String.format("%d:v",Commands.GET_ALLPRODUCTSID)).split(",");


        // should be replaced into get all Firstnames_Users from DB
        String[] FirstNames = c.send(String.format("%d:v",Commands.GET_ALLPRODUCTSID)).split(",");
        // should be replaced into get all Lastnames_Users from DB
        String[] LastNames = c.send(String.format("%d:v",Commands.GET_ALLPRODUCTSID)).split(",");
        // should be replaced into get all Usernames from DB
        String[] UserNames = c.send(String.format("%d:v",Commands.GET_ALLPRODUCTSID)).split(",");
        // should be replaced into get all E_mails_Users from DB
        String[] E_Mails  = c.send(String.format("%d:v",Commands.GET_ALLPRODUCTSID)).split(",");
        // should be replaced into get all User_Orders from DB
        String[] User_Orders = c.send(String.format("%d:v",Commands.GET_ALLPRODUCTSID)).split(",");
        // should be replaced into get all E_Wallets_Users from DB
        String[] E_Wallets = c.send(String.format("%d:v",Commands.GET_ALLPRODUCTSID)).split(",");

        for(int i = 0; i < ids.length;i++)
            UsersInfo.add(new Users(FirstNames[i], LastNames[i],E_Mails[i],UserNames[i],Integer.parseInt(E_Wallets[i]),Integer.parseInt(User_Orders[i])));


    }
    private List<Users> UsersInfo = new ArrayList<>();

    ObservableList<Users> Users_list = FXCollections.observableArrayList(UsersInfo);
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // how to display data into column cell
        columnFirstName.setCellValueFactory(new PropertyValueFactory<Users, String>("FirstName"));
        columnLastName.setCellValueFactory(new PropertyValueFactory<Users, String>("LastName"));
        columnUsername.setCellValueFactory(new PropertyValueFactory<Users, String>("UserName"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<Users, String>("E_mail"));
        columnWalletBalanceOfUser.setCellValueFactory(new PropertyValueFactory<Users, Integer>("E_wallet"));
        columnUserOrderID.setCellValueFactory(new PropertyValueFactory<Users, Integer>("User_orders"));

        tableUsers.setItems(Users_list);


    }    
    
}
