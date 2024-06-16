package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Manager {
    private String name;
    private String password;
    private List<Manager> subordinates;

    public Manager(String name, String password) {
        this.name = name;
        this.password = password;
        this.subordinates = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void changeFeeMenu() {
        Menu.printMenu(OptionEnums.ManagerChangeFee.values(), this::selectFeeToChange);
    }

    private void selectFeeToChange() {
        String selection = InputManager.getSelection(6);
        switch (selection) {
            case "1" -> this.changePayaFee();
            case "2" -> this.changePolFee();
            case "3" -> this.changeFariFee();
            case "4" -> this.changeCardFee();
            case "5" -> this.changeSimCardFee();
            default -> {
            }
        }
    }

    private void changePolFee() {
        System.out.println(Color.WHITE + "Please enter your new fee percentage (Maximum: 100)" + Color.RESET);
        System.out.println(Color.WHITE + "Current Pol fee percentage: " + Color.GREEN + Main.getManagerData().getFeeRate().getPolFee() * 100
                + Color.RESET);
        String input = InputManager.getInput();
        while (!feeAmountValidity(input) || Long.parseLong(input) > 100) {
            System.out.println(Color.RED + "Please enter a valid percentage. Maximum: 100");
            input = InputManager.getInput();
        }
        Main.getManagerData().getFeeRate().setPolFee(Double.parseDouble(input)/100);
        System.out.println(Color.GREEN + "Pol fee percentage has been successfully changed" + Color.RESET);
    }

    private void changeSimCardFee() {
        System.out.println(Color.WHITE + "Please enter your new fee (Maximum: 10000)" + Color.RESET);
        System.out.println(Color.WHITE + "Current SimCard fee: " + Color.GREEN + Main.getManagerData().getFeeRate().getSimCardFee()
                + Color.RESET);
        String input = InputManager.getInput();
        while (!feeAmountValidity(input) || Long.parseLong(input) > 10000) {
            System.out.println(Color.RED + "Please enter a valid amount. Maximum: 10000");
            input = InputManager.getInput();
        }
        Main.getManagerData().getFeeRate().setSimCardFee(Long.parseLong(input));
        System.out.println(Color.GREEN + "SimCard fee has been successfully changed" + Color.RESET);
    }

    private void changeCardFee() {
        System.out.println(Color.WHITE + "Please enter your new fee (Maximum: 10000)" + Color.RESET);
        System.out.println(Color.WHITE + "Current Card fee: " + Color.GREEN + Main.getManagerData().getFeeRate().getCardFee()
                + Color.RESET);
        String input = InputManager.getInput();
        while (!feeAmountValidity(input) || Long.parseLong(input) > 10000) {
            System.out.println(Color.RED + "Please enter a valid amount. Maximum: 10000");
            input = InputManager.getInput();
        }
        Main.getManagerData().getFeeRate().setCardFee(Long.parseLong(input));
        System.out.println(Color.GREEN + "Card fee has been successfully changed" + Color.RESET);
    }

    private void changeFariFee() {
        System.out.println(Color.WHITE + "Please enter your new fee (Maximum: 10000)" + Color.RESET);
        System.out.println(Color.WHITE + "Current Fari fee: " + Color.GREEN + Main.getManagerData().getFeeRate().getFariFee()
                + Color.RESET);
        String input = InputManager.getInput();
        while (!feeAmountValidity(input) || Long.parseLong(input) > 10000) {
            System.out.println(Color.RED + "Please enter a valid amount. Maximum: 10000");
            input = InputManager.getInput();
        }
        Main.getManagerData().getFeeRate().setFariFee(Long.parseLong(input));
        System.out.println(Color.GREEN + "Fari fee has been successfully changed" + Color.RESET);
    }

    private void changePayaFee() {
        System.out.println(Color.WHITE + "Please enter your new fee (Maximum: 10000)" + Color.RESET);
        System.out.println(Color.WHITE + "Current Paya fee: " + Color.GREEN + Main.getManagerData().getFeeRate().getPayaFee()
                + Color.RESET);
        String input = InputManager.getInput();
        while (!feeAmountValidity(input) || Long.parseLong(input) > 10000) {
            System.out.println(Color.RED + "Please enter a valid amount. Maximum: 10000");
            input = InputManager.getInput();
        }
        Main.getManagerData().getFeeRate().setPayaFee(Long.parseLong(input));
        System.out.println(Color.GREEN + "Paya fee has been successfully changed" + Color.RESET);
    }

    public static boolean feeAmountValidity(String input) {
        String numberRegex = "\\d+";
        Pattern numPattern = Pattern.compile(numberRegex);
        Matcher numMatcher = numPattern.matcher(input);
        return numMatcher.matches() && input.length() < 13;
    }

    public void changeInterestRate() {
        System.out.println(Color.WHITE + "Please enter your new Interest percentage (Maximum: 100)" + Color.RESET);
        System.out.println(Color.WHITE + "Current Interest percentage: " + Color.GREEN + Main.getManagerData().getInterestRate() * 100
                + Color.RESET);
        String input = InputManager.getInput();
        while (!feeAmountValidity(input) || Long.parseLong(input) > 100) {
            System.out.println(Color.RED + "Please enter a valid percentage. Maximum: 100");
            input = InputManager.getInput();
        }
        Main.getManagerData().setInterestRate(Double.parseDouble(input)/100);
        System.out.println(Color.GREEN + "Interest percentage has been successfully changed" + Color.RESET);
    }
}
