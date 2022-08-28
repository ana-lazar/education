package domain.entities;

public class User extends Entity<Integer> {
    private String name;
    private String username;
    private String password;

    public User() {
        this.name = null;
        this.username = null;
        this.password = null;
    }

    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User " + getId() + ", name " + name + ", username " + username + ", password " + password + "; ";
    }
}
