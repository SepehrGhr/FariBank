package ir.ac.kntu;

public class AuthenticationRequest {
    private User user;
    private boolean checked = false;
    private boolean approved = false;
    private String errorMassage;

    public AuthenticationRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public boolean isChecked() {
        return checked;
    }

    public boolean isApproved() {
        return approved;
    }

    public static void newAuthenticationRequest(User newUser) {
        AuthenticationRequest newRequest = new AuthenticationRequest(newUser);
        Main.getAdminData().addAuthenticationRequest(newRequest);
    }

    public void showErrorMassage(){
        System.out.println(errorMassage);
    }
}
