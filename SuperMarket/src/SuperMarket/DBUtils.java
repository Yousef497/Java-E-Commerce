package SuperMarket;

import java.io.IOException;
import javafx.event.ActionEvent;
import java.sql.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class DBUtils {
    
    private static Stage stage;
    private static Scene scene;
    private static Parent root;
    
    private static Connection con = null;
    
    private DBUtils(){
        
    }
    
    public static Connection openConnection(){
        if(con == null){
            try {
                //Below line must be changed when integrating the remote database part
                con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/market", "root", "DEYOkhha987");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return con;
    }
    
    public static void login(ActionEvent event, String username, String password) throws IOException{
        Connection connection = DBUtils.openConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try{
            preparedStatement = connection.prepareStatement("SELECT password FROM customer WHERE user_name = ? OR email = ?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, username);
            resultSet = preparedStatement.executeQuery();
            
            if(!resultSet.isBeforeFirst()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Incorrect Username or Email!");
                alert.show();
            }
            else{
                while(resultSet.next()){
                    String retrievedPassword = resultSet.getString("password");
                    
                    if(retrievedPassword.equals(password)){
                        //print statement for test
                        //System.out.println("Login is working correctly");
                        
                        //Create main Screens scene and check whether admin or normal user
                        if(username.equals("admin") || username.equals("admin@admin.com")){
                            root = FXMLLoader.load(DBUtils.class.getResource("AdminMainScreen.fxml"));
                            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                            scene = new Scene(root);
                            stage.setResizable(false);
                            stage.resizableProperty().setValue(Boolean.FALSE);
                            //stage.initStyle(StageStyle.UNDECORATED);
                            stage.setScene(scene);
                            stage.centerOnScreen();
                            stage.show();
                        }
                        else{
                            root = FXMLLoader.load(DBUtils.class.getResource("UserMainScreen.fxml"));
                            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                            scene = new Scene(root);
                            stage.setResizable(false);
                            stage.resizableProperty().setValue(Boolean.FALSE);
                            //stage.initStyle(StageStyle.UNDECORATED);
                            stage.setScene(scene);
                            stage.centerOnScreen();
                            stage.show();
                        }
                    }
                    else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Incorrect Password!");
                        alert.show();
                    }
                }
            }
            
        }
        catch(SQLException e){
           e.printStackTrace();
        }
        finally{
            if(resultSet != null){
               try{
                   resultSet.close();
                } catch (SQLException e){
                   e.printStackTrace();
                }
            }
            
            if(preparedStatement != null){
               try{
                   preparedStatement.close();
                } catch (SQLException e){
                   e.printStackTrace();
                }
            }
        }
        
    }
    
    public static void signUp(ActionEvent event, String firstname, String lastname, String username, String email, String password) throws IOException{
       Connection connection = null;
       PreparedStatement psInsert = null;
       PreparedStatement psCheckUserExists = null;
       PreparedStatement psCheckEmailExists = null;
       ResultSet userSet = null;
       ResultSet emailSet = null;
       
       
       try{
           // connection line to be changed when integrating the remote database
           connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/market", "root", "DEYOkhha987");
           psCheckUserExists = connection.prepareStatement("SELECT * FROM customer WHERE user_name = ?");
           psCheckUserExists.setString(1, username);
           userSet = psCheckUserExists.executeQuery();
           
           psCheckEmailExists = connection.prepareStatement("SELECT * FROM customer WHERE email = ?");
           psCheckEmailExists.setString(1, email);
           emailSet = psCheckEmailExists.executeQuery();
           
           if(userSet.isBeforeFirst() || emailSet.isBeforeFirst()){
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setContentText("This Username or Email is already used.\nPlease Sign Up again.");
               alert.show();
           }
           else{
               psInsert = connection.prepareStatement("INSERT INTO customer (first_name, last_name, user_name, email, password, wallet) VALUES (?, ?, ?, ?, ?, ?)");
               psInsert.setString(1, firstname);
               psInsert.setString(2, lastname);
               psInsert.setString(3, username);
               psInsert.setString(4, email);
               psInsert.setString(5, password);
               psInsert.setDouble(6, 0);
               psInsert.executeUpdate();
               
               //create info alert
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setContentText("Congratulations, You are now part of our Family.\nPlease Sign in.");
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
       catch(SQLException e){
           e.printStackTrace();
       }
       finally{
           if(userSet != null){
               try{
                   userSet.close();
               } catch (SQLException e){
                   e.printStackTrace();
               }
           }
           
           if(emailSet != null){
               try{
                   emailSet.close();
               } catch (SQLException e){
                   e.printStackTrace();
               }
           }
           
           if(psCheckUserExists != null){
               try{
                   psCheckUserExists.close();
               } catch (SQLException e){
                   e.printStackTrace();
               }
           }
           
           if(psCheckEmailExists != null){
               try{
                   psCheckEmailExists.close();
               } catch (SQLException e){
                   e.printStackTrace();
               }
           }
           
           if(psInsert != null){
               try{
                   psInsert.close();
               } catch (SQLException e){
                   e.printStackTrace();
               }
           }
           
           if(connection != null){
               try{
                   connection.close();
               } catch (SQLException e){
                   e.printStackTrace();
               }
           }
       }
    }
    
    
    //start new function here
    
    
}
