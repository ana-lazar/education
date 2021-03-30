using System;

namespace lab_7.Domain
{
    public class Elev : Entity<Double>
    {
        public String Nume { get; set; }

        public String Scoala { get; set; }

        public Elev()
        {
        }

        public override string ToString()
        {
            return "Elev: {ID} = " + ID + ", {Nume} = " + Nume + ", {Scoala} = " + Scoala;
        }
    }
}
