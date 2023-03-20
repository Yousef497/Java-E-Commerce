/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SuperMarket;

/**
 *
 * @author DEYOKHHA
 */
public class Category {
    private int ID;
    private String Category_Name;
    private String Status;

    public Category(int ID, String Category_Name, String Status) {
        this.ID = ID;
        this.Category_Name = Category_Name;
        this.Status = Status;
    }

    public int getID() {
        return ID;
    }

    public String getCategory_Name() {
        return Category_Name;
    }

    public String getStatus() {
        return Status;
    }


    public void setCategory_Name(String Category_Name) {
        this.Category_Name = Category_Name;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }
    
}
