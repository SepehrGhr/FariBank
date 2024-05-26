package ir.ac.kntu;

public class Main {
    private static UserData users = new UserData();
    private static AdminData adminData = new AdminData();

    public static void main(String[] args) {
        User test = new User("Sepehr", "Ghardashi", "09111262338", "5820175281", "Sepehr1384@");
        User test2 = new User("Sina", "Najafi", "09121103946", "0250388109", "IloveMoney$420");
        User test3 = new User("Hedie", "Tahmouresi", "09109056296", "0124523423", "H@Tah1384");
        test.setAuthenticated(true);
        test2.setAuthenticated(true);
        test3.setAuthenticated(true);
        test.setAccount();
        test2.setAccount();
        test3.setAccount();
        System.out.println(test.getAccount().getAccountID());
        System.out.println(test2.getAccount().getAccountID());
        System.out.println(test3.getAccount().getAccountID());
        test.addNewContact(new Contact(test3, "Hedieeee", "Tah", "09109056296"));
        test.addNewContact(new Contact(test2, "Sina", " ", "09121103946"));
        test3.addNewContact(new Contact(test, "Sep", "khals", "09111262338"));
        adminData.addAdmin(new Admin("sepi", "Sepehr", "12345"));
        adminData.addAdmin(new Admin("Ali", "Ali12", "Ali123"));
        users.addUser(test);
        users.addUser(test2);
        users.addUser(test3);
        Menu.printSelectRuleMenu();
    }

    public static UserData getUsers() {
        return users;
    }

    public static AdminData getAdminData() {
        return adminData;
    }
}
