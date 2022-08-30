using System;
using System.Collections.Generic;

using MotorcycleContest.Domain.Entities;

namespace MotorcycleContest.Repositories.Interfaces
{
    public interface IParticipantRepository : IRepository<int, Participant>
    {
        List<Participant> FindByTeam(int teamId);
        List<Participant> FindByRace(int raceId);
    }
}
