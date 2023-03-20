package SuperMarket;

import static SuperMarket.Client.c;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class OrdersUserController implements Initializable {
    
    @FXML
    private TableColumn<Orders, String> columnDate;

    @FXML
    private TableColumn<Orders, Integer> columnOrderID;

    @FXML
    private TableColumn<Orders, Float> columnPrice;

    @FXML
    private Label lblTotUsersOrders;

    @FXML
    private TableView<Orders> tableOrders;

    private void get_ProductsForShopping() throws IOException {
        OrdersUser = new ArrayList<>();
        //FIX next lines

        String[] Order_ids = c.send(String.format("%d:v",Commands.GET_ALLPRODUCTSID)).split(",");
        // should be replaced into get all names_category from DB
        String[] Order_Price = c.send(String.format("%d:v",Commands.GET_ALLPRODUCTSID)).split(",");
        // should be replaced into get all names_product from DB
        String[] Order_Date = c.send(String.format("%d:v",Commands.GET_ALLPRODUCTSID)).split(",");

        for(int i = 0; i < Order_ids.length;i++)
            OrdersUser.add(new Orders(Integer.parseInt(Order_ids[i]),Float.parseFloat(Order_Price[i]), Order_Date[i]));


    }
    // set the values
    private List<Orders> OrdersUser = new ArrayList<>();

    ObservableList<Orders> Orders_list = FXCollections.observableArrayList(OrdersUser);
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        columnOrderID.setCellValueFactory(new PropertyValueFactory<Orders,Integer>("order_Id"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<Orders,Float>("price"));
        columnDate.setCellValueFactory(new PropertyValueFactory<Orders,String>("Date"));

        tableOrders.setItems(Orders_list);

    }    
    
}
