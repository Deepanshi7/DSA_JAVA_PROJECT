package SecureCryptoWallet;

import java.io.*;
import java.util.*;

public class FileUtil {

    // The name of the text file where wallets will be saved
    private static final String FILE_NAME = "wallets.txt";

    // Load wallets from the text file
    public static List<Wallet> loadWalletsFromFile() {
        List<Wallet> wallets = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Parse each line and create a Wallet object
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    String email = parts[2].trim();
                    double balance = Double.parseDouble(parts[3].trim());
                    Wallet wallet = new Wallet(id, name, email, balance);
                    wallets.add(wallet);
                }
            }
        } catch (FileNotFoundException e) {
            // Handle case where the file doesn't exist yet (first-time run)
            System.out.println("No saved wallets found, starting fresh.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wallets;
    }

    // Save the list of wallets to the text file
    public static void saveWalletsToFile(List<Wallet> wallets) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Wallet wallet : wallets) {
                // Write each wallet as a line in the format: id, name, email, balance
                writer.write(wallet.getId() + ", " + wallet.getName() + ", " + wallet.getEmail() + ", " + wallet.getBalance());
                writer.newLine();  // Add a new line after each wallet entry
            }
            System.out.println("Wallets saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
