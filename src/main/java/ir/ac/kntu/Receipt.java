package ir.ac.kntu;

import ir.ac.kntu.util.Calendar;

import java.time.Instant;

public class Receipt {
    private Instant time;

    public Receipt() {
        time = Calendar.now();
    }
}
