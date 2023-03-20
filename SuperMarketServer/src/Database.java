/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

/**
 *
 * @author omara
 */
public class Database {

    /**
     * @param args the command line arguments
     */
    
    static final String DB_URL = "jdbc:mysql://localhost:3306/market";
    static final String USER = "root";
    static final String PASS = "root";
    static boolean e = false;
    static Database d;
    static int product_id = 1;
    static int user_id = 1;
    static int admin_id = 1;
    static int category_id = 1;

    private Database(){         
    }
   
    static public Database create(){
        if(!e){          
            d = new Database();
            e = true;
            d.set_static_admin_id();
            d.set_static_category_id();
            d.set_static_product_id();
            d.set_static_user_id();
            return d;
       }
        return d;
   }
      
    synchronized String login(String username, String password){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();){
            PreparedStatement psCheckUserExists = null;
            PreparedStatement psCheckAdminExists = null;
            ResultSet userSet = null;
            ResultSet adminSet = null;
            psCheckUserExists = conn.prepareStatement("SELECT * FROM customer WHERE userName = ? OR email = ?");
            psCheckUserExists.setString(1, username);
            psCheckUserExists.setString(2, username);
            userSet = psCheckUserExists.executeQuery();
           
            psCheckAdminExists = conn.prepareStatement("SELECT * FROM admin WHERE userName = ? OR email = ?");
            psCheckAdminExists.setString(1, username);
            psCheckAdminExists.setString(2, username);
            adminSet = psCheckAdminExists.executeQuery();
            
            if(adminSet.isBeforeFirst()){               
                String strSelect = "select pword,idadmin from admin where userName = \""+username+"\" "+"OR email = \""+username+"\";";
                ResultSet r = stmt.executeQuery(strSelect);
                String p;
                r.next();
                p = r.getString("pword");
                String s = "" +r.getInt("idadmin");
                if (p.equals(password)){
                    return "admin,"+s;
                }
                else{
                    // JFrame parent = new JFrame();
                    // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    // JOptionPane.showMessageDialog(parent, "wrong username or password");
                    return "wrong username or password";
                }
            }
            

            
            if(userSet.isBeforeFirst()){               
                String strSelect = "select pword,customer_id from customer where userName = \""+username+"\" "+"OR email = \""+username+"\";";
                ResultSet r = stmt.executeQuery(strSelect);
                String p;
                r.next();
                p = r.getString("pword");
                String s = "" + r.getInt("customer_id");
                if (p.equals(password)){
                    return "customer,"+s;
                }
                else{
                    // JFrame parent = new JFrame();
                    // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    // JOptionPane.showMessageDialog(parent, "wrong username or password");
                    return "wrong username or password";
                }

            }
            
            // JFrame parent = new JFrame();
            // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // JOptionPane.showMessageDialog(parent, "wrong username or password");
            return "wrong username or password";
             
         } catch (SQLException e) {
            e.printStackTrace();
            // JFrame parent = new JFrame();
            // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // JOptionPane.showMessageDialog(parent, "doesn't exist");
            return "doesn't exist";
         }    

    }
    
    synchronized String addUser(String usrname, String pword, String Fname, String Lname, String email){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){            
            PreparedStatement psCheckUserExists = null;
            PreparedStatement psCheckEmailExists = null;
            PreparedStatement psCheckidExists = null;
            ResultSet userSet = null;
            ResultSet emailSet = null;
            ResultSet idSet = null;
            psCheckUserExists = conn.prepareStatement("SELECT * FROM customer WHERE userName = ?");
            psCheckUserExists.setString(1, usrname);
            userSet = psCheckUserExists.executeQuery();
           
            psCheckEmailExists = conn.prepareStatement("SELECT * FROM customer WHERE email = ?");
            psCheckEmailExists.setString(1, email);
            emailSet = psCheckEmailExists.executeQuery();
           
            psCheckidExists = conn.prepareStatement("SELECT * FROM customer WHERE customer_id = ?");
            psCheckidExists.setString(1,"" + user_id);
            idSet = psCheckidExists.executeQuery();
           
            if(userSet.isBeforeFirst() || emailSet.isBeforeFirst() || idSet.isBeforeFirst()){               
                // JFrame parent = new JFrame();
                // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // JOptionPane.showMessageDialog(parent, "already exists");
                return "already exists";
            }
           
            else{              
                String sqlInsert = "insert into customer values ("+user_id+",\""+Fname+"\",\""+Lname+"\",\""+usrname+"\",\""+email+"\","+pword+",0)";
                int countInserted = stmt.executeUpdate(sqlInsert);
                sqlInsert = "insert into cart values ("+user_id+","+user_id+")";
                countInserted = stmt.executeUpdate(sqlInsert);                  
                // JFrame parent = new JFrame();
                // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // JOptionPane.showMessageDialog(parent, "Added");
                user_id++;
                return "added";
            }
        } catch (SQLException e) {
          e.printStackTrace();
       }    
        return "";
    }

    synchronized String addAdmin(String usrname, String pword, String Fname, String Lname, String email){
       try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){
            PreparedStatement psCheckUserExists = null;
            PreparedStatement psCheckEmailExists = null;
            PreparedStatement psCheckidExists = null;
            ResultSet userSet = null;
            ResultSet emailSet = null;
            ResultSet idSet = null;
            psCheckUserExists = conn.prepareStatement("SELECT * FROM admin WHERE userName = ?");
            psCheckUserExists.setString(1, usrname);
            userSet = psCheckUserExists.executeQuery();
           
            psCheckEmailExists = conn.prepareStatement("SELECT * FROM admin WHERE email = ?");
            psCheckEmailExists.setString(1, email);
            emailSet = psCheckEmailExists.executeQuery();
           
            psCheckidExists = conn.prepareStatement("SELECT * FROM admin WHERE idadmin = ?");
            psCheckidExists.setString(1,"" + admin_id);
            idSet = psCheckidExists.executeQuery();
           
            if(userSet.isBeforeFirst() || emailSet.isBeforeFirst() || idSet.isBeforeFirst()){               
                // JFrame parent = new JFrame();
                // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // JOptionPane.showMessageDialog(parent, "already exists");
                return "aleardy exists";
            }
            else{  
                String sqlInsert = "insert into admin values ("+admin_id+",\""+Fname+"\",\""+Lname+"\",\""+usrname+"\",\""+email+"\",\""+pword+"\")";
                System.out.println(sqlInsert);
                int countInserted = stmt.executeUpdate(sqlInsert);
                // JFrame parent = new JFrame();
                // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // JOptionPane.showMessageDialog(parent, "Added");
                admin_id++;
                return "added";
            }            

       } catch (SQLException e) {
          e.printStackTrace();
       }    
       return "";
    }

    synchronized String addProduct(String pname, int category_id, double price, int stock, String status){
       try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){
            PreparedStatement psCheckidExists = null;
            ResultSet idSet = null;
            psCheckidExists = conn.prepareStatement("SELECT * FROM product WHERE product_id = ?");
            psCheckidExists.setString(1,""+ product_id);
            idSet = psCheckidExists.executeQuery();
           
            if(idSet.isBeforeFirst()){               
                // JFrame parent = new JFrame();
                // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // JOptionPane.showMessageDialog(parent, "already exists");
                return "aleardy exists";
            }
            else{  
                String sqlInsert = "insert into product values ("+product_id+",\""+pname+"\",\""+category_id+"\",\""+price+"\",\""+stock+"\",\""+status+"\")";
                int countInserted = stmt.executeUpdate(sqlInsert);
                // JFrame parent = new JFrame();
                // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // JOptionPane.showMessageDialog(parent, "Added");
                product_id++;
                return "added";
            }

       } catch (SQLException e) {
          e.printStackTrace();
       }    
       return "";
    }
    
    synchronized String addCategory(String cname,String status){
       try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){
            PreparedStatement psChecknameExists = null;
            PreparedStatement psCheckidExists = null;
            ResultSet idSet = null;
            ResultSet nameSet = null;
            psCheckidExists = conn.prepareStatement("SELECT * FROM category WHERE idcategory = ?");
            psCheckidExists.setString(1,""+ category_id);
            idSet = psCheckidExists.executeQuery();
           
            psChecknameExists = conn.prepareStatement("SELECT * FROM category WHERE Cname = ?");
            psChecknameExists.setString(1, cname);
            nameSet = psChecknameExists.executeQuery();
           
            if(nameSet.isBeforeFirst() || idSet.isBeforeFirst()){               
                // JFrame parent = new JFrame();
                // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // JOptionPane.showMessageDialog(parent, "already exists");
                return "aleardy exists";
            }
            else{  
                String sqlInsert = "insert into category values ("+category_id+",\""+cname+"\",\""+status+"\")";
                int countInserted = stmt.executeUpdate(sqlInsert);
                // JFrame parent = new JFrame();
                // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // JOptionPane.showMessageDialog(parent, "Added");
                category_id++;
                return "added";
            }
       } catch (SQLException e) {
          e.printStackTrace();
       }    
       return "";
    }  
    
    synchronized String addToCart(int product_id, int cart_id, int quantity){
       try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){
            PreparedStatement psCheckidExists = null;
            ResultSet idSet = null;
            String strSelect = "select stock from product where product_id ="+product_id +";";
            ResultSet r = stmt.executeQuery(strSelect);
            int stock = 0;
            while(r.next()){
                stock = r.getInt("stock");
            }
            if(quantity > stock){
                // JFrame parent = new JFrame();
                // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // JOptionPane.showMessageDialog(parent, "Not enough in stock");
                return "not enough in stock";
            }            
            psCheckidExists = conn.prepareStatement("SELECT * FROM cart_product WHERE p_id = ? AND c_id = ?");
            psCheckidExists.setInt(1,product_id);
            psCheckidExists.setInt(2,cart_id);
            idSet = psCheckidExists.executeQuery();
           
            if(idSet.isBeforeFirst()){
                strSelect = "select quantity from cart_product where p_id = "+product_id+" and c_id = "+cart_id+";";
                r = stmt.executeQuery(strSelect);
                int q=0;
                 while(r.next()){
                    q = r.getInt(product_id);
                }                
                if(q+quantity > stock){
                    // JFrame parent = new JFrame();
                    // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    // JOptionPane.showMessageDialog(parent, "Not enough in stock");
                    return "not enough in stock";               
                }
                else{
                    String sqlInsert = "update cart_product set quantity = quantity + "+quantity+" where p_id = " + product_id +" and c_id = "+cart_id+";";
                    int countInserted = stmt.executeUpdate(sqlInsert);                
                    // JFrame parent = new JFrame();
                    // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    // JOptionPane.showMessageDialog(parent, "increased quantity");
                    return "increased quantity";
                }
            }
            else{  
                String sqlInsert = "insert into cart_product values ("+product_id+","+cart_id+","+quantity+")";
                int countInserted = stmt.executeUpdate(sqlInsert);
                // JFrame parent = new JFrame();
                // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // JOptionPane.showMessageDialog(parent, "Added");
                return "added";
            }

       } catch (SQLException e) {
          e.printStackTrace();
       }    
       return "";
    }
    
    synchronized String addOrder(int order_id,int product_id,int customer_id, String date, int quantity){
       try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){
            PreparedStatement psCheckOrderidExists = null;
            ResultSet orderidSet = null;
            String strSelect = "select stock from product where product_id ="+product_id +";";
            ResultSet r = stmt.executeQuery(strSelect);
            int stock = 0;
            while(r.next()){
                stock = r.getInt("stock");
            }
            if(quantity > stock){
                // JFrame parent = new JFrame();
                // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // JOptionPane.showMessageDialog(parent, "Not enough in stock");
                return "not enough in stock";
            }
            else{
                String sqlInsert = "update product set stock = stock - "+quantity+" where product_id = " + product_id +";";
                int countInserted = stmt.executeUpdate(sqlInsert);
            }              
            psCheckOrderidExists = conn.prepareStatement("SELECT * FROM _order WHERE order_id = ?");
            psCheckOrderidExists.setString(1, ""+ order_id);
            orderidSet = psCheckOrderidExists.executeQuery();
           double total_amount = 0;
            if(!orderidSet.isBeforeFirst()){               
                String sqlInsert = "insert into _order values ("+order_id+","+customer_id+",\""+date+"\","+0+")";
                int countInserted = stmt.executeUpdate(sqlInsert);                
            }
            String sqlInsert = "insert into order_product values ("+product_id+","+order_id+","+quantity+")";
            int countInserted = stmt.executeUpdate(sqlInsert);
            sqlInsert = "select price from product where product_id = "+product_id+";";
            r = stmt.executeQuery(sqlInsert);
            double price = 0;
            while(r.next()){
                price = r.getDouble("price");               
                System.out.println(price);
            }
            total_amount = price * quantity;
            sqlInsert = "update _order set total_amount = total_amount + "+total_amount+" where order_id = " + order_id +";";
            countInserted = stmt.executeUpdate(sqlInsert);            
            // JFrame parent = new JFrame();
            // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // JOptionPane.showMessageDialog(parent, "Added");
            return "added";
            
       } catch (SQLException e) {
          e.printStackTrace();
       }    
       return "";
    }

    synchronized int count_users(){
            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){
                  String strSelect = "select count(*) as Ccount from customer";
                  ResultSet r = stmt.executeQuery(strSelect);
                  r.next();
                  return r.getInt("Ccount");
            } catch (SQLException e) {
               e.printStackTrace();
            }    
              return 0;
    }

    synchronized int count_products(){
            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){
                  String strSelect = "select count(*) as Ccount from product";
                  ResultSet r = stmt.executeQuery(strSelect);
                  r.next();
                  return r.getInt("Ccount");
            } catch (SQLException e) {
               e.printStackTrace();
            }    
              return 0;
    }

    synchronized int count_categories(){
            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){
                  String strSelect = "select count(*) as Count from category";
                  ResultSet r = stmt.executeQuery(strSelect);
                  r.next();
                  return r.getInt("Ccount");
            } catch (SQLException e) {
               e.printStackTrace();
            }    
              return 0;
    }
    
    synchronized int count_orders(){
            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){
                  String strSelect = "select count(*) as Ccount from _order";
                  ResultSet r = stmt.executeQuery(strSelect);
                  r.next();
                  return r.getInt("Ccount");
            } catch (SQLException e) {
               e.printStackTrace();
            }    
              return 0;
    }
    
    synchronized int count_products_in_cart(int cart_id){
            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){
                  String strSelect = "select quantity from cart_product where c_id = "+cart_id;
                  ResultSet r = stmt.executeQuery(strSelect);
                  int total = 0;
                  while(r.next()){
                    total+= r.getInt("quantity");                      
                  }
                 return total;
                  
            } catch (SQLException e) {
               e.printStackTrace();
            }    
              return 0;
    }

    synchronized String get_user_firstName(int id){
            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){
                  String strSelect = "select Fname from customer where customer_id = "+id;
                  ResultSet r = stmt.executeQuery(strSelect);
                  while(r.next()){
                      return r.getString("Fname");
                  }
            } catch (SQLException e) {
               e.printStackTrace();
            //    JFrame parent = new JFrame();
            //    parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //    JOptionPane.showMessageDialog(parent, "doesn't exist");
               return "doesn't exist";
            }    
            return "doesn't exist";
    }

    synchronized String get_user_lastName(int id){
            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){
                  String strSelect = "select Lname from customer where customer_id = "+id;
                  ResultSet r = stmt.executeQuery(strSelect);
                  while(r.next()){
                      return r.getString("Lname");
                  }
            } catch (SQLException e) {
               e.printStackTrace();
            //    JFrame parent = new JFrame();
            //    parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //    JOptionPane.showMessageDialog(parent, "doesn't exist");
               return "doesn't exist";
            }    
            return "doesn't exist";
    }   

    synchronized int get_user_balance(int id){
            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();){
                String strSelect = "select balance from customer where customer_id = "+id;
                ResultSet r = stmt.executeQuery(strSelect);
                while(r.next()){
                    return r.getInt("balance");
                }
            } catch (SQLException e) {
               e.printStackTrace();
               return 0;
            }    
            return 0;
    }
    
    synchronized String get_user_password(int id){
            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();){
                String strSelect = "select pword from customer where customer_id = "+id;
                ResultSet r = stmt.executeQuery(strSelect);
                while(r.next()){
                    return r.getString("pword");
                }
            } catch (SQLException e) {
               e.printStackTrace();
            //    JFrame parent = new JFrame();
            //    parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //    JOptionPane.showMessageDialog(parent, "doesn't exist");
               return "doesn't exist";
            }    
            return "doesn't exist";
    }
    
    synchronized String get_user_name(int id){
            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){
                String strSelect = "select userName from customer where customer_id = "+id;
                ResultSet r = stmt.executeQuery(strSelect);
                while(r.next()){
                    return r.getString("userName");
                }
            } catch (SQLException e) {
               e.printStackTrace();
            //    JFrame parent = new JFrame();
            //    parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //    JOptionPane.showMessageDialog(parent, "doesn't exist");
               return "doesn't exist";
            }    
            return "doesn't exist";
    }

    synchronized String get_user_email(int id){
            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){
                  String strSelect = "select email from customer where customer_id = "+id;
                  ResultSet r = stmt.executeQuery(strSelect);
                  while(r.next()){
                      return r.getString("email");
                  }
            } catch (SQLException e) {
               e.printStackTrace();
            //    JFrame parent = new JFrame();
            //    parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //    JOptionPane.showMessageDialog(parent, "doesn't exist");
               return "doesn't exist";
            }    
            return "doesn't exist";
    }

    synchronized int[] get_orderID(int customer_id){
      int a[] = {};
            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){
                String str = "select count(order_id) as Corder from _order group by cust_id having cust_id = "+customer_id;
                ResultSet rs = stmt.executeQuery(str);
                rs.next();
                int n = rs.getInt("Corder");
                a = new int[n];
                int i =0;
                  String strSelect = "select order_id from _order where cust_id = "+customer_id;
                  ResultSet r = stmt.executeQuery(strSelect);
                  while(r.next()){
                      a[i++] = r.getInt("order_id");
                  }
                  return a;
            } catch (SQLException e) {
               e.printStackTrace();
               return a;
            }    
    }

    synchronized int[] get_allUsersID(){
      int a[] = {};
            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){
                String str = "select count(customer_id) as countc from customer";
                ResultSet rs = stmt.executeQuery(str);
                rs.next();
                int n = rs.getInt("countc");
                a = new int[n];
                int i =0;
                  String strSelect = "select customer_id from customer";
                  ResultSet r = stmt.executeQuery(strSelect);
                  while(r.next()){
                      a[i++] = r.getInt("customer_id");
                  }
                  return a;
            } catch (SQLException e) {
               e.printStackTrace();
               return a;
            }    
    }

    synchronized int get_UserID(String username){
            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){
                  String strSelect = "select customer_id from customer where userName = \"" + username+"\"";
                  ResultSet r = stmt.executeQuery(strSelect);
                  while(r.next()){
                      return r.getInt("customer_id");
                  }
            } catch (SQLException e) {
               e.printStackTrace();
               return -1;
            }    
            return -1;
    }

    synchronized int[] get_allProductsID(){
      int a[] = {};
            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){
                String str = "select count(product_id) as countp from product";
                ResultSet rs = stmt.executeQuery(str);
                rs.next();
                int n = rs.getInt("countp");
                a = new int[n];
                int i =0;
                  String strSelect = "select product_id from product";
                  ResultSet r = stmt.executeQuery(strSelect);
                  while(r.next()){
                      a[i++] = r.getInt("product_id");
                  }
                  return a;
            } catch (SQLException e) {
               e.printStackTrace();
               return a;
            }    
    }

    synchronized int[] get_allCategoriesID(){
      int a[] = {};
            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){
                String str = "select count(idcategory) as countp from category";
                ResultSet rs = stmt.executeQuery(str);
                rs.next();
                int n = rs.getInt("countp");
                a = new int[n];
                int i =0;
                  String strSelect = "select idcategory from category";
                  ResultSet r = stmt.executeQuery(strSelect);
                  while(r.next()){
                      a[i++] = r.getInt("idcategory");
                  }
                  return a;
            } catch (SQLException e) {
               e.printStackTrace();
               return a;
            }    
    }
    
    synchronized int get_ProductID(String name){
            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){
                String str = "select product_id from product where Pname = \""+name+"\"";
                ResultSet rs = stmt.executeQuery(str);
                rs.next();
                int n = rs.getInt("product_id");
                  return n;
            } catch (SQLException e) {
               e.printStackTrace();
               return -1;
            }    
    }

    synchronized int get_CategoryID(String name){
            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){
                String str = "select idcategory as countp from category where cname = \""+name+"\"";
                ResultSet rs = stmt.executeQuery(str);
                rs.next();
                int n = rs.getInt("countp");
                  return n;
            } catch (SQLException e) {
               e.printStackTrace();
               return -1;
            }    
    }

    synchronized int[] get_allOrdersID(){
      int a[] = {};
            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){
                String str = "select count(order_id) as countp from _order";
                ResultSet rs = stmt.executeQuery(str);
                rs.next();
                int n = rs.getInt("countp");
                a = new int[n];
                int i =0;
                  String strSelect = "select order_id from _order";
                  ResultSet r = stmt.executeQuery(strSelect);
                  while(r.next()){
                      a[i++] = r.getInt("order_id");
                  }
                  return a;
            } catch (SQLException e) {
               e.printStackTrace();
               return a;
            }    
    }
    
    synchronized int get_order_count(int user_id){
            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){
                  String strSelect = "select count(*) as c from _order where cust_id = "+ user_id;
                  ResultSet r = stmt.executeQuery(strSelect);
                  int n = 0;
                  while(r.next()){
                    n = r.getInt("c");
                  }
                  return n;
            } catch (SQLException e) {
               e.printStackTrace();
               return -1;
            }    
    }

    synchronized String get_orderDate(int order_id){
            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){
                  String strSelect = "select ordered_at from _order where order_id = "+order_id;
                  ResultSet r = stmt.executeQuery(strSelect);
                  while(r.next()){
                      return r.getString("ordered_at");
                  }
            } catch (SQLException e) {
               e.printStackTrace();
            //    JFrame parent = new JFrame();
            //    parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //    JOptionPane.showMessageDialog(parent, "doesn't exist");
               return "doesn't exist";
            }    
            return "doesn't exist";
    }

    synchronized int get_orderTotalAmount(int order_id){
            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){
                  String strSelect = "select total_amount from _order where order_id = "+order_id;
                  ResultSet r = stmt.executeQuery(strSelect);
                  while(r.next()){
                      return r.getInt("total_amount");
                  }
            } catch (SQLException e) {
               e.printStackTrace();
               return -1;
            }    
            return -1;
    }

    synchronized String get_product_name(int product_id){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
           Statement stmt = conn.createStatement();){
              String strSelect = "select pname from product where product_id ="+product_id;
              ResultSet r = stmt.executeQuery(strSelect);
              while(r.next()){
                  return r.getString("pname");
              }
        } catch (SQLException e) {
           e.printStackTrace();
        //    JFrame parent = new JFrame();
        //    parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //    JOptionPane.showMessageDialog(parent, "doesn't exist");
           return "doesn't exist";
        }    
        return "doesn't exist";
    }  
    synchronized int get_product_category(int product_id){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
           Statement stmt = conn.createStatement();){
              String strSelect = "select category_id from product where product_id = "+product_id;
              ResultSet r = stmt.executeQuery(strSelect);
              while(r.next()){
                  return r.getInt("category_id");
              }
        } catch (SQLException e) {
           e.printStackTrace();
        //    JFrame parent = new JFrame();
        //    parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //    JOptionPane.showMessageDialog(parent, "doesn't exist");
           return -1;
        }    
        return -1;
    } 
    
    synchronized String get_category_name(int category_id){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
           Statement stmt = conn.createStatement();){
              String strSelect = "select Cname from category where idcategory ="+category_id;
              ResultSet r = stmt.executeQuery(strSelect);
              while(r.next()){
                  return r.getString("Cname");
              }
        } catch (SQLException e) {
           e.printStackTrace();
        //    JFrame parent = new JFrame();
        //    parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //    JOptionPane.showMessageDialog(parent, "doesn't exist");
           return "doesn't exist";
        }    
        return "doesn't exist";
    }
    
    

    synchronized double get_product_price(int product_id){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
           Statement stmt = conn.createStatement();){
              String strSelect = "select price from product where product_id ="+product_id;
              ResultSet r = stmt.executeQuery(strSelect);
              while(r.next()){
                  return r.getDouble("price");
              }
        } catch (SQLException e) {
           e.printStackTrace();
           return 0;
        }    
        return 0;
    }
    
    synchronized int get_product_quantity(int product_id){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
           Statement stmt = conn.createStatement();){
              String strSelect = "select stock from product where product_id ="+product_id;
              ResultSet r = stmt.executeQuery(strSelect);
              while(r.next()){
                  return r.getInt("quantity");
              }
        } catch (SQLException e) {
           e.printStackTrace();
        //    JFrame parent = new JFrame();
        //    parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //    JOptionPane.showMessageDialog(parent, "doesn't exist");
           return -1;
        }    
        return -1;
    }
    
    synchronized String get_product_status(int product_id){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
           Statement stmt = conn.createStatement();){
              String strSelect = "select status from product where product_id ="+product_id;
              ResultSet r = stmt.executeQuery(strSelect);
              while(r.next()){
                  return r.getString("status");
              }
        } catch (SQLException e) {
           e.printStackTrace();
        //    JFrame parent = new JFrame();
        //    parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //    JOptionPane.showMessageDialog(parent, "doesn't exist");
           return "doesn't exist";
        }    
        return "doesn't exist";
    }
    
    synchronized String get_category_status(int category_id){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
           Statement stmt = conn.createStatement();){
              String strSelect = "select status from category where idcategory ="+category_id;
              ResultSet r = stmt.executeQuery(strSelect);
              while(r.next()){
                  return r.getString("status");
              }
        } catch (SQLException e) {
           e.printStackTrace();
        //    JFrame parent = new JFrame();
        //    parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //    JOptionPane.showMessageDialog(parent, "doesn't exist");
           return "doesn't exist";
        }    
        return "doesn't exist";
    }
    
    synchronized int get_order_product_quantity(int order_id,int product_id){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
           Statement stmt = conn.createStatement();){
              String strSelect = "select quantity from order_product where pro_id = "+product_id+" and o_id = "+order_id+";";
              ResultSet r = stmt.executeQuery(strSelect);
              while(r.next()){
                  return r.getInt("quantity");
              }
        } catch (SQLException e) {
           e.printStackTrace();
        //    JFrame parent = new JFrame();
        //    parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //    JOptionPane.showMessageDialog(parent, "doesn't exist");
           return -1;
        }    
        return -1;
    }  
    
    synchronized int[] get_orderProducts(int order_id){
      int a[] = {};
            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){
                String strSelect = "select o_id from order_product where o_id = "+order_id+";";
                ResultSet r = stmt.executeQuery(strSelect);
                if(r.isBeforeFirst()){
                    String str = "select count(o_id) as Corder from order_product group by o_id having o_id = "+order_id;
                    ResultSet rs = stmt.executeQuery(str);
                    rs.next();
                    int n = rs.getInt("Corder");
                    a = new int[n];
                    int i =0;
                    strSelect = "select pro_id from order_product where o_id = "+order_id;
                    r = stmt.executeQuery(strSelect);
                    while(r.next()){
                        a[i++] = r.getInt("pro_id");
                    }
                    return a;
                }
                else{
                    // JFrame parent = new JFrame();
                    // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    // JOptionPane.showMessageDialog(parent, "doesn't exist");                
                }
            } catch (SQLException e) {
               e.printStackTrace();
               return a;
            } 
            return a;
    }
    
    synchronized String increase_balance(int customer_id, int amount){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){               
            String sqlInsert = "update customer set balance = balance + "+amount+" where customer_id = " + customer_id +";";
            int countInserted = stmt.executeUpdate(sqlInsert);
            // JFrame parent = new JFrame();
            // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // JOptionPane.showMessageDialog(parent, "Added to balance");
            return "Added to balance";
        } catch (SQLException e) {
          e.printStackTrace();
          return"";
        }          
    }
    
    synchronized String decrease_balance(int customer_id, int amount){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){
            String strSelect = "select balance from customer where customer_id ="+customer_id +";";
            ResultSet r = stmt.executeQuery(strSelect);
            int b = 0;
            while(r.next()){
                b = r.getInt("balance");
            }
            if(amount > b){
                // JFrame parent = new JFrame();
                // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // JOptionPane.showMessageDialog(parent, "Not enough in balance");
                return "Not enough in balance";
            }
            else{
                String sqlInsert = "update customer set balance = balance - "+amount+" where customer_id = " + customer_id +";";
                int countInserted = stmt.executeUpdate(sqlInsert);
                // JFrame parent = new JFrame();
                // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // JOptionPane.showMessageDialog(parent, "Added to balance");
                return "Added to balance";

            }           
        } catch (SQLException e) {
          e.printStackTrace();
       }  
       return "";
    }

    synchronized String increase_stock(int product_id, int amount){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){               
            String sqlInsert = "update product set stock = stock + "+amount+" where product_id = " + product_id +";";
            int countInserted = stmt.executeUpdate(sqlInsert);
            // JFrame parent = new JFrame();
            // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // JOptionPane.showMessageDialog(parent, "Added to stock");
            return "Added to stock";
        } catch (SQLException e) {
          e.printStackTrace();
        }          
        return "";
    }
    
    synchronized void clear_cart(int cart_id){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){
         String sqlInsert = "delete from cart_product where c_id = " +cart_id +";";
         int countInserted = stmt.executeUpdate(sqlInsert);
        } catch (SQLException e) {
          e.printStackTrace();
       }          
    }

    synchronized String get_admin_firstName(int id){
            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){
                  String strSelect = "select Fname from admin where idadmin = "+id;
                  ResultSet r = stmt.executeQuery(strSelect);
                  while(r.next()){
                      return r.getString("Fname");
                  }
            } catch (SQLException e) {
               e.printStackTrace();
            //    JFrame parent = new JFrame();
            //    parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //    JOptionPane.showMessageDialog(parent, "doesn't exist");
               return "doesn't exist";
            }    
            return "doesn't exist";
    }

    synchronized String get_admin_lastName(int id){
            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){
                  String strSelect = "select Lname from admin where idadmin = "+id;
                  ResultSet r = stmt.executeQuery(strSelect);
                  while(r.next()){
                      return r.getString("Lname");
                  }
            } catch (SQLException e) {
               e.printStackTrace();
            //    JFrame parent = new JFrame();
            //    parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //    JOptionPane.showMessageDialog(parent, "doesn't exist");
               return "doesn't exist";
            }    
            return "doesn't exist";
    }   

    synchronized String get_admin_username(int id){
            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){
                  String strSelect = "select userName from admin where idadmin = "+id;
                  ResultSet r = stmt.executeQuery(strSelect);
                  while(r.next()){
                      return r.getString("userName");
                  }
            } catch (SQLException e) {
               e.printStackTrace();
            //    JFrame parent = new JFrame();
            //    parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //    JOptionPane.showMessageDialog(parent, "doesn't exist");
               return "doesn't exist";
            }    
            return "doesn't exist";
    }

    synchronized String get_admin_email(int id){
            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){
                  String strSelect = "select email from admin where idadmin = "+id;
                  ResultSet r = stmt.executeQuery(strSelect);
                  while(r.next()){
                      return r.getString("email");
                  }
            } catch (SQLException e) {
               e.printStackTrace();
            //    JFrame parent = new JFrame();
            //    parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //    JOptionPane.showMessageDialog(parent, "doesn't exist");
               return "doesn't exist";
            }    
            return "doesn't exist";
    }
    
    synchronized String change_password(int id, String password){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
       Statement stmt = conn.createStatement();){
            PreparedStatement psCheckpasswordExists = null;
            ResultSet passwordSet = null;
            psCheckpasswordExists = conn.prepareStatement("SELECT * FROM customer WHERE pword = ?");
            psCheckpasswordExists.setString(1, password);
            passwordSet = psCheckpasswordExists.executeQuery();

            if(passwordSet.isBeforeFirst()){               
                // JFrame parent = new JFrame();
                // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // JOptionPane.showMessageDialog(parent, "already exists");
                return "aleardy exists";
            }
            else{
                String sqlInsert = "update customer set pword = \""+password+"\" where customer_id = " + id +";";
                int countInserted = stmt.executeUpdate(sqlInsert);
                // JFrame parent = new JFrame();
                // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // JOptionPane.showMessageDialog(parent, "Updated");
                return "Updated";
            }

        } catch (SQLException e) {
          e.printStackTrace();
       }          
        return "";
    }
    
    synchronized String change_userName(int id, String userName){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
       Statement stmt = conn.createStatement();){
                PreparedStatement psCheckpasswordExists = null;
                ResultSet passwordSet = null;
                psCheckpasswordExists = conn.prepareStatement("SELECT * FROM customer WHERE userName = ?");
                psCheckpasswordExists.setString(1, userName);
                passwordSet = psCheckpasswordExists.executeQuery();

                if(passwordSet.isBeforeFirst()){               
                    // JFrame parent = new JFrame();
                    // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    // JOptionPane.showMessageDialog(parent, "already exists");
                    return "aleardy exists";
                }
                else{
                    String sqlInsert = "update customer set userName = \""+userName+"\" where customer_id = " + id +";";
                    int countInserted = stmt.executeUpdate(sqlInsert);
                    // JFrame parent = new JFrame();
                    // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    // JOptionPane.showMessageDialog(parent, "Updated");
                    return "Updated";

                }
        } catch (SQLException e) {
          e.printStackTrace();
       }          
        return "";
    }
    
    synchronized void change_FirstName(int id, String Fname){
                try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){
         String sqlInsert = "update customer set Fname = \""+Fname+"\" where customer_id = " + id +";";
         int countInserted = stmt.executeUpdate(sqlInsert);
        } catch (SQLException e) {
          e.printStackTrace();
       }          

    }
    
    synchronized void change_LastName(int id, String Lname){
                try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){
         String sqlInsert = "update customer set Lname = \""+Lname+"\" where customer_id = " + id +";";
         int countInserted = stmt.executeUpdate(sqlInsert);
        } catch (SQLException e) {
          e.printStackTrace();
       }          

    }
    
    synchronized String change_CategoryName(int id, String category_name){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = conn.createStatement();){
        String sqlInsert = "select idcategory from category where idcategory = " + id +";";
        ResultSet r = stmt.executeQuery(sqlInsert);
        if(r.isBeforeFirst()){
            sqlInsert = "update category set Cname = \""+category_name+"\" where idcategory = " + id +";";
            int countInserted = stmt.executeUpdate(sqlInsert);  
            // JFrame parent = new JFrame();
            // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // JOptionPane.showMessageDialog(parent, "Category updated");             
            return "category updated";
        }
        else{
            // JFrame parent = new JFrame();
            // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // JOptionPane.showMessageDialog(parent, "Category doesn't exist"); 
            return "category doesn't exist";
        }
        } catch (SQLException e) {
          e.printStackTrace();
       }          
        return "";
    }
    
    synchronized String change_CategoryStatus(int id, String status){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = conn.createStatement();){
        String sqlInsert = "select idcategory from category where idcategory = " + id +";";
        ResultSet r = stmt.executeQuery(sqlInsert);
        if(r.isBeforeFirst()){
            sqlInsert = "update category set status = \""+status+"\" where idcategory = " + id +";";
            int countInserted = stmt.executeUpdate(sqlInsert);
            // JFrame parent = new JFrame();
            // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // JOptionPane.showMessageDialog(parent, "Category updated");  
            return "Category updated";
        }
        else{
            // JFrame parent = new JFrame();
            // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // JOptionPane.showMessageDialog(parent, "Category doesn't exist"); 
            return "Category doesn't exist";
        }
        } catch (SQLException e) {
          e.printStackTrace();
       }          
        return "";
    }
    
    synchronized String delete_user(int id){
                    try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){
                  String sqlDelete = "delete from customer where customer_id = "+id;
                  int countDeleted = stmt.executeUpdate(sqlDelete);
                  if(countDeleted == 0){
                    // JFrame parent = new JFrame();
                    // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    // JOptionPane.showMessageDialog(parent, "Doesn't exist");
                    return "Doesn't exist";
                  }
                  else{
                    // JFrame parent = new JFrame();
                    // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    // JOptionPane.showMessageDialog(parent, "Deleted");
                    return "Deleted";
                  }
            } catch (SQLException e) {
               e.printStackTrace();
            }    
            return"";

    }
    
    synchronized String delete_from_cart(int product_id, int customer_id){
                try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
               Statement stmt = conn.createStatement();){    
                    String sqlDelete = "delete from cart_product where p_id = "+product_id+" and c_id = "+customer_id+";";
                    int countDeleted = stmt.executeUpdate(sqlDelete);
                     if(countDeleted == 0){
                    // JFrame parent = new JFrame();
                    // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    // JOptionPane.showMessageDialog(parent, "Doesn't exist");
                    return "Doesn't exist";
                    }
                    else{
                    // JFrame parent = new JFrame();
                    // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    // JOptionPane.showMessageDialog(parent, "Deleted");
                    return "Deleted";
                }
            } catch (SQLException e) {
               e.printStackTrace();
            }    
                return "";
    }
    
    synchronized int[] searchByCategory(String category){
                int[] arr = {};
                int n = 0;
                try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();){
                String sqlSelect = "select idcategory from category where Cname = \""+ category+"\";";
                ResultSet r = stmt.executeQuery(sqlSelect);
                int category_id = 0;
                if(r.isBeforeFirst()){
                    while(r.next()){
                        category_id = r.getInt("idcategory");
                    }
                    String str = "select count(product_id) as c from product group by category_id having category_id = "+ category_id;
                    ResultSet rs = stmt.executeQuery(str);
                    rs.next();
                    n = rs.getInt("c");
                    arr = new int[n];
                    sqlSelect = "select product_id from product where category_id = "+ category_id+";";
                    r = stmt.executeQuery(sqlSelect);
                    int i = 0;
                    while(r.next()){
                        arr[i++] = r.getInt("product_id");
                    }                       
                }
                else{
                    // JFrame parent = new JFrame();
                    // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    // JOptionPane.showMessageDialog(parent, "Doesn't exist");                  
                }
            } catch (SQLException e) {
               e.printStackTrace();
            }    
           return arr;     
    }
    
    synchronized int[] searchByName(String name){
                int[] arr = {};
                int n = 0;
                try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();){
                    String strSelect = "select product_id from product where Pname like \"%"+name+"%\"";
                    ResultSet r = stmt.executeQuery(strSelect);
                    if(r.isBeforeFirst()){
                        String str = "select count(product_id) as c from product where Pname like \"%"+name+"%\"";
                        ResultSet rs = stmt.executeQuery(str); 
                        rs.next();
                        n = rs.getInt("c");
                        strSelect = "select product_id from product where Pname like \"%"+name+"%\"";
                        r = stmt.executeQuery(strSelect);
                        arr = new int[n];
                        int i = 0;
                        while(r.next()){
                        arr[i++] = r.getInt("product_id");
                        }                     
                    }
                        
                 else{
                    // JFrame parent = new JFrame();
                    // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    // JOptionPane.showMessageDialog(parent, "Doesn't exist");                  
                }
            } catch (SQLException e) {
               e.printStackTrace();
            }    
           return arr;     
    }
    
    synchronized String change_ProductName(int id, String product_name){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();){
            String sqlInsert = "select product_id from product where product_id = " + id +";";
            ResultSet r = stmt.executeQuery(sqlInsert);
            if(r.isBeforeFirst()){
                sqlInsert = "update product set Pname = \""+product_name+"\" where product_id = " + id +";";
                int countInserted = stmt.executeUpdate(sqlInsert);  
                // JFrame parent = new JFrame();
                // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // JOptionPane.showMessageDialog(parent, "product updated");     
                return "product updated";
        }
        else{
            // JFrame parent = new JFrame();
            // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // JOptionPane.showMessageDialog(parent, "product doesn't exist"); 
            return "product doesn't exist";
        }
        } catch (SQLException e) {
          e.printStackTrace();
       }          
        return "";
    }    
    
    synchronized String change_ProductPrice(int id, double price){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();){
            String sqlInsert = "select price from product where product_id = " + id +";";
            ResultSet r = stmt.executeQuery(sqlInsert);
            if(r.isBeforeFirst()){
                sqlInsert = "update product set price = "+price+" where product_id = " + id +";";
                int countInserted = stmt.executeUpdate(sqlInsert);  
                // JFrame parent = new JFrame();
                // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // JOptionPane.showMessageDialog(parent, "product updated");   
                return "product updated";
        }
        else{
            // JFrame parent = new JFrame();
            // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // JOptionPane.showMessageDialog(parent, "product doesn't exist"); 
            return "product doesn't exist";
        }
        } catch (SQLException e) {
          e.printStackTrace();
       }          
        return "";

    }    
    
    synchronized String change_ProductCategory(int id, String category_name){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();){
            String sqlInsert = "select category_id from product where product_id = " + id +";";
            ResultSet r = stmt.executeQuery(sqlInsert);
            if(r.isBeforeFirst()){
                String sqlSelect = "select idcategory from category where Cname = \"" + category_name +"\";";
                ResultSet rid = stmt.executeQuery(sqlSelect);
                rid.next();
                int Cid = rid.getInt("idcategory");
                sqlInsert = "update product set category_id = "+Cid+" where product_id = " + id +";";
                int countInserted = stmt.executeUpdate(sqlInsert);  
                // JFrame parent = new JFrame();
                // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // JOptionPane.showMessageDialog(parent, "product updated"); 
                return "product updated";
        }
        else{
            // JFrame parent = new JFrame();
            // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // JOptionPane.showMessageDialog(parent, "product doesn't exist");
            return "product doesn't exist";
        }
        } catch (SQLException e) {
          e.printStackTrace();
       }          
        return "";
    }    
    
    synchronized String change_ProductStatus(int id, String status){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = conn.createStatement();){
        String sqlInsert = "select product_id from product where product_id = " + id +";";
        ResultSet r = stmt.executeQuery(sqlInsert);
        if(r.isBeforeFirst()){
            sqlInsert = "update product set status = \""+status+"\" where product_id = " + id +";";
            int countInserted = stmt.executeUpdate(sqlInsert);
            // JFrame parent = new JFrame();
            // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // JOptionPane.showMessageDialog(parent, "product updated");     
            return "product updated";
        }
        else{
            // JFrame parent = new JFrame();
            // parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // JOptionPane.showMessageDialog(parent, "product doesn't exist"); 
            return "product doesn't exist";
        }
        } catch (SQLException e) {
          e.printStackTrace();
       }          
        return "";
    }    
	
	synchronized void set_static_user_id(){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = conn.createStatement();){
        String sqlSelect = "SELECT customer_id FROM customer ORDER BY customer_id DESC LIMIT 1";
        ResultSet r = stmt.executeQuery(sqlSelect);
        if(r.isBeforeFirst()){
            while(r.next()){
                user_id = r.getInt("customer_id");
            }
            user_id++;
        }
        else{
            user_id = 1;
        }
        } catch (SQLException e) {
          e.printStackTrace();
       }          
    }
	
	synchronized void set_static_admin_id(){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = conn.createStatement();){
        String sqlSelect = "SELECT idadmin FROM admin ORDER BY idadmin DESC LIMIT 1";
        ResultSet r = stmt.executeQuery(sqlSelect);
        if(r.isBeforeFirst()){
            while(r.next()){
                admin_id = r.getInt("idadmin");
            }
            admin_id++;
        }
        else{
            admin_id = 1;
        }
        } catch (SQLException e) {
          e.printStackTrace();
       }          
    }
	
	synchronized void set_static_product_id(){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = conn.createStatement();){
        String sqlSelect = "SELECT product_id FROM product ORDER BY product_id DESC LIMIT 1";
        ResultSet r = stmt.executeQuery(sqlSelect);
        if(r.isBeforeFirst()){
            while(r.next()){
                product_id = r.getInt("product_id");
            }
            product_id++;
        }
        else{
            product_id = 1;
        }
        } catch (SQLException e) {
          e.printStackTrace();
       }          
    }
	
	synchronized void set_static_category_id(){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = conn.createStatement();){
        String sqlSelect = "SELECT idcategory FROM category ORDER BY idcategory DESC LIMIT 1";
        ResultSet r = stmt.executeQuery(sqlSelect);
        if(r.isBeforeFirst()){
            while(r.next()){
                category_id = r.getInt("idcategory");
            }
            category_id++;
        }
        else{
            category_id = 1;
        }
        } catch (SQLException e) {
          e.printStackTrace();
       }          
    }
}