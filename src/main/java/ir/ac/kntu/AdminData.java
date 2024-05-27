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

    public void removeRequest(User user) {
        requests.remove(user);
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
        while (!InputManager.isInputValid(selection, requestList.size())) {
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


    public void displayTicketsMenu() {
        System.out.println(Color.WHITE + "Please select the filter you want to use" + Color.RESET);
        System.out.println(Color.WHITE + "1-" + Color.BLUE + "Status" + Color.RESET);
        System.out.println(Color.WHITE + "2-" + Color.BLUE + "Type" + Color.RESET);
        System.out.println(Color.WHITE + "3-" + Color.BLUE + "User" + Color.RESET);
        System.out.println(Color.WHITE + "4-" + Color.BLUE + "Return" + Color.RESET);
        selectTicketFilter();
    }

    private void selectTicketFilter() {
        String selection = InputManager.getInput();
        while (!InputManager.isInputValid(selection, 4)) {
            System.out.println(Color.RED + "Please enter a number between 1 and 4" + Color.RESET);
            selection = InputManager.getInput();
        }
        switch (selection) {
            case "1":
                showTicketsByStatus();
                break;
            case "2":
                showTicketsByType();
                break;
            case "3":
                showTicketsByUser();
                break;
            case "4":
                Menu.printAdminMenu();
                break;
            default:
                break;
        }
    }

    private void showTicketsByUser() {
        int count = 1;
        System.out.println(Color.CYAN + "*".repeat(35) + Color.RESET);
        for (Ticket ticket : tickets) {
            System.out.println(Color.WHITE + count + "-" + Color.BLUE + ticket.getSubmitter().getPhoneNumber() + Color.RESET);
            count++;
        }
        System.out.println(Color.CYAN + "*".repeat(35) + Color.RESET);
        selectTicket();
    }

    private void selectTicket() {
        System.out.println(Color.WHITE + "Enter the number of the ticket you want to see or enter -1 to return to last menu" + Color.RESET);
        String selection = InputManager.getInput();
        if ("-1".equals(selection)) {
            Menu.printAdminMenu();
            return;
        }
        while (!InputManager.isInputValid(selection, tickets.size())) {
            System.out.println(Color.RED + "Please enter a number from the list or enter -1" + Color.RESET);
            selection = InputManager.getInput();
            if ("-1".equals(selection)) {
                Menu.printAdminMenu();
                return;
            }
        }
        Ticket selected = tickets.get(Integer.parseInt(selection) - 1);
        System.out.println(selected.toString());
        submitAdminMessage(selected);
    }

    private void submitAdminMessage(Ticket selected) {
        System.out.println(Color.WHITE + "Please enter your message for them" + Color.RESET);
        String adminMessage = InputManager.getInput();
        selected.setAdminMessage(adminMessage);
        selected.setStatus(Status.CLOSED);
        System.out.println(Color.GREEN + "Your message has been submitted and ticket status has been updated" + Color.RESET);
    }

    private void showTicketsByType() {
        int count = 1;
        System.out.println(Color.CYAN + "*".repeat(35) + Color.RESET);
        for (Ticket ticket : tickets) {
            System.out.println(Color.WHITE + count + "-" + Color.BLUE + ticket.getType().toString() + Color.RESET);
            count++;
        }
        System.out.println(Color.CYAN + "*".repeat(35) + Color.RESET);
        selectTicket();
    }

    private void showTicketsByStatus() {
        int count = 1;
        System.out.println(Color.CYAN + "*".repeat(35) + Color.RESET);
        for (Ticket ticket : tickets) {
            System.out.println(Color.WHITE + count + "-" + Color.BLUE + ticket.getStatus().toString() + Color.RESET);
            count++;
        }
        System.out.println(Color.CYAN + "*".repeat(35) + Color.RESET);
        selectTicket();
    }
}
