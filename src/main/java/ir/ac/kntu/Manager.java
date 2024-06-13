package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;

public class Manager {
    private String name;
    private String password;
    private List<Manager> subordinates;

    public Manager(String name, String password) {
        this.name = name;
        this.password = password;
        this.subordinates = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
