package assignments.assignment2;

import java.util.ArrayList;

public class Restaurant {
    // Attributes yang dimiliki sebuah Restaurant
    private String nama;
    private int jumlahMenu;
    private ArrayList<Menu> menuList; // Menyimpan menu yang disediakan oleh restaurant ini

    public Restaurant(String nama, int jumlahMenu){
        this.nama = nama;
        this.jumlahMenu = jumlahMenu;
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
            if (menuList.get(i).getNamaMakanan().toLowerCase().equals(namaMakanan.toLowerCase())){
                return menuList.get(i);
            }
        }
        return null;
    }
}
