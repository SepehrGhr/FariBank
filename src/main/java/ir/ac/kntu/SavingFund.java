package ir.ac.kntu;

import org.jfree.chart.entity.PlotEntity;

public class SavingFund extends Fund {

    public SavingFund(User owner) {
        super(owner);
    }

    public void deposit(long amount) {
        if (getOwner().getAccount().getBalance() >= amount) {
            setBalance(getBalance() + amount);
            getOwner().getAccount().setBalance(getOwner().getAccount().getBalance() - amount);
            System.out.println(Color.GREEN + "Selected amount was successfully deposited to your saving fund" + Color.RESET);
        } else {
            System.out.println(Color.RED + "Your account's balance is not enough" + Color.RESET);
        }
    }

    public void withdraw(long amount) {
        if (getBalance() >= amount) {
            setBalance(getBalance() - amount);
            getOwner().getAccount().setBalance(getOwner().getAccount().getBalance() + amount);
            System.out.println(Color.GREEN + "Selected amount was successfully withdrew from your saving fund" + Color.RESET);
        } else {
            System.out.println(Color.RED + "Selected Fund's balance is not enough" + Color.RESET);
        }
    }

    public String getType(){
        return Color.PURPLE + "Saving Fund" + Color.RESET;
    }

    public void openManagement(){
        Menu.printMenu(OptionEnums.SelectedFundMenu.values(), this::handleManagementInput);
    }

    private void handleManagementInput() {
        String selection = InputManager.getSelection(4);
        switch (selection){
            case "1" -> {
                String amount = getAmount();
                this.deposit(Long.parseLong(amount));
            }
            case "2" -> {
                String amount = getAmount();
                this.withdraw(Long.parseLong(amount));
            }
            case "3" -> {
                this.showBalance();
            }
            default -> {}
        }
    }

    private String getAmount() {
        System.out.println(Color.WHITE + "Please enter the amount you want" + Color.RESET);
        String amount = InputManager.getInput();
        while (!InputManager.chargeAmountValidity(amount)) {
            System.out.println(Color.RED + "Please enter a valid number (Maximum 12 digits , Minimum 1)" + Color.RESET);
            amount = InputManager.getInput();
        }
        return amount;
    }

    private void showBalance() {
        System.out.println(Color.WHITE + "Selected saving fund's current balance : " + Color.GREEN + getBalance() + Color.RESET);
    }

    @Override
    public String toString() {
        return Color.CYAN + "*".repeat(35) + '\n' + Color.BLUE + "Saving Fund:" + '\n' + Color.WHITE + "Balance: "
                + Color.BLUE + getBalance() + '\n' + Color.CYAN + "*".repeat(35) + Color.RESET;
    }
}
