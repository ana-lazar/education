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
        public static void Main(string[] args)
        {
            IUserRepository userRepository = new UserDbRepository(new UserValidator());
            IRaceRepository raceRepository = new RaceDbRepository(new RaceValidator());
            ITeamRepository teamRepository = new TeamDbRepository(new TeamValidator());
            IParticipantRepository participantRepository = new ParticipantDbRepository(new ParticipantValidator());
            IMotorcycleService service = new MotorcycleServiceImpl(userRepository, raceRepository, teamRepository, participantRepository);
            MotorcycleServerGrpcImpl serviceGrpc = new MotorcycleServerGrpcImpl(service);
            GrpcServer server = new GrpcServer(55555, "localhost", serviceGrpc);
            server.Start();
        }
    }
}
