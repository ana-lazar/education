using MotorcycleContest.Domain.Entities;

namespace MotorcycleContest.Repositories.Interfaces
{
    public interface ITeamRepository : IRepository<int, Team>
    {
        int FindIdByName(string name);
    }
}
