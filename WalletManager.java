package SecureCryptoWallet;

import java.util.*;

public class WalletManager {
    private List<Wallet> wallets;
    private static int nextId = 1001;

    public WalletManager() {
        wallets = FileUtil.loadWalletsFromFile();
        for (Wallet w : wallets) {
            nextId = Math.max(nextId, w.getId() + 1);
        }
    }

    public List<Wallet> getUsers() {
        return wallets;
    }

    public void walletLogin(Scanner sc) {
        System.out.print("Enter Wallet ID: ");
        int id = Integer.parseInt(sc.nextLine());
        Wallet user = findWalletById(id);
        if (user == null) {
            System.out.println("Wallet not found.");
            return;
        }

        while (true) {
            System.out.println("\n--- Wallet Menu ---");
            System.out.println("1. View Wallet");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. View Transactions");
            System.out.println("6. Logout");
            System.out.print("Choose option: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    user.display();
                    break;
                case "2":
                depositFunds(user, sc);
                break;
                case "3":
                withdrawFunds(user, sc);
                break;
                case "4":
                    System.out.print("Enter receiver's Wallet ID: ");
                    int toId = Integer.parseInt(sc.nextLine());
                    Wallet receiver = findWalletById(toId);
                    if (receiver == null || receiver == user) {
                        System.out.println("Invalid receiver.");
                    } else {
                        System.out.print("Enter amount to transfer: ");
                        double amount = Double.parseDouble(sc.nextLine());
                        user.transfer(receiver, amount);
                    }
                    break;
                case "5":
                    user.getTransactions().display();
                    break;
                case "6":
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    public void depositFunds(Wallet user, Scanner sc) {
        System.out.print("Enter amount to deposit (in crypto): ");
        double depositAmount = sc.nextDouble();

        if (depositAmount <= 0) {
            System.out.println("Amount must be greater than 0.");
            return;
        }

        user.deposit(depositAmount);
        System.out.println("Deposited " + depositAmount + " crypto into wallet.");
        FileUtil.saveWalletsToFile(wallets);  // Save to file after deposit
    }

    public void withdrawFunds(Wallet user, Scanner sc) {
        System.out.print("Enter amount to withdraw (in crypto): ");
        double withdrawAmount = sc.nextDouble();

        if (withdrawAmount <= 0) {
            System.out.println("Amount must be greater than 0.");
            return;
        }

        if (user.getBalance() < withdrawAmount) {
            System.out.println("Insufficient balance.");
            return;
        }

        user.withdraw(withdrawAmount);
        System.out.println("Withdrew " + withdrawAmount + " crypto from wallet.");
        FileUtil.saveWalletsToFile(wallets);  // Save to file after withdrawal
    }

    public void createWallet(Scanner sc) {
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        while (!name.matches("[A-Za-z ]+")) {
            System.out.print("Invalid name. Only letters allowed: ");
            name = sc.nextLine();
        }

        System.out.print("Enter your email: ");
        String email = sc.nextLine();
        while (!email.matches("^\\S+@\\S+\\.\\S+$")) {
            System.out.print("Invalid email. Try again: ");
            email = sc.nextLine();
        }
        double initialBalance = 0;
        while (true) {
            System.out.print("Enter initial balance for the wallet (in crypto, must be greater than 0): ");
            initialBalance = sc.nextDouble();
            sc.nextLine();  // consume the newline character after the number input

            if (initialBalance > 0) {
                break;  // exit the loop if valid
            } else {
                System.out.println("Initial balance must be greater than 0. Please try again.");
            }
        }
        Wallet wallet = new Wallet(nextId++, name, email, initialBalance);
        wallets.add(wallet);
        System.out.println("Wallet created! Your ID: " + wallet.getId());

        FileUtil.saveWalletsToFile(wallets);
    }

    public void displayAllWallets() {
        for (Wallet w : wallets) {
            w.display();
        }
    }

    public void searchWallet(String keyword) {
        boolean found = false;
        for (Wallet w : wallets) {
            if (w.getName().contains(keyword) || w.getEmail().contains(keyword)) {
                w.display();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No wallet found.");
        }
    }

    public void deleteWallet(int id) {
        Wallet wallet = findWalletById(id);
        if (wallet != null) {
            wallets.remove(wallet);
            System.out.println("Wallet deleted.");
        } else {
            System.out.println("Wallet not found.");
        }
    }

    public void displayTotalBalance() {
        double total = 0;
        for (Wallet w : wallets) {
            total += w.getBalance();
        }
        System.out.println("Total system balance: $" + total);
    }

    private Wallet findWalletById(int id) {
        for (Wallet w : wallets) {
            if (w.getId() == id) {
                return w;
            }
        }
        return null;
    }
}
