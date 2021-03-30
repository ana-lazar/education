using System;

namespace lab_7.Domain
{
    public class Meci : Entity<Double>
    {
        public Echipa Echipa1 { get; set; }

        public Echipa Echipa2 { get; set; }

        public DateTime Data { get; set; }

        public override string ToString()
        {
            return "Meci: {ID} = " + ID + ", {Echipa1} = " + Echipa1.ToString() + ", {Echipa2} = " + Echipa2.ToString() + ", {Data} = " + Data;
        }
    }
}
