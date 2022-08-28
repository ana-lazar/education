using System;

namespace MotorcycleContest.Domain.Entities
{
    public class Participant : Entity<int>
    {
        public String Name { get; set; }
        public int RaceId { get; set; }
        public int TeamId { get; set; }

        public Participant()
        {
            this.Name = null;
            this.RaceId = -1;
            this.TeamId = -1;
        }

        public Participant(String name, int raceId, int teamId)
        {
            this.Name = name;
            this.RaceId = raceId;
            this.TeamId = teamId;
        }

        public override string ToString()
        {
            return "Participant " + ID + ", name " + Name + ", race " + RaceId + ", team " + TeamId + "; ";
        }
    }
}
