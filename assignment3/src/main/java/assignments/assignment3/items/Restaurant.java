package assignments.assignment3.items;

import java.util.ArrayList;

// Class Restaurant merepresentasikan sebuah object restoran yang menyediakan berbagai menu makanan [Diadaptasi dari TP 2]

public class Restaurant {
    // Attributes yang dimiliki sebuah Restaurant
    private String nama;
    private int jumlahMenu;
    private ArrayList<Menu> menuList = new ArrayList<Menu>(); // Menyimpan menu yang disediakan oleh restaurant ini

    // Untuk TP 3: Saldo atau penghasilan dari Restoran
    long saldo;

    public Restaurant(String nama, int jumlahMenu, long saldo){
        this.nama = nama;
        this.jumlahMenu = jumlahMenu;
        this.saldo = saldo;
    }
    
    // Getter
    public String getNama(){
        return nama;
    }

    public Menu getMenu(int index){
        return menuList.get(index);
    }

    public int getJumlahMenu(){
        return jumlahMenu;
    }

    // Untuk TP 3
    public long getSaldo(){
        return saldo;
    }

    public void setSaldo(long amount){
        this.saldo = amount;
    }

    // ====== Method ======
    
    // Menambahkan semua menu baru ke dalam menuList
    public void addMenu(ArrayList<Menu> newMenus){
        for (int i = 0; i < newMenus.size(); i++){
            menuList.add(newMenus.get(i));
        }
    }

    // Mencari menu bedasarkan namanya
    public Menu searchMenu(String namaMakanan){
        for (int i = 0; i < menuList.size(); i++){
            if (menuList.get(i).getNamaMakanan().equals(namaMakanan)){
                return menuList.get(i);
            }
        }
        return null;
    }

    // Bubble Sort Function bedasarkan harga dan nama (jika harga sama di sort dengan memperhatikan nama)
    public void sortMenu(){
        int n = menuList.size();

        for (int i = 0; i < n-1; i++){
            for (int j = 0; j < n-i-1; j++){
                if (menuList.get(j).getHarga() > menuList.get(j+1).getHarga()){
                    Menu temp = menuList.get(j);
                    menuList.set(j, menuList.get(j+1));
                    menuList.set(j+1, temp);
                } else if (menuList.get(j).getHarga() == menuList.get(j+1).getHarga()){
                    if (menuList.get(j).getNamaMakanan().compareTo(menuList.get(j+1).getNamaMakanan()) > 0){
                        // Jika harga sama namun secara alfabet tidak urut
                        Menu temp = menuList.get(j);
                        menuList.set(j, menuList.get(j+1));
                        menuList.set(j+1, temp);
                    }
                }
            }
        }
    }
}
