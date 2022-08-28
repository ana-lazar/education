using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using MotorcycleContest.Domain.Validators;
using MotorcycleContest.Repositories.Database;
using MotorcycleContest.Repositories.Interfaces;
using MotorcycleContest.Services;

namespace motorcycle_contest
{
    static class Program
    {
        private static MotorcycleContestService GetService()
        {
            IUserRepository userRepository = new UserDbRepository(new UserValidator());
            ITeamRepository teamRepository = new TeamDbRepository(new TeamValidator());
            IRaceRepository raceRepository = new RaceDbRepository(new RaceValidator()); 
            IParticipantRepository participantRepository = new ParticipantDbRepository(new ParticipantValidator());

            return new MotorcycleContestService(
                userRepository, raceRepository, teamRepository, participantRepository
            );
        }
        
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new LoginForm(GetService()));
        }
    }
}