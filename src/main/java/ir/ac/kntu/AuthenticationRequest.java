package ir.ac.kntu;

public class AuthenticationRequest {
    private User user;
    private boolean isChecked;
    public AuthenticationRequest(User user){
        this.user = user;
    }

    public static void newAuthenticationRequest(String name , String lastName , String securityNumber , String phoneNumber , String password ){
        AuthenticationRequest newRequest = new AuthenticationRequest(new User(name , lastName , phoneNumber , securityNumber , password));
        Data.addAuthenticationRequest(newRequest);
    }
}
