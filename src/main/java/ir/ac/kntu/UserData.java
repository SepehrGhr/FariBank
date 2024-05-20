package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private static List<User> allUsers = new ArrayList<>();
    private static List<AuthenticationRequest> authenticationRequests = new ArrayList<>();

    public static void addAuthenticationRequest(AuthenticationRequest newRequest){
        authenticationRequests.add(newRequest);
    }
}
