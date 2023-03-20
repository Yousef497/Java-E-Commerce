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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class WalletController implements Initializable {
    
    @FXML
    private Button btnAddBalance;

    @FXML
    private Label lblCurrentUserBalance;

    @FXML
    private TextField txtFieldAmountToAdd;
    
    private String current_balance;
    
    
    @FXML
    public void btnAddBalance(ActionEvent event) throws IOException{
        
        // get amount from the txtFieldAmountToAdd - cast it to double - Add balance in database and update
        int addAmount = Integer.parseInt(txtFieldAmountToAdd.getText());
        int oldAmount = Integer.parseInt(current_balance);
        if(addAmount > 0){
            int balance = addAmount + oldAmount;
            String newBalance = Double.toString(balance);
            lblCurrentUserBalance.setText(newBalance);
            c.send(new String().format("%d:%d;%d",Commands.INCREASE_BALANCE,user_id,addAmount));
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Can't add negative balance");
            alert.show();
        }
        
        
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            current_balance = c.send(new String().format("%d:%d",Commands.GET_USER_BALANCE,user_id));
            lblCurrentUserBalance.setText(current_balance);
        } catch (IOException ex) {
            Logger.getLogger(WalletController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
