package ir.ac.kntu;

public class ChargeReceipt extends Receipt{
    private int newBalance;

    public ChargeReceipt(int newBalance){
        super();
        this.newBalance = newBalance;
    }

    public static void createChargeReceipt(int balance) {
        Receipt newReceipt = new ChargeReceipt(balance);
        Main.getUsers().getCurrentUser().getAccount().addReceipt(newReceipt);
    }
}
