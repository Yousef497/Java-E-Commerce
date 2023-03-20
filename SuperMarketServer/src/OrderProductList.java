/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author medo
 */
public class OrderProductList {
    
    int product_id;
    String product;
    double price;
    int quantity;
    Database db = Database.create();
    
    
    public OrderProductList(int order_id, int product_id){
        this.product_id = product_id;
        product = db.get_product_name(product_id);
        price = db.get_product_price(product_id);
        quantity = db.get_order_product_quantity(order_id, product_id);
    }
}
