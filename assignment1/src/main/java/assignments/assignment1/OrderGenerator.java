package assignments.assignment1;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class OrderGenerator {
    private static final Scanner input = new Scanner(System.in);
    private static String divider = "--------------------------------------------------"; // Divider
    // Code 39 character set
    private static char[] code39 = new char[]{
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', // Numeric 0-9
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', // A - M
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', // N - Z
    };


    // Menu
    public static void showMenu(){
        System.out.println(">>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.err.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
        System.out.println();
        System.out.println("Pilih menu:");
        System.err.println("1. Generate Order ID");
        System.out.println("2. Generate Bill");
        System.out.println("3. Keluar");
    }
    
    // Menu for repetition
    public static void repMenu(){
        System.out.println(divider);
        System.out.println("Pilih menu:");
        System.out.println("1. Generate Order ID");
        System.out.println("2. Generate Bill");
        System.out.println("3. Keluar");
    }
    
    // Find Index untuk array
    public static int findIndexArray(char[] array, char chr){
        int index = -1;

        for (int i = 0; i < array.length; i++){
            if(array[i] == chr){
                index = i;
                break;
            }
        }

        return index;
    }
    
    // Check sum generator 
    public static String checkSum(String orderID){
        String output = "";

        // 2 Karakter checkSum
        int checkSumOdd = 0;
        int checkSumEven = 0;
        for (int i = 0; i < orderID.length(); i++){
            char c = orderID.charAt(i);
            if(i % 2 == 0){
                checkSumOdd += findIndexArray(code39, c);
            } else {
                checkSumEven += findIndexArray(code39, c);
            }
        }

        // Menambahkan hasil translasi Code 39 ke Order ID
        output += code39[checkSumOdd % 36];
        output += code39[checkSumEven % 36];

        return output;
    }

    // -- Generaye Order ID -- //
    public static String generateOrderID(String namaRestoran, String tanggalOrder, String noTelepon) {
        String orderID = "";

        // Generate 4 huruf pertama dari namaRestoran
        if(namaRestoran.length() == 4){
            // Kalau namaRestoran sudah 4 panjangnya, langsung tambahkan ke orderID
            orderID += namaRestoran.toUpperCase();
        } else {
            orderID += namaRestoran.substring(0, 4).toUpperCase();
        }

        // Menambahkan tanggal order ke Order ID
        orderID += tanggalOrder.replace("/", "");

        // Membuat 2 karakter untuk OrderID dari jumlah telepon mod 100
        int sumTelepon = 0;
        for (char chr : noTelepon.toCharArray()){
            // Iterasi tiap karakter menjadi angka dan ditambah
            int num = Character.getNumericValue(chr);
            sumTelepon += num;
        }
        
        sumTelepon = sumTelepon % 100; // Modulo sum dengan 100 ==> digit 0 -99

        // Tahap penambahan
        if (sumTelepon < 10){
            // Jika sum hanya 1 digit
            orderID += "0" + sumTelepon;
        } else {
            orderID += sumTelepon;
        }

        orderID += checkSum(orderID); // Menanmbahkan checksum

        return orderID;
    }

    // -- Generate Bill -- //
    public static String generateBill(String orderID, String lokasi){
        // Kota dan Tarif, dihubungkan oleh index yang sama
        char[] kota = new char[]{'P', 'U', 'T', 'S', 'B'}; // Daftar Kota yang dapat dijangkau
        String[] tarif = new String[]{"10.000", "20.000", "35.000", "40.000", "60.000"}; // Daftar tarif untuk menjangkau kota tsb

        String orderDay = orderID.substring(4, 6);
        String orderMonth = orderID.substring(6, 8);
        String orderYear = orderID.substring(8, 12);

        return (
            "Bill:\n" + 
            "Order ID: " + orderID + "\n" +
            "Tanggal Pemesanan: " + orderDay + "/" + orderMonth + "/" + orderYear + "\n" +
            "Lokasi Pengiriman: " + lokasi.toUpperCase() + "\n" +
            "Biaya Ongkos Kirim: " + "Rp " + tarif[findIndexArray(kota, lokasi.toUpperCase().charAt(0))] + "\n"
        ); 
    }
    
    // -- Main Function -- //
    public static void main(String[] args) {
        boolean running = true;
        boolean firstTime = true;
        
        showMenu(); // Show Menu and Dekdepe Big Text
        while (running) {
            if (!firstTime) repMenu(); // Different menu
            
            firstTime = false;
            System.out.println(divider);
            System.err.print("Pilihan Menu: ");
            int pilihan;
            
            // Input Validation: Not number, not 1 <= pilihan <= 3
            try {
                pilihan = input.nextInt();
                input.nextLine();
            } catch (Exception e) {
                System.out.println("Input tidak valid, silahkan ulangi!\n");
                input.nextLine();
                continue;
            }
            
            if (pilihan != 1 && pilihan != 2 && pilihan != 3){
                // Invalid choice
                System.out.println("Masukkan Nomor Pilihan yang tepat!\n");
                continue;
            } else if (pilihan == 3){
                // Keluar
                System.out.println("Terima kasih telah menggunakan DepeFood!");
                running = false;
            } else if (pilihan == 1){
                // Generate Order ID
                String namaRestoran;
                String tanggalOrder;
                String noTelepon;
                while (true){
                    // Ambil nama restoran & validasi
                    System.out.print("\nNama Restoran: ");
                    namaRestoran = input.nextLine().replace(" ", "").toUpperCase();
                    if (namaRestoran.length() < 4 || !checkAlphaNumeric(namaRestoran)){
                        System.out.println("Nama restoran tidak valid!");
                        continue;
                    }
                    // Ambil tanggal pemesanan & validasi
                    System.out.print("Tanggal Pemesanan: ");
                    tanggalOrder = input.nextLine();
                    if (!checkDate(tanggalOrder)){
                        System.out.println("Tanggal Pemesanan dalam format DD/MM/YYYY!");
                        continue;
                    }
                    // Ambil nomor telepon & validasi
                    System.out.print("No. Telepon: ");
                    noTelepon = input.nextLine();
                    if (!checkPhone(noTelepon)){
                        System.out.println("Harap masukkan nomor telepon dalam bentuk bilangan bulat positif.");
                        continue;
                    }

                    // All test passed, generate ID nya!
                    System.out.println("Order ID " + generateOrderID(namaRestoran, tanggalOrder, noTelepon) + " diterima!");
                    System.out.println();
                    break;
                }
            } else if (pilihan == 2){
                // Generate bill / tagihan
                String orderID;
                String lokasi;
                while (true){
                    // Ambil Order ID dan validasi
                    System.out.print("\nOrder ID: ");
                    orderID = input.nextLine().toUpperCase();
                    if (orderID.length() != 16 || !checkOrderID(orderID)){
                        System.out.println("Silahkan masukkan Order ID yang valid!");
                        continue;
                    }
                    // Ambil lokasi (P, U, S, T, B) dan validasi
                    System.out.print("Lokasi Pengiriman: ");
                    lokasi = input.nextLine().toUpperCase();
                    if (
                        // Panjang lokasi harus 1 karakter
                        lokasi.length() != 1
                        // Lokasi harus berupa huruf
                        || !Character.isAlphabetic(lokasi.charAt(0)) 
                        // Lokasi harus salah satu dari P, U, T, S, B
                        || findIndexArray(new char[]{'P', 'U', 'T', 'S', 'B'}, lokasi.charAt(0)) == -1
                    ){
                        System.out.println("Harap masukkan lokasi pengiriman yang bisa dijangkau atau valid!");
                        continue;
                    }

                    // All test passed, generate Bill nya
                    System.out.println();
                    System.out.println(generateBill(orderID, lokasi));
                    break;
                }
            }
        }
    }
    
    // Helper Function untuk cek format tanggal
    public static boolean checkDate(String date){
        // Cek panjang tanggal (generic test, tidak memeriksa bulan dan tahun)
        if (date.length() != 10){
            return false;
        }

        DateFormat dateCheck = new SimpleDateFormat("dd/MM/yyyy"); // Format tanggal yang diharapkan
        
        // Tidak menerima tanggal yang tidak valid
        dateCheck.setLenient(false);
        
        // Cek jika format tanggal sesuai
        try {
            dateCheck.parse(date);
        } catch (Exception e) {
            return false; // Jika tidak sesuai (gagal diubah menjadi sebuah tanggal), return false
        }

        return true;
    }

    // Helper Function untuk cek nomor telepon
    public static boolean checkPhone(String phone){
        // Cek setiap digit
        for (int i = 0; i < phone.length(); i++){
            char digit = phone.charAt(i);
            if (!Character.isDigit(digit)) return false;
        }

        return true;
    }

    // Helper Function untuk cek jika inout bersifat alphanumeric
    public static boolean checkAlphaNumeric(String str){
        for (int i = 0; i < str.length(); i++) {
            char chr = str.charAt(i);
            if (!Character.isAlphabetic(chr) && !Character.isDigit(chr)) {
                return false;
            }
        }

        return true;
    }

    // Helper function untuk check kevalidan ID
    public static boolean checkOrderID(String orderID){
        return checkSum(orderID.substring(0, orderID.length() - 2)).equals(orderID.substring(orderID.length() - 2));
    }
}
