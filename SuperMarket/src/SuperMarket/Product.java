package SuperMarket;


public class Product {

    private int id;
    private String Product_Name;
    private String Category_Name;
    private int Qty;
    private double Price;
    private  String Status;

    public Product(String Product_Name, int Qty, double Price) {
        this.Product_Name = Product_Name;
        this.Qty = Qty;
        this.Price = Price;
    }

    
    public Product(String product_Name, String category_Name, double price, String status) {
        Product_Name = product_Name;
        Category_Name = category_Name;
        Price = price;
        Status = status;
    }

    public Product(int id, String product_Name, String category_Name, int qty, double price, String status) {
        this.id = id;
        Product_Name = product_Name;
        Category_Name = category_Name;
        Qty = qty;
        Price = price;
        Status = status;
    }

    public int getId() {
        return id;
    }

    public String getProduct_Name() {
        return Product_Name;
    }

    public String getCategory_Name() {
        return Category_Name;
    }

    public int getQty() {
        return Qty;
    }

    public double getPrice() {
        return Price;
    }

    public String getStatus() {
        return Status;
    }

    public void setProduct_Name(String Product_Name) {
        this.Product_Name = Product_Name;
    }

    public void setCategory_Name(String Category_Name) {
        this.Category_Name = Category_Name;
    }

    public void setQty(int Qty) {
        this.Qty = Qty;
    }

    public void setPrice(float Price) {
        this.Price = Price;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

}
