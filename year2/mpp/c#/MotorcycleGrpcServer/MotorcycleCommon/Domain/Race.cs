using System;

namespace MotorcycleContest.Domain.Entities
{
    [Serializable]
    public class Race : Entity<int>
    {
        public int Capacity { get; set; }

        public Race()
        {
            this.Capacity = -1;
        }

        public Race(int capacity)
        {
            this.Capacity = capacity;
        }

        public override string ToString()
        {
            return "Race " + ID + ", capacity " + Capacity + "; ";
        }
    }
}
