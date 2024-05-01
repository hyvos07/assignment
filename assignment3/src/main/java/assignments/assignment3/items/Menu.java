package assignments.assignment3.items;

// Class Menu merepresentasikan sebuah object menu makanan yang dimiliki oleh restoran [Diadaptasi dari TP 2]

public class Menu {
    // Attributes yang dimiliki sebuah menu makanan
    private String namaMakanan;
    private double harga;

    public Menu(String namaMakanan, double harga){
        this.namaMakanan = namaMakanan;
        this.harga = harga;
    }
    
    // Getter
    public String getNamaMakanan(){
        return namaMakanan;
    }

    public double getHarga(){
        return harga;
    }
}
