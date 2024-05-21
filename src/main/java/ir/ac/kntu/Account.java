package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Account {
    private String accountID;
    private CreditCard creditCard;
    private int balance;
    private List<Receipt> receipts;

    public Account(){
        setAccountID();
        creditCard = new CreditCard();
        this.balance = 0;
        receipts = new ArrayList<>();
    }

    public String generateAccountID(){
        String id = "0";
        Random random = new Random();
        for(int i=1;i <10;i++){
            id += Integer.toString(random.nextInt(10));
        }
        return id + "000";
    }

    public void setAccountID(){
        String id = generateAccountID();
        while(Main.getUsers().accountIdAlreadyExists(id)){
            id = generateAccountID();
        }
        accountID = id;
    }

    public String getAccountID() {
        return accountID;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void addReceipt(Receipt receipt){
        receipts.add(receipt);
    }
}
