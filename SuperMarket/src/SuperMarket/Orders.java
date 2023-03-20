package SuperMarket;


public class Orders {
    private  int order_Id;
    private float price;
    private  String Date;

    public Orders(int order_Id, float price, String date) {
        this.order_Id = order_Id;
        this.price = price;
        Date = date;
    }

    public int getOrder_Id() {
        return order_Id;
    }

    public float getPrice() {
        return price;
    }

    public String getDate() {
        return Date;
    }
}
