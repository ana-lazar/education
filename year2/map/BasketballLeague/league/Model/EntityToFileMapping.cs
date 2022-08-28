using System;
using lab_7.Domain;

namespace lab_7.Model
{
    class EntityToFileMapping
    {
        public static Elev CreateElev(string line)
        {
            string[] fields = line.Split(',');

            Elev elev = new Elev()
            {
                ID = Double.Parse(fields[0]),
                Nume = fields[1],
                Scoala = fields[2]
            };

            return elev;
        }

        public static Echipa CreateEchipa(string line)
        {
            string[] fields = line.Split(',');

            Echipa echipa = new Echipa()
            {
                ID = Double.Parse(fields[0]),
                Nume = fields[1]
            };

            return echipa;
        }

        public static JucatorActiv CreateJucatorActiv(string line)
        {
            string[] fields = line.Split(',');

            JucatorActiv jucatorActiv = new JucatorActiv()
            {
                IdJucator = Double.Parse(fields[0]),
                IdMeci = Double.Parse(fields[1]),
                NrPuncteInscrise = Double.Parse(fields[2]),
                Tip = (TipJucator)Enum.Parse(typeof(TipJucator), fields[3])
            };

            jucatorActiv.ID = new Tuple<Double, Double>(jucatorActiv.IdJucator, jucatorActiv.IdMeci);

            return jucatorActiv;
        }
    }
}