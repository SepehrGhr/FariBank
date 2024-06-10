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
            Menu.printMenu(OptionEnums.SignOrLogin.values(), InputManager::handleSignOrLogin);
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
                Menu.printMenu(OptionEnums.AdminMenu.values(), InputManager::handleAdminInput);
            }
            case "3" -> Menu.printMenu(OptionEnums.AdminUserMenu.values(), Main.getUsers()::handleAdminUserInput);
            default -> Menu.printMenu(OptionEnums.SelectRuleOption.values(), InputManager::handleSelectRuleInput);
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
        Menu.printMenu(OptionEnums.SelectRuleOption.values(), InputManager::handleSelectRuleInput);
    }

    public static void handleUserMainMenuInput() {
        String selection = getSelection(8);
        switch (selection) {
            case "1" -> {
                Menu.printMenu(OptionEnums.ManagementMenuOption.values(), InputManager::handleManagementInput);
                Menu.printMenu(OptionEnums.UserMainMenuOption.values(), InputManager::handleUserMainMenuInput);
            }
            case "2" -> {
                Menu.printContactsMenu();
                Menu.printMenu(OptionEnums.UserMainMenuOption.values(), InputManager::handleUserMainMenuInput);
            }
            case "3" -> Menu.printMenu(OptionEnums.TransferMenuOption.values(), InputManager::handleTransferMethod);
            case "4" -> Menu.printMenu(OptionEnums.SupportMenuOption.values(), InputManager::handleSupportInput);
            case "5" -> Menu.printMenu(OptionEnums.SettingsMenuOption.values(), InputManager::handleSettingsInput);
            case "6" -> {
                Menu.printAccountDetails();
                generateReports();
                Menu.printMenu(OptionEnums.UserMainMenuOption.values(), InputManager::handleUserMainMenuInput);
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
                Menu.printMenu(OptionEnums.SupportMenuOption.values(), InputManager::handleSupportInput);
            }
            case "2" -> {
                Main.getUsers().getCurrentUser().displayTickets();
                Menu.printMenu(OptionEnums.SupportMenuOption.values(), InputManager::handleSupportInput);
            }
            default -> Menu.printMenu(OptionEnums.UserMainMenuOption.values(), InputManager::handleUserMainMenuInput);
        }
    }

    public static void handleSettingsInput() {
        String selection = getSelection(4);
        switch (selection) {
            case "1" -> {
                Main.getUsers().getCurrentUser().changePassword();
                Menu.printMenu(OptionEnums.SettingsMenuOption.values(), InputManager::handleSettingsInput);
            }
            case "2" -> {
                Main.getUsers().getCurrentUser().changeCreditPassword();
                Menu.printMenu(OptionEnums.SettingsMenuOption.values(), InputManager::handleSettingsInput);
            }
            case "3" -> {
                Main.getUsers().getCurrentUser().changeContactStatus();
                Menu.printMenu(OptionEnums.SettingsMenuOption.values(), InputManager::handleSettingsInput);
            }
            default -> Menu.printMenu(OptionEnums.UserMainMenuOption.values(), InputManager::handleUserMainMenuInput);
        }
    }

    public static void handleTransferMethod() {
        String selection = getSelection(4);
        switch (selection) {
            case "1" -> {
                Main.getUsers().transferByAccountID();
                Menu.printMenu(OptionEnums.TransferMenuOption.values(), InputManager::handleTransferMethod);
            }
            case "2" -> {
                Main.getUsers().transferByContact();
                Menu.printMenu(OptionEnums.TransferMenuOption.values(), InputManager::handleTransferMethod);
            }
            case "3" -> {
                Main.getUsers().transferByRecentUser();
                Menu.printMenu(OptionEnums.TransferMenuOption.values(), InputManager::handleTransferMethod);
            }
            default -> Menu.printMenu(OptionEnums.UserMainMenuOption.values(), InputManager::handleUserMainMenuInput);
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
            default -> Menu.printMenu(OptionEnums.UserMainMenuOption.values(), InputManager::handleUserMainMenuInput);
        }
    }

    public static void handleManagementInput() {
        String selection = getSelection(4);
        switch (selection) {
            case "1" -> {
                Main.getUsers().getCurrentUser().printChargeAccount();
                Menu.printMenu(OptionEnums.ManagementMenuOption.values(), InputManager::handleManagementInput);
            }
            case "2" -> {
                Main.getUsers().getCurrentUser().displayBalance();
                Menu.printMenu(OptionEnums.ManagementMenuOption.values(), InputManager::handleManagementInput);
            }
            case "3" -> {
                Menu.printMenu(OptionEnums.ShowReceiptsOption.values(), InputManager::handleShowReceipt);
            }
            default -> Menu.printMenu(OptionEnums.UserMainMenuOption.values(), InputManager::handleUserMainMenuInput);
        }
    }

    public static void handleShowReceipt() {
        String selection = getSelection(3);
        switch (selection) {
            case "1" -> {
                Main.getUsers().getCurrentUser().displayReceipts();
                Menu.printMenu(OptionEnums.ManagementMenuOption.values(), InputManager::handleManagementInput);
            }
            case "2" -> {
                Main.getUsers().getCurrentUser().filterReceipt();
                Menu.printMenu(OptionEnums.ManagementMenuOption.values(), InputManager::handleManagementInput);
            }
            default -> Menu.printMenu(OptionEnums.ManagementMenuOption.values(), InputManager::handleManagementInput);
        }
    }

    public static boolean chargeAmountValidity(String input) {
        String numberRegex = "\\d+";
        Pattern numPattern = Pattern.compile(numberRegex);
        Matcher numMatcher = numPattern.matcher(input);
        return numMatcher.matches() && input.length() < 13 && Long.parseLong(input) != 0;
    }
}
