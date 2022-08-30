using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using MotorcycleCommon.Services;
using MotorcycleContest.Domain.Dtos;
using MotorcycleContest.Domain.Entities;
using MotorcycleContest.Repositories.Interfaces;

namespace MotorcycleContest.Services
{
    public class MotorcycleServiceImpl : IMotorcycleService
    {
        private readonly IUserRepository _userRepository;
        private readonly IRaceRepository _raceRepository;
        private readonly ITeamRepository _teamRepository;
        private readonly IParticipantRepository _participantRepository;

        private List<IMotorcycleObserver> _observers = new List<IMotorcycleObserver>();

        public MotorcycleServiceImpl(IUserRepository userRepository, IRaceRepository raceRepository,
            ITeamRepository teamRepository, IParticipantRepository participantRepository)
        {
            this._userRepository = userRepository;
            this._raceRepository = raceRepository;
            this._teamRepository = teamRepository;
            this._participantRepository = participantRepository;
        }

        public User Authenticate(string username, string password)
        {
            return _userRepository.FindByUsernameAndPassword(username, password);
        }

        public RaceDto GetRaceByCapacity(int capacity)
        {
            int raceId = _raceRepository.FindIdByCapacity(capacity);
            Race race = _raceRepository.FindOne(raceId);
            List<Participant> participants = _participantRepository.FindByRace(race.ID);
            return new RaceDto(race, participants.Count);
        }

        public IEnumerable<Race> GetRaces() {
            return _raceRepository.FindAll();
        }

        public IEnumerable<RaceDto> GetRaceInfos()
        {
            List<RaceDto> races = new List<RaceDto>();
            foreach (var race in _raceRepository.FindAll())
            {
                List<Participant> participants = _participantRepository.FindByRace(race.ID);
                races.Add(new RaceDto(race, participants.Count));
            }

            return races;
        }

        public List<ParticipantDto> GetRaceParticipantsByTeam(string teamName) {
            List<ParticipantDto> participants = new List<ParticipantDto>();
            int teamId = _teamRepository.FindIdByName(teamName);
            if (teamId == 0) {
                return participants;
            }
            foreach (Participant participant in _participantRepository.FindByTeam(teamId)) {
                Race race = _raceRepository.FindOne(participant.RaceId);
                if (race != null)
                {
                    participants.Add(new ParticipantDto(race, participant));
                }
            }
            return participants;
        }

        public void RegisterParticipant(string name, string teamName, int capacity) {
            int teamId = _teamRepository.FindIdByName(teamName);
            int raceId = _raceRepository.FindIdByCapacity(capacity);
            Participant participant = _participantRepository.Save(new MotorcycleContest.Domain.Entities.Participant(name, raceId, teamId));

            Task.Run(() =>
            {
                _observers.ForEach(o =>
                {
                    o.RegisteredParticipant(participant);
                });
            });
        }

        public void logOut(User user)
        {
            
        }

        public void AddMotorcycleObserver(IMotorcycleObserver observer)
        {
            _observers.Add(observer);
        }

        public void RemoveMotorcycleObserver(IMotorcycleObserver observer)
        {
            _observers.Remove(observer);
        }
    }
}