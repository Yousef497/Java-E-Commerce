/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author omara
 */
public class customer {
    private Database db = Database.create();
    private int id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private int balance;
    private int[] orders;
    
    public customer(int id){
        this.id =id;
        firstName = db.get_user_firstName(id);
        lastName = db.get_user_lastName(id);
        userName = db.get_user_name(id);
        email = db.get_user_email(id);
        balance = db.get_user_balance(id);  
        orders = db.get_orderID(id);
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public int getBalance() {
        return balance;
    }

    public int[] getOrders() {
        return orders;
    }
    
}
