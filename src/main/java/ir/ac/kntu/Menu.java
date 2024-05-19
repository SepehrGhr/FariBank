package ir.ac.kntu;

public class Menu {
    public static void printSelectRuleMenu(){
        System.out.println(Color.YELLOW_BOLD_BRIGHT + "please enter what you want to login as" + Color.RESET);
        System.out.println(Color.WHITE_BOLD_BRIGHT + "1-" + Color.BLUE_BOLD_BRIGHT + "User");
        System.out.println(Color.WHITE_BOLD_BRIGHT + "2-" + Color.BLUE_BOLD_BRIGHT + "Admin" + Color.RESET);
        handleSelectRuleInput();

    }

    private static void handleSelectRuleInput() {
        int selection;
        do {
            selection = InputManager.input.nextInt();
        } while (selection != 1 && selection != 2);
        if(selection == 1){
            printUserLoginMenu();
        }
    }

    private static void printUserLoginMenu() {
    }
}
