package SecureCryptoWallet;

public class TransactionStack {
    private Node top;

    private static class Node {
        String transaction;
        Node next;

        Node(String transaction) {
            this.transaction = transaction;
        }
    }

    public void push(String tx) {
        Node newNode = new Node(tx);
        newNode.next = top;
        top = newNode;
    }

    public void display() {
        if (top == null) {
            System.out.println("No transactions.");
            return;
        }
        System.out.println("Recent Transactions:");
        Node curr = top;
        while (curr != null) {
            System.out.println("- " + curr.transaction);
            curr = curr.next;
        }
    }
}
