using System.Collections.Generic;
using MotorcycleContest.Domain.Dtos;
using MotorcycleContest.Domain.Entities;

namespace MotorcycleCommon.Services
{
    public interface IMotorcycleService
    {
        User Authenticate(string username, string password);

        RaceDto GetRaceByCapacity(int capacity);

        IEnumerable<Race> GetRaces();

        List<ParticipantDto> GetRaceParticipantsByTeam(string teamName);

        void RegisterParticipant(string name, string teamName, int capacity);

        void logOut(User user);

        void AddMotorcycleObserver(IMotorcycleObserver observer);
        
        void RemoveMotorcycleObserver(IMotorcycleObserver observer);
    }
}
