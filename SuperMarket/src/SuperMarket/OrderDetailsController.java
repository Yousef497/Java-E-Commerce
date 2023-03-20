package SuperMarket;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class OrderDetailsController implements Initializable {
    
    @FXML
    private TableColumn<?, ?> columnProdID;

    @FXML
    private TableColumn<?, ?> columnProdName;

    @FXML
    private TableColumn<?, ?> columnProdPrice;

    @FXML
    private TableColumn<?, ?> columnProdQty;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderID;

    @FXML
    private Label lblTotPrice;

    @FXML
    private TableView<?> tableProducts;
    
    
    // implement a setter functions to set all fields and call it in initialize

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
