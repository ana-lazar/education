using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Threading.Tasks.Dataflow;
using grpc;
using Grpc.Core;
using MotorcycleCommon.Networking;
using MotorcycleCommon.Services;

namespace MotorcycleServer.Networking
{
    public class MotorcycleServerGrpcImpl : MotorcycleServices.MotorcycleServicesBase
    {
        private IMotorcycleService service;

        private ConcurrentQueue<IServerStreamWriter<Response>> observers = new();

        private readonly BufferBlock<Response> buffer = new BufferBlock<Response>();

        public MotorcycleServerGrpcImpl(IMotorcycleService service)
        {
            this.service = service;
        }

        public override Task<Response> authenticate(Request request, ServerCallContext context)
        {
            MotorcycleContest.Domain.Dtos.UserDto user = ProtoUtilsServer.GetUserDto(request);
            Console.WriteLine("authenticating user " + user.Password + " " + user.Username);
            Response response = null;
            try
            {
                MotorcycleContest.Domain.Entities.User u = service.Authenticate(user.Username, user.Password);
                if (user == null)
                {
                    response = ProtoUtilsServer.CreateErrorResponse("Invalid user or password");
                }
                else
                {
                    response = ProtoUtilsServer.CreateLoginResponse(u);
                }
            }
            catch (Exception exception)
            {
                response = ProtoUtilsServer.CreateErrorResponse(exception.Message);
            }
            Console.WriteLine("sending response " + response.ToString());
            
            return Task.FromResult(response);
        }

        public override Task<Response> GetRaceByCapacity(Request request, ServerCallContext context)
        {
            int capacity = request.Capacity;
            Console.WriteLine("getting race " + capacity);
            Response response = null;
            try
            {
                MotorcycleContest.Domain.Dtos.RaceDto r = service.GetRaceByCapacity(capacity);
                if (r == null)
                {
                    response = ProtoUtilsServer.CreateErrorResponse("race not found");
                }
                else
                {
                    response = ProtoUtilsServer.CreateGetRaceByCapacityResponse(r);
                }
            }
            catch (Exception exception)
            {
                response = ProtoUtilsServer.CreateErrorResponse(exception.Message);
            }
            Console.WriteLine("sending response " + response.ToString());
            return Task.FromResult(response);
        }

        public override Task<Response> GetRaces(Request request, ServerCallContext context)
        {
            Response response = null;
            try
            {
                List<MotorcycleContest.Domain.Entities.Race> races = service.GetRaces().ToList();
                response = ProtoUtilsServer.CreateGetRacesResponse(races);
            }
            catch (Exception e)
            {
                response = ProtoUtilsServer.CreateErrorResponse(e.Message);
            }

            return Task.FromResult(response);
        }

        public override Task<Response> GetRaceInfos(Request request, ServerCallContext context)
        {
            Response response = null;
            try
            {
                List<MotorcycleContest.Domain.Dtos.RaceDto> races = service.GetRaceInfos().ToList();
                response = ProtoUtilsServer.CreateGetRaceInfosResponse(races);
            }
            catch (Exception e)
            {
                response = ProtoUtilsServer.CreateErrorResponse(e.Message);
            }

            return Task.FromResult(response);
        }

        public override Task<Response> GetRaceParticipantsByTeam(Request request, ServerCallContext context)
        {
            Response response = null;
            try
            {
                List<MotorcycleContest.Domain.Dtos.ParticipantDto> participantDtos = service.GetRaceParticipantsByTeam(request.TeamName).ToList();
                response = ProtoUtilsServer.CreateGetParticipantsResponse(participantDtos);
            }
            catch (Exception e)
            {
                response = ProtoUtilsServer.CreateErrorResponse(e.Message);
            }

            return Task.FromResult(response);
        }

        public override Task<Response> RegisterParticipant(Request request, ServerCallContext context)
        {
            Response response = null;
            try
            {
                CredentialsDto c = request.CredentialsDto;
                MotorcycleContest.Domain.Dtos.CredentialsDto credentials =
                    new MotorcycleContest.Domain.Dtos.CredentialsDto(c.Name, c.TeamName, c.Capacity);
                service.RegisterParticipant(credentials.Name, credentials.TeamName, credentials.Capacity);

                Response responseRegistered = new Response()
                {
                    Type = Response.Types.ResponseType.RegisteredParticipant
                };

                buffer.Post(responseRegistered);
            }
            catch (Exception e)
            {
                response = ProtoUtilsServer.CreateErrorResponse(e.Message);
            }

            return Task.FromResult(ProtoUtilsServer.CreateOkResponse());
        }

        public override Task<Response> LogOut(Request request, ServerCallContext context)
        {
            IServerStreamWriter<Response> o = null;
            return Task.FromResult(new Response()
            {
                Type = Response.Types.ResponseType.Ok
            });
        }

        public override async Task LogIn(Request request, IServerStreamWriter<Response> responseStream, ServerCallContext context)
        {
            observers.Enqueue(responseStream);

            var e = await buffer.ReceiveAsync();
            foreach (var serverStreamWriter in observers)
            {
                await serverStreamWriter.WriteAsync(e);
            }
            
            while (!context.CancellationToken.IsCancellationRequested)
            {
                await Task.Delay(100);
            }
        }
    }
}