using lab_7.Model;
using lab_7.Model.Validator;
using lab_7.Repository;
using lab_7.Domain;

using System;

namespace lab_7.Repository
{
    class EchipeInFileRepository : InFileRepository<Double, Echipa>
    {
        public EchipeInFileRepository(IValidator<Echipa> validator, string fileName) : base(validator, fileName, EntityToFileMapping.CreateEchipa)
        {

        }
    }
}
