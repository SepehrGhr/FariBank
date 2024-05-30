package ir.ac.kntu;

public class Setup {
    public static void userSetup() {
        User test = new User("Sepehr", "Ghardashi", "09111262338", "5820175281", "Sepehr1384@");
        User test2 = new User("Sina", "Najafi", "09121103946", "0250388109", "IloveMoney$420");
        User test3 = new User("Hedie", "Tahmouresi", "09109056296", "0124523423", "H@Tah1384");
        User test4 = new User("Sajad", "Eslami", "09300408447", "5820158237", "Saj@1234");
        test.setAuthenticated(true);
        test2.setAuthenticated(true);
        test3.setAuthenticated(true);
        test4.setAuthenticated(true);
        test.setAccount();
        test2.setAccount();
        test3.setAccount();
        test4.setAccount();
        test.addNewContact(new Contact(test3, "Hedieeee", "Tah", "09109056296"));
        test.addNewContact(new Contact(test2, "Sina", " ", "09121103946"));
        test3.addNewContact(new Contact(test, "Sep", "khals", "09111262338"));
        Main.getUsers().addUser(test);
        Main.getUsers().addUser(test2);
        Main.getUsers().addUser(test3);
        Main.getUsers().addUser(test4);
    }
}
