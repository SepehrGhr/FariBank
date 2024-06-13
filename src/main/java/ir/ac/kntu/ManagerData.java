package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;

public class ManagerData {
    private List<Manager> managers;
    private Manager currentManager;

    public ManagerData() {
        this.managers = new ArrayList<>();
    }

    public Manager getCurrentManager() {
        return currentManager;
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
}
