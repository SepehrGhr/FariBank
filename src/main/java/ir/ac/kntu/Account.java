package ir.ac.kntu;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Account {
    private String accountID;
    private CreditCard creditCard;
    private long balance;

    public Account() {
        setAccountID();
        creditCard = new CreditCard();
        this.balance = 0;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public String generateAccountID() {
        String accID = "0";
        Random random = new Random();
        for (int i = 1; i < 10; i++) {
            accID += Integer.toString(random.nextInt(10));
        }
        return accID + "000";
    }

    public void setAccountID() {
        String accID = generateAccountID();
        while (Main.getUsers().accountIdAlreadyExists(accID)) {
            accID = generateAccountID();
        }
        accountID = accID;
    }

    public String getAccountID() {
        return accountID;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public static boolean accountIDValidity(String accountID) {
        String idRegex = "\\d{13}";
        Pattern idPattern = Pattern.compile(idRegex);
        Matcher idMatcher = idPattern.matcher(accountID);
        return idMatcher.matches() && "0".equals(accountID.substring(0, 1));
    }
}
