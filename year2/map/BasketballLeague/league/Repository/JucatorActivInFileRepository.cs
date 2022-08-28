using lab_7.Model;
using lab_7.Model.Validator;
using lab_7.Repository;
using lab_7.Domain;

using System;

namespace lab_7.Repository
{
    class JucatorActivInFileRepository : InFileRepository<Tuple<Double, Double>, JucatorActiv>
    {
        public JucatorActivInFileRepository(IValidator<JucatorActiv> validator, string fileName) : base(validator, fileName, EntityToFileMapping.CreateJucatorActiv)
        {

        }
    }
}
