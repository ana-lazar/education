using System;

namespace rt
{
    class RayTracer
    {
        private Geometry[] geometries;
        private Light[] lights;

        public RayTracer(Geometry[] geometries, Light[] lights)
        {
            this.geometries = geometries;
            this.lights = lights;
        }

        private double ImageToViewPlane(int n, int imgSize, double viewPlaneSize)
        {
            var u = n * viewPlaneSize / imgSize;
            u -= viewPlaneSize / 2;
            return u;
        }

        private Intersection FindFirstIntersection(Line ray, double minDist, double maxDist)
        {
            var intersection = new Intersection();

            foreach (var geometry in geometries)
            {
                var intr = geometry.GetIntersection(ray, minDist, maxDist);

                if (!intr.Valid || !intr.Visible) continue;

                if (!intersection.Valid || !intersection.Visible)
                {
                    intersection = intr;
                }
                else if (intr.T < intersection.T)
                {
                    intersection = intr;
                }
            }

            return intersection;
        }

        private bool IsLit(Vector point, Light light)
        {
            // ADD CODE HERE: Detect whether the given point has a clear line of sight to the given light
            
            // create a line object starting in the intersection and going to the light position and
            // check the intersection of that line to every other line geometry
            // if it intersects anything within the segment, then that light does not hit the object
            // and you don't take into consideration the other lights

            // generate line from intersection to light
            var line = new Line(light.Position, point);
            
            // check if the line intersects any geometry
            foreach (var geometry in geometries)
            {
                var intr = geometry.GetIntersection(line, 0, (light.Position - point).Length());

                // if there is an intersection, the point is not hit by the light
                if (intr.Valid && intr.Visible)
                {
                    // ignore points of current vector
                    if ((intr.Position - point).Length() < 0.0001) continue;
                    
                    // if (Math.Abs(intr.Position.X - point.X) < 0.0001 && 
                    //     Math.Abs(intr.Position.Y - point.Y) < 0.0001 && 
                    //     Math.Abs(intr.Position.Z - point.Z) < 0.0001) 
                    //     continue;

                    return false;
                }
            }
            
            // if there are no intersections, the point is lit
            return true;
        }

        public void Render(Camera camera, int width, int height, string filename)
        {
            var background = new Color(1, 1, 1, 1);
            var image = new Image(width, height);

            for (var i = 0; i < width; i++)
            {
                for (var j = 0; j < height; j++)
                {
                    // ADD CODE HERE: Implement pixel color calculation
                    
                    // generate line from camera to pixel
                    var x0 = camera.Position;
                    var x1 = camera.Position + camera.Direction * camera.ViewPlaneDistance +
                             (camera.Direction ^ camera.Up) * ImageToViewPlane(i, width, camera.ViewPlaneWidth) +
                             camera.Up * ImageToViewPlane(j, height, camera.ViewPlaneHeight);
                    var line = new Line(x0, x1);

                    // find first intersection between line and spheres (line, frontPlane, backPlane)
                    var intersection = FindFirstIntersection(line, camera.FrontPlaneDistance, camera.BackPlaneDistance);
                    
                    // find colour for pixel (Intersection.Geometry.Color if it intersects a sphere and background otherwise)
                    Color color = null;
                    if (intersection.Geometry == null)
                    {
                        color = background;
                    }
                    else
                    {
                        var sphere = (Sphere) intersection.Geometry;
                        var material = intersection.Geometry.Material;

                        // adjust colour for each light
                        foreach (var light in lights)
                        {
                            // find ambient colour
                            var currentColor = material.Ambient * light.Ambient;

                            // check if the object is hit by this specific light. if not, don't compute the diffuse and specular
                            if (IsLit(intersection.Position, light))
                            {
                                // find diffuse colour using the normal to the surface and intersection to light vectors
                                var N = sphere.Normal(intersection.Position);
                                var T = (light.Position - intersection.Position).Normalize();
                                if (N * T > 0)
                                {
                                    currentColor += material.Diffuse * light.Diffuse * (N * T);
                                }
                                
                                // find specular colour using the intersection to camera and reflection vectors
                                var E = (camera.Position - intersection.Position).Normalize();
                                var R = (N * (N * T) * 2 - T).Normalize();
                                if (E * R > 0)
                                {
                                    currentColor += material.Specular * light.Specular * Math.Pow(E * R, material.Shininess);
                                }

                                // manage intensity of colour
                                currentColor *= light.Intensity;
                            }

                            // add deducted colour for this light to final colour
                            if (color == null)
                            {
                                color = currentColor;
                            }
                            else
                            {
                                color += currentColor;
                            }
                        }
                    }

                    image.SetPixel(i, j, color);
                }
            }

            image.Store(filename);
        }
    }
}