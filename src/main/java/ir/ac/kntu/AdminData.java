package ir.ac.kntu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminData {
    //private List<AuthenticationRequest> authenticationRequests;
    private Map<User, AuthenticationRequest> requests;
    private List<Admin> admins;
    private List<Ticket> tickets;


    public AdminData() {
        requests = new HashMap<>();
        admins = new ArrayList<>();
        tickets = new ArrayList<>();
    }

    public void addAdmin(Admin newAdmin) {
        admins.add(newAdmin);
    }

    public Admin findAdminByUsername(String input) {
        for (Admin admin : admins) {
            if (admin.getUsername().equals(input)) {
                return admin;
            }
        }
        return null;
    }

    public Map<User, AuthenticationRequest> getRequests() {
        return requests;
    }

    public void addAuthenticationRequest(AuthenticationRequest newRequest) {
        requests.put(newRequest.getUser(), newRequest);
    }

    public void showAuthenticationRequests() {
        int count = 1;
        List<AuthenticationRequest> requestList = new ArrayList<>();
        System.out.println(Color.CYAN + "*".repeat(35) + Color.RESET);
        for (Map.Entry<User, AuthenticationRequest> set : requests.entrySet()) {
            if (!set.getValue().isChecked()) {
                System.out.println(Color.WHITE + count + "-" + Color.BLUE + set.getKey().getPhoneNumber() + Color.RESET);
                requestList.add(set.getValue());
                count++;
            }
        }
        System.out.println(Color.CYAN + "*".repeat(35) + Color.RESET);
        editAuthenticationMenu(requestList);
    }

    private void editAuthenticationMenu(List<AuthenticationRequest> requestList) {
        System.out.println(Color.WHITE + "Enter the number of the request you want to see or enter -1 to return to last menu" + Color.RESET);
        String selection = InputManager.getInput();
        if ("-1".equals(selection)) {
            Menu.printAdminMenu();
            return;
        }
        while (!Menu.isInputValid(selection, requestList.size())) {
            System.out.println(Color.RED + "Please enter a number between 1 and " + requestList.size() + " or enter -1" + Color.RESET);
            selection = InputManager.getInput();
            if ("-1".equals(selection)) {
                Menu.printAdminMenu();
                return;
            }
        }
        AuthenticationRequest selected = requestList.get(Integer.parseInt(selection) - 1);
        selected.showUserInformation();
        AuthenticationRequest.chooseAcceptOrReject(selected);
    }

    public void addNewTicket(Ticket newTicket) {
        tickets.add(newTicket);
    }
}
