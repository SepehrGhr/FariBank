package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;

public class ManagerData {
    private List<Manager> managers;

    private List<InterestFund> interestFunds;
    private Manager currentManager;
    private boolean processStarted;

    public ManagerData() {
        this.managers = new ArrayList<>();
        this.interestFunds = new ArrayList<>();
        processStarted = false;
    }

    public Manager getCurrentManager() {
        return currentManager;
    }

    public void addNewInterestFund(InterestFund newFund){
        interestFunds.add(newFund);
        if(interestFunds.size() == 1 && !processStarted){
            startInterestProcess();
            processStarted = true;
        }
    }

    public void removeInterestFund(InterestFund fund) {
        interestFunds.remove(fund);
    }

    private void startInterestProcess() {
        InterestDepositRunnable interestRunnable = new InterestDepositRunnable();
        Thread interestDepositThread = new Thread(interestRunnable);
        interestDepositThread.start();
    }

    public void setCurrentManager(Manager currentManager) {
        this.currentManager = currentManager;
    }

    public Manager findManagerByUsername(String input) {
        for (Manager manager : managers) {
            if (manager.getName().equals(input)) {
                return manager;
            }
        }
        return null;
    }

    public void managerSetup() {
        managers.add(new Manager("Sepehr", "12345"));
    }

    public void depositMonthlyInterest() {
        for (InterestFund fund : interestFunds) {
            if (fund.getReceivedCount() < fund.getMustReceiveCount()) {
                fund.depositInterest();
            }
        }
    }
}
