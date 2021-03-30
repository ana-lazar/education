using System;

namespace lab_7.Domain
{
    public enum TipJucator
    {
        Rezerva, Participant
    }

    public class JucatorActiv : Entity<Tuple<Double, Double>>
    {
        public Double IdJucator { get; set; }

        public Double IdMeci { get; set; }

        public Double NrPuncteInscrise { get; set; }

        public TipJucator Tip { get; set; }

        public JucatorActiv()
        {
        }

        public override string ToString()
        {
            return "JucatorActiv: {IdJucator} = " + IdJucator + ", {IdMeci} = " + IdMeci + ", {NrPuncteInscrise} = " + NrPuncteInscrise + ", {Tip} = " + Tip;
        }
    }
}
