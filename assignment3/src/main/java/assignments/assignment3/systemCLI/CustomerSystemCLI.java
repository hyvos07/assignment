package assignments.assignment3.systemCLI;

import java.util.ArrayList;
import java.util.Scanner;

import assignments.assignment1.OrderGenerator;
import assignments.assignment3.items.Menu;
import assignments.assignment3.items.Order;
import assignments.assignment3.items.Restaurant;
import assignments.assignment3.items.User;
import assignments.assignment3.MainMenu;
import assignments.assignment3.payment.CreditCardPayment;
import assignments.assignment3.payment.DebitPayment;
import assignments.assignment3.payment.DepeFoodPaymentSystem;

// Class CustomerSystemCLI: Implementasi CLI system untuk Customer

public class CustomerSystemCLI extends UserSystemCLI{
    
    @Override
    public boolean handleMenu(int choice){
        switch(choice){
            case 1 -> handleBuatPesanan();
            case 2 -> handleCetakBill();
            case 3 -> handleLihatMenu();
            case 4 -> handleBayarBill();
            case 5 -> handleCekSaldo();
            case 6 -> {
                return false;
            }
            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
        }
        return true;
    }

    @Override
    public void displayMenu() {
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Buat Pesanan");
        System.out.println("2. Cetak Bill");
        System.out.println("3. Lihat Menu");
        System.out.println("4. Bayar Bill");
        System.out.println("5. Cek Saldo");
        System.out.println("6. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    protected void handleBuatPesanan(){
        char[] kota = new char[]{'P', 'U', 'T', 'S', 'B'}; // Daftar Kota yang dapat dijangkau
        String[] tarif = new String[]{"10000", "20000", "35000", "40000", "60000"}; // Daftar tarif untuk menjangkau kota tsb

        System.out.println("\n--------------- Buat Pesanan ---------------");
        while(true){
            // Input nama restoran
            System.out.print("Nama Restoran: ");
            String namaResto = input.nextLine().toLowerCase();
            if(MainMenu.getResto(namaResto) == null){
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }

            // Validasi restoran jika belum pernah ditambahkan sebelumnya
            Restaurant restoran = MainMenu.getResto(namaResto);
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

            // Input menu yang dipesan
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

            // Validasi jika menu-menu yang dipesan ada di restoran
            if(!validOrder){
                // Pesan error muncul belakangan disini
                System.out.println("Mohon memesan menu yang tersedia di Restoran!\n");
                continue;
            }

            // All validation passed, sekarang bisa generate order
            String orderID = OrderGenerator.generateOrderID(namaResto, tanggalOrder, userLoggedIn.getNomorTelepon());
            String ongkosKirim = tarif[OrderGenerator.findIndexArray(kota, userLoggedIn.getLokasi().charAt(0))];
            
            Order newOrder = new Order(orderID, tanggalOrder, ongkosKirim, restoran, listPesanan); // Buat order baru
            userLoggedIn.addOrderHistory(newOrder); // Tambahkan order ke history pesanan milik user

            System.out.println("Pesanan dengan ID " + orderID + " diterima!");

            break;
        }
    }

    protected void handleCetakBill(){
        System.out.println("\n--------------- Cetak Bill ---------------");
        
        while(true){    
            System.out.print("Masukkan Order ID: ");
            String orderID = input.nextLine();
            Order selectedOrder = getOrder(orderID, userLoggedIn); // Cari order dari si User

            if (selectedOrder == null) {
                // Validasi jika orderID tidak ditemukan
                System.out.println("Order ID tidak dapat ditemukan.\n");
                continue;
            }

            printBill(orderID, selectedOrder); // Function untuk print bill

            break;
        }
    }

    protected void handleLihatMenu(){
        System.out.println("\n--------------- Lihat Menu ---------------");
        while(true){
            System.out.print("Nama Restoran: ");
            String namaResto = input.nextLine().toLowerCase();

            // Cek apakah restoran ada di sistem
            if(MainMenu.getResto(namaResto) != null){
                Restaurant restoran = MainMenu.getResto(namaResto);
                if(restoran == null){
                    // Just in case kalau ada bug di program
                    System.out.println("Restoran tidak terdaftar pada sistem.");
                    return;
                }

                System.out.println("Menu:");
                restoran.sortMenu(); // Sort menu berdasarkan harga dan nama

                for(int i = 0; i < restoran.getJumlahMenu(); i++){
                    // Print semua menu yang ada di restoran tersebut
                    Menu menu = restoran.getMenu(i);
                    System.out.println((int) (i + 1) + ". " + menu.getNamaMakanan() + " " + (int) menu.getHarga());
                }
            } else {
                // Error case
                System.out.println("Restoran tidak terdaftar pada sistem.");
                continue;
            }
            
            break;
        }
    }

    protected void handleBayarBill(){
        Order selectedOrder;
        String orderID;

        System.out.println("\n--------------- Bayar Bill ---------------");

        while(true){    
            // Kurang lebih sama saat tampilkan Bill
            System.out.print("Masukkan Order ID: ");
            orderID = input.nextLine();
            selectedOrder = getOrder(orderID, userLoggedIn);

            if (selectedOrder == null) {
                // Validasi jika orderID tidak ditemukan
                System.out.println("Order ID tidak dapat ditemukan.\n");
                continue;
            } else if(selectedOrder.getOrderStatus()){
                System.out.println("Pesanan dengan ID ini sudah lunas!\n");
                return;
            }

            printBill(orderID, selectedOrder);

            break;
        }
        
        System.out.println(
            "\nOpsi Pembayaran:\n" +
            "1. Credit Card\n" +
            "2. Debit"
        ); // Opsi pembayaran

        while(true){
            int pilihan;
            int orderCost = selectedOrder.calculateTotal(); // Total biaya pesanan tanpa pajak CC dan lainnya

            System.out.print("Pilihan Metode Pembayaran: ");
            try {
                pilihan = input.nextInt();
                input.nextLine();
            } catch (Exception e) {
                // Validasi jika input bukan angka
                input.nextLine();
                System.out.println("Pilihan bukan berupa angka, coba masukkan kembali pilihan anda.");
                continue;
            }

            try {
                // Tambah saldo restoran dengan biaya pesanan awal
                selectedOrder.getRestaurant().setSaldo(selectedOrder.getRestaurant().getSaldo() + orderCost);

                if(pilihan == 1){
                    // Kalkulasi biaya transaksi dan pajak Credit Card secara terpisah
                    long totalFee = ((CreditCardPayment) userLoggedIn.getPaymentSystem()).processPayment(orderCost);
                    long transactionFee = ((CreditCardPayment) userLoggedIn.getPaymentSystem()).countTransactionFee(orderCost);

                    if(userLoggedIn.getSaldo() - totalFee < 0){
                        // Saldo tidak mencukupi
                        System.out.println("Saldo tidak mencukupi. Mohon menggunakan metode pembayaran yang lain.");
                        return;
                    }
                    
                    userLoggedIn.setSaldo(userLoggedIn.getSaldo() - totalFee); // Potong saldo user
                    
                    System.out.println(
                        "\nBerhasil Membayar Bill sebesar Rp " + 
                        orderCost + " dengan biaya transaksi sebesar Rp " + 
                        transactionFee
                    );
                } else {
                    // Kalkulasi biaya transaksi Debit
                    long totalFee = ((DebitPayment) userLoggedIn.getPaymentSystem()).processPayment(orderCost);

                    if(totalFee < 0){
                        // Validasi jika pesanan kurang dari 50000
                        System.out.println("Jumlah pesanan < 50000. Mohon menggunakan metode pembayaran yang lain.");
                        return;
                    } else if(userLoggedIn.getSaldo() - totalFee < 0){
                        // Saldo tidak mencukupi
                        System.out.println("Saldo tidak mencukupi. Mohon menggunakan metode pembayaran yang lain");
                        return;
                    }

                    userLoggedIn.setSaldo(userLoggedIn.getSaldo() - totalFee); // Potong saldo user

                    System.out.println("\nBerhasil Membayar Bill sebesar Rp " + orderCost);
                }
            } catch (Exception e) {
                // Catch error dari casting yang dilakukan
                System.out.println("User belum memiliki metode pembayaran ini!");
                return;
            }

            handleUpdateStatusPesanan(orderID, userLoggedIn); // Update status pesanan biar ga dibayar lagi
            
            break;
        }
    }

    protected void handleUpdateStatusPesanan(String orderID, User userLoggedIn){
        Order selectedOrder = getOrder(orderID, userLoggedIn); // Ambil order dari order history

        if (!selectedOrder.getOrderStatus()){
            selectedOrder.setOrderStatus(); // Set status pesanan menjadi lunas
        } else {
            // Harusnya tidak perlu, karena pesanan yang sudah lunas sudah divalidasi di awal
            throw new IllegalArgumentException("Pesanan sudah lunas!"); // Jika pesanan sudah lunas
        }
    }

    protected void handleCekSaldo(){
        // Print saldo user yang sedang login
        System.out.println("Sisa saldo sebesar Rp " + userLoggedIn.getSaldo() + "\n");
    }

    // Helper Function untuk mengambil order dri order history
    public static Order getOrder(String orderID, User userLoggedIn){
        // Order History yang diambil merupakan deepcopy dari order history yang sebenarnya
        for (Order order : userLoggedIn.getOrderHistory()) {
            if(order.getOrderId().equals(orderID)){
                return order;
            }
        }
        return null; // In case orderIDnya tidak ada
    }

    // Helper Function untuk print bill saja bedasarkan Order object yang diterima
    public void printBill(String orderID, Order selectedOrder){
        // Pengambilan tanggal dari orderID
        String orderDay = orderID.substring(4, 6);
        String orderMonth = orderID.substring(6, 8);
        String orderYear = orderID.substring(8, 12);

        System.out.println("\nBill:");
        System.out.println("Order ID: " + selectedOrder.getOrderId());
        System.out.println("Tanggal Pemesanan: " + orderDay + "/" + orderMonth + "/" + orderYear);
        System.out.println("Restaurant: " + selectedOrder.getRestaurant().getNama());
        System.out.println("Lokasi Pengiriman: " + userLoggedIn.getLokasi());
        System.out.println("Status Pengiriman: " + (selectedOrder.getOrderStatus() ? "Finished" : "Not Finished"));

        System.out.println("Pesanan:");
        for(Menu menu : selectedOrder.getItems()){
            // Print semua menu yang dipesan
            System.out.println("- " + menu.getNamaMakanan() + " " + (int) menu.getHarga());
        }

        System.out.println("Biaya Ongkos Kirim: " + selectedOrder.getBiayaOngkosKirim());
        System.out.println("Total Biaya: Rp " + selectedOrder.calculateTotal());
    }
}
