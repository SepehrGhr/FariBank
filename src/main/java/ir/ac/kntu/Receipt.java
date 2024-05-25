package ir.ac.kntu;

import ir.ac.kntu.util.Calendar;

import java.time.Instant;

public class Receipt {
    private Instant time;
    private int amount;

    public Receipt(int amount) {
        this.time = Calendar.now();
        this.amount = amount;
    }

    public Instant getTime() {
        return time;
    }

    @Override
    public String toString() {
        return Color.CYAN + "*".repeat(35) + '\n' + Color.WHITE + "Date : " + Color.YELLOW + timeToString(time) +
                           '\n' + Color.WHITE + "Amount : " + Color.GREEN + amount + '\n' + Color.RESET;
    }

    public String timeToString(Instant time){
        return time.toString().substring(0 ,10) + " " + time.toString().substring(11 , 19);
    }
}
