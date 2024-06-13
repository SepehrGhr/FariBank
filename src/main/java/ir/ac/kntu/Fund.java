package ir.ac.kntu;

public abstract class Fund {
    private long balance;
    private User owner;

    public Fund(User owner){
        this.owner = owner;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = Math.max(0, balance);
    }

    public User getOwner() {
        return owner;
    }

    public abstract void deposit(long amount);

    public abstract void withdraw(long amount);

    public abstract String getType();

    public abstract void openManagement();
}
