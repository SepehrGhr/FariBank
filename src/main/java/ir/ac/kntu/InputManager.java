package ir.ac.kntu;

import java.util.Scanner;

public class InputManager {
    private static Scanner input = new Scanner(System.in);

    public static String getInput() {
        return input.nextLine().trim();
    }

}
