package domain.dtos;

import domain.entities.Participant;
import domain.entities.Race;

public class ParticipantInfo {
    private Race race;
    private Participant participant;

    public ParticipantInfo(Race race, Participant participant) {
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
