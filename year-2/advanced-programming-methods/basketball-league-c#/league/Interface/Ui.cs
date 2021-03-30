using lab_7.Services;
using lab_7.Domain;

using System;
using System.Collections.Generic;

namespace lab_7.Interface
{
    class Ui
    {
        AppService Service { get; set; }

        public Ui(AppService service)
        {
            this.Service = service;
        }

        public void start()
        {
            while (true)
            {
                try
                {
                    Console.WriteLine("--------------------------------------------------------------------------------------------------");
                    Console.Write("Optiune ---> ");
                    String command = Console.ReadLine();
                    if (command.Equals("x"))
                    {
                        break;
                    }
                    else if (command.Equals("0"))
                    {
                        PrintAll();
                    }
                    else if (command.Equals("1"))
                    {
                        ShowJucatori();
                    }
                    else if (command.Equals("2"))
                    {
                        ShowJucatoriActivi();
                    }
                    else if (command.Equals("3"))
                    {
                        ShowMeciuri();
                    }
                    else if (command.Equals("4"))
                    {
                        ShowScor();
                    }
                }
                catch (Exception ex)
                {
                    Console.WriteLine(ex.Message);
                }
            }
        }

        private void PrintAll()
        {
            List<Echipa> echipe = Service.FindAllEchipe();
            List<Jucator> jucatori = Service.FindAllJucatori();
            List<Meci> meciuri = Service.FindAllMeciuri();
            List<JucatorActiv> jucatoriActivi = Service.FindAllJucatoriActivi();

            echipe.ForEach(Console.WriteLine);
            Console.WriteLine("...\n");
            jucatori.ForEach(Console.WriteLine);
            Console.WriteLine("...\n");
            meciuri.ForEach(Console.WriteLine);
            Console.WriteLine("...\n");
            jucatoriActivi.ForEach(Console.WriteLine);
            Console.WriteLine("...\n");
        }

        private void ShowJucatori()
        {
            Console.WriteLine("Sa se afiseze toti jucatorii unei echipe date.");
            Console.Write("Echipa: ");
            String name = Console.ReadLine();
            Console.Write("Jucatorii sunt: ");
            Service.FindJucatori(name).ForEach(Console.WriteLine);
        }

        private void ShowJucatoriActivi()
        {
            Console.WriteLine("Sa se afiseze toti jucatorii activi ai unei echipe de la un anumit meci.");
            Console.Write("Echipa: ");
            String name = Console.ReadLine();
            Console.Write("Meci: ");
            Double meci = Double.Parse(Console.ReadLine());
            Console.WriteLine("Jucatorii activi sunt: ");
            Service.FindJucatoriActivi(name, meci).ForEach(Console.WriteLine);
        }

        private void ShowMeciuri()
        {
            Console.WriteLine("Sa se afiseze toate meciurile dintr-o anumita perioada calendaristica.");
            Console.Write("De la: ");
            DateTime from = DateTime.Parse(Console.ReadLine());
            Console.Write("Pana la: ");
            DateTime to = DateTime.Parse(Console.ReadLine());
            Console.WriteLine("Meciurile sunt: ");
            Service.FindMeciuri(from, to).ForEach(Console.WriteLine);
        }

        private void ShowScor()
        {
            Console.WriteLine("Sa se determine si sa se afiseze scorul de la un anumit meci.");
            Console.Write("Meci: ");
            Double meci = Double.Parse(Console.ReadLine());
            Console.Write("Scorul este: ");
            var scor = Service.FindScor(meci);
            Console.WriteLine(scor.Item1 + " - " + scor.Item2);
        }
    }
}
