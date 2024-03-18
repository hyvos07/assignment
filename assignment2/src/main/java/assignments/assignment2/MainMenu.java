package assignments.assignment2;
import assignments.assignment1.OrderGenerator;

import java.util.ArrayList;
import java.util.Scanner;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static ArrayList<Restaurant> restoList = new ArrayList<Restaurant>();
    private static ArrayList<User> userList = new ArrayList<User>();

    public static void main(String[] args) {
        boolean programRunning = true;

        initUser(); // Inisialisasi user-user yang terdaftar

        while(programRunning){
            printHeader();
            startMenu();
            int command = input.nextInt();
            input.nextLine();

            if(command == 1){
                System.out.println("\nSilakan Login:");
                System.out.print("Nama: ");
                String nama = input.nextLine();
                System.out.print("Nomor Telepon: ");
                String noTelp = input.nextLine();

                User checkUser = getUser(nama, noTelp);

                if(checkUser == null){
                    System.out.println("Pengguna dengan data tersebut tidak ditemukan!");
                    continue;
                } else {
                    // Greetings
                    System.out.println("Selamat Datang " + checkUser.getNama() + "!");
                }

                User userLoggedIn = checkUser;
                boolean isLoggedIn = true;

                if(userLoggedIn.getRole() == "Customer"){
                    while (isLoggedIn){
                        menuCustomer();
                        int commandCust = input.nextInt();
                        input.nextLine();

                        switch(commandCust){
                            case 1 -> handleBuatPesanan(userLoggedIn);
                            case 2 -> handleCetakBill(userLoggedIn);
                            case 3 -> handleLihatMenu();
                            case 4 -> handleUpdateStatusPesanan(userLoggedIn);
                            case 5 -> isLoggedIn = false;
                            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
                        }
                    }
                }else{
                    while (isLoggedIn){
                        menuAdmin();
                        int commandAdmin = input.nextInt();
                        input.nextLine();

                        switch(commandAdmin){
                            case 1 -> handleTambahRestoran();
                            case 2 -> handleHapusRestoran();
                            case 3 -> isLoggedIn = false;
                            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
                        }
                    }
                }
            }else if(command == 2){
                programRunning = false;
            }else{
                System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        System.out.println("\nTerima kasih telah menggunakan DepeFood ^___^");
    }

    public static User getUser(String nama, String nomorTelepon){
        // Iterasi setiap user di userList untuk mencari user dengan nama dan nomor telepon yang sesuai
        for (User user : userList) {
            if(user.getNama().equals(nama) && user.getNomorTelepon().equals(nomorTelepon)){
                return user;
            }
        }
        // Jika tidak ditemukan user dengan nama dan nomor telepon yang sesuai, return null (user tidak ditemukan)
        return null;
    }

    public static void handleBuatPesanan(User userLoggedIn){
        char[] kota = new char[]{'P', 'U', 'T', 'S', 'B'}; // Daftar Kota yang dapat dijangkau
        String[] tarif = new String[]{"10000", "20000", "35000", "40000", "60000"}; // Daftar tarif untuk menjangkau kota tsb

        System.out.println("\n-------------- Buat Pesanan ----------------");
        while(true){
            // Input nama restoran
            System.out.print("Nama Restoran: ");
            String namaResto = input.nextLine().toLowerCase();
            if(!checkResto(namaResto)){
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }

            Restaurant restoran = getResto(namaResto);
            if(restoran == null){
                // Just in case kalau ada bug di program
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }

            // Input tanggal pemesanan
            System.out.print("Tanggal Pemesanan (DD/MM/YYYY): ");
            String tanggalOrder = input.nextLine();
            if (!OrderGenerator.checkDate(tanggalOrder)){
                System.out.println("Masukkan tanggal sesuai format (DD/MM/YYYY)!\n");
                continue;
            }

            // Input jumlah menu yang dipesan
            System.out.print("Jumlah Pesanan: ");
            int jumlahPesanan = 0;
            try {
                jumlahPesanan = input.nextInt();
                input.nextLine();
            } catch (Exception e) {
                System.out.println("Jumlah pesanan harus berupa angka!\n");
                continue;
            }

            System.out.println("Order:");
            Menu[] listPesanan = new Menu[jumlahPesanan];
            boolean validOrder = true;

            for (int i = 0; i < jumlahPesanan; i++) {
                String pesanan = input.nextLine();
                pesanan = pesanan.trim();

                Menu menuPesanan = restoran.searchMenu(pesanan);
                if (menuPesanan == null) {
                    validOrder = false;
                    continue;
                    // skip like nothing happened :)
                }

                listPesanan[i] = menuPesanan;
            }

            if(!validOrder){
                // Pesan error muncul belakangan disini
                System.out.println("Mohon memesan menu yang tersedia di Restoran!\n");
                continue;
            }

            // All validation passed, sekarang bisa generate order
            String orderID = OrderGenerator.generateOrderID(namaResto, tanggalOrder, userLoggedIn.getNomorTelepon());
            String ongkosKirim = tarif[OrderGenerator.findIndexArray(kota, userLoggedIn.getLokasi().charAt(0))];
            
            Order newOrder = new Order(orderID, tanggalOrder, ongkosKirim, restoran, listPesanan); // Buat order baru
            userLoggedIn.orderHistory.add(newOrder); // Tambahkan order ke history pesanan milik user

            System.out.println("Pesanan dengan ID " + orderID + " diterima!");

            break;
        }   
    }

    public static void handleCetakBill(User userLoggedIn){
        System.out.println("\n--------------- Cetak Bill ---------------");
        while(true){    
            System.out.print("Masukkan Order ID: ");
            String orderID = input.nextLine();
            Order selectedOrder = getOrder(orderID, userLoggedIn);

            if (selectedOrder == null) {
                System.out.println("Order ID tidak dapat ditemukan.\n");
                continue;
            }

            // Print Bill
            String orderDay = orderID.substring(4, 6);
            String orderMonth = orderID.substring(6, 8);
            String orderYear = orderID.substring(8, 12);

            System.out.println("\nBill:");
            System.out.println("Order ID: " + selectedOrder.getOrderId());
            System.out.println("Tanggal Pemesanan: " + orderDay + "/" + orderMonth + "/" + orderYear);
            System.out.println("Restaurant: " + selectedOrder.getRestaurant().getNama());
            System.out.println("Lokasi Pengiriman: " + userLoggedIn.getLokasi());
            System.out.println("Status Pengiriman: " + (selectedOrder.orderFinished ? "Finished" : "Not Finished"));

            System.out.println("Pesanan:");
            for(Menu menu : selectedOrder.items){
                System.out.println("- " + menu.getNamaMakanan() + " " + (int) menu.getHarga());
            }

            System.out.println("Biaya Ongkos Kirim: " + selectedOrder.getBiayaOngkosKirim());
            System.out.println("Total Biaya: Rp " + selectedOrder.calculateTotal());

            break;
        }
    }

    public static void handleLihatMenu(){
        System.out.println("\n--------------- Lihat Menu ---------------");
        while(true){
            System.out.print("Nama Restoran: ");
            String namaResto = input.nextLine().toLowerCase();

            if(checkResto(namaResto)){
                Restaurant restoran = getResto(namaResto);
                if(restoran == null){
                    // Just in case kalau ada bug di program
                    System.out.println("Restoran tidak terdaftar pada sistem.");
                    return;
                }

                System.out.println("Menu:");
                restoran.sortMenu(); // Sort menu berdasarkan harga dan nama

                for(int i = 0; i < restoran.getJumlahMenu(); i++){
                    Menu menu = restoran.getMenu(i);
                    System.out.println((int) (i + 1) + ". " + menu.getNamaMakanan() + " " + (int) menu.getHarga());
                }
            } else {
                System.out.println("Restoran tidak terdaftar pada sistem.");
                continue;
            }
            
            break;
        }
    }

    public static void handleUpdateStatusPesanan(User userLoggedIn){
        System.out.println("\n--------------- Update Status Pesanan ---------------");
        while (true) {
            System.out.print("Order ID: ");
            String orderID = input.nextLine();

            Order selectedOrder = getOrder(orderID, userLoggedIn);

            if (selectedOrder == null) {
                System.out.println("Order ID tidak dapat ditemukan.\n");
                return;
            }

            System.out.print("Status: ");
            String statusOrder = input.nextLine().toLowerCase();

            if (statusOrder.equals("selesai") && !selectedOrder.orderFinished){
                selectedOrder.orderFinished = true;
                System.out.printf("Status pesanan dengan ID %s berhasil diupdate!\n", orderID);
            } else if(selectedOrder.orderFinished) {
                System.out.printf("Status pesanan dengan ID %s tidak berhasil diupdate!\n", orderID);
            } else {
                System.out.println("Input tidak valid, coba lagi!");
                continue;
            }

            break;
        }
    }

    public static void handleTambahRestoran(){
        Restaurant newResto; // Restoran yang akan ditambahkan

        System.out.println("\n-------------- Tambah Restoran ----------------");
        while(true){
            System.out.print("Nama: ");
            String namaRestoDisplay = input.nextLine();
            String namaResto = namaRestoDisplay.trim().replace(" ", "");

            if(namaResto.length() < 4){
                // Validasi nama restoran; minimal 4 karakter
                System.out.println("Nama restoran tidak valid.\n");
                continue;
            } else if(checkResto(namaRestoDisplay)){
                // Validasi nama restoran; tidak boleh sama dengan restoran yang sudah ada
                System.out.printf(
                    "Restoran dengan nama %s sudah pernah terdaftar. Mohon masukkan nama yang berbeda.\n\n", 
                    namaRestoDisplay
                );
                continue;
            }

            System.out.print("Jumlah Makanan: ");
            int jumlahMenu = input.nextInt();
            input.nextLine();
            
            newResto = new Restaurant(namaRestoDisplay, jumlahMenu); // Initiate restoran baru
            ArrayList<Menu> newMenuList = new ArrayList<Menu>();
            boolean validMenu = true;

            for(int i = 0; i < jumlahMenu; i++){
                String menuString = input.nextLine();
                String[] menuData = menuString.split(" ");

                String namaMakanan = "";
                int harga = 0;

                for (int j = 0; j < menuData.length - 1; j++) {
                    menuData[j] = menuData[j].trim(); // sama seperti strip() di Python
                    namaMakanan += (namaMakanan.isEmpty() ? "" : " ") + menuData[j]; // Tambah spasi jika sebelumnya sudah ada isinya
                }

                if(!checkNum(menuData[menuData.length - 1].trim())){
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
                    break;
                }
            }

            if(validMenu){
                restoList.add(newResto);
                newResto.addMenu(newMenuList);
                System.out.printf("Restoran %s berhasil didaftarkan.\n", namaRestoDisplay);
                break;
            } else {
                System.out.println("Harga menu harus bilangan bulat!\n");
                continue;
            }
        }
    }

    public static void handleHapusRestoran(){
        System.out.println("\n--------------- Hapus Restoran ---------------");
        System.out.print("Nama Restoran: ");
        String namaResto = input.nextLine();

        if(checkResto(namaResto)){
            for (int i = 0; i < restoList.size(); i++) {
                if(restoList.get(i).getNama().toLowerCase().equals(namaResto.toLowerCase())){
                    restoList.remove(i);
                    System.out.println("Restoran berhasil dihapus.");
                }
            }
        } else {
            System.out.println("Restoran tidak terdaftar pada sistem.");
            // Jika restoran tidak ada, balik ke menu admin
        }
    }

    public static void initUser(){
       userList = new ArrayList<User>();

       // Customer Acc
       userList.add(new User("Thomas N", "9928765403", "thomas.n@gmail.com", "P", "Customer"));
       userList.add(new User("Sekar Andita", "089877658190", "dita.sekar@gmail.com", "B", "Customer"));
       userList.add(new User("Sofita Yasusa", "084789607222", "sofita.susa@gmail.com", "T", "Customer"));
       userList.add(new User("Dekdepe G", "080811236789", "ddp2.gampang@gmail.com", "S", "Customer"));
       userList.add(new User("Aurora Anum", "087788129043", "a.anum@gmail.com", "U", "Customer"));

       // Admin Acc
       userList.add(new User("Admin", "123456789", "admin@gmail.com", "-", "Admin"));
       userList.add(new User("Admin Baik", "9123912308", "admin.b@gmail.com", "-", "Admin"));
    }

    public static void printHeader(){
        System.out.println("\n>>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
    }

    public static void startMenu(){
        System.out.println("Selamat datang di DepeFood!");
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Login");
        System.out.println("2. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void menuAdmin(){
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Tambah Restoran");
        System.out.println("2. Hapus Restoran");
        System.out.println("3. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void menuCustomer(){
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Buat Pesanan");
        System.out.println("2. Cetak Bill");
        System.out.println("3. Lihat Menu");
        System.out.println("4. Update Status Pesanan");
        System.out.println("5. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    // Helper Function untuk cek jika restoran sudah pernah terdaftar
    public static boolean checkResto(String namaResto){
        // Iterasi setiap restoran di restoList untuk mencari restoran dengan nama yang sama
        for (Restaurant resto : restoList) {
            if(resto.getNama().toLowerCase().equals(namaResto.toLowerCase())){
                return true;
            }
        }
        return false;
    }

    // Helper Function untuk cek jika input adalah angka
    public static boolean checkNum(String str){
        for (int i = 0; i < str.length(); i++) {
            char chr = str.charAt(i);
            if (!Character.isDigit(chr)) {
                return false;
            }
        }
        return true;
    }

    // Helper Function untuk mencari restoran berdasarkan nama
    public static Restaurant getResto(String namaResto){
        for (Restaurant resto : restoList) {
            if(resto.getNama().toLowerCase().equals(namaResto.toLowerCase())){
                return resto;
            }
        }
        return null; // In case tidak ada restorannya
    }

    // Helper Function untuk mencari history order berdasarkan orderID
    public static Order getOrder(String orderID, User userLoggedIn){
        for (Order order : userLoggedIn.orderHistory) {
            if(order.getOrderId().equals(orderID)){
                return order;
            }
        }
        return null; // In case orderIDnya tidak ada
    }
}
