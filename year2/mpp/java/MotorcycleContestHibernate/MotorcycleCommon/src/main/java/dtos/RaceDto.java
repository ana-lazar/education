package dtos;

import domain.Race;

import java.io.Serializable;

public class RaceDto implements Serializable {
    private static final long serialVersionUID = 7331115341259248461L;
    private Race race;
    private Integer participantCount;

    public RaceDto(Race race, Integer participantCount) {
        this.race = race;
        this.participantCount = participantCount;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Integer getParticipantCount() {
        return participantCount;
    }

    public void setParticipantCount(Integer participantCount) {
        this.participantCount = participantCount;
    }

    @Override
    public String toString() {
        return race.getId() + ". Capacity " + race.getCapacity() + "cc"+ ", Participants: " + participantCount.toString();
    }
}
