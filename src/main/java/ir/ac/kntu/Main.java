package ir.ac.kntu;

public class Main {
    private static UserData users = new UserData();
    private static AdminData adminData = new AdminData();

    public static void main(String[] args) {
        Setup.userSetup();
        adminData.adminSetup();
        Menu.printMenu(OptionEnums.SelectRuleOption.values(), InputManager::handleSelectRuleInput);
    }

    public static UserData getUsers() {
        return users;
    }

    public static AdminData getAdminData() {
        return adminData;
    }
}
