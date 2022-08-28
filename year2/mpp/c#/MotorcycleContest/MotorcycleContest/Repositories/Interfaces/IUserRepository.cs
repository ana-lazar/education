using System;
using System.Collections.Generic;

using MotorcycleContest.Domain.Entities;

namespace MotorcycleContest.Repositories.Interfaces
{
    public interface IUserRepository : IRepository<int, User>
    {
        User FindByUsernameAndPassword(String username, String password);
    }
}
