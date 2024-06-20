package ir.ac.kntu;

public class Admin {
    private String name;
    private String username;
    private String password;

    public Admin(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return Color.CYAN + "*".repeat(35) + '\n' +
                Color.YELLOW + "Admin: " + '\n' +
                Color.WHITE + "Name: " + Color.BLUE + name + '\n' +
                Color.WHITE + "Username: " + Color.BLUE + username + '\n' +
                Color.WHITE + "Password: " + Color.BLUE + password + '\n' +
                Color.CYAN + "*".repeat(35) + Color.RESET;
    }
}
