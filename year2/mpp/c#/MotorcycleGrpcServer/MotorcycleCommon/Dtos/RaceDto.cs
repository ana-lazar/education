using System;

using MotorcycleContest.Domain.Entities;

namespace MotorcycleContest.Domain.Dtos
{
    [Serializable]
    public class RaceDto
    {
        public Race Race { get; set; }
        public int ParticipantCount { get; set; }

        public RaceDto(Race race, int participantCount)
        {
            this.Race = race;
            this.ParticipantCount = participantCount;
        }

        public RaceDto()
        {
        }

        public override string ToString()
        {
            return "Race: " + Race.ID + ", Participants: " + ParticipantCount;
        }
    }
}
