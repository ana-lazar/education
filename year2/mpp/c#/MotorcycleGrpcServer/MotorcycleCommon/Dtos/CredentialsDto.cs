using System;

namespace MotorcycleContest.Domain.Dtos
{
    [Serializable]
    public class CredentialsDto
    {
        public String Name { get; set; }
        public String TeamName { get; set; }
        public int Capacity { get; set; }

        public CredentialsDto(String name, String teamName, int capacity)
        {
            Name = name;
            TeamName = teamName;
            Capacity = capacity;
        }
        
        public override string ToString()
        {
            return "Name: " + Name + ", Team Name: " + TeamName + ", Capacity: " + Capacity;
        }
    }
}
