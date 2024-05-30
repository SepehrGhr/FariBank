package ir.ac.kntu;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
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
    private List<Receipt> receipts;
    private Account account;
    private boolean authenticated = false;
    private boolean contactsActivated = true;

    public User(String name, String lastName, String phoneNumber, String securityNumber, String password) {
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.securityNumber = securityNumber;
        this.password = password;
        contacts = new ArrayList<>();
        recentUsers = new ArrayList<>();
        tickets = new ArrayList<>();
        receipts = new ArrayList<>();
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

    public String getName() {
        return name;
    }

    public void setContactsActivated(boolean contactsActivated) {
        this.contactsActivated = contactsActivated;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSecurityNumber() {
        return securityNumber;
    }

    public boolean isContactsActivated() {
        return contactsActivated;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setSecurityNumber(String securityNumber) {
        this.securityNumber = securityNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addReceipt(Receipt newReceipt) {
        receipts.add(newReceipt);
    }

    public void printChargeAccount() {
        System.out.println(Color.WHITE + "Please enter the amount your trying to charge your account (Maximum 12 digits)" + Color.RESET);
        String input = InputManager.getInput();
        while (!InputManager.chargeAmountValidity(input)) {
            System.out.println(Color.RED + "Please enter a valid number (Maximum 12 digits , Minimum 1)" + Color.RESET);
            input = InputManager.getInput();
        }
        chargeAccount(Long.parseLong(input));
    }

    public void chargeAccount(long amount) {
        account.setBalance(account.getBalance() + amount);
        ChargeReceipt.createChargeReceipt(amount, account.getBalance());
    }

    public void displayBalance() {
        System.out.println(Color.WHITE + "Your current balance is " + Color.GREEN + account.getBalance() + "$" + Color.RESET);
    }

    public void addNewContact(Contact newContact) {
        contacts.add(newContact);
    }

    public boolean contactAlreadyExists(String phoneNumber) {
        for (Contact contact : contacts) {
            if (contact.getPhoneNumber().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }

    public void displayAllContacts() {
        if (contacts.size() == 0) {
            System.out.println(Color.RED + "You don't have any contacts yet!" + Color.RESET);
            return;
        }
        int count = 1;
        System.out.println(Color.CYAN + "*".repeat(35) + Color.RESET);
        for (Contact contact : contacts) {
            System.out.println(Color.WHITE + count + "-" + Color.BLUE + contact.getName() + " " + contact.getLastName());
            count++;
        }
        System.out.println(Color.CYAN + "*".repeat(35) + Color.RESET);
    }

    public void showAndEditContact() {
        if(contacts.size() == 0){
            return;
        }
        Contact selectedContact = selectContactFromList();
        if (selectedContact == null) {
            return;
        }
        System.out.println(selectedContact);
        editContactMenu(selectedContact);
    }

    private void editContactMenu(Contact selectedContact) {
        System.out.println(Color.WHITE + "Enter 1 if you want to edit this contact and 2 to return" + Color.RESET);
        String selection = InputManager.getSelection(2);
        if ("2".equals(selection)) {
            return;
        }
        selectedContact.edit();
    }

    public void addToRecentUsers(User destination) {
        if (!recentUsers.contains(destination)) {
            recentUsers.add(destination);
        }
    }

    public Contact selectContactFromList() {
        System.out.println(Color.WHITE + "Enter the number of the contact you want to see or enter -1 to return to last menu" + Color.RESET);
        String selection = getMenuSelection(contacts.size());
        if ("-1".equals(selection)) {
            return null;
        }
        return contacts.get(Integer.parseInt(selection) - 1);
    }

    public boolean haveInContacts(Contact selected) {
        for (Contact contact : selected.getUser().contacts) {
            if (contact.getUser().getPhoneNumber().equals(Main.getUsers().getCurrentUser().getPhoneNumber())) {
                return true;
            }
        }
        return false;
    }

    public void displayRecentUsers() {
        if (recentUsers.size() == 0) {
            System.out.println(Color.RED + "You haven't transferred money to anyone yet" + Color.RESET);
            return;
        }
        int count = 1;
        System.out.println(Color.CYAN + "*".repeat(35) + Color.RESET);
        for (User recentUser : recentUsers) {
            System.out.println(Color.WHITE + count + "-" + Color.BLUE + recentUser.getName() + " " + recentUser.getLastName());
            count++;
        }
        System.out.println(Color.CYAN + "*".repeat(35) + Color.RESET);
    }

    public User selectRecentUserFromList() {
        System.out.println(Color.WHITE + "Enter the number of the user you want to transfer money or enter " + Color.RED +
                "-1 " + Color.WHITE + "to return to last menu" + Color.RESET);
        String selection = getMenuSelection(recentUsers.size());
        if ("-1".equals(selection)) {
            return null;
        }
        return recentUsers.get(Integer.parseInt(selection) - 1);
    }

    private String getMenuSelection(int size) {
        String selection = InputManager.getInput();
        if ("-1".equals(selection)) {
            return selection;
        }
        while (!InputManager.isInputValid(selection, size)) {
            System.out.println(Color.RED + "Please enter a valid number or enter -1" + Color.RESET);
            selection = InputManager.getInput();
            if ("-1".equals(selection)) {
                return selection;
            }
        }
        return selection;
    }

    public void changePassword() {
        System.out.println(Color.WHITE + "Please enter your new password" + Color.RESET);
        password = Menu.setPassword();
        System.out.println(Color.GREEN + "Your password has been successfully changed" + Color.RESET);
    }

    public void changeCreditPassword() {
        System.out.println(Color.WHITE + "Please enter your new password" + Color.RESET);
        account.getCreditCard().setPassword(getNewCreditPass());
        System.out.println(Color.GREEN + "Your credit card password has been successfully changed" + Color.RESET);
    }

    private String getNewCreditPass() {
        String password = InputManager.getInput();
        while (!creditPassValidity(password)) {
            System.out.println(Color.RED + "Please enter a valid credit card password . it must contain 4 digits");
            password = InputManager.getInput();
        }
        return password;
    }

    private boolean creditPassValidity(String password) {
        String regex = "\\d{4}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public void changeContactStatus() {
        setContactsActivated(!isContactsActivated());
        System.out.println(Color.GREEN + "Your contacts option has been successfully changed" + Color.RESET);
    }

    public void addNewTicket(Ticket newTicket) {
        tickets.add(newTicket);
    }

    public void displayTickets() {
        if(tickets.size() == 0){
            System.out.println(Color.RED + "You don't have any tickets yet!" + Color.RESET);
            return;
        }
        int count = 1;
        System.out.println(Color.CYAN + "*".repeat(35) + Color.RESET);
        for (Ticket ticket : tickets) {
            System.out.println(Color.WHITE + count + "-" + Color.BLUE + ticket.getType().toString() + Color.RESET);
            count++;
        }
        System.out.println(Color.CYAN + "*".repeat(35) + Color.RESET);
        selectTicket();
    }

    private void selectTicket() {
        System.out.println(Color.WHITE + "Enter the number of the ticket you want to see or enter " + Color.RED + "-1 " +
                Color.WHITE + "to return to last menu" + Color.RESET);
        String selection = InputManager.getInput();
        if ("-1".equals(selection)) {
            Menu.printSupportMenu();
            return;
        }
        while (!InputManager.isInputValid(selection, tickets.size())) {
            System.out.println(Color.RED + "Please enter a number from the list or enter -1" + Color.RESET);
            selection = InputManager.getInput();
            if ("-1".equals(selection)) {
                Menu.printSupportMenu();
                return;
            }
        }
        Ticket selected = tickets.get(Integer.parseInt(selection) - 1);
        System.out.println(selected.toString());
    }

    @Override
    public String toString() {
        return Color.CYAN + "*".repeat(35) + '\n' + Color.WHITE + "Fullname : " + Color.BLUE +
                name + " " + lastName + '\n' + Color.WHITE + "Phone number : " + Color.BLUE +
                phoneNumber + '\n' + Color.WHITE + "Account ID : " + Color.BLUE + account.getAccountID() +
                '\n' + Color.CYAN + "*".repeat(35) + Color.RESET;
    }

    public Contact findContact(String phoneNumber) {
        for (Contact contact : contacts) {
            if (contact.getPhoneNumber().equals(phoneNumber)) {
                return contact;
            }
        }
        return null;
    }

    public void displayReceipts() {
        if(receipts.size() == 0){
            System.out.println(Color.RED + "There is no receipts for you yet!" + Color.RESET);
            return;
        }
        int count = 1;
        Collections.reverse(receipts);
        System.out.println(Color.CYAN + "*".repeat(35) + Color.RESET);
        for (Receipt receipt : receipts) {
            Receipt.printSimpleReceipt(receipt, count);
            count++;
        }
        System.out.println(Color.CYAN + "*".repeat(35) + Color.RESET);
        Receipt selected = selectReceipt(receipts);
        Collections.reverse(receipts);
        if (selected == null) {
            return;
        }
        System.out.println(selected);
    }

    private Receipt selectReceipt(List<Receipt> receipts) {
        System.out.println(Color.WHITE + "Enter the number of the receipt you want to see or enter " + Color.RED + "-1 "
                + Color.WHITE + "to return to last menu" + Color.RESET);
        String selection = InputManager.getInput();
        if ("-1".equals(selection)) {
            return null;
        }
        while (!InputManager.isInputValid(selection, receipts.size())) {
            System.out.println(Color.RED + "Please enter a valid number or enter -1" + Color.RESET);
            selection = InputManager.getInput();
            if ("-1".equals(selection)) {
                return null;
            }
        }
        return receipts.get(Integer.parseInt(selection) - 1);
    }

    public void filterReceipt() {
        System.out.println(Color.WHITE + "Please enter the start date for your filter in YYYY-MM-DD format" + Color.RESET);
        Instant start = Receipt.getDateInput();
        System.out.println(Color.WHITE + "Please enter the end date for your filter in YYYY-MM-DD format" + Color.RESET);
        Instant end = Receipt.getDateInput();
        int count = 1;
        List<Receipt> matchedReceipts = new ArrayList<>();
        for (Receipt receipt : receipts) {
            if (receipt.getTime().isAfter(start) && end.isAfter(receipt.getTime())) {
                matchedReceipts.add(receipt);
                Receipt.printSimpleReceipt(receipt, count);
                count++;
            }
        }
        Receipt selected = selectReceipt(matchedReceipts);
        if (selected != null) {
            System.out.println(selected);
        }
    }
}