package assignments.assignment3.systemCLI;

import java.util.Scanner;
import assignments.assignment3.items.User;

// Abstract class dari CLI System yang akan digunakan untuk user

public abstract class UserSystemCLI {
    protected Scanner input = new Scanner(System.in);
    protected User userLoggedIn; // Diambil dari Main Menu

    public void run(User userLoggedIn) {
        this.userLoggedIn = userLoggedIn; // Set user yang sedang login

        System.out.println("Selamat datang " + this.userLoggedIn.getNama() + "!");

        boolean isLoggedIn = true;
        while (isLoggedIn) {
            displayMenu();
            int command; // Untuk switch case di handleMenu
            try {
                command = input.nextInt();
                input.nextLine();
            } catch (Exception e) {
                // Jika input bukan angka, maka akan dianggap sebagai input yang tidak valid
                input.nextLine();
                System.out.println("Input tidak valid, silakan coba lagi.");
                continue;
            }
            isLoggedIn = handleMenu(command); // handleMenu akan mengembalikan false jika user memilih untuk keluar
        }
    }

    abstract void displayMenu(); // Implementasi berbeda di tiap CLI Sysytem
    abstract boolean handleMenu(int command); // Menu yang tersedia untuk role berbeda akan berbeda juga
}
