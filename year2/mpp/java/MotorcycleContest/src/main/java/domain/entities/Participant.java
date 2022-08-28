package domain.entities;

public class Participant extends Entity<Integer> {
    private String name;
    private Integer raceId;
    private Integer teamId;

    public Participant() {
        this.name = null;
        this.raceId = null;
        this.teamId = null;
    }

    public Participant(String name, Integer raceId, Integer teamId) {
        this.name = name;
        this.raceId = raceId;
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRaceId() {
        return raceId;
    }

    public void setRaceId(Integer raceId) {
        this.raceId = raceId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    @Override
    public String toString() {
        return "Participant " + getId() + ", name " + name + ", race " + raceId + ", team " + teamId + "; ";
    }
}
