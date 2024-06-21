package ir.ac.kntu.style;

import ir.ac.kntu.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyTest {
    private User user1;

    @BeforeEach
    public void setUp() {
        user1 = new User("Sepehr", "Ghardashi", new PhoneNumber("09111262238", 0), "5820175281", "Sepehr1384@");
        user1.setAuthenticated(true);
        user1.setAccount();
        Main.getUsers().setCurrentUser(user1);
        Main.getUsers().addUser(user1);
    }

    @Test
    public void testCharge() {
        user1.getAccount().chargeAccount(5000);
        assertEquals(5000, user1.getAccount().getBalance());
    }

    @Test
    public void testAccID() {
        assertEquals(13, user1.getAccount().getAccountID().length());
    }

    @Test
    public void testDistance() {
        assertEquals(2, Display.distance("Sepehr", "sephr"));
        assertEquals(3, Display.distance("Careless", "Hopeless"));
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
        User user2 = new User("Milad", "Alavi", new PhoneNumber("09111234578", 0), "5820192839", "Milad1@@");
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

    @Test
    void testAuthenticationRequest() {
        AdminData admins = new AdminData();
        admins.adminSetup();
        User user2 = new User("Milad", "Alavi", new PhoneNumber("09111234587", 0), "5820192839", "Milad1@@");
        AuthenticationRequest request = new AuthenticationRequest(user2);
        assertFalse(request.isApproved());
        assertFalse(request.isChecked());
        request.setupAccepted();
        assertTrue(request.isChecked());
        assertTrue(request.isApproved());
        assertTrue(request.getUser().isAuthenticated());
    }

    @Test
    void simCardCharge() {
        assertEquals(0, user1.getSimCard().getBalance());
        user1.getAccount().setBalance(100000);
        user1.getSimCard().checkIsBalanceEnough(user1.getSimCard(), "10000");
        assertEquals(89900, user1.getAccount().getBalance());
        assertEquals(10000, user1.getSimCard().getBalance());
        PhoneNumber newNumber = new PhoneNumber("09123451212", 0);
        user1.getSimCard().checkIsBalanceEnough(newNumber, "10000");
        assertEquals(10000, newNumber.getBalance());
        assertEquals(79800, user1.getAccount().getBalance());
    }

    @Test
    void polTransfer() {
        user1.getAccount().setBalance(100000);
        User user2 = new User("Amir", "Akbari", new PhoneNumber("09110408447", 0), "5820148237", "Saj@1234");
        user2.setAuthenticated(true);
        user2.setAccount();
        Main.getUsers().addForeignUser(user2);
        assertEquals(-1 , Main.getUsers().handleMethodLimits(6000000, "Pol", user2));
    }

    @Test
    void payaTransfer() {
        user1.getAccount().setBalance(100000);
        User user2 = new User("Amir", "Akbari", new PhoneNumber("09110408447", 0), "5820148237", "Saj@1234");
        user2.setAuthenticated(true);
        user2.setAccount();
        Main.getUsers().addForeignUser(user2);
        Main.getUsers().handlePayaMethod(10000, user2, true);
        assertEquals(88000, user1.getAccount().getBalance());
//        try {
//            Thread.sleep(40000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        assertEquals(10000, user2.getAccount().getBalance());
    }

    @Test
    void managerBlock(){
        Manager first = new Manager("Karim" , "1234" , 1);
        Manager second = new Manager("Reza" , "2345" , 2);
        assertTrue(first.canEditOrBlock(second));
        assertFalse(second.canEditOrBlock(first));
    }

    @Test
    void interestFund(){
        InterestFund newFund = new InterestFund(user1, 2, 100000);
        user1.addFund(newFund);
        user1.getAccount().setBalance(10000);
        Main.getManagerData().addNewInterestFund(newFund);
        Manager first = new Manager("Karim" , "1234" , 1);
        Main.getManagerData().addNewManager(first);
        Main.getManagerData().depositMonthlyInterest();
        Main.getManagerData().depositMonthlyInterest();
        assertEquals(14500, user1.getAccount().getBalance());
    }

}
