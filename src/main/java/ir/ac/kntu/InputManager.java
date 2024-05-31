package ir.ac.kntu;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputManager {
    private static Scanner input = new Scanner(System.in);

    public static String getInput() {
        return input.nextLine().trim();
    }

    public static boolean isInputValid(String input, int max) {
        String checkNumeric = "\\d+";
        Pattern pattern = Pattern.compile(checkNumeric);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches() && input.length() < 5 && Integer.parseInt(input) <= max && Integer.parseInt(input) > 0;
    }

    public static void handleSelectRuleInput() {
        String selection = getSelection(3);
        if ("1".equals(selection)) {
            Menu.printSignOrLoginMenu();
            return;
        } else if ("2".equals(selection)) {
            Menu.printAdminLoginMenu();
            return;
        }
        Menu.endProgram();
    }

    public static String getSelection(int options) {
        String selection = getInput();
        while (!isInputValid(selection, options)) {
            System.out.println(Color.RED + "Please enter a number between 1 and " + options + Color.RESET);
            selection = getInput();
        }
        return selection;
    }

    public static void handleAdminInput() {
        String selection = getSelection(4);
        switch (selection) {
            case "1" -> Main.getAdminData().showAuthenticationRequests();
            case "2" -> {
                Main.getAdminData().displayTicketsMenu();
                Menu.printAdminMenu();
            }
            case "3" -> Menu.printAdminUserMenu();
            default -> Menu.printSelectRuleMenu();
        }
    }

    public static void handleSignOrLogin() {
        String selection = getSelection(3);
        if ("1".equals(selection)) {
            Menu.printUserLoginMenu();
            return;
        }
        if ("2".equals(selection)) {
            Menu.printSignUpMenu();
        }
        Menu.printSelectRuleMenu();
    }

    public static void handleUserMainMenuInput() {
        String selection = getSelection(8);
        switch (selection) {
            case "1" -> {
                Menu.printManagementMenu();
                Menu.printUserMainMenu();
            }
            case "2" -> {
                Menu.printContactsMenu();
                Menu.printUserMainMenu();
            }
            case "3" -> Menu.printTransferMenu();
            case "4" -> Menu.printSupportMenu();
            case "5" -> Menu.printSettingsMenu();
            case "6" -> {
                Menu.printAccountDetails();
                generateReports();
                Menu.printUserMainMenu();
            }
            case "7" -> Menu.userLogout();
            default -> Menu.endProgram();
        }
    }

    private static void generateReports() {
        String chartFilePath = Main.getUsers().getCurrentUser().getName() + "_" +
                               Main.getUsers().getCurrentUser().getLastName() + "_balance_chart.jpg";
        String htmlFilePath = Main.getUsers().getCurrentUser().getName() + "_" +
                              Main.getUsers().getCurrentUser().getLastName() + "_account_report.html";
        Main.getUsers().getCurrentUser().generateReport(chartFilePath, htmlFilePath);
        System.out.println(Color.GREEN + "Your account reports have been successfully generated" + Color.RESET);
    }

    public static void handleSupportInput() {
        String selection = getSelection(3);
        switch (selection) {
            case "1" -> {
                Ticket.submitNewTicket();
                Menu.printSupportMenu();
            }
            case "2" -> {
                Main.getUsers().getCurrentUser().displayTickets();
                Menu.printSupportMenu();
            }
            default -> Menu.printUserMainMenu();
        }
    }

    public static void handleSettingsInput() {
        String selection = getSelection(4);
        switch (selection) {
            case "1" -> {
                Main.getUsers().getCurrentUser().changePassword();
                Menu.printSettingsMenu();
            }
            case "2" -> {
                Main.getUsers().getCurrentUser().changeCreditPassword();
                Menu.printSettingsMenu();
            }
            case "3" -> {
                Main.getUsers().getCurrentUser().changeContactStatus();
                Menu.printSettingsMenu();
            }
            default -> Menu.printUserMainMenu();
        }
    }

    public static void handleTransferMethod() {
        String selection = getSelection(4);
        switch (selection) {
            case "1" -> {
                Main.getUsers().transferByAccountID();
                Menu.printTransferMenu();
            }
            case "2" -> {
                Main.getUsers().transferByContact();
                Menu.printTransferMenu();
            }
            case "3" -> {
                Main.getUsers().transferByRecentUser();
                Menu.printTransferMenu();
            }
            default -> Menu.printUserMainMenu();
        }
    }

    public static void handleContactsInput() {
        String selection = getSelection(3);
        switch (selection) {
            case "1" -> {
                Contact.getNewContactInfo();
                Menu.printContactsMenu();
            }
            case "2" -> {
                Main.getUsers().getCurrentUser().displayAllContacts();
                Main.getUsers().getCurrentUser().showAndEditContact();
                Menu.printContactsMenu();
            }
            default -> Menu.printUserMainMenu();
        }
    }

    public static void handleManagementInput() {
        String selection = getSelection(4);
        switch (selection) {
            case "1" -> {
                Main.getUsers().getCurrentUser().printChargeAccount();
                Menu.printManagementMenu();
            }
            case "2" -> {
                Main.getUsers().getCurrentUser().displayBalance();
                Menu.printManagementMenu();
            }
            case "3" -> {
                Menu.printShowReceipts();
            }
            default -> Menu.printUserMainMenu();
        }
    }

    public static void handleShowReceipt() {
        String selection = getSelection(3);
        switch (selection) {
            case "1" -> {
                Main.getUsers().getCurrentUser().displayReceipts();
                Menu.printManagementMenu();
            }
            case "2" -> {
                Main.getUsers().getCurrentUser().filterReceipt();
                Menu.printManagementMenu();
            }
            default -> Menu.printManagementMenu();
        }
    }

    public static boolean chargeAmountValidity(String input) {
        String numberRegex = "\\d+";
        Pattern numPattern = Pattern.compile(numberRegex);
        Matcher numMatcher = numPattern.matcher(input);
        return numMatcher.matches() && input.length() < 13 && Long.parseLong(input) != 0;
    }
}
