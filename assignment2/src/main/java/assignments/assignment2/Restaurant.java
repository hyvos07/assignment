package assignments.assignment2;

import java.util.ArrayList;

public class Restaurant {
    // Attributes yang dimiliki sebuah Restaurant
    private String nama;
    private int jumlahMenu;
    private ArrayList<Menu> menuList = new ArrayList<Menu>(); // Menyimpan menu yang disediakan oleh restaurant ini

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

    // Bubble Sort Function bedasarkan harga lalu nama (jika sama)
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
