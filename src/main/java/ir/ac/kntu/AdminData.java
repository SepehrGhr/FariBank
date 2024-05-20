package ir.ac.kntu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminData {
    //private List<AuthenticationRequest> authenticationRequests;
    private Map<User,AuthenticationRequest> requests;

    public AdminData() {
        requests = new HashMap<>();
    }

    public Map<User, AuthenticationRequest> getRequests() {
        return requests;
    }

    public void addAuthenticationRequest(AuthenticationRequest newRequest) {
        requests.put(newRequest.getUser(),newRequest);
    }
}
