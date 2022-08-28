using System;
using System.Collections.Generic;
using MotorcycleContest.Domain.Dtos;
using MotorcycleContest.Domain.Entities;
using MotorcycleContest.Repositories.Interfaces;

namespace MotorcycleContest.Services
{
    public class MotorcycleContestService
    {
        private readonly IUserRepository _userRepository;
        private readonly IRaceRepository _raceRepository;
        private readonly ITeamRepository _teamRepository;
        private readonly IParticipantRepository _participantRepository;

        public MotorcycleContestService(IUserRepository userRepository, IRaceRepository raceRepository,
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

        public List<RaceInfo> GetRacesBetween(int capacity1, int capacity2)
        {
            List<RaceInfo> races = new List<RaceInfo>();
            foreach (Race race in _raceRepository.FindByCapacityInterval(capacity1, capacity2)) {
                List<Participant> participants = _participantRepository.FindByRace(race.ID);
                races.Add(new RaceInfo(race, participants.Count));
            }
            return races;
        }

        public RaceInfo GetRaceByCapacity(int capacity)
        {
            int raceId = _raceRepository.FindIdByCapacity(capacity);
            Race race = _raceRepository.FindOne(raceId);
            List<Participant> participants = _participantRepository.FindByRace(race.ID);
            return new RaceInfo(race, participants.Count);
        }

        public IEnumerable<Race> GetRaces() {
            return _raceRepository.FindAll();
        }
        
        public List<ParticipantInfo> GetRaceParticipantsByTeam(string teamName) {
            List<ParticipantInfo> participants = new List<ParticipantInfo>();
            int teamId = _teamRepository.FindIdByName(teamName);
            if (teamId == 0) {
                return participants;
            }
            foreach (Participant participant in _participantRepository.FindByTeam(teamId)) {
                Race race = _raceRepository.FindOne(participant.RaceId);
                if (race != null)
                {
                    participants.Add(new ParticipantInfo(race, participant));
                }
            }
            return participants;
        }

        public void RegisterParticipant(string name, string teamName, int capacity) {
            int teamId = _teamRepository.FindIdByName(teamName);
            int raceId = _raceRepository.FindIdByCapacity(capacity);
            Participant participant = _participantRepository.Save(new Participant(name, raceId, teamId));
        }
    }
}