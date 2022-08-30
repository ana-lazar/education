using System;
using System.Collections.Generic;
using System.Data;
using MotorcycleContest.Domain.Entities;
using MotorcycleContest.Domain.Validators;
using MotorcycleContest.Repositories.Interfaces;
using MotorcycleContest.Utils;

namespace MotorcycleContest.Repositories.Database
{
    public class ParticipantDbRepository : AbstractDbRepository<int, Participant>, IParticipantRepository
    {
        public ParticipantDbRepository(IValidator<Participant> validator) : base(validator)
        {
            // Logger.Info("Creating ParticipantDbRepository ...");
        }

        protected override Participant ExtractEntity(IDataReader reader)
        {
            int id = reader.GetInt32(0);
            string name = reader.GetString(1);
            int raceId = reader.GetInt32(2);
            int teamId = reader.GetInt32(3);
            Participant participant = new Participant(name, raceId, teamId) {ID = id};
            return participant;
        }

        protected override string SelectByIdCommand()
        {
            return "SELECT id, name, raceId, teamId FROM participants WHERE id = @id;";
        }

        protected override string SelectAllCommand()
        {
            return "SELECT id, name, raceId, teamId FROM participants;";
        }

        protected override void AddInsertParameters(IDbCommand command, Participant participant)
        {
            IDataParameter name = command.CreateParameter();
            name.ParameterName = "@name";
            name.Value = participant.Name;
            command.Parameters.Add(name);
            IDataParameter raceId = command.CreateParameter();
            raceId.ParameterName = "@raceId";
            raceId.Value = participant.RaceId;
            command.Parameters.Add(raceId);
            IDataParameter teamId = command.CreateParameter();
            teamId.ParameterName = "@teamId";
            teamId.Value = participant.TeamId;
            command.Parameters.Add(teamId);
        }

        protected override bool IsValidId(int id)
        {
            return id > 0;
        }

        protected override string InsertCommand(Participant entity)
        {
            return "INSERT INTO participants (name, raceId, teamId) VALUES (@name, @raceId, @teamId);";
        }

        protected override void SetId(Participant entity, object id)
        {
            entity.ID = Convert.ToInt32(id);
        }

        protected override string UpdateCommand(Participant entity)
        {
            return "UPDATE participants SET name = @name, raceId = @raceId, teamId = @teamId WHERE id = @id;";
        }

        protected override string GetTableName()
        {
            return "participants";
        }

        public List<Participant> FindByTeam(int teamId)
        {
            // Logger.InfoFormat("Entering find by team id");
            IDbConnection connection = DbUtils.GetConnection();
            List<Participant> list = new List<Participant>();
            using (var command = connection.CreateCommand())
            {
                command.CommandText = "SELECT id, name, raceId, teamId FROM participants WHERE teamId = @teamId;";
                IDataParameter teamParameter = command.CreateParameter();
                teamParameter.ParameterName = "@teamId";
                teamParameter.Value = teamId;
                command.Parameters.Add(teamParameter);
                using (var reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        list.Add(ExtractEntity(reader));
                    }
                }
            }
            // Logger.InfoFormat("Exiting find by team id");
            return list;
        }

        public List<Participant> FindByRace(int raceId)
        {
            // Logger.InfoFormat("Entering find by race id");
            IDbConnection connection = DbUtils.GetConnection();
            List<Participant> list = new List<Participant>();
            using (var command = connection.CreateCommand())
            {
                command.CommandText = "SELECT id, name, raceId, teamId FROM participants WHERE raceId = @raceId;";
                IDataParameter raceParameter = command.CreateParameter();
                raceParameter.ParameterName = "@raceId";
                raceParameter.Value = raceId;
                command.Parameters.Add(raceParameter);
                using (var reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        list.Add(ExtractEntity(reader));
                    }
                }
            }
            // Logger.InfoFormat("Exiting find by race id");
            return list;
        }
    }
}