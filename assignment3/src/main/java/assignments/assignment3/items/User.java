package assignments.assignment3.items;

import java.util.ArrayList;
import assignments.assignment3.payment.DepeFoodPaymentSystem;

// Class User: Object user dari sistem

public class User {
    // Attributes yang dimiliki seorang User
    private String nama;
    private String nomorTelepon;
    private String email;
    private String lokasi;
    private String role;

    protected ArrayList<Order> orderHistory = new ArrayList<Order>(); // Menyimpan order yang pernah dipesan oleh user

    // Untuk TP 3: Sistem Payment dan Saldo User
    private DepeFoodPaymentSystem payment;
    private long saldo;

    // Constructor
    public User(String nama, String nomorTelepon, String email, String lokasi, String role, DepeFoodPaymentSystem payment, long saldo){
        this.nama = nama;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.lokasi = lokasi;
        this.role = role;
        this.payment = payment;
        this.saldo = saldo;
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

    public void addOrderHistory(Order order){
        orderHistory.add(order);
    }

    public ArrayList<Order> getOrderHistory(){
        ArrayList<Order> orderList = new ArrayList<>();
        for(Order order: orderHistory){
            orderList.add(order);
        }

        return orderList;
    }

    public DepeFoodPaymentSystem getPaymentSystem(){
        return payment;
    }

    public long getSaldo(){
        return this.saldo;
    }

    public void setSaldo(long amount){
        this.saldo = (long) amount;
    }
}
