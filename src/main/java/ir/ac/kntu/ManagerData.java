package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ManagerData {
    private List<Manager> managers;
    private List<InterestFund> interestFunds;
    private List<Paya> pendingPaya;
    private Manager currentManager;
    private FeeRate feeRate;
    private double interestRate;
    private boolean processStarted;

    public ManagerData() {
        this.feeRate = new FeeRate();
        this.interestRate = 0.27;
        this.managers = new ArrayList<>();
        this.interestFunds = new ArrayList<>();
        this.pendingPaya = new ArrayList<>();
        this.processStarted = false;
    }

    public Manager getCurrentManager() {
        return currentManager;
    }

    public FeeRate getFeeRate() {
        return feeRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public void addNewInterestFund(InterestFund newFund) {
        interestFunds.add(newFund);
        if (interestFunds.size() == 1 && !processStarted) {
            startInterestProcess();
            processStarted = true;
        }
    }

    public void addNewPaya(Paya newPaya) {
        pendingPaya.add(newPaya);
        startScheduler(newPaya);
    }

    public void removePaya(Paya newPaya) {
        pendingPaya.remove(newPaya);
    }

    public void startScheduler(Paya newPaya) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable task = newPaya::doTransfer;
        scheduler.schedule(task, 30, TimeUnit.SECONDS);
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
        managers.add(new Manager("Sepehr", "12345", 1));
    }

    public void depositMonthlyInterest() {
        for (InterestFund fund : interestFunds) {
            if (fund.getReceivedCount() < fund.getMustReceiveCount()) {
                fund.depositInterest();
            }
        }
    }

    public void doAllPayas() {
        if (pendingPaya.size() == 0) {
            System.out.println(Color.RED + "There is no pending Paya transfer" + Color.RESET);
            return;
        }
        for (Paya paya : pendingPaya) {
            paya.doTransfer();
        }
        System.out.println(Color.GREEN + "All pending Paya transfers are done" + Color.RESET);
    }

    public void doAllInterests() {
        System.out.println(Color.RED + "There is no delayed interest deposit" + Color.RESET);
    }

    public void showListOfEveryone() {
        List<Object> everyone = new ArrayList<>(managers);
        Main.getAdminData().addAllAdmins(everyone);
        Main.getUsers().addAllUsers(everyone);
        Object selected = Display.pageShow(everyone,
                theOne -> {
                    if (theOne instanceof User) {
                        System.out.println(Color.GREEN + ((User) theOne).getName() + " " + ((User) theOne).getLastName() + Color.RESET);
                    } else if (theOne instanceof Admin) {
                        System.out.println(Color.YELLOW + ((Admin) theOne).getName() + Color.RESET);
                    } else if (theOne instanceof Manager) {
                        System.out.println(Color.RED + ((Manager) theOne).getName() + Color.RESET);
                    }
                });
        if(selected instanceof User){
            System.out.println(((User) selected).fullInfo());
        } else if(selected instanceof Admin){
            System.out.println(selected);
        } else if(selected instanceof Manager){
            System.out.println(selected);
        }
        chooseEditOrNot(selected);
    }

    private void chooseEditOrNot(Object selected) {
        System.out.println(Color.WHITE + "Please enter" + Color.BLUE + " 1 " + Color.WHITE + "to edit the selected person or"
                + Color.BLUE + " 2 " + Color.WHITE + "to return" + Color.RESET);
        String selection = InputManager.getSelection(2);
        if("1".equals(selection)){
            if(selected instanceof User){
                currentManager.editUserMenu((User)selected);
            } else if(selected instanceof Admin){
                currentManager.editAdminMenu((Admin) selected);
            } else if(selected instanceof Manager){
                currentManager.editManagerMenu((Manager) selected);
            }
        }
    }

}
