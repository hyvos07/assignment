package assignments.assignment2;

import java.util.ArrayList;

public class User {
    // Attributes yang dimiliki seorang User
    private String nama;
    private String nomorTelepon;
    private String email;
    private String lokasi;
    private String role;

    protected ArrayList<Order> orderHistory = new ArrayList<Order>(); // Menyimpan order yang pernah dipesan oleh user

    // Constructor
    public User(String nama, String nomorTelepon, String email, String lokasi, String role){
        this.nama = nama;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.lokasi = lokasi;
        this.role = role;
    }

    // Getter
    public String getNama(){
        return nama;
    }

    public String getNomorTelepon(){
        return nomorTelepon;
    }

    public String getEmail(){
        return email;
    }

    public String getLokasi(){
        return lokasi;
    }

    public String getRole(){
        return role;
    }
}
