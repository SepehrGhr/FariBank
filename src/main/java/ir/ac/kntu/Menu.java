package ir.ac.kntu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menu {
    public static void printSelectRuleMenu() {
        System.out.println(Color.YELLOW + "please enter what you want to login as" + Color.RESET);
        System.out.println(Color.WHITE + "1-" + Color.BLUE + "User");
        System.out.println(Color.WHITE + "2-" + Color.BLUE + "Admin" + Color.RESET);
        handleSelectRuleInput();

    }

    private static void handleSelectRuleInput() {
        String selection = InputManager.getInput().next();
        while (!isInputValid(selection, 2)) {
            System.out.println(Color.RED + "Please enter a number between 1 and 2" + Color.RESET);
            selection = InputManager.getInput().next();
        }
        if (selection.equals("1")) {
            printSignOrLoginMenu();
            return;
        }
        printAdminLoginMenu();
    }

    private static void printAdminLoginMenu() {
    }

    private static boolean isInputValid(String input, int max) {
        String checkNumeric = "\\d+";
        Pattern checkNumericPattern = Pattern.compile(checkNumeric);
        Matcher checkNumericMatcher = checkNumericPattern.matcher(input);
        return checkNumericMatcher.matches() && Integer.parseInt(input) <= max && Integer.parseInt(input) > 0;

    }

    private static void printSignOrLoginMenu() {
        System.out.println(Color.YELLOW + "choose an option");
        System.out.println(Color.WHITE + "1-" + Color.BLUE + "Login");
        System.out.println(Color.WHITE + "2-" + Color.BLUE + "Sign up" + Color.RESET);
        System.out.println(Color.WHITE + "3-" + Color.BLUE + "Return to previous menu" + Color.RESET);
        handleSignOrLogin();
    }

    private static void handleSignOrLogin() {
        String selection = InputManager.getInput().next();
        while (!isInputValid(selection, 3)) {
            System.out.println(Color.RED + "Please enter a number between 1 and 3" + Color.RESET);
            selection = InputManager.getInput().next();
        }
        if (selection.equals("1")) {
            printLoginMenu();
            return;
        }
        if (selection.equals("2")) {
            printSignUpMenu();
        }
        printSelectRuleMenu();
    }

    private static void printSignUpMenu() {
        System.out.println(Color.YELLOW + "Please enter your name" + Color.RESET);
        String name = setUserName();
        System.out.println(Color.YELLOW + "Please enter your last name" + Color.RESET);
        String lastName = setUserName();
        System.out.println( Color.YELLOW + "Please enter your phone number" + Color.RESET);
        String phoneNumber = setPhoneNumber();
        System.out.println(Color.YELLOW + "Please enter your security number" + Color.RESET);
        String securityNumber = setSecurityNumber();
        System.out.println(Color.YELLOW + "Please enter your password" + Color.WHITE + "(it must contain at least " +
                                            "one lowercase,uppercase,number and character)" + Color.RESET);
        String password = setPassword();
        System.out.println(Color.GREEN + "Your information has been successfully registered and will be checked soon" + Color.RESET );
    }

    private static String setPassword() {
        String password = InputManager.getInput().next();
        while(!checkPasswordValidity(password)){
            System.out.println(Color.RED + "Your password must contain at least one lowercase,uppercase,number and" +
                                            " character, please try again" + Color.RESET);
            password = InputManager.getInput().next();
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
        String securityNumber = InputManager.getInput().next();
        while(!checkSecurityNumberValidity(securityNumber)){
            System.out.println(Color.RED + "Please enter your security number correctly (10 digits)" + Color.RESET);
            securityNumber = InputManager.getInput().next();
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
        String phoneNumber = InputManager.getInput().next();
        while(!checkPhoneNumberValidity(phoneNumber)){
            System.out.println(Color.RED + "Please enter your phone number correctly" + Color.RESET);
            phoneNumber = InputManager.getInput().next();
        }
        return phoneNumber;
    }

    private static boolean checkPhoneNumberValidity(String phoneNumber) {
        String numberRegex = "\\d{11}";
        Pattern numberPattern = Pattern.compile(numberRegex);
        Matcher numberMatcher = numberPattern.matcher(phoneNumber);
        return (numberMatcher.matches() && phoneNumber.substring(0,2).equals("09"));
    }

    private static String setUserName() {
        String name = InputManager.getInput().next();
        while(!checkStringValidity(name)){
            System.out.println(Color.RED + "Please enter your name correctly" + Color.RESET);
            name = InputManager.getInput().next();
        }
        return name;

    }

    public static boolean checkStringValidity(String name) {
        String regex = "[a-zA-z]+";
        Pattern checkStringPattern = Pattern.compile(regex);
        Matcher checkStringMatcher = checkStringPattern.matcher(name);
        return checkStringMatcher.matches();
    }

    private static void printLoginMenu() {
    }
}
