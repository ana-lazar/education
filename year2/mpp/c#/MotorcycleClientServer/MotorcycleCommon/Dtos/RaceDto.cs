using System;

using MotorcycleContest.Domain.Entities;

namespace MotorcycleContest.Domain.Dtos
{
    [Serializable]
    public class RaceDto
    {
        public Race Race { get; set; }
        public Double ParticipantCount { get; set; }

        public RaceDto(Race race, Double participantCount)
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
