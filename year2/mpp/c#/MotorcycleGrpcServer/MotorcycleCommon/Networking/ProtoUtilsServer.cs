using System.Collections.Generic;
using grpc;
using Domain_UserDto = MotorcycleContest.Domain.Dtos.UserDto;
using ParticipantDto = MotorcycleContest.Domain.Dtos.ParticipantDto;
using RaceDto = MotorcycleContest.Domain.Dtos.RaceDto;

namespace MotorcycleCommon.Networking
{
    public class ProtoUtilsServer
    {
        public static Response CreateLoginResponse(MotorcycleContest.Domain.Entities.User user)
        {
            Response response = null;
            if (user == null)
            {
                response = new Response()
                {
                    Type = Response.Types.ResponseType.Error,
                    Error = "Invalid name or password"
                };
            }
            else
            {
                response = new Response()
                {
                    User = new User()
                    {
                        Id = user.ID, 
                        Name = user.Name, 
                        Username = user.Username, 
                        Password = user.Password
                    },
                    Type = Response.Types.ResponseType.Ok
                };
            }
    
            return response;
        }

        public static Domain_UserDto GetUserDto(Request request)
        {
            UserDto user = request.UserDto;
            return new MotorcycleContest.Domain.Dtos.UserDto(user.Username, user.Password);
        }

        public static Response CreateErrorResponse(string message)
        {
            return new Response()
            {
                Type = Response.Types.ResponseType.Error,
                Error = message
            };
        }

        public static Response CreateGetRaceByCapacityResponse(RaceDto raceDto)
        {
            Response response = null;
            if (raceDto == null)
            {
                response = new Response()
                {
                    Type = Response.Types.ResponseType.Error,
                    Error = "Race not found"
                };
            }
            else
            {
                grpc.Race race = new Race()
                {
                    Capacity = raceDto.Race.Capacity,
                    Id = raceDto.Race.ID
                };
                response = new Response()
                {
                    RaceDto = new grpc.RaceDto()
                    {
                        Race = race,
                        Count = raceDto.ParticipantCount
                    },
                    Type = Response.Types.ResponseType.Ok
                };
            }
    
            return response;
        }

        public static Response CreateGetRacesResponse(List<MotorcycleContest.Domain.Entities.Race> races)
        {
            Response response = new Response()
            {
                Type = Response.Types.ResponseType.Ok
            };
            races.ForEach(el =>
            {
                Race race = new Race()
                {
                    Id = el.ID,
                    Capacity = el.Capacity
                };
                response.Races.Add(race);
            });
            
            return response;
        }

        public static Response CreateGetRaceInfosResponse(List<RaceDto> races)
        {
            Response response = new Response()
            {
                Type = Response.Types.ResponseType.Ok
            };
            races.ForEach(el =>
            {
                Race r = new Race()
                {
                    Id = el.Race.ID,
                    Capacity = el.Race.Capacity
                };
                grpc.RaceDto race = new grpc.RaceDto()
                {
                    Race = r,
                    Count = el.ParticipantCount
                };
                response.RaceDtos.Add(race);
            });
            
            return response;
        }

        public static Response CreateGetParticipantsResponse(List<ParticipantDto> participantDtos)
        {
            Response response = new Response()
            {
                Type = Response.Types.ResponseType.Ok
            };
            participantDtos.ForEach(el =>
            {
                grpc.Race r = new Race()
                {
                    Id = el.Race.ID,
                    Capacity = el.Race.Capacity
                };
                grpc.Participant p = new Participant()
                {
                    Id = el.Participant.ID,
                    Name = el.Participant.Name,
                    TeamId = el.Participant.TeamId,
                    RaceId = el.Participant.RaceId
                };
                grpc.ParticipantDto part = new grpc.ParticipantDto()
                {
                    Race = r,
                    Participant = p
                };
                response.Participants.Add(part);
            });
            
            return response;
        }

        public static Response CreateOkResponse()
        {
            return new Response()
            {
                Type = Response.Types.ResponseType.Ok
            };
        }
    }
}