using lab_7.Model;
using lab_7.Model.Validator;
using lab_7.Domain;

using System;
using System.Collections.Generic;
using System.IO;

namespace lab_7.Repository
{
    class MeciInFileRepository : InFileRepository<Double, Meci>
    {
        public MeciInFileRepository(IValidator<Meci> validator, string fileName) : base(validator, fileName, null)
        {
            loadFromFile();
        }

        private new void loadFromFile()
        {
            List<Echipa> echipe = DataReader.ReadData<Echipa>("../../../data/echipe.txt", EntityToFileMapping.CreateEchipa);

            using (StreamReader sr = new StreamReader(fileName))
            {
                string line;
                while ((line = sr.ReadLine()) != null)
                {
                    string[] fields = line.Split(',');

                    Echipa echipa1 = echipe.Find(x => x.ID.Equals(Double.Parse(fields[1])));
                    Echipa echipa2 = echipe.Find(x => x.ID.Equals(Double.Parse(fields[2])));
                    DateTime data = DateTime.Parse(fields[3]);

                    Meci meci = new Meci()
                    {
                        ID = Double.Parse(fields[0]),
                        Echipa1 = echipa1,
                        Echipa2 = echipa2,
                        Data = data
                    };

                    base.entities[meci.ID] = meci;
                }
            }
        }
    }
}
