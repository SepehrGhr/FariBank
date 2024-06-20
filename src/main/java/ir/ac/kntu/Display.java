package ir.ac.kntu;

import java.util.List;

public class Display {
    public static <T> T pageShow(List<T> items, ItemPrinter<T> itemPrinter) {
        int pageIndex = 0;
        while (true) {
            int startIndex = pageIndex * 5;
            int endIndex = Math.min(startIndex + 5, items.size());
            System.out.println(Color.YELLOW + "<>".repeat(20));
            for (int i = startIndex; i < endIndex; i++) {
                T item = items.get(i);
                System.out.print(Color.WHITE + (i+1) + "-");
                itemPrinter.printItem(item);
            }
            System.out.println(
                    Color.WHITE + (endIndex+1) +  "-" + Color.CYAN + "Next " +
                            Color.WHITE + (endIndex+2) + "-" + Color.CYAN + "Previous " +
                            Color.WHITE + (endIndex+3) + "-" + Color.CYAN + "Return\n" +
                            Color.YELLOW + "<>".repeat(20) + '\n' +
                            Color.WHITE + "Please select an option" + Color.RESET);

            int userChoice = fetchValidChoice(startIndex + 1, endIndex + 1);
            if (userChoice == endIndex + 1) {
                if (endIndex < items.size()) {
                    pageIndex++;
                } else {
                    System.out.println(Color.RED + "Your already on last page" + Color.RESET);
                }
            } else if (userChoice == endIndex + 2) {
                if (pageIndex > 0) {
                    pageIndex--;
                } else {
                    System.out.println(Color.RED + "Your on first page" + Color.RESET);
                }
            } else if (userChoice == endIndex + 3) {
                return null;
            } else {
                return items.get(userChoice - 1);
            }
        }
    }

    public static <T> T pageShowAndEdit(List<T> items, ItemPrinter<T> itemPrinter, ItemEditor<T> itemEditor) {
        int pageIndex = 0;
        while (true) {
            int startIndex = pageIndex * 5;
            int endIndex = Math.min(startIndex + 5, items.size());
            System.out.println(Color.YELLOW + "<>".repeat(20));
            for (int i = startIndex; i < endIndex; i++) {
                T item = items.get(i);
                System.out.print(Color.WHITE + (i+1) + "-");
                itemPrinter.printItem(item);
            }
            System.out.println(
                    Color.WHITE + (endIndex+1) +  "-" + Color.CYAN + "Next " +
                            Color.WHITE + (endIndex+2) + "-" + Color.CYAN + "Previous " +
                            Color.WHITE + (endIndex+3) + "-" + Color.CYAN + "Return\n" +
                            Color.YELLOW + "<>".repeat(20) + '\n' +
                            Color.WHITE + "Please select an option" + Color.RESET);
            int userChoice = fetchValidChoice(startIndex + 1, endIndex);
            if (userChoice == 6) {
                if (endIndex < items.size()) {
                    pageIndex++;
                }
            } else if (userChoice == 7) {
                if (pageIndex > 0) {
                    pageIndex--;
                }
            } else if (userChoice == 8) {
                return null;
            } else {
                T selectedItem = items.get(userChoice - 1);
                itemEditor.editItem(selectedItem);
                return selectedItem;
            }
        }
    }

    private static int fetchValidChoice(int minValid, int maxValid) {
        while (true) {
            String input = InputManager.getInput();
            if (isChoiceValid(input, minValid, maxValid)) {
                return Integer.parseInt(input);
            }
            System.out.println(Color.RED + "Please select a valid option" + Color.RESET);
        }
    }

    private static boolean isChoiceValid(String input, int minVal, int maxVal) {
        try {
            int choice = Integer.parseInt(input);
            return (choice == maxVal || choice == maxVal+1 || choice == maxVal+2) ||
                    (choice >= minVal && choice <= maxVal);
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
