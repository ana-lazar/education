using System;
using Newtonsoft.Json;

namespace motorcycle_rest_client
{
    [Serializable]
    public class Race
    {
        private int id;
        private int capacity;

        public Race()
        {
        }

        public Race(int capacity, int id)
        {
            this.capacity = capacity;
            this.id = id;
        }
        
        [JsonProperty("capacity")]
        public int Capacity
        {
            get => capacity;
            set => capacity = value;
        }
        
        [JsonProperty("id")]
        public int Id
        {
            get => id;
            set => id = value;
        }

        public override string ToString()
        {
            return "Race " + Id + ", capacity " + Capacity + "; ";
        }
    }
}
