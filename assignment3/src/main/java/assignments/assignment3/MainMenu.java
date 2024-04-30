package assignments.assignment3;

import java.util.ArrayList;
import java.util.Scanner;

import assignments.assignment3.items.Restaurant;
import assignments.assignment3.items.User;
import assignments.assignment3.LoginManager;
import assignments.assignment3.payment.CreditCardPayment;
import assignments.assignment3.payment.DebitPayment;
import assignments.assignment3.systemCLI.AdminSystemCLI;
import assignments.assignment3.systemCLI.CustomerSystemCLI;
import assignments.assignment3.systemCLI.UserSystemCLI;


public class MainMenu {
    private final Scanner input;
    private final LoginManager loginManager;
    private static ArrayList<Restaurant> restoList = new ArrayList<Restaurant>();
    private static ArrayList<User> userList;

    public MainMenu(Scanner in, LoginManager loginManager) {
        this.input = in;
        this.loginManager = loginManager;
    }

    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu(new Scanner(System.in), new LoginManager(new AdminSystemCLI(), new CustomerSystemCLI()));
        mainMenu.run();
    }

    public void run(){
        initUser(); // Isi userList dengan akun2 nya

        printHeader();
        boolean exit = false;
        while (!exit) {
            startMenu();
            int choice;
            try {
                choice = input.nextInt();
                input.nextLine();
            } catch (Exception e) {
                input.nextLine();
                System.out.println("Input tidak valid, silakan coba lagi.");
                continue;
            }
            switch (choice) {
                case 1 -> login();
                case 2 -> exit = true;
                default -> System.out.println("Pilihan tidak valid, silakan coba lagi.");
            }
        }

        System.out.println("Terima kasih telah menggunakan DepeFood!");

        input.close();
    }

    private void login(){
        System.out.println("\nSilakan Login:");
        System.out.print("Nama: ");
        String nama = input.nextLine();
        System.out.print("Nomor Telepon: ");
        String noTelp = input.nextLine();

        User userLoggedIn = getUser(nama, noTelp);

        if(userLoggedIn == null){
            System.out.println("Pengguna dengan data tersebut tidak ditemukan!");
            return;
        }

        try {
            loginManager.getSystem(userLoggedIn.getRole()).run(userLoggedIn);
        } catch (Exception e) {
            System.out.println("Terjadi error tidak terduga. Silakan coba lagi.");
            input.nextLine();
            e.printStackTrace();
        }
    }

    private static void printHeader(){
        System.out.println("\n>>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
    }

    private static void startMenu(){
        System.out.println("\nSelamat datang di DepeFood!");
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Login");
        System.out.println("2. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void initUser(){
        userList = new ArrayList<User>();

        userList.add(new User("Thomas N", "9928765403", "thomas.n@gmail.com", "P", "Customer", new DebitPayment(), 500000));
        userList.add(new User("Sekar Andita", "089877658190", "dita.sekar@gmail.com", "B", "Customer", new CreditCardPayment(), 2000000));
        userList.add(new User("Sofita Yasusa", "084789607222", "sofita.susa@gmail.com", "T", "Customer", new DebitPayment(), 750000));
        userList.add(new User("Dekdepe G", "080811236789", "ddp2.gampang@gmail.com", "S", "Customer", new CreditCardPayment(), 1800000));
        userList.add(new User("Aurora Anum", "087788129043", "a.anum@gmail.com", "U", "Customer", new DebitPayment(), 650000));

        userList.add(new User("Admin", "123456789", "admin@gmail.com", "-", "Admin", new CreditCardPayment(), 0));
        userList.add(new User("Admin Baik", "9123912308", "admin.b@gmail.com", "-", "Admin", new CreditCardPayment(), 0));
    }

    // Helper Function untuk ambil user dari list di atas
    public static User getUser(String nama, String nomorTelepon){
        // Iterasi setiap user di userList untuk mencari user dengan nama dan nomor telepon yang sesuai
        for (User user : userList) {
            if(user.getNomorTelepon().equals(nomorTelepon) && user.getNama().equals(nama)){
                return user;
            }
        }
        // Jika tidak ditemukan user dengan nama dan nomor telepon yang sesuai, return null (user tidak ditemukan)
        return null;
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

    // Helper Function untuk validasi inputan berupa angka
    public static boolean checkNum(String str){
        for (int i = 0; i < str.length(); i++) {
            char chr = str.charAt(i);
            if (!Character.isDigit(chr)) {
                return false;
            }
        }
        return true;
    }

    public static ArrayList<Restaurant> getRestoList(){
        return restoList;
    }
}
