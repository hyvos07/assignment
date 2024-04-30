package assignments.assignment3.systemCLI;

import java.util.Scanner;
import java.util.ArrayList;

import assignments.assignment3.items.Menu;
import assignments.assignment3.items.Restaurant;
import assignments.assignment3.items.User;

import assignments.assignment3.MainMenu;

public class AdminSystemCLI extends UserSystemCLI{
    private static ArrayList<Restaurant> restoList = MainMenu.getRestoList();

    @Override
    public boolean handleMenu(int command){
        switch(command){
            case 1 -> handleTambahRestoran();
            case 2 -> handleHapusRestoran();
            case 3 -> {return false;}
            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
        }
        return true;
    }

    @Override
    public void displayMenu() {
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Tambah Restoran");
        System.out.println("2. Hapus Restoran");
        System.out.println("3. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    protected void handleTambahRestoran(){
        Restaurant newResto; // Restoran yang akan ditambahkan

        System.out.println("\n-------------- Tambah Restoran ----------------");
        while(true){
            // Input nama restoran
            System.out.print("Nama: ");
            String namaRestoDisplay = input.nextLine();
            String namaResto = namaRestoDisplay.trim().replace(" ", "");

            if(namaResto.length() < 4){
                // Validasi nama restoran; minimal 4 karakter
                System.out.println("Nama restoran tidak valid.\n");
                continue;
            } else if(MainMenu.getResto(namaRestoDisplay) != null){
                // Validasi nama restoran; tidak boleh sama dengan restoran yang sudah ada
                System.out.printf(
                    "Restoran dengan nama %s sudah pernah terdaftar. Mohon masukkan nama yang berbeda.\n\n", 
                    namaRestoDisplay
                );
                continue;
            }

            // Input jumlah menu yang disediakan
            System.out.print("Jumlah Makanan: ");
            int jumlahMenu = input.nextInt();
            input.nextLine();
            
            newResto = new Restaurant(namaRestoDisplay, jumlahMenu, 0); // Initiate restoran baru
            ArrayList<Menu> newMenuList = new ArrayList<Menu>();
            boolean validMenu = true;

            // Input semua menu yang disediakan
            for(int i = 0; i < jumlahMenu; i++){
                String menuString = input.nextLine();
                String[] menuData = menuString.split(" ");

                if(menuData.length < 2){
                    // Validasi input menu; minimal ada 2 kata (nama makanan dan harga)
                    validMenu = false;
                    continue;
                }

                String namaMakanan = "";
                int harga = 0;

                for (int j = 0; j < menuData.length - 1; j++) {
                    menuData[j] = menuData[j].trim(); // sama seperti strip() di Python
                    namaMakanan += (namaMakanan.isEmpty() ? "" : " ") + menuData[j]; // Tambah spasi jika sebelumnya sudah ada isinya
                }

                if(!MainMenu.checkNum(menuData[menuData.length - 1].trim())){
                    validMenu = false;
                } else {
                    try {
                        harga = Integer.parseInt(menuData[menuData.length - 1].trim()); // Mengambil harga yang valid
                    } catch (NumberFormatException e) {
                        // Just in case jika harga tidak valid setelah checkNum
                        validMenu = false;
                        continue;
                    }
                }

                if (harga > 0) {
                    newMenuList.add(new Menu(namaMakanan, (double) harga));
                } else {
                    continue; // Tidak menambahkan menu jika harga tidak valid
                }
            }

            if(validMenu){
                // Jika semua menu yang diinput valid, maka restoran dan menu-menu yang diinput akan ditambahkan ke restoList
                restoList.add(newResto);
                newResto.addMenu(newMenuList);
                System.out.printf("Restoran %s berhasil didaftarkan.\n", namaRestoDisplay);
                break;
            } else {
                // Jika ada menu yang tidak valid, maka restoran tidak akan ditambahkan ke restoList dan muncul pesan error
                System.out.println("Harga menu harus bilangan bulat!\n");
                continue;
            }
        }
    }

    protected void handleHapusRestoran(){
        if(restoList.size() == 0){
            // Jika tidak ada restoran yang terdaftar, munculkan pesan error
            System.out.println("Belum ada restoran yang terdaftar pada sistem.\n");
            return;
        }

        System.out.println("\n--------------- Hapus Restoran ---------------");

        while(true){
            // Input nama restoran yang mau dihapus
            System.out.print("Nama Restoran: ");
            String namaResto = input.nextLine();

            if(MainMenu.getResto(namaResto) != null){
                // Jika restoran ada, hapus restoran dari restoList
                for (int i = 0; i < restoList.size(); i++) {
                    if(restoList.get(i).getNama().toLowerCase().equals(namaResto.toLowerCase())){
                        restoList.remove(i);
                        System.out.println("Restoran berhasil dihapus.");
                        break;
                    }
                }
            } else {
                // Jika restoran tidak ada, munculkan pesan error
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }

            break; // Keluar dari loop jika sudah berhasil menghapus restoran
        }
    }
}
