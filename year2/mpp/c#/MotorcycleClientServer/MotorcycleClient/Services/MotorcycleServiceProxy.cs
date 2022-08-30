using System;
using System.Collections.Generic;
using MotorcycleCommon.Networking;
using MotorcycleCommon.Services;
using MotorcycleContest.Domain.Dtos;
using MotorcycleContest.Domain.Entities;

namespace motorcycle_contest.Services
{
    public class MotorcycleServiceProxy : BaseServiceProxy, IMotorcycleService
    {
        private IMotorcycleObserver _observer;
        
        public MotorcycleServiceProxy(string host, int port) : base(host, port)
        {
        }
        
        public User Authenticate(string username, string password)
        {
            Logger.InfoFormat("autheticating account by name {0} and password {1}", username, password);
            GetConnection();
            Request request = new Request()
            {
                Type = RequestType.LOGIN,
                Data = new UserDto(username, password)
            };
            Logger.InfoFormat("sending login request {0}", request);
            SendRequest(request);
            Response response = ReadResponse();
            Logger.InfoFormat("got login response {0}", response);
            switch (response.Type)
            {
                case ResponseType.OK:
                {
                    User user = (User) response.Data;
                    if (user == null)
                    {
                        Logger.Info("user not found");
                        return null;
                    }
                    Logger.InfoFormat("found user {0}", user);
                    return user;
                }
                case ResponseType.ERROR:
                {
                    String exception = response.Data.ToString();
                    CloseConnection();
                    _observer = null;
                    Logger.Info("exception getting races");
                    throw new ServerException(exception);
                }
                case ResponseType.REGISTERED_PARTICIPANTS:
                    break;
                default:
                    return null;
            }
            return null;
        }

        public RaceDto GetRaceByCapacity(int capacity)
        {
            Logger.InfoFormat("entering get race by capacity {0}", capacity);
            GetConnection();
            Request request = new Request()
            {
                Type = RequestType.FILTER_RACES,
                Data = capacity
            };
            Logger.InfoFormat("sending get race by capacity request {0}", request);
            SendRequest(request);
            Response response = ReadResponse();
            Logger.InfoFormat("get race by capacity response {0}", response);
            switch (response.Type)
            {
                case ResponseType.OK:
                {
                    RaceDto race = (RaceDto) response.Data;
                    Logger.InfoFormat("get race by capacity found {0}", race);
                    return race;
                }
                case ResponseType.ERROR:
                {
                    String exception = response.Data.ToString();
                    Logger.Info("exception get race by capacity");
                    throw new ServerException(exception);
                }
                default:
                    return null;
            }
        }

        public IEnumerable<Race> GetRaces()
        {
            Logger.Info("entering get races");
            GetConnection();
            Request request = new Request()
            {
                Type = RequestType.GET_RACES
            };
            Logger.InfoFormat("sending get racesrequest {0}", request);
            SendRequest(request);
            Response response = ReadResponse();
            Logger.InfoFormat("get races response {0}", response);
            switch (response.Type)
            {
                case ResponseType.OK:
                {
                    List<Race> races = (List<Race>) response.Data;
                    Logger.InfoFormat("get races found {0}", races);
                    return races;
                }
                case ResponseType.ERROR:
                {
                    String exception = response.Data.ToString();
                    Logger.Info("exception get races");
                    throw new ServerException(exception);
                }
                default:
                    return null;
            }
        }

        public List<ParticipantDto> GetRaceParticipantsByTeam(string teamName)
        {
            Logger.InfoFormat("entering get race participants by team {0}", teamName);
            GetConnection();
            Request request = new Request()
            {
                Type = RequestType.GET_PARTICIPANTS,
                Data = teamName
            };
            Logger.InfoFormat("sending get race participants by team {0}", request);
            SendRequest(request);
            Response response = ReadResponse();
            Logger.InfoFormat("get race participants by team response {0}", response);
            switch (response.Type)
            {
                case ResponseType.OK:
                {
                    List<ParticipantDto> participants = (List<ParticipantDto>) response.Data;
                    Logger.InfoFormat("get race participants by team found {0}", participants);
                    return participants;
                }
                case ResponseType.ERROR:
                {
                    String exception = response.Data.ToString();
                    Logger.Info("exception get race participants by team");
                    throw new ServerException(exception);
                }
                default:
                    return null;
            }
        }

        public void RegisterParticipant(string name, string teamName, int capacity)
        {
            Logger.InfoFormat("entering register participant with name {0}, teamName {1}, capacity {2}", name, teamName, capacity);
            GetConnection();
            Request request = new Request()
            {
                Type = RequestType.REGISTER,
                Data = new CredentialsDto(name, teamName, capacity)
            };
            Logger.InfoFormat("sending register participant request {0}", request);
            SendRequest(request);
            Response response = ReadResponse();
            Logger.InfoFormat("register participant response {0}", response);
            switch (response.Type)
            {
                case ResponseType.OK:
                {
                    Logger.Info("register participant successful");
                    break;
                }
                case ResponseType.ERROR:
                {
                    String exception = response.Data.ToString();
                    Logger.Info("exception during register participant");
                    throw new ServerException(exception);
                }
            }
        }
        
        public override void HandleRegisteredParticipant(Response response)
        {
            Logger.InfoFormat("handling registered participant response {0}", response);
            Participant participant = (Participant) response.Data;
            _observer.RegisteredParticipant(participant);
        }

        public void logOut(User user)
        {
            Logger.Info("logging out");
            if (_connection == null)
            {
                Logger.Info("connection already closed");
                return;
            }

            Request request = new Request()
            {
                Type = RequestType.LOGOUT
            };
            SendRequest(request);
            Response response = ReadResponse();
            CloseConnection();
            if (response.Type == ResponseType.ERROR)
            {
                String exception = response.Data.ToString();
                Logger.Warn("logging out failed " + exception);
                throw new ServerException(exception);
            }
        }

        public void AddMotorcycleObserver(IMotorcycleObserver observer)
        {
            this._observer = observer;
        }

        public void RemoveMotorcycleObserver(IMotorcycleObserver observer)
        {
            this._observer = null;
        }
    }
}
