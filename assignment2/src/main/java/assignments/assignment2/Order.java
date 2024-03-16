package assignments.assignment2;

import java.util.ArrayList;
import assignments.assignment1.OrderGenerator;


public class Order {
    // Attributes yang dimiliki order dari sebuah user
    private String orderId;
    private String tanggalPemesanan;
    private int biayaOngkosKirim;
    private Restaurant restaurant;
    protected ArrayList<Menu> items;
    protected boolean orderFinished;


    public Order(String orderId, String tanggal, int ongkir, Restaurant resto, Menu[] items){
        // TODO: buat constructor untuk class ini
    }
    
    // Getter
    public String getOrderId(){
        return orderId;
    }

    public String getTanggalPemesanan(){
        return tanggalPemesanan;
    }

    public int getBiayaOngkosKirim(){
        return biayaOngkosKirim;
    }

    public Restaurant getRestaurant(){
        return restaurant;
    }
}
