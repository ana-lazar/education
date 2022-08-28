package domain.dtos;

import domain.entities.Race;

public class RaceInfo {
    private Race race;
    private Integer participantCount;

    public RaceInfo(Race race, Integer participantCount) {
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
