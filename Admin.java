package SecureCryptoWallet;
import java.io.Console;
import java.util.Scanner;

public class Admin {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin123"; // plain text password

    public static void login(Scanner sc, WalletManager manager) {
        Console console = System.console();

        if (console == null) {
            System.out.println("Console is not available. Cannot hide password input.");
            System.out.print("Enter admin username: ");
            String user = sc.nextLine();
            System.out.print("Enter admin password: ");
            String pass = sc.nextLine(); // fallback to visible input

            if (user.equals(USERNAME) && pass.equals(PASSWORD)) {
                System.out.println("Admin logged in (without hidden input).");
                adminMenu(sc, manager);
            } else {
                System.out.println("Invalid credentials.");
            }
            return;
        }

        System.out.print("Enter admin username: ");
        String user = console.readLine();
        char[] passwordChars = console.readPassword("Enter admin password: ");
        String pass = new String(passwordChars);

        if (user.equals(USERNAME) && pass.equals(PASSWORD)) {
            System.out.println("Admin logged in successfully!");
            adminMenu(sc, manager);
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    private static void adminMenu(Scanner sc, WalletManager manager) {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Create New Wallet");
            System.out.println("2. View All Wallets");
            System.out.println("3. Search Wallet");
            System.out.println("4. Delete Wallet");
            System.out.println("5. Show Total Balance");
            System.out.println("6. Logout");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    manager.createWallet(sc); // Admin creates wallet
                    break;
                case 2:
                    manager.displayAllWallets();
                    break;
                case 3:
                    System.out.print("Enter name or email to search: ");
                    String keyword = sc.nextLine();
                    manager.searchWallet(keyword);
                    break;
                case 4:
                    System.out.print("Enter wallet ID to delete: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    manager.deleteWallet(id);
                    break;
                case 5:
                    manager.displayTotalBalance();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
