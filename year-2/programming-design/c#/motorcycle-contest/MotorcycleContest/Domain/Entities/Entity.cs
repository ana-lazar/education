using System;

namespace MotorcycleContest.Domain.Entities
{
    public class Entity<TID>
    {
        public TID ID { get; set; }

        public Entity()
        {
        }
    }
}
