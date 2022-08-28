using System;

namespace rt
{
    public class Sphere : Geometry
    {
        private Vector Center { get; set; }
        private double Radius { get; set; }

        public Sphere(Vector center, double radius, Material material, Color color) : base(material, color)
        {
            Center = center;
            Radius = radius;
        }

        public override Intersection GetIntersection(Line line, double minDist, double maxDist)
        {
            // ADD CODE HERE: Calculate the intersection between the given line and this sphere
            
            // find equation coefficients
            var a = line.Xd.X;
            var c = line.Xd.Y;
            var e = line.Xd.Z;

            var b = line.X0.X;
            var d = line.X0.Y;
            var f = line.X0.Z;

            var xc = Center.X;
            var yc = Center.Y;
            var zc = Center.Z;

            var A = a * a + c * c + e * e;
            var B = 2 * a * b - 2 * a * xc + 2 * c * d - 2 * c * yc + 2 * e * f - 2 * e * zc;
            var C = b * b + xc * xc - 2 * b * xc + d * d + yc * yc - 2 * d * yc +
                f * f + zc * zc - 2 * f * zc - Radius * Radius;
            
            // find discriminant
            var delta = B * B - 4 * A * C;
            
            // check if we have an intersection
            if (delta >= 0)
            {
                var t1 = (-1 * B + Math.Sqrt(delta)) / (2 * A);
                var t2 = (-1 * B - Math.Sqrt(delta)) / (2 * A);
                
                if (t1 < t2)
                {
                    // value must be between bounds
                    if (t1 >= minDist && t1 <= maxDist)
                    {
                        return new Intersection(true, true, this, line, t1);
                    }
                }
                else
                {
                    // value must be between bounds
                    if (t2 >= minDist && t2 <= maxDist)
                    {
                        return new Intersection(true, true, this, line, t2);
                    }
                }
            }
            
            return new Intersection();
        }

        public override Vector Normal(Vector v)
        {
            var n = v - Center;
            n.Normalize();
            return n;
        }
    }
}