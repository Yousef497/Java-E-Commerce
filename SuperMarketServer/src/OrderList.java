/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author medo
 */
public class OrderList {
    int order_id;
    double price;
    String date;
    Database db = Database.create();
    
    public OrderList(int order_id){
        this.order_id = order_id;
        price = db.get_orderTotalAmount(order_id);
        date = db.get_orderDate(order_id);
    }
    
}
