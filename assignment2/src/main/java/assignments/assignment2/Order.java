package assignments.assignment2;

import java.util.ArrayList; // XXX: Apakah parameter boleh diganti?


public class Order {
    // Attributes yang dimiliki order dari sebuah user
    private String orderId;
    private String tanggalPemesanan;
    private String biayaOngkosKirim;
    private Restaurant restaurant;
    protected Menu[] items;
    protected boolean orderFinished;


    public Order(String orderId, String tanggal, String ongkir, Restaurant resto, Menu[] items){
        this.orderId = orderId;
        this.tanggalPemesanan = tanggal;
        this.biayaOngkosKirim = ongkir;
        this.restaurant = resto;
        this.items = items;
        this.orderFinished = false; // Initial Value = Order belum diselesaikan
    }
    
    // Getter
    public String getOrderId(){
        return orderId;
    }

    public String getTanggalPemesanan(){
        return tanggalPemesanan;
    }

    public String getBiayaOngkosKirim(){
        return biayaOngkosKirim;
    }

    public Restaurant getRestaurant(){
        return restaurant;
    }
}
