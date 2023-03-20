package SuperMarket;


public class Users {
    private String FirstName;
    private String LastName;
    private String E_mail;
    private String UserName;
    private int E_wallet;
    private int User_orders;

    public Users(String firstName, String lastName, String e_mail, String userName, int e_wallet, int user_orders) {
        FirstName = firstName;
        LastName = lastName;
        E_mail = e_mail;
        UserName = userName;
        E_wallet = e_wallet;
        User_orders = user_orders;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getE_mail() {
        return E_mail;
    }

    public String getUserName() {
        return UserName;
    }

    public int getE_wallet() {
        return E_wallet;
    }

    public int getUser_orders() {
        return User_orders;
    }
}