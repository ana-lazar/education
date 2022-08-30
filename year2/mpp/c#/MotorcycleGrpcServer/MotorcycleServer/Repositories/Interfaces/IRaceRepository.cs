using System;
using System.Collections.Generic;

using MotorcycleContest.Domain.Entities;

namespace MotorcycleContest.Repositories.Interfaces
{
    public interface IRaceRepository : IRepository<int, Race>
    {
        int FindIdByCapacity(int capacity);
        IEnumerable<Race> FindByCapacityInterval(int capacity1, int capacity2);
    }
}
