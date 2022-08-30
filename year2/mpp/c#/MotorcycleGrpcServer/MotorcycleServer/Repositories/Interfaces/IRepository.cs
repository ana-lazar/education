using System;
using System.Collections.Generic;

using MotorcycleContest.Domain.Entities;

namespace MotorcycleContest.Repositories.Interfaces
{
    public interface IRepository<ID, E> where E : Entity<ID>
    {
        E FindOne(ID id);

        IEnumerable<E> FindAll();

        E Save(E entity);

        E Delete(ID id);

        int Size();
    }
}
