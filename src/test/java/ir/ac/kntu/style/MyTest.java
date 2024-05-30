package ir.ac.kntu.style;

import ir.ac.kntu.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void testPassword() {
        assertTrue(Menu.checkPasswordValidity("Ilove@pplePie123"));
        assertFalse(Menu.checkPasswordValidity("Reza12345"));
        assertFalse(Menu.checkPasswordValidity("Af@3"));
    }

    @Test
    public void testPhoneNumber() {
        assertFalse(Menu.checkPhoneNumberValidity("091112939"));
        assertTrue(Menu.checkPhoneNumberValidity("09391293971"));
        assertFalse(Menu.checkPhoneNumberValidity("00111238746"));
    }

    @Test
    public void testCreditCard() {
        User user2 = new User("Milad", "Alavi", "09111234587", "5820192839", "Milad1@@");
        user2.setAuthenticated(true);
        user2.setAccount();
        assertEquals(4, user2.getAccount().getCreditCard().getPassword().length());
        assertEquals(16, user2.getAccount().getCreditCard().getCardNumber().length());
        assertEquals('0', user2.getAccount().getAccountID().charAt(0));
    }

    @Test
    public void testAdminSetup() {
        AdminData admins = new AdminData();
        admins.adminSetup();
        Admin newAdmin = new Admin("Amir", "Amir1376", "A123$321a");
        admins.addAdmin(newAdmin);
        assertEquals(newAdmin, admins.findAdminByUsername("Amir1376"));
    }

    @Test void testAuthenticationRequest(){
        AdminData admins = new AdminData();
        admins.adminSetup();
        User user2 = new User("Milad", "Alavi", "09111234587", "5820192839", "Milad1@@");
        AuthenticationRequest request = new AuthenticationRequest(user2);
        assertFalse(request.isApproved());
        assertFalse(request.isChecked());
        AuthenticationRequest.setupAccepted(request);
        assertTrue(request.isChecked());
        assertTrue(request.isApproved());
        assertTrue(request.getUser().isAuthenticated());
    }
}
