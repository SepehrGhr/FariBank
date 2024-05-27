package ir.ac.kntu.style;

import ir.ac.kntu.Main;
import ir.ac.kntu.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyTest {
    private User user1;

    @BeforeEach
    public void setUp() {
        user1 = new User("Sepehr", "Ghardashi", "09111262338", "5820175281", "Sepehr1384@");
        user1.setAuthenticated(true);
        user1.setAccount();
        Main.getUsers().setCurrentUser(user1);
        Main.getUsers().addUser(user1);
    }

    @Test
    public void testCharge() {
        user1.chargeAccount(5000);
        assertEquals(5000, user1.getAccount().getBalance());
    }

    @Test
    public void testAccID() {
        assertEquals(13, user1.getAccount().getAccountID().length());
    }

    @Test
    public void testDistance() {
        assertEquals(2, Main.getUsers().distance("Sepehr", "sephr"));
        assertEquals(3, Main.getUsers().distance("Careless", "Hopeless"));
    }
}
