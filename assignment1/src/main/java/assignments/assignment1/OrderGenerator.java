package assignments.assignment1;

import java.util.Scanner;

public class OrderGenerator {
    private static final Scanner input = new Scanner(System.in);

    // Menu
    public static void showMenu(){
        System.out.println(">>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
        System.out.println();
        System.out.println("Pilih menu:");
        System.out.println("1. Generate Order ID");
        System.out.println("2. Generate Bill");
        System.out.println("3. Keluar");
    }
    
    // Menu for repetition
    public static void repMenu(){
        System.out.println(">>=======================================<<");
        System.out.println("Pilih menu:");
        System.out.println("1. Generate Order ID");
        System.out.println("2. Generate Bill");
        System.out.println("3. Keluar");
    }

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

    // Generaye Order ID
    public static String generateOrderID(String namaRestoran, String tanggalOrder, String noTelepon) {
        // Code 39 character set
        char[] code39 = new char[]{
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', // Numeric 0-9
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', // A - M
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', // N - Z
        };

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
        orderID += code39[checkSumOdd % 36];
        orderID += code39[checkSumEven % 36];

        return orderID;
    }

    // Generate Bill
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

    public static void main(String[] args) {
        System.out.println("Main Funtion");
    }

    
}
