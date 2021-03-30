namespace lab_7.Domain
{
    public class Jucator : Elev
    {
        public Echipa Echipa { get; set; }

        public Jucator()
        {
        }

        public override string ToString()
        {
            return "Jucator: " + base.ToString() + ", {Echipa} = " + Echipa.ToString();
        }
    }
}
