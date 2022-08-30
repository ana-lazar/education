using System.Collections.Generic;

namespace MotorcycleCommon.Services
{
    public interface IMotorcycleService
    {
        MotorcycleContest.Domain.Entities.User Authenticate(string username, string password);

        MotorcycleContest.Domain.Dtos.RaceDto GetRaceByCapacity(int capacity);

        IEnumerable<MotorcycleContest.Domain.Entities.Race> GetRaces();
        
        IEnumerable<MotorcycleContest.Domain.Dtos.RaceDto> GetRaceInfos();

        List<MotorcycleContest.Domain.Dtos.ParticipantDto> GetRaceParticipantsByTeam(string teamName);

        void RegisterParticipant(string name, string teamName, int capacity);

        void logOut(MotorcycleContest.Domain.Entities.User user);

        void AddMotorcycleObserver(IMotorcycleObserver observer);
        
        void RemoveMotorcycleObserver(IMotorcycleObserver observer);
    }
}
