package domain;

public class Team extends Entity<Integer> {
    private String name;

    public Team() {
        this.name = null;
    }

    public Team(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Team " + getId() + ", name " + name + "; ";
    }
}
