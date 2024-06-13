package ir.ac.kntu;

public class Main {
    private static UserData users = new UserData();
    private static AdminData adminData = new AdminData();
    private static ManagerData managerData = new ManagerData();

    public static void main(String[] args) {
        Setup.userSetup();
        adminData.adminSetup();
        managerData.managerSetup();
        Menu.printMenu(OptionEnums.SelectRuleOption.values(), InputManager::handleSelectRuleInput);
    }

    public static UserData getUsers() {
        return users;
    }

    public static AdminData getAdminData() {
        return adminData;
    }

    public static ManagerData getManagerData() {
        return managerData;
    }
}
