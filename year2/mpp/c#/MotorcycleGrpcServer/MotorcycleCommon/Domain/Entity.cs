using System;

namespace MotorcycleContest.Domain.Entities
{
    [Serializable]
    public class Entity<TID>
    {
        public TID ID { get; set; }

        public Entity()
        {
        }
    }
}
