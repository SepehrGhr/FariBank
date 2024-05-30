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

    public User findUserBySecurityNumber(String securityNumber) {
        for (User user : allUsers) {
            if (user.getSecurityNumber().equals(securityNumber)) {
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
        long amount = 0;
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
            if (amount != -1) {
                TransferReceipt newReceipt = new TransferReceipt(amount, Main.getUsers().getCurrentUser(), destination, Method.ACCOUNT);
                Main.getUsers().getCurrentUser().addReceipt(newReceipt);
                destination.addReceipt(newReceipt);
                System.out.println(newReceipt);
            }
        }
    }

    private long transferMoney(User destination) {
        System.out.println(Color.WHITE + "Please enter the amount you want to transfer (Maximum : 10 million)");
        String amount = InputManager.getInput();
        while (!InputManager.chargeAmountValidity(amount) || amount.length() > 8 || Long.parseLong(amount) > 10000000) {
            System.out.println(Color.RED + "Please enter a valid amount (Maximum : 10 million)" + Color.RESET);
            amount = InputManager.getInput();
        }
        if (Long.parseLong(amount) + 500 > currentUser.getAccount().getBalance()) {
            System.out.println(Color.RED + "Your balance is not enough. Current Balance : " +
                    Color.GREEN + currentUser.getAccount().getBalance() + "$ " + Color.RED + "required balance:"
                    + Color.GREEN + (Long.parseLong(amount) + 500) + Color.RESET);
            return -1;
        }
        updateBalances(destination, amount);
        currentUser.addToRecentUsers(destination);
        return Long.parseLong(amount);
    }

    private void updateBalances(User destination, String amount) {
        destination.getAccount().setBalance(destination.getAccount().getBalance() + Long.parseLong(amount));
        currentUser.getAccount().setBalance(currentUser.getAccount().getBalance() - (Long.parseLong(amount) + 500));
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
        long amount = 0;
        System.out.println(Color.WHITE + "Please select the contact you want to transfer money to" + Color.RESET);
        currentUser.displayAllContacts();
        Contact selected = currentUser.selectContactFromList();
        if (selected == null) {
            return;
        }
        if (selected.getUser().haveInContacts(selected) && selected.getUser().isContactsActivated()) {
            amount = transferMoney(selected.getUser());
            if (amount != -1) {
                TransferReceipt newReceipt = new TransferReceipt(amount, Main.getUsers().getCurrentUser(), selected.getUser(), Method.CONTACT);
                Main.getUsers().getCurrentUser().addReceipt(newReceipt);
                TransferReceipt destReceipt = new TransferReceipt(amount, Main.getUsers().getCurrentUser(), selected.getUser(), Method.ACCOUNT);
                selected.getUser().addReceipt(destReceipt);
                System.out.println(newReceipt);
            }
        } else {
            System.out.println(Color.RED + "Selected contact does not have you in their contacts or they have deactivated getting money from contacts" + Color.RESET);
        }
    }

    public void transferByRecentUser() {
        long amount = 0;
        System.out.println(Color.WHITE + "Please select the user you want to transfer money to" + Color.RESET);
        currentUser.displayRecentUsers();
        User selected = currentUser.selectRecentUserFromList();
        if (selected == null) {
            return;
        }
        amount = transferMoney(selected);
        if (amount != -1) {
            TransferReceipt newReceipt = new TransferReceipt(amount, Main.getUsers().getCurrentUser(), selected, Method.ACCOUNT);
            Main.getUsers().getCurrentUser().addReceipt(newReceipt);
            selected.addReceipt(newReceipt);
            System.out.println(newReceipt);
        }
    }

    public void handleAdminUserInput() {
        String selection = InputManager.getSelection(3);
        switch (selection) {
            case "1" -> {
                showAllUsers();
                selectUserToDisplay();
                Menu.printAdminUserMenu();
            }
            case "2" -> {
                printSearchMethod();
            }
            default -> Menu.printAdminMenu();
        }
    }

    private void printSearchMethod() {
        System.out.println(Color.WHITE + "Please select your search method" + Color.RESET);
        System.out.println(Color.WHITE + "1-" + Color.BLUE + "Name" + Color.RESET);
        System.out.println(Color.WHITE + "2-" + Color.BLUE + "Lastname" + Color.RESET);
        System.out.println(Color.WHITE + "3-" + Color.BLUE + "Phone Number" + Color.RESET);
        System.out.println(Color.WHITE + "4-" + Color.BLUE + "Return" + Color.RESET);
        handleSearchMethod();
    }

    private void handleSearchMethod() {
        String selection = InputManager.getSelection(4);
        switch (selection) {
            case "1" -> {
                searchByName();
                Menu.printAdminUserMenu();
            }
            case "2" -> {
                searchByLastname();
                Menu.printAdminUserMenu();
            }
            case "3" -> {
                searchByPhoneNumber();
                Menu.printAdminUserMenu();
            }
            default -> Menu.printAdminMenu();
        }
    }

    private void searchByPhoneNumber() {
        System.out.println(Color.WHITE + "Please enter the phone number your searching for" + Color.RESET);
        String phoneNumber = InputManager.getInput();
        for (User user : allUsers) {
            if (distance(phoneNumber, user.getPhoneNumber()) < 3) {
                System.out.println(user);
                return;
            }
        }
        System.out.println(Color.RED + "No user with this phone number was found" + Color.RESET);
    }

    private void searchByLastname() {
        System.out.println(Color.WHITE + "Please enter the lastname your searching for" + Color.RESET);
        String lastname = InputManager.getInput();
        for (User user : allUsers) {
            if (distance(lastname, user.getLastName()) < 4) {
                System.out.println(user);
                return;
            }
        }
        System.out.println(Color.RED + "No user with this lastname was found" + Color.RESET);
    }

    private void searchByName() {
        System.out.println(Color.WHITE + "Please enter the name your searching for" + Color.RESET);
        String name = InputManager.getInput();
        for (User user : allUsers) {
            if (distance(name, user.getName()) < 4) {
                System.out.println(user);
                return;
            }
        }
        System.out.println(Color.RED + "No user with this name was found" + Color.RESET);
    }

    private void selectUserToDisplay() {
        System.out.println(Color.WHITE + "Enter the number of the user you want to see or enter -1 to return to last menu" + Color.RESET);
        String selection = InputManager.getInput();
        if ("-1".equals(selection)) {
            Menu.printAdminUserMenu();
            return;
        }
        while (!InputManager.isInputValid(selection, allUsers.size())) {
            System.out.println(Color.RED + "Please enter a number from the list or enter -1" + Color.RESET);
            selection = InputManager.getInput();
            if ("-1".equals(selection)) {
                Menu.printAdminUserMenu();
                return;
            }
        }
        User selected = allUsers.get(Integer.parseInt(selection) - 1);
        System.out.println(selected.toString());
    }

    private void showAllUsers() {
        int count = 1;
        for (User user : allUsers) {
            System.out.println(Color.WHITE + count + "-" + Color.BLUE + user.getName() + " " + user.getLastName());
            count++;
        }
    }

    public int distance(String str1, String str2) {
        int length1 = str1.length();
        int length2 = str2.length();
        int[][] distances = new int[length1 + 1][length2 + 1];
        for (int i = 0; i <= length1; i++) {
            for (int j = 0; j <= length2; j++) {
                if (i == 0) {
                    distances[i][j] = j;
                } else if (j == 0) {
                    distances[i][j] = i;
                } else {
                    int insertion = distances[i][j - 1] + 1;
                    int deletion = distances[i - 1][j] + 1;
                    int substitution = distances[i - 1][j - 1] + (str1.charAt(i - 1) == str2.charAt(j - 1) ? 0 : 1);
                    distances[i][j] = Math.min(insertion, Math.min(deletion, substitution));
                }
            }
        }
        return distances[length1][length2];
    }

    public void addNewUserToDatabase(User newUser) {
        AuthenticationRequest.newAuthenticationRequest(newUser);
        Main.getUsers().addUser(newUser);
    }
}
