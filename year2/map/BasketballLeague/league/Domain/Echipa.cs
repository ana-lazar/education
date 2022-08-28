using System;

namespace lab_7.Domain
{
    public class Echipa : Entity<Double>
    {
        public String Nume { get; set; }

        public Echipa()
        {
        }

        public override string ToString()
        {
            return "Echipa: {ID} = " + ID + ", {Nume} = " + Nume;
        }
    }
}
