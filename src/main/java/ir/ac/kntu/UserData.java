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
            if (user.getPhoneNumber().equals(phoneNumber) && user.isAuthenticated()) {
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
        boolean confirmation = printConfirmation(destination, amount);
        if (!confirmation) {
            return -1;
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

    private boolean printConfirmation(User destination, String amount) {
        System.out.println(Color.YELLOW + "<>".repeat(20) + Color.RESET);
        System.out.println(Color.WHITE + "Your transfer's details:" + Color.RESET);
        System.out.println(Color.WHITE + "Amount: " + Color.GREEN + amount + Color.RESET);
        System.out.println(Color.WHITE + "Destination name : " + Color.BLUE + destination.getName() + " "
                + destination.getLastName() + Color.RESET);
        System.out.println(Color.YELLOW + "<>".repeat(20) + Color.RESET);
        System.out.println(Color.WHITE + "Enter " + Color.GREEN + "1 " + Color.WHITE + "to confirm and " + Color.RED +
                "2 " + Color.WHITE + "to cancel" + Color.RESET);
        String selection = InputManager.getSelection(2);
        return "1".equals(selection);
    }

    private void updateBalances(User destination, String amount) {
        destination.getAccount().setBalance(destination.getAccount().getBalance() + Long.parseLong(amount));
        currentUser.getAccount().setBalance(currentUser.getAccount().getBalance() - (Long.parseLong(amount) + 500));
        System.out.println(Color.GREEN + "The money has been transferred successfully!!" + Color.RESET);
    }

    public User findUserByAccountID(String accountID) {
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
        System.out.println(Color.WHITE + "4-" + Color.BLUE + "Combination" + Color.RESET);
        System.out.println(Color.WHITE + "5-" + Color.BLUE + "Return" + Color.RESET);
        handleSearchMethod();
    }

    private void handleSearchMethod() {
        String selection = InputManager.getSelection(5);
        switch (selection) {
            case "1" -> {
                List<User> matched = searchByName();
                printMatched(matched);
                Menu.printAdminUserMenu();
            }
            case "2" -> {
                List<User> matched = searchByLastname();
                printMatched(matched);
                Menu.printAdminUserMenu();
            }
            case "3" -> {
                List<User> matched = searchByPhoneNumber();
                printMatched(matched);
                Menu.printAdminUserMenu();
            }
            case "4" ->{
                List<User> matched = combinedSearch();
                printMatched(matched);
                Menu.printAdminUserMenu();
            }
            default -> Menu.printAdminMenu();
        }
    }

    private List<User> combinedSearch() {
        System.out.println(Color.WHITE + "Please enter the name your searching for" + Color.RESET);
        String name = InputManager.getInput();
        System.out.println(Color.WHITE + "Please enter the lastname your searching for" + Color.RESET);
        String lastName = InputManager.getInput();
        System.out.println(Color.WHITE + "Please enter the phone number your searching for" + Color.RESET);
        String phoneNumber = InputManager.getInput();
        String lookingFor = name + lastName + phoneNumber;
        List<User> matched = new ArrayList<>();
        for(User user : allUsers){
            if(lookingFor.equals(user.getName()+user.getLastName()+user.getPhoneNumber())){
                matched.add(user);
            }
        }
        if(matched.size() == 0){
            for (User user : allUsers) {
                if (distance(lookingFor, user.getName()+user.getLastName()+user.getPhoneNumber()) < 7) {
                    matched.add(user);
                }
            }
        }
        return matched;
    }

    private void printMatched(List<User> matched) {
        if(matched.size() == 0){
            System.out.println(Color.RED + "No user with this information was found" + Color.RESET);
            return;
        }
        for(User user : matched){
            System.out.println(user);
        }
    }

    private List<User> searchByPhoneNumber() {
        List<User> matched = new ArrayList<>();
        System.out.println(Color.WHITE + "Please enter the phone number your searching for" + Color.RESET);
        String phoneNumber = InputManager.getInput();
        for(User user : allUsers){
            if(phoneNumber.equals(user.getPhoneNumber())){
                matched.add(user);
            }
        }
        if(matched.size() == 0) {
            for (User user : allUsers) {
                if (distance(phoneNumber, user.getPhoneNumber()) < 2) {
                    matched.add(user);
                }
            }
        }
        return matched;
    }

    private List<User> searchByLastname() {
        List<User> matched = new ArrayList<>();
        System.out.println(Color.WHITE + "Please enter the lastname your searching for" + Color.RESET);
        String lastname = InputManager.getInput();
        for(User user : allUsers){
            if(lastname.equals(user.getLastName())){
                matched.add(user);
            }
        }
        if(matched.size() == 0) {
            for (User user : allUsers) {
                if (distance(lastname, user.getLastName()) < 4) {
                    matched.add(user);
                }
            }
        }
        return matched;
    }

    private List<User> searchByName() {
        List<User> matched = new ArrayList<>();
        System.out.println(Color.WHITE + "Please enter the name your searching for" + Color.RESET);
        String name = InputManager.getInput();
        for(User user : allUsers){
            if(name.equals(user.getName())){
                matched.add(user);
            }
        }
        if(matched.size() == 0) {
            for (User user : allUsers) {
                if (distance(name, user.getName()) < 4) {
                    matched.add(user);
                }
            }
        }
        return matched;
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
            if(user.isAuthenticated()) {
                System.out.println(Color.WHITE + count + "-" + Color.BLUE + user.getName() + " " + user.getLastName());
                count++;
            }
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

    public void removeUser(User user){
        allUsers.remove(user);
    }
}
