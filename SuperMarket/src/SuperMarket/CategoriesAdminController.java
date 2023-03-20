package SuperMarket;

import static SuperMarket.Client.c;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class CategoriesAdminController implements Initializable {
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    private Button btnAddCategory;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnDeleteCategory;

    @FXML
    private Button btnEditCategory;

    @FXML
    private TableColumn<Category, Integer> columnCategoryID;

    @FXML
    private TableColumn<Category, String> columnCategoryName;

    @FXML
    private TableColumn<Category, String> columnCategoryStatus;

    @FXML
    private ComboBox<String> comboStatus;

    @FXML
    private Label lblTotCategories;

    @FXML
    private TableView<Category> tableCategories;

    @FXML
    private TextField txtFieldCategoryName;
    
    
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
    public void btnAddCategory(ActionEvent event) throws IOException{
        
        // add category to table and database
        String category = txtFieldCategoryName.getText().trim();
        String status = comboStatus.getValue().toString();
        
        c.send(String.format("%d:%s;%s",Commands.ADDCATEGORY,category,status));
        get_Categories();
        
        tableCategories.setItems(category_list);
        
        lblTotCategories.setText(c.send(String.format("%d:v",Commands.COUNT_CATEGORIES)));  
        
        //long category_id =  new Date().getTime();  
        //String cmd = new String().formatted("%d:%d;%s;%s;$s;%s;%s",Commands.ADDCATEGORY,category_id,category,status);
   
    }
    
    @FXML
    public void btnDeleteCategory(ActionEvent event) throws IOException{
        
        // delete selected category from table and database
        
    }
    
    @FXML
    public void btnEditCategory(ActionEvent event) throws IOException{
        
        // Edit selected category in table and database
        
    }
    
    private void get_Categories() throws IOException{
        categories_Items = new ArrayList<>();
        //FIX next lines
        String[] ids = c.send(String.format("%d:v",Commands.GET_ALLCATEGORIESID)).split(",");
        
        for (String id : ids) {
            String name = c.send(String.format("%d:%d", Commands.GET_CATEGORY_NAME, Integer.parseInt(id.trim())));
            String status = c.send(String.format("%d:%d", Commands.GET_CATEGORY_STATUS, Integer.parseInt(id.trim())));
            categories_Items.add(new Category(Integer.parseInt(id.trim()), name, status));
        }
        category_list = FXCollections.observableArrayList(categories_Items);
        
        
    }

    
private  List<Category> categories_Items = new ArrayList<>();

    ObservableList<Category> category_list = FXCollections.observableArrayList(categories_Items);
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            get_Categories();
        } catch (IOException ex) {
            Logger.getLogger(CategoriesAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        comboStatus.getItems().addAll("ACTIVE","INACTIVE");
        comboStatus.setPromptText("Select Status");
        columnCategoryID.setCellValueFactory(new PropertyValueFactory<Category,Integer>("ID"));
        columnCategoryName.setCellValueFactory(new PropertyValueFactory<Category,String>("Category_Name"));
        columnCategoryStatus.setCellValueFactory(new PropertyValueFactory<Category,String>("Status"));
        
        
        tableCategories.setItems(category_list);
        
        try {
            //get_Categories();
            lblTotCategories.setText(c.send(String.format("%d:v",Commands.COUNT_CATEGORIES)));
        } catch (IOException ex) {
            Logger.getLogger(CategoriesAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}




