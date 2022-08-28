package dtos;

import java.io.Serializable;

public class UserDto implements Serializable {
    private static final long serialVersionUID = 7331115341259248461L;
    private String username, password;

    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
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
}
