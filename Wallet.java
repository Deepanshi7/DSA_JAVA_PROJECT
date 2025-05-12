package SecureCryptoWallet;

import java.io.Serializable;

public class Wallet implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String email;
    private double balance;
    private TransactionStack transactions;

    // Modify the constructor to accept a double for initialBalance
    public Wallet(int id, String name, String email, double initialBalance) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.balance = initialBalance;  // Set the balance as a double
        this.transactions = new TransactionStack();
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public double getBalance() { return balance; }
    public TransactionStack getTransactions() { return transactions; }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactions.push("Deposited: Crypto " + amount);
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactions.push("Withdrawn: Crypto " + amount);
            return true;
        }
        return false;
    }

    public void transfer(Wallet receiver, double amount) {
        if (withdraw(amount)) {
            receiver.deposit(amount);
            transactions.push("Transferred: Crypto " + amount + " to ID " + receiver.getId());
        }
    }

    public void display() {
        System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email + ", Balance: Crypto " + balance);
    }
}
