package assignments.assignment3.systemCLI;

import java.util.Scanner;
import assignments.assignment3.items.User;

public abstract class UserSystemCLI {
    protected Scanner input = new Scanner(System.in);
    protected User userLoggedIn;

    public void run(User userLoggedIn) {
        this.userLoggedIn = userLoggedIn;

        System.out.println("Selamat datang " + userLoggedIn.getNama() + "!");

        boolean isLoggedIn = true;
        while (isLoggedIn) {
            displayMenu();
            int command = input.nextInt();
            input.nextLine();
            isLoggedIn = handleMenu(command);
        }
    }

    abstract void displayMenu();
    abstract boolean handleMenu(int command);
}
