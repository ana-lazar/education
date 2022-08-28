using System;

namespace MotorcycleContest.Domain.Entities
{
    public class Team : Entity<int>
    {
        public String Name { get; set; }

        public Team()
        {
            this.Name = null;
        }

        public Team(String name)
        {
            this.Name = name;
        }

        public override string ToString()
        {
            return "Team " + ID + ", name " + Name + "; ";
        }
    }
}
