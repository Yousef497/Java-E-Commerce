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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class ProductsAdminController implements Initializable {
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    private Button btnAddProdToSystem;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnDeleteProdFromSystem;

    @FXML
    private Button btnEditProdInSystem;

    @FXML
    private TableColumn<Product, String> columnProdCategory;

    @FXML
    private TableColumn<Product, Integer> columnProdID;

    @FXML
    private TableColumn<Product, String> columnProdName;

    @FXML
    private TableColumn<Product, Float> columnProdPrice;

    @FXML
    private TableColumn<Product, Integer> columnProdQty;

    @FXML
    private TableColumn<Product, String> columnProdStatus;


    // retrive all category name from DB
    // should implement in DB & Command
    @FXML
    private ComboBox<String> comboCategory;

    @FXML
    private ComboBox<String> comboStatus;

    @FXML
    private Label lblTotSystemProd;

    @FXML
    private TableView<Product> tableProducts;

    @FXML
    private TextField txtFieldProdName;

    @FXML
    private TextField txtFieldProdPrice;

    @FXML
    private TextField txtFieldQtyToBeAdded;
    
    
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
    
    @FXML
    public void btnAddProd(ActionEvent event) throws IOException{
        
        // add product to table and database
        lblTotSystemProd.setText(c.send(String.format("%d:v",Commands.COUNT_PRODUCTS)));
        String product = txtFieldProdName.getText().trim();
        double price = Double.parseDouble(txtFieldProdPrice.getText().trim());
        int quantity = Integer.parseInt(txtFieldQtyToBeAdded.getText().trim());
        String category = comboCategory.getValue().toString();
        String status = comboStatus.getValue().toString();
        
        if(price > 0 && quantity > 0){
            int categoryID = Integer.parseInt(c.send(String.format("%d:%s",Commands.COUNT_PRODUCTS, category)));

            c.send(String.format("%d:%s;%d;%f;%d;%s",Commands.ADDPRODUCT, product, categoryID , price,quantity,status));
            get_Products();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Price and quantity can't be neagtive ");
            alert.show();
        }
        
        
        
        
        // handle for shelby

       // should to call function "whenComboCategory_Is_select" to store it into string
        // واعمل اللي انت عايزه
        
    }
    
    @FXML
    public void btnDeleteProd(ActionEvent event) throws IOException{
        
        // delete selected product from table and database
        
    }
    
    @FXML
    public void btnEditProd(ActionEvent event) throws IOException{
        
        // Edit selected product in table and database
        
    }
    private void get_Products() throws IOException{
        Products_Items = new ArrayList<>();
        //FIX next lines
        String[] ids = c.send(String.format("%d:v",Commands.GET_ALLPRODUCTSID)).split(",");
        for(int i = 0; i < ids.length;i++){
            String name = c.send(String.format("%d:%d",Commands.GET_PRODUCT_NAME, Integer.parseInt(ids[i].trim()) ));
            float price = Float.parseFloat(c.send(String.format("%d:%d",Commands.GET_PRODUCT_PRICE, Integer.parseInt(ids[i].trim()))));
            int qty = Integer.parseInt(c.send(String.format("%d:%d",Commands.GET_PRODUCT_QUANTITY, Integer.parseInt(ids[i].trim()))));
            String status = c.send(String.format("%d:%d",Commands.GET_PRODUCT_STATUS, Integer.parseInt(ids[i].trim()) ));
            int category_id =Integer.parseInt(c.send(String.format("%d:%d",Commands.GET_PRODUCT_CATEGORY,Integer.parseInt(ids[i].trim()) )));
            String category_Name = c.send(String.format("%d:%d",Commands.GET_CATEGORY_NAME, category_id ));
            System.out.println("\n\nQ = " + qty + "\n\n");
            if(qty > 0){
                Products_Items.add(new Product(Integer.parseInt(ids[i].trim()), name, category_Name, qty, price, status));
            }
            
        }
        for(Product p : Products_Items){
            System.out.println(p.getProduct_Name());
        }
        product_list = FXCollections.observableArrayList(Products_Items);
        tableProducts.setItems(product_list);
        lblTotSystemProd.setText(c.send(String.format("%d:v",Commands.COUNT_PRODUCTS)));  

    }
     

    

    private static List<Product> Products_Items = new ArrayList<>();

    ObservableList<Product> product_list = FXCollections.observableArrayList(Products_Items);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            get_Products();
            lblTotSystemProd.setText(c.send(String.format("%d:v",Commands.COUNT_PRODUCTS)));
        } catch (IOException ex) {
            
        }
        comboStatus.getItems().addAll("ACTIVE","INACTIVE");
        comboStatus.setPromptText("Select Status");
        comboCategory.setPromptText("select Category Name");
        columnProdID.setCellValueFactory(new PropertyValueFactory<Product,Integer>("id"));
        columnProdCategory.setCellValueFactory(new PropertyValueFactory<Product,String>("Category_Name"));
        columnProdName.setCellValueFactory(new PropertyValueFactory<Product,String>("Product_Name"));
        columnProdPrice.setCellValueFactory(new PropertyValueFactory<Product,Float>("Price"));
        columnProdQty.setCellValueFactory(new PropertyValueFactory<Product,Integer>("Qty"));
        columnProdStatus.setCellValueFactory(new PropertyValueFactory<Product,String>("Status"));

        tableProducts.setItems(product_list);

        // should be replaced into get all names_category from DB
        try {
            String[] ids = c.send(String.format("%d:v",Commands.GET_ALLPRODUCTSID)).split(",");
            String[] names_category = c.send(String.format("%d:v",Commands.GET_ALLPRODUCTSID)).split(",");
            for (int i = 0 ; i< ids.length ;i++){
                String name = c.send(String.format("%d:%d",Commands.GET_CATEGORY_NAME, Integer.parseInt(names_category[i].trim()) ));
                comboCategory.getItems().add(name);
                
                
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // should add to action in scene builder
    // return selected CompoCategory of String
    public String whenComboCategory_Is_select(){
       String shelby = comboCategory.getValue().toString();
       return shelby;
    } 
    
}
