package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public void chargeAccount() {
        System.out.println(Color.WHITE + "Please enter the amount your trying to charge your account");
        String input = InputManager.getInput();
        while (!chargeAmountValidity(input)) {
            System.out.println(Color.RED + "Please enter a valid number" + Color.RESET);
            input = InputManager.getInput();
        }
        account.setBalance(account.getBalance() + Integer.parseInt(input));
        ChargeReceipt.createChargeReceipt(account.getBalance());
        System.out.println(Color.GREEN + "Your account has been successfully charged. new balance : " + Color.WHITE
                + account.getBalance() + '$' + Color.RESET);
    }

    public static boolean chargeAmountValidity(String input) {
        String numberRegex = "\\d+";
        Pattern numPattern = Pattern.compile(numberRegex);
        Matcher numMatcher = numPattern.matcher(input);
        return numMatcher.matches() && input.length() < 10;
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
        int count = 1;
        System.out.println(Color.CYAN + "*".repeat(35) + Color.RESET);
        for (Contact contact : contacts) {
            System.out.println(Color.WHITE + count + "-" + Color.BLUE + contact.getName() + " " + contact.getLastName());
            count++;
        }
        System.out.println(Color.CYAN + "*".repeat(35) + Color.RESET);
    }

    public void showAndEditContact() {
        Contact selectedContact = selectContactFromList();
        if (selectedContact == null) {
            return;
        }
        System.out.println(selectedContact.toString());
        editContactMenu(selectedContact);
    }

    private void editContactMenu(Contact selectedContact) {
        System.out.println(Color.WHITE + "Enter 1 if you want to edit this contact and 2 to return" + Color.RESET);
        String selection = InputManager.getInput();
        while (!Menu.isInputValid(selection, 2)) {
            System.out.println(Color.RED + "Please enter a number between 1 and 2" + Color.RESET);
            selection = InputManager.getInput();
        }
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
        String selection = InputManager.getInput();
        if ("-1".equals(selection)) {
            return null;
        }
        while (!Menu.isInputValid(selection, contacts.size())) {
            System.out.println(Color.RED + "Please enter a number between 1 and " + contacts.size() + " or enter -1" + Color.RESET);
            selection = InputManager.getInput();
            if ("-1".equals(selection)) {
                return null;
            }
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
        int count = 1;
        System.out.println(Color.CYAN + "*".repeat(35) + Color.RESET);
        for (User recentUser : recentUsers) {
            System.out.println(Color.WHITE + count + "-" + Color.BLUE + recentUser.getName() + " " + recentUser.getLastName());
            count++;
        }
        System.out.println(Color.CYAN + "*".repeat(35) + Color.RESET);
    }

    public User selectRecentUserFromList() {
        System.out.println(Color.WHITE + "Enter the number of the user you want to transfer money or enter -1 to return to last menu" + Color.RESET);
        String selection = InputManager.getInput();
        if ("-1".equals(selection)) {
            return null;
        }
        while (!Menu.isInputValid(selection, recentUsers.size())) {
            System.out.println(Color.RED + "Please enter a valid number or enter -1" + Color.RESET);
            selection = InputManager.getInput();
            if ("-1".equals(selection)) {
                return null;
            }
        }
        return recentUsers.get(Integer.parseInt(selection) - 1);
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
        System.out.println(Color.WHITE + "Enter the number of the ticket you want to see or enter -1 to return to last menu" + Color.RESET);
        String selection = InputManager.getInput();
        if ("-1".equals(selection)) {
            Menu.printSupportMenu();
            return;
        }
        while (!Menu.isInputValid(selection, tickets.size())) {
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
}
