package ir.ac.kntu;

public class ChargeReceipt extends Receipt {
    private int newBalance;

    public ChargeReceipt(int amount, int newBalance) {
        super(amount);
        this.newBalance = newBalance;
    }

    public static void createChargeReceipt(int amount, int balance) {
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
