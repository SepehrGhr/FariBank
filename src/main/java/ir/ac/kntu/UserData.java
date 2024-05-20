package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;

public class UserData {
    private List<User> allUsers;
    private User currentUser;

    public UserData() {
        allUsers = new ArrayList<>();
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void addUser(User newUser) {
        allUsers.add(newUser);
    }

    public User findUserByPhoneNumber(String phoneNumber) {
        for (User user : allUsers) {
            if (user.getPhoneNumber().equals(phoneNumber)) {
                return user;
            }
        }
        return null;
    }

    public boolean accountIdAlreadyExists(String id) {
        for (User user : allUsers) {
            if (user.getAccount().getAccountID().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
