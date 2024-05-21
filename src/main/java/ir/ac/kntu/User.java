package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private String name;
    private String lastName;
    private String phoneNumber;
    private String securityNumber;
    private String password;
    private List<Contact> contacts;
    private List<User> recentUsers;
    private List<Ticket> tickets;
    private Account account;
    private boolean authenticated = false;
    private boolean contactsActivated;

    public User(String name, String lastName, String phoneNumber, String securityNumber, String password) {
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.securityNumber = securityNumber;
        this.password = password;
        contacts = new ArrayList<>();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount() {
        this.account = new Account();
    }

    public void chargeAccount(){
        System.out.println(Color.WHITE + "Please enter the amount your trying to charge your account");
        String input = InputManager.getInput();
        while(!chargeAmountValidity(input)){
            System.out.println(Color.RED + "Please enter a valid number" + Color.RESET);
            input = InputManager.getInput();
        }
        account.setBalance(account.getBalance() + Integer.parseInt(input));
        ChargeReceipt.createChargeReceipt(account.getBalance());
        System.out.println(Color.GREEN + "Your account has been successfully charged. new balance : " + Color.WHITE
                + account.getBalance() + '$' +  Color.RESET);
    }

    private boolean chargeAmountValidity(String input) {
        String numberRegex = "\\d+";
        Pattern numPattern = Pattern.compile(numberRegex);
        Matcher numMatcher = numPattern.matcher(input);
        return numMatcher.matches() && input.length() < 10;
    }

    public void displayBalance() {
        System.out.println(Color.WHITE + "Your current balance is " + Color.GREEN + account.getBalance() + "$" + Color.RESET);
    }

    public void addNewContact(Contact newContact){
        contacts.add(newContact);
    }

    public boolean contactAlreadyExists(String phoneNumber){
        for(Contact contact : contacts){
            if(contact.getPhoneNumber().equals(phoneNumber)){
                return true;
            }
        }
        return false;
    }

    public void displayAllContacts(){
        int i=1;
        System.out.println(Color.CYAN + "*".repeat(35) + Color.RESET);
        for(Contact contact : contacts){
            System.out.println(Color.WHITE + i + "-" + Color.BLUE + contact.getName() + " " + contact.getLastName());
            i++;
        }
        System.out.println(Color.CYAN + "*".repeat(35) + Color.RESET);
    }
}
