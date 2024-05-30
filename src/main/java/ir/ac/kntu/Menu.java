package ir.ac.kntu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menu {
    public static void printSelectRuleMenu() {
        System.out.println(Color.YELLOW + "<>".repeat(20) + Color.RESET);
        System.out.println(Color.WHITE + "Please enter what you want to login as" + Color.RESET);
        System.out.println(Color.WHITE + "1-" + Color.BLUE + "User");
        System.out.println(Color.WHITE + "2-" + Color.BLUE + "Admin" + Color.RESET);
        System.out.println(Color.WHITE + "3-" + Color.BLUE + "Quit" + Color.RESET);
        System.out.println(Color.YELLOW + "<>".repeat(20) + Color.RESET);
        InputManager.handleSelectRuleInput();
    }

    public static void printAdminLoginMenu() {
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
        System.out.println(Color.YELLOW + "<>".repeat(20) + Color.RESET);
        System.out.println(Color.WHITE + "Please select the option you want" + Color.RESET);
        System.out.println(Color.WHITE + "1-" + Color.BLUE + "Authentication requests" + Color.RESET);
        System.out.println(Color.WHITE + "2-" + Color.BLUE + "Tickets" + Color.RESET);
        System.out.println(Color.WHITE + "3-" + Color.BLUE + "Users" + Color.RESET);
        System.out.println(Color.WHITE + "4-" + Color.BLUE + "Log out" + Color.RESET);
        System.out.println(Color.YELLOW + "<>".repeat(20) + Color.RESET);
        InputManager.handleAdminInput();
    }

    public static void printAdminUserMenu() {
        System.out.println(Color.YELLOW + "<>".repeat(20) + Color.RESET);
        System.out.println(Color.WHITE + "Please select the option you want" + Color.RESET);
        System.out.println(Color.WHITE + "1-" + Color.BLUE + "View All" + Color.RESET);
        System.out.println(Color.WHITE + "2-" + Color.BLUE + "Search" + Color.RESET);
        System.out.println(Color.WHITE + "3-" + Color.BLUE + "Return" + Color.RESET);
        System.out.println(Color.YELLOW + "<>".repeat(20) + Color.RESET);
        Main.getUsers().handleAdminUserInput();
    }

    public static void printSignOrLoginMenu() {
        System.out.println(Color.YELLOW + "<>".repeat(20) + Color.RESET);
        System.out.println(Color.WHITE + "Please select the option you want" + Color.RESET);
        System.out.println(Color.WHITE + "1-" + Color.BLUE + "Login" + Color.RESET);
        System.out.println(Color.WHITE + "2-" + Color.BLUE + "Sign up" + Color.RESET);
        System.out.println(Color.WHITE + "3-" + Color.BLUE + "Return to previous menu" + Color.RESET);
        System.out.println(Color.YELLOW + "<>".repeat(20) + Color.RESET);
        InputManager.handleSignOrLogin();
    }

    public static void printSignUpMenu() {
        System.out.println(Color.WHITE + "-Please enter your name-" + Color.RESET);
        String name = setUserName();
        System.out.println(Color.WHITE + "-Please enter your last name-" + Color.RESET);
        String lastName = setUserName();
        System.out.println(Color.WHITE + "-Please enter your phone number-" + Color.RESET);
        String phoneNumber = setPhoneNumber();
        if ("".equals(phoneNumber)) {
            System.out.println(Color.WHITE + "Please login instead" + Color.RESET);
            printSignOrLoginMenu();
            return;
        }
        System.out.println(Color.WHITE + "-Please enter your security number-" + Color.RESET);
        String securityNumber = setSecurityNumber();
        if ("".equals(securityNumber)) {
            System.out.println(Color.WHITE + "Please login instead" + Color.RESET);
            printSignOrLoginMenu();
            return;
        }
        System.out.println(Color.WHITE + "-Please enter your password-" + Color.WHITE + " (it must contain at least " +
                "one lowercase,uppercase,number and character)" + Color.RESET);
        String password = setPassword();
        System.out.println(Color.GREEN + "Your information has been successfully registered and will be checked soon" + Color.RESET);
        User newUser = new User(name, lastName, phoneNumber, securityNumber, password);
        Main.getUsers().addNewUserToDatabase(newUser);
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

    public static String setSecurityNumber() {
        String securityNumber = InputManager.getInput();
        while (!checkSecurityNumberValidity(securityNumber)) {
            System.out.println(Color.RED + "Please enter your security number correctly (10 digits)" + Color.RESET);
            securityNumber = InputManager.getInput();
        }
        if (Main.getUsers().findUserBySecurityNumber(securityNumber) != null) {
            System.out.println(Color.RED + "There is already an account with this security number in our bank" + Color.RESET);
            return "";
        }
        return securityNumber;
    }

    private static boolean checkSecurityNumberValidity(String securityNumber) {
        String ssnRegex = "\\d{10}";
        Pattern ssnPattern = Pattern.compile(ssnRegex);
        Matcher ssnMatcher = ssnPattern.matcher(securityNumber);
        return ssnMatcher.matches();
    }

    public static String setPhoneNumber() {
        String phoneNumber = InputManager.getInput();
        while (!checkPhoneNumberValidity(phoneNumber)) {
            System.out.println(Color.RED + "Please enter your phone number correctly" + Color.RESET);
            phoneNumber = InputManager.getInput();
        }
        if (Main.getUsers().findUserByPhoneNumber(phoneNumber) != null) {
            System.out.println(Color.RED + "There is already an user registered with this phone number" + Color.RESET);
            return "";
        }
        return phoneNumber;
    }

    public static boolean checkPhoneNumberValidity(String phoneNumber) {
        String numberRegex = "\\d{11}";
        Pattern numberPattern = Pattern.compile(numberRegex);
        Matcher numberMatcher = numberPattern.matcher(phoneNumber);
        return numberMatcher.matches() && "09".equals(phoneNumber.substring(0, 2));
    }

    public static String setUserName() {
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

    public static void printUserLoginMenu() {
        System.out.println(Color.WHITE + "-Please enter your username (Phone number)-" + Color.RESET);
        User loggingIn = getUsername();
        System.out.println(Color.WHITE + "-Please enter your password-" + Color.RESET);
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
                AuthenticationRequest.editInformation();
                Menu.userLogout();
            } else {
                System.out.println(Color.RED + "We are sorry but your authentication request has not been checked yet, please come back later");
                System.out.println(Color.WHITE + "enter any key to log out" + Color.RESET);
                InputManager.getInput();
                userLogout();
            }
        }
    }

    public static void printUserMainMenu() {
        System.out.println(Color.YELLOW + "<>".repeat(20) + Color.RESET);
        System.out.println(Color.PURPLE + "Welcome to your bank " + Main.getUsers().getCurrentUser().getName() + "!!" + Color.RESET);
        System.out.println(Color.WHITE + "1-" + Color.BLUE + "Account management" + Color.RESET);
        System.out.println(Color.WHITE + "2-" + Color.BLUE + "Contacts" + Color.RESET);
        System.out.println(Color.WHITE + "3-" + Color.BLUE + "Transfer money" + Color.RESET);
        System.out.println(Color.WHITE + "4-" + Color.BLUE + "Support" + Color.RESET);
        System.out.println(Color.WHITE + "5-" + Color.BLUE + "Settings" + Color.RESET);
        System.out.println(Color.WHITE + "6-" + Color.BLUE + "Log out" + Color.RESET);
        System.out.println(Color.WHITE + "7-" + Color.BLUE + "Quit" + Color.RESET);
        System.out.println(Color.YELLOW + "<>".repeat(20) + Color.RESET);
        InputManager.handleUserMainMenuInput();
    }

    public static void printSupportMenu() {
        System.out.println(Color.YELLOW + "<>".repeat(20) + Color.RESET);
        System.out.println(Color.WHITE + "Please select the option you want" + Color.RESET);
        System.out.println(Color.WHITE + "1-" + Color.BLUE + "new Ticket" + Color.RESET);
        System.out.println(Color.WHITE + "2-" + Color.BLUE + "Tickets" + Color.RESET);
        System.out.println(Color.WHITE + "3-" + Color.BLUE + "Return" + Color.RESET);
        System.out.println(Color.YELLOW + "<>".repeat(20) + Color.RESET);
        InputManager.handleSupportInput();
    }

    public static void printSettingsMenu() {
        System.out.println(Color.YELLOW + "<>".repeat(20) + Color.RESET);
        System.out.println(Color.WHITE + "Please select the option you want" + Color.RESET);
        System.out.println(Color.WHITE + "1-" + Color.BLUE + "Change password" + Color.RESET);
        System.out.println(Color.WHITE + "2-" + Color.BLUE + "Change credit card password" + Color.RESET);
        System.out.println(Color.WHITE + "3-" + Color.BLUE + "Activate/Deactivate contacts option" + Color.RESET);
        System.out.println(Color.WHITE + "4-" + Color.BLUE + "Return" + Color.RESET);
        System.out.println(Color.YELLOW + "<>".repeat(20) + Color.RESET);
        InputManager.handleSettingsInput();
    }

    public static void printTransferMenu() {
        System.out.println(Color.YELLOW + "<>".repeat(20) + Color.RESET);
        System.out.println(Color.WHITE + "Please select the method you want to use for transfer");
        System.out.println(Color.WHITE + "1-" + Color.BLUE + "Account ID" + Color.RESET);
        System.out.println(Color.WHITE + "2-" + Color.BLUE + "Contacts" + Color.RESET);
        System.out.println(Color.WHITE + "3-" + Color.BLUE + "Recent Users" + Color.RESET);
        System.out.println(Color.WHITE + "4-" + Color.BLUE + "Return" + Color.RESET);
        System.out.println(Color.YELLOW + "<>".repeat(20) + Color.RESET);
        InputManager.handleTransferMethod();
    }

    public static void printContactsMenu() {
        if (!Main.getUsers().getCurrentUser().isContactsActivated()) {
            System.out.println(Color.RED + "You have deactivated contacts option! change it from settings and try again" + Color.RESET);
            return;
        }
        System.out.println(Color.YELLOW + "<>".repeat(20) + Color.RESET);
        System.out.println(Color.WHITE + "Please select the option you want" + Color.RESET);
        System.out.println(Color.WHITE + "1-" + Color.BLUE + "Add contact" + Color.RESET);
        System.out.println(Color.WHITE + "2-" + Color.BLUE + "View contacts" + Color.RESET);
        System.out.println(Color.WHITE + "3-" + Color.BLUE + "Return" + Color.RESET);
        System.out.println(Color.YELLOW + "<>".repeat(20) + Color.RESET);
        InputManager.handleContactsInput();
    }

    public static void printManagementMenu() {
        System.out.println(Color.YELLOW + "<>".repeat(20) + Color.RESET);
        System.out.println(Color.WHITE + "Please select the option you want" + Color.RESET);
        System.out.println(Color.WHITE + "1-" + Color.BLUE + "Charge" + Color.RESET);
        System.out.println(Color.WHITE + "2-" + Color.BLUE + "View balance" + Color.RESET);
        System.out.println(Color.WHITE + "3-" + Color.BLUE + "View receipts" + Color.RESET);
        System.out.println(Color.WHITE + "4-" + Color.BLUE + "Return" + Color.RESET);
        System.out.println(Color.YELLOW + "<>".repeat(20) + Color.RESET);
        InputManager.handleManagementInput();
    }

    public static void printShowReceipts() {
        System.out.println(Color.YELLOW + "<>".repeat(20) + Color.RESET);
        System.out.println(Color.WHITE + "Please select an option" + Color.RESET);
        System.out.println(Color.WHITE + "1-" + Color.BLUE + "View all receipts" + Color.RESET);
        System.out.println(Color.WHITE + "2-" + Color.BLUE + "Filter by time span" + Color.RESET);
        System.out.println(Color.WHITE + "3-" + Color.BLUE + "Return" + Color.RESET);
        System.out.println(Color.YELLOW + "<>".repeat(20) + Color.RESET);
        InputManager.handleShowReceipt();
    }

    public static void endProgram() {
        System.out.println(Color.PURPLE + "Thanks for choosing our bank!" + Color.RESET);
        System.exit(0);
    }

    public static void userLogout() {
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
