using System;
using System.Windows.Forms;
using motorcycle_contest.Controllers;
using motorcycle_contest.Services;

namespace motorcycle_contest
{
    static class Program
    {
        private static MotorcycleController GetController()
        {
            return new MotorcycleController(new MotorcycleServiceProxy("127.0.0.1", 55555));
        }
        
        [STAThread]
        public static void Main(string[] args)
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new LoginForm(GetController()));
        }
    }
}