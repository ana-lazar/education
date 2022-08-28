package dtos;

import java.io.Serializable;

public class CredentialsDto implements Serializable {
    private static final long serialVersionUID = 7331115341259248461L;
    private String name, teamName;
    private int capacity;

    public CredentialsDto(String name, String teamName, int capacity) {
        this.name = name;
        this.teamName = teamName;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
