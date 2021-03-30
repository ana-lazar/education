using System;
using System.Data;

using MotorcycleContest.Domain.Entities;
using MotorcycleContest.Domain.Validators;
using MotorcycleContest.Repositories.Interfaces;
using MotorcycleContest.Utils;

namespace MotorcycleContest.Repositories.Database
{
    public class TeamDbRepository : AbstractDbRepository<int, Team>, ITeamRepository
    {
        public TeamDbRepository(IValidator<Team> validator) : base(validator)
        {
            Logger.Info("Creating TeamDbRepository ...");
        }
        
        protected override Team ExtractEntity(IDataReader reader)
        {
            int id = reader.GetInt32(0);
            string name = reader.GetString(1);
            Team team = new Team(name) {ID = id};
            return team;
        }

        protected override string SelectByIdCommand()
        {
            return "SELECT id, name FROM teams WHERE id = @id;";
        }

        protected override string SelectAllCommand()
        {
            return "SELECT id, name FROM teams;";
        }

        protected override void AddInsertParameters(IDbCommand command, Team team)
        {
            IDataParameter name = command.CreateParameter();
            name.ParameterName = "@name";
            name.Value = team.Name;
            command.Parameters.Add(name);
        }

        protected override bool IsValidId(int id)
        {
            return id > 0;
        }

        protected override string InsertCommand(Team entity)
        {
            return "INSERT INTO teams (name) VALUES (@name);";
        }

        protected override void SetId(Team entity, object id)
        {
            entity.ID = Convert.ToInt32(id);
        }

        protected override string UpdateCommand(Team entity)
        {
            return "UPDATE teams SET name = @name WHERE id = @id;";
        }

        protected override string GetTableName()
        {
            return "teams";
        }

        public int FindIdByName(string name)
        {
            Logger.InfoFormat("Entering find id by name {0}", name);
            IDbConnection connection = DbUtils.GetConnection();
            using (var command = connection.CreateCommand())
            {
                command.CommandText = "SELECT id, name FROM teams WHERE name = @name;";
                IDataParameter parameter = command.CreateParameter();
                parameter.ParameterName = "@name";
                parameter.Value = name;
                command.Parameters.Add(parameter);
                int id = Convert.ToInt32(command.ExecuteScalar());
                Logger.InfoFormat("Exiting find id by name with value {0}", id);
                return id;
            }
        }
    }
}