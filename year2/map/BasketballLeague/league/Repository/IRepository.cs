using lab_7.Domain;

using System.Collections.Generic;

namespace lab_7.Repository
{
    interface IRepository<ID, E> where E : Entity<ID>
    {
        E FindOne(ID id);

        IEnumerable<E> FindAll();

        E Save(E entity);

        E Delete(ID id);

        E Update(E entity);
    }
}
