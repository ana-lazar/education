using System;
using System.Collections.Generic;
using MotorcycleCommon.Services;
using MotorcycleContest.Domain.Dtos;
using MotorcycleContest.Domain.Entities;

namespace motorcycle_contest.Controllers
{
    public class MotorcycleController : IMotorcycleObserver
    {
        private readonly IMotorcycleService _service;
        public static event EventHandler<MotorcycleEventArgs> UpdateEvent;
        
        public MotorcycleController(IMotorcycleService service)
        {
            _service = service;
            _service.AddMotorcycleObserver(this);
        }

        public User Authenticate(string username, string password)
        {
            return _service.Authenticate(username, password);
        }

        public List<ParticipantDto> GetRaceParticipantsByTeam(string teamName)
        {
            return _service.GetRaceParticipantsByTeam(teamName);
        }

        public RaceDto GetRaceByCapacity(int capacity)
        {
            return _service.GetRaceByCapacity(capacity);
        }

        public IEnumerable<Race> GetRaces()
        {
            return _service.GetRaces();
        }

        public void RegisterParticipant(string name, string team, int capacity)
        {
            _service.RegisterParticipant(name, team, capacity);
        }

        public void RegisteredParticipant(Participant participant)
        {
            MotorcycleEventArgs motorcycleEventArgs =
                new MotorcycleEventArgs(MotorcycleEvent.REGISTERED_PARTICIPANT, participant);
            OnMotorcycleEvent(motorcycleEventArgs);
        }

        private void OnMotorcycleEvent(MotorcycleEventArgs motorcycleEventArgs)
        {
            if (UpdateEvent == null)
            {
                return;
            }

            UpdateEvent(this, motorcycleEventArgs);
        }
    }
}