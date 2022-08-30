using System;
using MotorcycleCommon.Services;
using MotorcycleContest.Domain.Validators;
using MotorcycleContest.Repositories.Database;
using MotorcycleContest.Repositories.Interfaces;
using MotorcycleContest.Services;
using MotorcycleServer.Networking;

namespace MotorcycleServer
{
    internal class StartServer
    {
        // protected static readonly ILog Logger = LogManager.GetLogger("StartServer");
        
        public static void Main(string[] args)
        {
            RpcConcurrentServer server = null;
            try
            {
                // Logger.Info("creating server");
                server = GetServer();
                Console.WriteLine("server started");
                server.Start();
            }
            catch (Exception exception)
            {
                // Logger.Warn("creating application context failed " + exception.Message);
            }
            finally
            {
                if (server != null)
                {
                    server.Stop();
                }
            }
        }

        private static RpcConcurrentServer GetServer()
        {
            // Logger.Info("creating application context ...");
            IUserRepository userRepository = new UserDbRepository(new UserValidator());
            IRaceRepository raceRepository = new RaceDbRepository(new RaceValidator());
            ITeamRepository teamRepository = new TeamDbRepository(new TeamValidator());
            IParticipantRepository participantRepository = new ParticipantDbRepository(new ParticipantValidator());
            IMotorcycleService service = new MotorcycleServiceImpl(userRepository, raceRepository, teamRepository, participantRepository);
            return new RpcConcurrentServer("127.0.0.1", 55555, service);
        }
    }
}
