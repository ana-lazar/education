namespace rt
{
    public class Line
    {
        public Vector X0 { get; set; }
        public Vector Xd { get; set; }

        public Line(Vector x0, Vector x1)
        {
            X0 = new Vector(x0);
            Xd = new Vector(x1 - x0);
            Xd.Normalize();
        }

        public Vector CoordinateToPosition(double t)
        {
            return new Vector(Xd * t + X0);
        }
    }
}