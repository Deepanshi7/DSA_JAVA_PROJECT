package SecureCryptoWallet;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        WalletManager manager = new WalletManager();

        System.out.println("Welcome to Secure Crypto Wallet System\n");

        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Login as Wallet User");
            System.out.println("2. Login as Admin");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    manager.walletLogin(sc);
                    break;
                case "2":
                    Admin.login(sc, manager);
                    break;
                case "3":
                    FileUtil.saveWalletsToFile(manager.getUsers());
                    System.out.println("Thank you for using the system!");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
