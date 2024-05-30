package ir.ac.kntu;

public class Main {
    private static UserData users = new UserData();
    private static AdminData adminData = new AdminData();

    public static void main(String[] args) {
        Setup.userSetup();
        adminData.adminSetup();
//      System.out.println(test.getAccount().getAccountID());
//      System.out.println(test2.getAccount().getAccountID());
//      System.out.println(test3.getAccount().getAccountID());
        Menu.printSelectRuleMenu();
    }

    public static UserData getUsers() {
        return users;
    }

    public static AdminData getAdminData() {
        return adminData;
    }
}
