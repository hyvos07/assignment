package assignments.assignment2;

import java.util.ArrayList;

public class Restaurant {
    // Attributes yang dimiliki sebuah Restaurant
    private String nama;
    private ArrayList<Menu> menu; // Menyimpan menu yang disediakan oleh restaurant ini

    public Restaurant(String nama){
        this.nama = nama;
    }
    
    // Getter
    public String getNama(){
        return nama;
    }

    public ArrayList<Menu> getMenu(){
        return menu;
    }
}
