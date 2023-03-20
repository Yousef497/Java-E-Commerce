package SuperMarket;

import static SuperMarket.Client.c;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class ShopUserController implements Initializable {
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    private Button btnAddToCart;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnDecreaseQty;

    @FXML
    private Button btnIncreaseQty;

    @FXML
    private Button btnSearch;

    @FXML
    private TableColumn<Product, String> columnAvailable;

    @FXML
    private TableColumn<Product, String> columnCategory;

    @FXML
    private TableColumn<Product, Float> columnPrice;

    @FXML
    private TableColumn<Product, String> columnProd;

    @FXML
    private TableView<Product> tableShop;

    @FXML
    private TextField txtFieldSearch;
    
    @FXML
    private TextField txtFieldQty;
    
    
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
    
    private int count = 0;
    
    @FXML
    public void btnIncreaseQty(ActionEvent event) throws IOException{
        
        // increment txtFieldQty by 1
        count++;
        txtFieldQty.setText(Integer.toString(count));
        
    }
    
    @FXML
    public void btnDecreaseQty(ActionEvent event) throws IOException{
        
        // decrement txtFieldQty by 1
        if(count > 0){
            count--;
            txtFieldQty.setText(Integer.toString(count));
        }
        
    }
    
    
    
    @FXML
    public void btnAddToCart(ActionEvent event) throws IOException{
        int QtyFeild = Integer.parseInt(txtFieldQty.getText().trim());
        
        // we need function to set quantity and send it to DB , command
        // getting quantity for txtFieldQty and add to user cart then maybe reset the txtField to empty
        
    }
    
    @FXML
    public void btnSearch(ActionEvent event) throws IOException{

        // search in table
        String search = txtFieldSearch.getText().trim();

        // should be replaced into get all names_category from DB
        String[] names_category = c.send(String.format("%d:v",Commands.GET_ALLPRODUCTSID)).split(",");
        // should be replaced into get all names_product from DB
        String[] name_product = c.send(String.format("%d:v",Commands.GET_ALLPRODUCTSID)).split(",");
        // should be replaced into get all Status_product from DB
        String[] status = c.send(String.format("%d:v",Commands.GET_ALLPRODUCTSID)).split(",");
        // should be replaced into get all Price_product from DB
        String[] price_product = c.send(String.format("%d:v",Commands.GET_ALLPRODUCTSID)).split(",");


// revision
        String[] searchCategoryIds = c.send(String.format("%d:v;%s",Commands.SEARCHBYCATEGORY,search)).split(",");
        String[] searchNameIds = c.send(String.format("%d:v;%s",Commands.SEARCHBYNAME,search)).split(",");
        if(searchCategoryIds != null){
            for(int i = 0; i < searchCategoryIds.length;i++){
                int j = Integer.parseInt(searchNameIds[i]);
                productShop.add(new Product(name_product[j], names_category[j],Float.parseFloat(price_product[j]),status[j]));

            }
            tableShop.setItems(productShop_list);

        }
        else{
            for(int i = 0; i < searchNameIds.length;i++){
                int j = Integer.parseInt(searchNameIds[i]);
                productShop.add(new Product(name_product[j], names_category[j],Float.parseFloat(price_product[j]),status[j]));

            }
            tableShop.setItems(productShop_list);
        }

    }
    
    private void get_ProductsForShopping() throws IOException{
        productShop = new ArrayList<>();
        //FIX next lines
        String[] ids = c.send(String.format("%d:v",Commands.GET_ALLPRODUCTSID)).split(",");
        for(int i = 0; i < ids.length;i++){
            String name = c.send(String.format("%d:%d",Commands.GET_PRODUCT_NAME, Integer.parseInt(ids[i].trim())));
            double price = Double.parseDouble(c.send(String.format("%d:%d",Commands.GET_PRODUCT_PRICE, Integer.parseInt(ids[i].trim()))));
            int qty = Integer.parseInt(c.send(String.format("%d:%d",Commands.GET_PRODUCT_QUANTITY, Integer.parseInt(ids[i].trim()))));
            String status = c.send(String.format("%d:%d",Commands.GET_PRODUCT_STATUS, Integer.parseInt(ids[i].trim()) ));
            int category_id =Integer.parseInt(c.send(String.format("%d:%d",Commands.GET_PRODUCT_CATEGORY, Integer.parseInt(ids[i].trim()) )));
            String category_Name = c.send(String.format("%d:%d",Commands.GET_CATEGORY_NAME, category_id ));
            
            productShop.add(new Product(Integer.parseInt(ids[i].trim()), name, category_Name, qty, price, status));
        }
        productShop_list = FXCollections.observableArrayList(productShop);
        tableShop.setItems(productShop_list);
    }
    
//    private void get_ProductsForShopping() throws IOException{
//        productShop = new ArrayList<>();
//        //FIX next lines
//        String[] ids = c.send(String.format("%d:v",Commands.GET_ALLPRODUCTSID)).split(",");
//        for(int i = 0; i < ids.length;i++){
//            String name = c.send(String.format("%d:v",Commands.GET_PRODUCT_NAME));
//            int price = Integer.parseInt(c.send(String.format("%d:%d",Commands.GET_PRODUCT_PRICE, Integer.parseInt(ids[i]))));
//            int qty = Integer.parseInt(c.send(String.format("%d:%d",Commands.GET_PRODUCT_QUANTITY, Integer.parseInt(ids[i]))));
//            String status = c.send(String.format("%d:%d",Commands.GET_PRODUCT_STATUS, Integer.parseInt(ids[i]) ));
//            int category_id =Integer.parseInt(c.send(String.format("%d:v",Commands.GET_PRODUCT_CATEGORY)));
//            String category_Name = c.send(String.format("%d:%d",Commands.GET_CATEGORY_NAME, category_id ));
//            
//            productShop.add(new Product(Integer.parseInt(ids[i]), name, category_Name, qty, price, status));
//        }
//
//    }
    private static List<Product> productShop = new ArrayList<>();

    ObservableList<Product> productShop_list = FXCollections.observableArrayList(productShop);
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            get_ProductsForShopping();
        } catch (IOException ex) {
            
        }
        columnCategory.setCellValueFactory(new PropertyValueFactory<Product,String>("Category_Name"));
        columnProd.setCellValueFactory(new PropertyValueFactory<Product,String>("Product_Name"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<Product,Float>("Price"));
        columnAvailable.setCellValueFactory(new PropertyValueFactory<Product,String>("Status"));

        tableShop.setItems(productShop_list);


    }    
    
}
