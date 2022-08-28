using lab_7.Model;
using lab_7.Model.Validator;
using lab_7.Domain;

using System;
using System.Collections.Generic;
using System.IO;

namespace lab_7.Repository
{
    class JucatorInFileRepository : InFileRepository<Double, Jucator>
    {
        public JucatorInFileRepository(IValidator<Jucator> validator, string fileName) : base(validator, fileName, null)
        {
            loadFromFile();
        }

        private new void loadFromFile()
        {
            List<Elev> elevi = DataReader.ReadData<Elev>("../../../data/elevi.txt", EntityToFileMapping.CreateElev);
            List<Echipa> echipe = DataReader.ReadData<Echipa>("../../../data/echipe.txt", EntityToFileMapping.CreateEchipa);

            using (StreamReader sr = new StreamReader(fileName))
            {
                string line;
                while ((line = sr.ReadLine()) != null)
                {
                    string[] fields = line.Split(',');

                    Elev elev = elevi.Find(x => x.ID.Equals(Double.Parse(fields[0])));
                    Echipa echipa = echipe.Find(x => x.ID.Equals(Double.Parse(fields[1])));

                    Jucator jucator = new Jucator()
                    {
                        ID = elev.ID,
                        Nume = elev.Nume,
                        Scoala = elev.Scoala,
                        Echipa = echipa
                    };

                    base.entities[jucator.ID] = jucator;
                }
            }
        }
    }
}
