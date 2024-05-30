package ir.ac.kntu;

public class ChargeReceipt extends Receipt {
    private long newBalance;

    public ChargeReceipt(long amount, long newBalance) {
        super(amount);
        this.newBalance = newBalance;
    }

    public static void createChargeReceipt(long amount, long balance) {
        ChargeReceipt newReceipt = new ChargeReceipt(amount, balance);
        Main.getUsers().getCurrentUser().addReceipt(newReceipt);
        System.out.println(newReceipt);
    }

    @Override
    public String toString() {
        return super.toString() + Color.WHITE + "New balance : " + Color.GREEN + newBalance + '\n' +
                Color.CYAN + "*".repeat(35) + Color.RESET;
    }
}
