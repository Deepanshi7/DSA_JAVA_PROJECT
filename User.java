package SecureCryptoWallet;
public class User {
    private String name;
    private String email;
    private String address;
    private double balance;
    private TransactionStack transactions;
    public void setName(String name) {
        // Validate that the name contains only letters and spaces
        if (name.matches("[A-Za-z ]+")) {
            this.name = name;
        } else {
            System.out.println("Invalid name. Only letters are allowed.");
        }
    }
    public void setEmail(String email) {
        // Validate that the email contains "@" and "."
        if (email.contains("@") && email.contains(".")) {
            this.email = email;
        } else {
            System.out.println("Invalid email format.");
        }
    }
    public void setAddress(String address) {
        // Validate that the address is not empty
        if (!address.isEmpty()) {
            this.address = address;
        } else {
            System.out.println("Address cannot be empty.");
        }
    }
    public void setBalance(double balance) {
        // Validate that the balance is not negative
        if (balance >= 0) {
            this.balance = balance;
        } else {
            System.out.println("Balance cannot be negative.");
        }
    }
    public void setTransactions(TransactionStack transactions) {
        this.transactions = transactions;
    }
    public void setTransaction(String transaction) {
        this.transactions.push(transaction);
    }

    public User(String name, String email, String address) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.balance = 0.0;
        this.transactions = new TransactionStack();
    }
    public String getName() {
        return name;
    }
    
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public double getBalance() { return balance; }
    public TransactionStack getTransactions() { return transactions; }

    public void deposit(double amount) {
    if (amount <= 0) {
        System.out.println("Amount must be positive.");
        return;
    }
    balance += amount;
    transactions.push("Deposited: $" + amount);
}

public boolean withdraw(double amount) {
    if (amount <= 0) {
        System.out.println("Amount must be positive.");
        return false;
    }
    if (amount > balance) {
        System.out.println("Insufficient balance.");
        return false;
    }
    balance -= amount;
    transactions.push("Withdrawn: $" + amount);
    return true;
}


    public void transfer(User receiver, double amount) {
        if (withdraw(amount)) {
            receiver.deposit(amount);
            transactions.push("Transferred: $" + amount + " to " + receiver.getAddress());
        }
        
    }

    public void display() {
        System.out.println("Name: " + name + ", Email: " + email + ", Address: " + address + ", Balance: $" + balance);
    }
}
