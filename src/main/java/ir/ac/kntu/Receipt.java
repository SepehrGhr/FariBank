package ir.ac.kntu;

import ir.ac.kntu.util.Calendar;

import java.time.Instant;
import java.time.format.DateTimeParseException;

public class Receipt {
    private Instant time;
    private long amount;

    public Receipt(long amount) {
        this.time = Calendar.now();
        this.amount = amount;
    }

    public Instant getTime() {
        return time;
    }

    public long getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return Color.CYAN + "*".repeat(35) + '\n' + Color.WHITE + "Date : " + Color.YELLOW + timeToString(time) +
                '\n' + Color.WHITE + "Amount : " + Color.GREEN + amount + '\n' + Color.RESET;
    }

    public String timeToString(Instant time) {
        return time.toString().substring(0, 10) + " " + time.toString().substring(11, 19);
    }

    public static void printSimpleReceipt(Receipt receipt, int count) {
        if (receipt instanceof ChargeReceipt) {
            System.out.println(Color.WHITE + count + "-" + Color.GREEN + receipt.timeToString(receipt.getTime()) + Color.RESET);
        } else {
            System.out.println(Color.WHITE + count + "-" + Color.YELLOW + receipt.timeToString(receipt.getTime()) + Color.RESET);
        }
    }

    public static Instant getDateInput() {
        Instant instant = null;
        while (instant == null) {
            String input = InputManager.getInput();
            input += "T00:00:00.000Z";
            try {
                instant = Instant.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println(Color.RED + "Please enter a valid date. example: 2024-05-14" + Color.RESET);
            }
        }
        return instant;
    }
}
