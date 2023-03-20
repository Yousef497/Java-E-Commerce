package SuperMarket;

import static SuperMarket.Client.c;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class SignUpController implements Initializable {
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    //private String fn;
    //private String lastn;
    //private String email;
    //private String un;
    //private String pw;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField emailtxtField;

    @FXML
    private TextField firstNametxtField;

    @FXML
    private TextField lastNametxtField;

    @FXML
    private PasswordField pswdtxtField;

    @FXML
    private PasswordField retypePswdtxtField;

    @FXML
    private Button signUpBtn;

    @FXML
    private TextField usernametxtField;
    
    
    
    private boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
    
    
    public void keyReleased(){
        String fn = firstNametxtField.getText().trim();
        String lastn = lastNametxtField.getText().trim();
        String email = emailtxtField.getText().trim();
        String un = usernametxtField.getText().trim();
        String pw = pswdtxtField.getText().trim();
        String retypePw = retypePswdtxtField.getText().trim();
        
        boolean empty = (un.isEmpty() || pw.isEmpty() || fn.isEmpty() || lastn.isEmpty() || email.isEmpty() || retypePw.isEmpty());
        boolean equalpw = (retypePw.equals(pw));
        boolean valid = isValid(email);
        
        boolean isDisabled = (empty || !equalpw || !valid);
        
        signUpBtn.setDisable(isDisabled);
        
        
        
    }
    
    @FXML
    public void btnSignUp(ActionEvent event) throws IOException{
        
        //register user in database
        String fn = firstNametxtField.getText().trim();
        String lastn = lastNametxtField.getText().trim();
        String email = emailtxtField.getText().trim();
        String un = usernametxtField.getText().trim();
        String pw = pswdtxtField.getText().trim();
        
        
        if(un.equalsIgnoreCase("admin") || email.contains("@emart")){
            String cmd = (new String().format("%d:%s;%s;%s;%s;%s",Commands.ADDADMIN,un,pw,fn,lastn,email));
            String res = c.send(cmd);
            
            System.out.println(res);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Added new Admin");
            alert.show();
          
          
            root = FXMLLoader.load(DBUtils.class.getResource("Login.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setResizable(false);
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }
        
        else{
            String cmd = String.format("%d:%s;%s;%s;%s;%s",Commands.ADDUSER,un,pw,fn,lastn,email);
            String res = c.send(cmd);
            System.out.println(res);            
            System.out.println(cmd);

            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Added new User");
            alert.show();
            
            root = FXMLLoader.load(DBUtils.class.getResource("Login.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setResizable(false);
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }
        
        
        
    }
    
    @FXML
    public void btnCancel(ActionEvent event) throws IOException{
        
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
        // TODO
        keyReleased();
    }    
    
}
