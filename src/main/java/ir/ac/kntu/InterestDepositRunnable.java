package ir.ac.kntu;

import ir.ac.kntu.util.Calendar;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class InterestDepositRunnable implements Runnable {
    @Override
    public void run() {
        while (true) {
            try {
                Instant now = Calendar.now();
                ZonedDateTime currentDateTime = now.atZone(ZoneId.systemDefault());
                int dayOfMonth = currentDateTime.getDayOfMonth();
                if (dayOfMonth == 1) {
                    Main.getManagerData().depositMonthlyInterest();
                }
                Thread.sleep(24 * 60 * 60 * 1000 / 6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
