using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;

using MotorcycleCommon.Networking;
using MotorcycleCommon.Services;
using MotorcycleContest.Domain.Dtos;
using MotorcycleContest.Domain.Entities;

namespace MotorcycleServer.Networking
{
    public class MotorcycleClientRpcWorker : IMotorcycleObserver
    {
        // protected static readonly ILog Logger = LogManager.GetLogger("MotorcycleClientRpcWorker");
        
        private IMotorcycleService service;
        private TcpClient connection;

        private NetworkStream stream;
        private IFormatter formatter;
        private volatile bool connected;
        
        public MotorcycleClientRpcWorker(IMotorcycleService service, TcpClient connection)
        {
            // Logger.Info("creating motorcycle rpc client worker");
            this.service = service;
            this.connection = connection;
            try
            {
                // Logger.Info("initiating connection");
                stream = connection.GetStream();
                formatter = new BinaryFormatter();
                connected = true;
            }
            catch (Exception exception)
            {
                // Logger.Warn("initiating connection failed");
                Console.WriteLine(exception.StackTrace);
            }
        }

        public virtual void Run()
        {
            // Logger.Info("running worker thread");
            while (connected)
            {
                try
                {
                    // Logger.Info("getting request");
                    object request = formatter.Deserialize(stream);
                    // Logger.Info("handling request " + request.ToString());
                    object response = HandleRequest((Request) request);
                    if (response != null)
                    {
                        // Logger.Info("sending response " + response.ToString());
                        SendResponse((Response) response);
                    }
                }
                catch (Exception exception)
                {
                    if (connected)
                    {
                        // Logger.Warn("running worker stopped with exception " + exception.Message);
                    }
                    break;
                }
            }

            try
            {
                // Logger.Info("closing connection");
                stream.Close();
                connection.Close();
            }
            catch (Exception exception)
            {
                // Logger.Warn("closing connection failed with exception " + exception.Message);
            }
        }

        private static Response okResponse = new Response() { Type = ResponseType.OK };
        private static Response errorResponse = new Response() { Type = ResponseType.ERROR };

        private Response HandleRequest(Request request)
        {
            // Logger.InfoFormat("handling request {0}", request);
            Response response = null;
            switch (request.Type)
            {
                case RequestType.LOGIN:
                {
                    // Logger.Info("resolving login request");
                    UserDto userDto = (UserDto) request.Data;
                    try
                    {
                        // Logger.InfoFormat("finding user with username {0} and password {1}", username, password);
                        User user = service.Authenticate(userDto.Username, userDto.Password);
                        if (user == null)
                        {
                            // Logger.Info("user not found");
                            return okResponse;
                        }
                        service.AddMotorcycleObserver(this);
                        // Logger.Info("exiting with user {0}", user);
                        return new Response()
                        {
                            Type = ResponseType.OK,
                            Data = user
                        };
                    }
                    catch (Exception)
                    {
                        // Logger.Warn("finding user failed");
                        connected = false;
                        return errorResponse;
                    }

                    break;
                }
                case RequestType.GET_RACES:
                    // Logger.Info("resolving get races request");
                    try
                    {
                        // Logger.Info("finding races");
                        List<Race> races = service.GetRaces().ToList();
                        // Logger.InfoFormat("found races {0}", races);
                        return new Response()
                        {
                            Type = ResponseType.OK,
                            Data = races
                        };
                    }
                    catch (Exception exception)
                    {
                        // Logger.Warn("finding races failed");
                        return errorResponse;
                    }

                    break;
                case RequestType.FILTER_RACES:
                    // Logger.Info("resolving get race by capacity request");
                    try
                    {
                        int capacity = (int) request.Data;
                        // Logger.InfoFormat("finding race by capacity {0}", capacity);
                        RaceDto race = service.GetRaceByCapacity(capacity);
                        // Logger.InfoFormat("found race {0}", race);
                        return new Response()
                        {
                            Type = ResponseType.OK,
                            Data = race
                        };
                    }
                    catch (Exception exception)
                    {
                        // Logger.Warn("finding races failed");
                        return errorResponse;
                    }

                    break;
                case RequestType.GET_PARTICIPANTS:
                    // Logger.Info("resolving get participants request");
                    try
                    {
                        String teamName = (String) request.Data;
                        // Logger.InfoFormat("finding participants by team {0}", teamName);
                        List<ParticipantDto> participants = service.GetRaceParticipantsByTeam(teamName);
                        // Logger.InfoFormat("exiting with participant dtos {0}", participants);
                        return new Response()
                        {
                            Type = ResponseType.OK,
                            Data = participants
                        };
                    }
                    catch (Exception exception)
                    {
                        // Logger.Warn("finding participants failed");
                        return errorResponse;
                    }

                    break;
                case RequestType.REGISTER:
                    // Logger.Info("resolving register participant request");
                    try
                    {
                        CredentialsDto credentials = (CredentialsDto) request.Data;
                        service.RegisterParticipant(credentials.Name, credentials.TeamName, credentials.Capacity);
                        // Logger.Info("register participant");
                        return okResponse;
                    }
                    catch (Exception exception)
                    {
                        // Logger.Warn("finding participants failed");
                        return errorResponse;
                    }

                    break;
                case RequestType.LOGOUT:
                    // Logger.Info("resolving logout request");
                    try
                    {
                        connected = false;
                        service.RemoveMotorcycleObserver(this);
                        // Logger.Info("logout successful");
                        return okResponse;
                    }
                    catch (Exception exception)
                    {
                        // Logger.Warn("logout failed");
                        return errorResponse;
                    }

                    break;
                default:
                    return response;
            }
        }

        private void SendResponse(Response response)
        {
            // Logger.InfoFormat("sending response {0}", response);
            formatter.Serialize(stream, response);
            stream.Flush();
        }

        public void RegisteredParticipant(Participant participant)
        {
            // Logger.InfoFormat("registered participant {0}", participant);
            Response response = new Response()
            {
                Type = ResponseType.REGISTERED_PARTICIPANTS,
                Data = participant
            };
            try
            {
                SendResponse(response);
            }
            catch (Exception exception)
            {
                // Logger.Warn("sending registered participant failed");
                Console.WriteLine(exception.StackTrace);
            }
        }
    }
}
