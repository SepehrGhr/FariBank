package ir.ac.kntu;

public class Main {
    private static UserData users = new UserData();
    private static AdminData adminData = new AdminData();

    public static void main(String[] args) {
        User test = new User("Sepehr", "Ghardashi", "09111262338", "5820175281", "Sepehr1384@");
        //User test2 = new User("Sina", "Najafi", "09121103946", "0250388109", "IloveMoney$420");
        //AuthenticationRequest req = new AuthenticationRequest(test2);
        //adminData.addAuthenticationRequest(req);
        //User test3 = new User("Hedie", "Tahmouresi", "09109056296", "0124523423", "H@Tah1384");
        test.setAuthenticated(true);
        test.setAccount();
        test.addNewContact(new Contact(null , "Mahdi" , "Salman" , "09938634069" ));
        test.addNewContact(new Contact(null , "Hedie" , "Tahmouresi" , "09109056296" ));
        test.addNewContact(new Contact(null , "Mobin" , "Fallahi EshratAbadi" , "09999985628" ));
        users.addUser(test);
        //users.addUser(test2);
        //users.addUser(test3);

        Menu.printSelectRuleMenu();
    }

    public static UserData getUsers() {
        return users;
    }

    public static AdminData getAdminData() {
        return adminData;
    }
}
