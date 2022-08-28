package dtos;

import domain.Participant;
import domain.Race;

import java.io.Serializable;

public class ParticipantDto implements Serializable {
    private static final long serialVersionUID = 7331115341259248461L;
    private Race race;
    private Participant participant;

    public ParticipantDto(Race race, Participant participant) {
        this.race = race;
        this.participant = participant;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    @Override
    public String toString() {
        return "Name: " + participant.getName() + ", Race: " + race.getCapacity() + "cc";
    }
}
