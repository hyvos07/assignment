package assignments.assignment3.items;

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

    public boolean getOrderStatus(){
        return orderFinished;
    }

    public void setOrderStatus(){
        this.orderFinished = !this.orderFinished;
    }

    public Menu[] getItems(){
        return items;
    }

    // Method
    public int calculateTotal(){
        int total = 0;
        for (int i = 0; i < items.length; i++){
            total += items[i].getHarga();
        }
        total += Integer.parseInt(biayaOngkosKirim);
        return (int) total;
    }
}
