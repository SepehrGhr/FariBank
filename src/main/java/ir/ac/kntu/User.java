package ir.ac.kntu;

import java.util.List;

public class User {
    private String name;
    private String lastName;
    private String phoneNumber;
    private String securityNumber;
    private String password;
    private List<Contact> contacts;
    private List<User> recentUsers;
    private List<Ticket> tickets;
    private Account account;
    private boolean contactsActivated;

    public User(String name , String lastName , String phoneNumber , String securityNumber , String password){
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.securityNumber = securityNumber;
        this.password = password;
    }
}
