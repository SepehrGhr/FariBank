package ir.ac.kntu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menu {
    public static void printSelectRuleMenu() {
        System.out.println(Color.YELLOW + "please enter what you want to login as" + Color.RESET);
        System.out.println(Color.WHITE + "1-" + Color.BLUE + "User");
        System.out.println(Color.WHITE + "2-" + Color.BLUE + "Admin" + Color.RESET);
        System.out.println(Color.WHITE + "3-" + Color.BLUE + "Quit" + Color.RESET);
        handleSelectRuleInput();
    }

    private static void handleSelectRuleInput() {
        String selection = InputManager.getInput();
        while (!isInputValid(selection, 3)) {
            System.out.println(Color.RED + "Please enter a number between 1,2 or 3" + Color.RESET);
            selection = InputManager.getInput();
        }
        if ("1".equals(selection)) {
            printSignOrLoginMenu();
            return;
        } else if ("2".equals(selection)) {
            printAdminLoginMenu();
            return;
        }
        endProgram();
    }

    private static void printAdminLoginMenu() {
        System.out.println(Color.WHITE + "Please enter your username");
        String input = InputManager.getInput();
        Admin loggingIn = Main.getAdminData().findAdminByUsername(input);
        if (loggingIn == null) {
            System.out.println(Color.RED + "There is no admin with this username" + Color.RESET);
            printSelectRuleMenu();
        } else {
            System.out.println(Color.WHITE + "Please enter your password" + Color.RESET);
            String password = InputManager.getInput();
            while (!password.equals(loggingIn.getPassword())) {
                System.out.println(Color.RED + "Entered password is incorrect , please try again" + Color.RESET);
                password = InputManager.getInput();
            }
            printAdminMenu();
        }
    }

    public static void printAdminMenu() {
        System.out.println(Color.WHITE + "1-" + Color.BLUE + "Authentication requests" + Color.RESET);
        System.out.println(Color.WHITE + "2-" + Color.BLUE + "Tickets" + Color.RESET);
        System.out.println(Color.WHITE + "3-" + Color.BLUE + "Users" + Color.RESET);
        System.out.println(Color.WHITE + "4-" + Color.BLUE + "Log out" + Color.RESET);
        handleAdminInput();
    }

    private static void handleAdminInput() {
        String selection = InputManager.getInput();
        while (!isInputValid(selection, 4)) {
            System.out.println(Color.RED + "Please enter a number between 1 and 4" + Color.RESET);
            selection = InputManager.getInput();
        }
        switch (selection) {
            case "1":
                Main.getAdminData().showAuthenticationRequests();
                break;
            case "2":
                break;
            case "3":
                break;
            case "4":
                printSelectRuleMenu();
                break;
            default:
                break;
        }
    }

    public static boolean isInputValid(String input, int max) {
        String checkNumeric = "\\d+";
        Pattern pattern = Pattern.compile(checkNumeric);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches() && input.length() < 5 && Integer.parseInt(input) <= max && Integer.parseInt(input) > 0;

    }

    private static void printSignOrLoginMenu() {
        System.out.println(Color.YELLOW + "choose an option");
        System.out.println(Color.WHITE + "1-" + Color.BLUE + "Login");
        System.out.println(Color.WHITE + "2-" + Color.BLUE + "Sign up" + Color.RESET);
        System.out.println(Color.WHITE + "3-" + Color.BLUE + "Return to previous menu" + Color.RESET);
        handleSignOrLogin();
    }

    private static void handleSignOrLogin() {
        String selection = InputManager.getInput();
        while (!isInputValid(selection, 3)) {
            System.out.println(Color.RED + "Please enter a number between 1 and 3" + Color.RESET);
            selection = InputManager.getInput();
        }
        if ("1".equals(selection)) {
            printUserLoginMenu();
            return;
        }
        if ("2".equals(selection)) {
            printSignUpMenu();
        }
        printSelectRuleMenu();
    }

    private static void printSignUpMenu() {
        System.out.println(Color.YELLOW + "Please enter your name" + Color.RESET);
        String name = setUserName();
        System.out.println(Color.YELLOW + "Please enter your last name" + Color.RESET);
        String lastName = setUserName();
        System.out.println(Color.YELLOW + "Please enter your phone number" + Color.RESET);
        String phoneNumber = setPhoneNumber();
        System.out.println(Color.YELLOW + "Please enter your security number" + Color.RESET);
        String securityNumber = setSecurityNumber();
        System.out.println(Color.YELLOW + "Please enter your password" + Color.WHITE + "(it must contain at least " +
                "one lowercase,uppercase,number and character)" + Color.RESET);
        String password = setPassword();
        System.out.println(Color.GREEN + "Your information has been successfully registered and will be checked soon" + Color.RESET);
        User newUser = new User(name, lastName, phoneNumber, securityNumber, password);
        addNewUserToDatabase(newUser);
    }

    private static void addNewUserToDatabase(User newUser) {
        AuthenticationRequest.newAuthenticationRequest(newUser);
        Main.getUsers().addUser(newUser);
    }

    public static String setPassword() {
        String password = InputManager.getInput();
        while (!checkPasswordValidity(password)) {
            System.out.println(Color.RED + "Your password must contain at least one lowercase,uppercase,number and" +
                    " character, please try again" + Color.RESET);
            password = InputManager.getInput();
        }
        return password;
    }

    private static boolean checkPasswordValidity(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()-+=?])[^\\s]{8,}$";
        Pattern passwordPattern = Pattern.compile(passwordRegex);
        Matcher passwordMatcher = passwordPattern.matcher(password);
        return passwordMatcher.matches();
    }

    private static String setSecurityNumber() {
        String securityNumber = InputManager.getInput();
        while (!checkSecurityNumberValidity(securityNumber)) {
            System.out.println(Color.RED + "Please enter your security number correctly (10 digits)" + Color.RESET);
            securityNumber = InputManager.getInput();
        }
        return securityNumber;
    }

    private static boolean checkSecurityNumberValidity(String securityNumber) {
        String ssnRegex = "\\d{10}";
        Pattern ssnPattern = Pattern.compile(ssnRegex);
        Matcher ssnMatcher = ssnPattern.matcher(securityNumber);
        return ssnMatcher.matches();
    }

    private static String setPhoneNumber() {
        String phoneNumber = InputManager.getInput();
        while (!checkPhoneNumberValidity(phoneNumber)) {
            System.out.println(Color.RED + "Please enter your phone number correctly" + Color.RESET);
            phoneNumber = InputManager.getInput();
        }
        return phoneNumber;
    }

    public static boolean checkPhoneNumberValidity(String phoneNumber) {
        String numberRegex = "\\d{11}";
        Pattern numberPattern = Pattern.compile(numberRegex);
        Matcher numberMatcher = numberPattern.matcher(phoneNumber);
        return numberMatcher.matches() && "09".equals(phoneNumber.substring(0, 2));
    }

    private static String setUserName() {
        String name = InputManager.getInput();
        while (!checkStringValidity(name)) {
            System.out.println(Color.RED + "Please enter your name correctly" + Color.RESET);
            name = InputManager.getInput();
        }
        return name;

    }

    public static boolean checkStringValidity(String name) {
        String regex = "[a-zA-z]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    private static void printUserLoginMenu() {
        System.out.println(Color.YELLOW + "Please enter your username (phone number)" + Color.RESET);
        User loggingIn = getUsername();
        System.out.println(Color.YELLOW + "Please enter your password " + Color.RESET);
        getPasswordInput(loggingIn);
        System.out.println(Color.GREEN + "You have successfully logged in!" + Color.RESET);
        Main.getUsers().setCurrentUser(loggingIn);
        showMenuAfterLogin(loggingIn);
    }

    private static void showMenuAfterLogin(User loggingIn) {
        if (loggingIn.isAuthenticated()) {
            printUserMainMenu();
        } else {
            if (Main.getAdminData().getRequests().get(loggingIn).isChecked() && !Main.getAdminData().getRequests().get(loggingIn).isApproved()) {
                Main.getAdminData().getRequests().get(loggingIn).showErrorMassage();
                //editInformations();
            } else {
                System.out.println(Color.RED + "We are sorry but your authentication request has not been checked yet, please come back later");
                System.out.println(Color.WHITE + "enter any key to log out" + Color.RESET);
                InputManager.getInput();
                userLogout();
            }
        }
    }

    private static void printUserMainMenu() {
        System.out.println(Color.PURPLE + "Welcome to our bank " + Main.getUsers().getCurrentUser().getName() + "!!" + Color.RESET);
        System.out.println(Color.WHITE + "1-" + Color.BLUE + "Account management" + Color.RESET);
        System.out.println(Color.WHITE + "2-" + Color.BLUE + "Contacts" + Color.RESET);
        System.out.println(Color.WHITE + "3-" + Color.BLUE + "Transfer money" + Color.RESET);
        System.out.println(Color.WHITE + "4-" + Color.BLUE + "Support" + Color.RESET);
        System.out.println(Color.WHITE + "5-" + Color.BLUE + "Settings" + Color.RESET);
        System.out.println(Color.WHITE + "6-" + Color.BLUE + "Log out" + Color.RESET);
        System.out.println(Color.WHITE + "7-" + Color.BLUE + "Quit" + Color.RESET);
        handleUserMainMenuInput();
    }

    private static void handleUserMainMenuInput() {
        String selection = InputManager.getInput();
        while (!isInputValid(selection, 7)) {
            System.out.println(Color.RED + "Please enter a number between 1 and 7" + Color.RESET);
            selection = InputManager.getInput();
        }
        switch (selection) {
            case "1" -> {
                printManagementMenu();
                printUserMainMenu();
            }
            case "2" -> printContactsMenu();
            case "3" -> printTransferMenu();
            case "4" -> printSupportMenu();
            case "5" -> printSettingsMenu();
            case "6" -> userLogout();
            case "7" -> endProgram();
            default -> {
                break;
            }
        }
    }

    public static void printSupportMenu() {
        System.out.println(Color.WHITE + "Please enter the number of the option you want to select");
        System.out.println(Color.WHITE + "1-" + Color.BLUE + "new Ticket" + Color.RESET);
        System.out.println(Color.WHITE + "2-" + Color.BLUE + "Tickets" + Color.RESET);
        System.out.println(Color.WHITE + "3-" + Color.BLUE + "Return" + Color.RESET);
        handleSupportInput();
    }

    private static void handleSupportInput() {
        String selection = InputManager.getInput();
        while (!isInputValid(selection, 3)) {
            System.out.println(Color.RED + "Please enter a number between 1 and 3" + Color.RESET);
            selection = InputManager.getInput();
        }
        switch (selection) {
            case "1" -> {
                Ticket.submitNewTicket();
                printSupportMenu();
            }
            case "2" -> {
                Main.getUsers().getCurrentUser().displayTickets();
                printSupportMenu();
            }
            case "3" -> printUserMainMenu();
            default -> {
                break;
            }
        }
    }

    private static void printSettingsMenu() {
        System.out.println(Color.WHITE + "Please enter the number of the option you want to select");
        System.out.println(Color.WHITE + "1-" + Color.BLUE + "Change password" + Color.RESET);
        System.out.println(Color.WHITE + "2-" + Color.BLUE + "Change credit card password" + Color.RESET);
        System.out.println(Color.WHITE + "3-" + Color.BLUE + "Activate/Deactivate contacts option" + Color.RESET);
        System.out.println(Color.WHITE + "4-" + Color.BLUE + "Return" + Color.RESET);
        handleSettingsInput();
    }

    private static void handleSettingsInput() {
        String selection = InputManager.getInput();
        while (!isInputValid(selection, 4)) {
            System.out.println(Color.RED + "Please enter a number between 1 and 4" + Color.RESET);
            selection = InputManager.getInput();
        }
        switch (selection) {
            case "1" -> {
                Main.getUsers().getCurrentUser().changePassword();
                printSettingsMenu();
            }
            case "2" -> {
                Main.getUsers().getCurrentUser().changeCreditPassword();
                printSettingsMenu();
            }
            case "3" -> {
                Main.getUsers().getCurrentUser().changeContactStatus();
                printSettingsMenu();
            }
            case "4" -> printUserMainMenu();
            default -> {
                break;
            }
        }
    }

    public static void printTransferMenu() {
        System.out.println(Color.WHITE + "Please enter the number of the method you want to use for transfer");
        System.out.println(Color.WHITE + "1-" + Color.BLUE + "Account ID" + Color.RESET);
        System.out.println(Color.WHITE + "2-" + Color.BLUE + "Contacts" + Color.RESET);
        System.out.println(Color.WHITE + "3-" + Color.BLUE + "Recent Users" + Color.RESET);
        System.out.println(Color.WHITE + "4-" + Color.BLUE + "Return" + Color.RESET);
        handleTransferMethod();
    }

    private static void handleTransferMethod() {
        String selection = InputManager.getInput();
        while (!isInputValid(selection, 4)) {
            System.out.println(Color.RED + "Please enter a number between 1 and 4" + Color.RESET);
            selection = InputManager.getInput();
        }
        switch (selection) {
            case "1" -> {
                Main.getUsers().transferByAccountID();
                printTransferMenu();
            }
            case "2" -> {
                Main.getUsers().transferByContact();
                printTransferMenu();
            }
            case "3" -> {
                Main.getUsers().transferByRecentUser();
                printTransferMenu();
            }
            case "4" -> printUserMainMenu();
            default -> {
                break;
            }
        }
    }

    private static void printContactsMenu() {
        System.out.println(Color.WHITE + "1-" + Color.BLUE + "Add contact" + Color.RESET);
        System.out.println(Color.WHITE + "2-" + Color.BLUE + "View contacts" + Color.RESET);
        System.out.println(Color.WHITE + "3-" + Color.BLUE + "Return" + Color.RESET);
        handleContactsInput();
    }

    private static void handleContactsInput() {
        String selection = InputManager.getInput();
        while (!isInputValid(selection, 3)) {
            System.out.println(Color.RED + "Please enter a number between 1 and 3" + Color.RESET);
            selection = InputManager.getInput();
        }
        switch (selection) {
            case "1" -> {
                Contact.getNewContactInfo();
                printContactsMenu();
            }
            case "2" -> {
                Main.getUsers().getCurrentUser().displayAllContacts();
                Main.getUsers().getCurrentUser().showAndEditContact();
                printContactsMenu();
            }
            case "3" -> printUserMainMenu();
            default -> {
                break;
            }
        }
    }

    private static void printManagementMenu() {
        System.out.println(Color.WHITE + "1-" + Color.BLUE + "Charge" + Color.RESET);
        System.out.println(Color.WHITE + "2-" + Color.BLUE + "View balance" + Color.RESET);
        System.out.println(Color.WHITE + "3-" + Color.BLUE + "View receipts" + Color.RESET);
        System.out.println(Color.WHITE + "4-" + Color.BLUE + "Return" + Color.RESET);
        handleManagementInput();

    }

    private static void handleManagementInput() {
        String selection = InputManager.getInput();
        while (!isInputValid(selection, 4)) {
            System.out.println(Color.RED + "Please enter a number between 1 and 4" + Color.RESET);
            selection = InputManager.getInput();
        }
        switch (selection) {
            case "1" -> {
                Main.getUsers().getCurrentUser().chargeAccount();
                printManagementMenu();
            }
            case "2" -> {
                Main.getUsers().getCurrentUser().displayBalance();
                printManagementMenu();
            }
            case "3" -> Main.getUsers().getCurrentUser().displayBalance();
            case "4" -> printUserMainMenu();
            default -> {
                break;
            }
        }
    }

    private static void endProgram() {
        System.out.println(Color.PURPLE + "Thanks for choosing our bank!" + Color.RESET);
        System.exit(0);
    }

    private static void userLogout() {
        Main.getUsers().setCurrentUser(null);
        printSelectRuleMenu();
    }

    private static void getPasswordInput(User loggingIn) {
        String password = InputManager.getInput();
        while (!(password.equals(loggingIn.getPassword()))) {
            System.out.println(Color.RED + "entered password is incorrect , please try again" + Color.RESET);
            password = InputManager.getInput();
        }
    }

    private static User getUsername() {
        String input = InputManager.getInput();
        User toLoginUser = Main.getUsers().findUserByPhoneNumber(input);
        while (toLoginUser == null) {
            System.out.println(Color.RED + "no user with this phone-number exists" + Color.RESET);
            input = InputManager.getInput();
            toLoginUser = Main.getUsers().findUserByPhoneNumber(input);
        }
        return toLoginUser;
    }
}
