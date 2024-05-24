package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;

public class UserData {
    private List<User> allUsers;
    private User currentUser;

    public UserData() {
        allUsers = new ArrayList<>();
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void addUser(User newUser) {
        allUsers.add(newUser);
    }

    public User findUserByPhoneNumber(String phoneNumber) {
        for (User user : allUsers) {
            if (user.getPhoneNumber().equals(phoneNumber)) {
                return user;
            }
        }
        return null;
    }

    public boolean accountIdAlreadyExists(String accID) {
        for (User user : allUsers) {
            if (user.isAuthenticated() && user.getAccount().getAccountID().equals(accID)) {
                return true;
            }
        }
        return false;
    }


    public void transferByAccountID() {
        int amount = 0;
        System.out.println(Color.WHITE + "Please enter the account ID you want to transfer money to" + Color.RESET);
        String input = InputManager.getInput();
        while (!Account.accountIDValidity(input)) {
            System.out.println(Color.RED + "Please enter your account ID correctly (13 digits and starts with 0)" + Color.RESET);
            input = InputManager.getInput();
        }
        User destination = findUserByAccountID(input);
        if (destination == null) {
            System.out.println(Color.RED + "There is no user with this account ID" + Color.RESET);
            Menu.printTransferMenu();
        } else {
            amount = transferMoney(destination);
        }
    }

    private int transferMoney(User destination) {
        System.out.println(Color.WHITE + "Please enter the amount you want to transfer (Maximum : 10 million)");
        String amount = InputManager.getInput();
        while (!User.chargeAmountValidity(amount) && Integer.parseInt(amount) > 10000000) {
            System.out.println(Color.RED + "Please enter a valid amount (Maximum : 10 million)" + Color.RESET);
            amount = InputManager.getInput();
        }
        if (Integer.parseInt(amount) + 500 > currentUser.getAccount().getBalance()) {
            System.out.println(Color.RED + "Your balance is not enough. Current Balance : " +
                    Color.GREEN + currentUser.getAccount().getBalance() + '$' + Color.RESET);
            return -1;
        }
        updateBalances(destination, amount);
        currentUser.addToRecentUsers(destination);
        return Integer.parseInt(amount);
    }

    private void updateBalances(User destination, String amount) {
        destination.getAccount().setBalance(destination.getAccount().getBalance() + Integer.parseInt(amount));
        currentUser.getAccount().setBalance(currentUser.getAccount().getBalance() - (Integer.parseInt(amount) + 500));
        System.out.println(Color.GREEN + "The money has been transferred successfully!!" + Color.RESET);
    }

    private User findUserByAccountID(String accountID) {
        for (User user : allUsers) {
            if (user.isAuthenticated() && user.getAccount().getAccountID().equals(accountID)) {
                return user;
            }
        }
        return null;
    }

    public void transferByContact() {
        int amount = 0;
        System.out.println(Color.WHITE + "Please select the contact you want to transfer money to" + Color.RESET);
        currentUser.displayAllContacts();
        Contact selected = currentUser.selectContactFromList();
        if (selected == null) {
            return;
        }
        if (selected.getUser().haveInContacts(selected) && selected.getUser().isContactsActivated()) {
            amount = transferMoney(selected.getUser());
        } else {
            System.out.println(Color.RED + "Selected contact does not have you in their contacts or they have deactivated getting money from contacts" + Color.RESET);
        }
    }

    public void transferByRecentUser() {
        int amount = 0;
        System.out.println(Color.WHITE + "Please select the user you want to transfer money to" + Color.RESET);
        currentUser.displayRecentUsers();
        User selected = currentUser.selectRecentUserFromList();
        if (selected == null) {
            return;
        }
        amount = transferMoney(selected);
    }
}
