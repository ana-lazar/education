using System;

using MotorcycleContest.Domain.Entities;

namespace MotorcycleContest.Domain.Dtos
{
    public class RaceInfo
    {
        public Race Race { get; set; }
        public Double ParticipantCount { get; set; }

        public RaceInfo(Race race, Double participantCount)
        {
            this.Race = race;
            this.ParticipantCount = participantCount;
        }

        public override string ToString()
        {
            return "Race: " + Race.ID + ", Participants: " + ParticipantCount;
        }
    }
}
