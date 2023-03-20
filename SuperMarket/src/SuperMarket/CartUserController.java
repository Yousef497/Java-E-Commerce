package SuperMarket;

import static SuperMarket.Client.c;
import static SuperMarket.LoginController.user_id;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
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


public class CartUserController implements Initializable {
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    

    @FXML
    private Button btnCheckOut;

    @FXML
    private Button btnRemoveItem;

    @FXML
    private TableColumn<Product, Double> columnPrice;

    @FXML
    private TableColumn<Product, String> columnProd;

    @FXML
    private TableColumn<Product, Integer> columnQty;

    @FXML
    private Label lblCartPrice;

    @FXML
    private Label lblCurrentBalanceWallet;

    @FXML
    private TableView<Product> tableCart;
    
    
    
    
    @FXML
    public void btnRemoveItem(ActionEvent event) throws IOException{
        
        // remove selected product from cart
        
    }
    
    @FXML
    public void btnCheckOut(ActionEvent event) throws IOException{
         String[] ids = c.send(String.format("%d:%d",Commands.GET_CART_PRODUCTS, user_id)).split(",");
         int orderID = Integer.parseInt(c.send(String.format("%d:v",Commands.GET_ORDER_STATIC)) );
         for(String i : ids){
             int id = Integer.parseInt(i.trim());
             
             int qty = Integer.parseInt(c.send(String.format("%d:%d;%d",Commands.GET_CART_PRODUCT_QUANTITY, user_id, id)));
             c.send(String.format("%d:%d;%d;%s",Commands.ADDORDER, orderID, id, "2022-07-02"));
             
         }
         int cost = Integer.parseInt(c.send(String.format("%d:%d",Commands.GET_ORDERTOTALAMOUNT, orderID)));
         c.send(String.format("%d:%d;%d",Commands.DECREASE_BALANCE, user_id, cost));
         c.send(String.format("%d:%d",Commands.CLEAR_CART, user_id));
        // check out
        // decrease wallet - add entry in orders table with customers id - reset cart to empty
        
    }
    
    private void get_Products() throws IOException{
        Products_Items = new ArrayList<>();
        //FIX next lines
        String[] ids = c.send(String.format("%d:%d",Commands.GET_CART_PRODUCTS, user_id)).split(",");
        System.out.println("IDS"+ ids[0]);
        for(int i = 0; i < ids.length;i++){
            String name = c.send(String.format("%d:%d",Commands.GET_PRODUCT_NAME, Integer.parseInt(ids[i].trim()) ));
            float price = Float.parseFloat(c.send(String.format("%d:%d",Commands.GET_PRODUCT_PRICE, Integer.parseInt(ids[i].trim()))));
            int qty = Integer.parseInt(c.send(String.format("%d:%d",Commands.GET_PRODUCT_QUANTITY, Integer.parseInt(ids[i].trim()))));
            
            System.out.println("\n\nQ = " + qty + "\n\n");
            if(qty > 0){
                Products_Items.add(new Product(name, qty, price));
            }            
        }
        product_list = FXCollections.observableArrayList(Products_Items);
        tableCart.setItems(product_list);
//        product_list = FXCollections.observableArrayList(Products_Items);
//        tableCart.setItems(product_list);
        //lblCartPrice.setText(c.send(String.format("%d:v",Commands.COUNT_PRODUCTS)));  

    }
    
    private static List<Product> Products_Items = new ArrayList<>();

    ObservableList<Product> product_list = FXCollections.observableArrayList(Products_Items);
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        try {
            // TODO
            get_Products();
            //lblTotSystemProd.setText(c.send(String.format("%d:v",Commands.COUNT_PRODUCTS)));
        } catch (IOException ex) {
             ex.printStackTrace();
        }
        columnProd.setCellValueFactory(new PropertyValueFactory<Product,String>("Product_Name"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<Product,Double>("Price"));
        columnQty.setCellValueFactory(new PropertyValueFactory<Product,Integer>("Qty"));

        tableCart.setItems(product_list);

        
    }    
    
}
