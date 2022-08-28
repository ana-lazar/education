using System;
using System.Collections.Generic;
using System.Data;
using MotorcycleContest.Domain.Entities;
using MotorcycleContest.Domain.Validators;
using MotorcycleContest.Repositories.Interfaces;
using MotorcycleContest.Utils;

namespace MotorcycleContest.Repositories.Database
{
    public class RaceDbRepository : AbstractDbRepository<int, Race>, IRaceRepository
    {
        public RaceDbRepository(IValidator<Race> validator) : base(validator)
        {
            Logger.Info("Creating RaceDbRepository ...");
        }

        protected override Race ExtractEntity(IDataReader reader)
        {
            int id = reader.GetInt32(0);
            int capacity = reader.GetInt32(1);
            Race race = new Race(capacity) {ID = id};
            return race;
        }

        protected override string SelectByIdCommand()
        {
            return "SELECT id, capacity FROM races WHERE id = @id;";
        }

        protected override string SelectAllCommand()
        {
            return "SELECT id, capacity FROM races;";
        }

        protected override void AddInsertParameters(IDbCommand command, Race race)
        {
            IDataParameter capacity = command.CreateParameter();
            capacity.ParameterName = "@capacity";
            capacity.Value = race.Capacity;
            command.Parameters.Add(capacity);
        }

        protected override bool IsValidId(int id)
        {
            return id > 0;
        }

        protected override string InsertCommand(Race entity)
        {
            return "INSERT INTO races (capacity) VALUES (@capacity);";
        }

        protected override void SetId(Race entity, object id)
        {
            entity.ID = Convert.ToInt32(id);
        }

        protected override string UpdateCommand(Race entity)
        {
            return "UPDATE races SET capacity = @capacity WHERE id = @id;";
        }

        protected override string GetTableName()
        {
            return "races";
        }

        public int FindIdByCapacity(int capacity)
        {
            Logger.InfoFormat("Entering find by capacity");
            IDbConnection connection = DbUtils.GetConnection();
            IList<Race> list = new List<Race>();
            using (var command = connection.CreateCommand())
            {
                command.CommandText = "SELECT id, capacity FROM races WHERE capacity = @capacity;";
                IDataParameter capacityParameter = command.CreateParameter();
                capacityParameter.ParameterName = "@capacity";
                capacityParameter.Value = capacity;
                command.Parameters.Add(capacityParameter);
                using (var reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        list.Add(ExtractEntity(reader));
                    }
                }
            }
            Logger.InfoFormat("Exiting find by capacity");
            return list[0].ID;
        }

        public IEnumerable<Race> FindByCapacityInterval(int capacity1, int capacity2)
        {
            Logger.InfoFormat("Entering find by capacity");
            IDbConnection connection = DbUtils.GetConnection();
            IList<Race> list = new List<Race>();
            using (var command = connection.CreateCommand())
            {
                command.CommandText = "SELECT id, capacity FROM races WHERE @capacity1 <= capacity AND capacity <= @capacity2;";
                IDataParameter capacity1Parameter = command.CreateParameter();
                capacity1Parameter.ParameterName = "@capacity1";
                capacity1Parameter.Value = capacity1;
                command.Parameters.Add(capacity1Parameter);
                IDataParameter capacity2Parameter = command.CreateParameter();
                capacity2Parameter.ParameterName = "@capacity2";
                capacity2Parameter.Value = capacity2;
                command.Parameters.Add(capacity2Parameter);
                using (var reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        list.Add(ExtractEntity(reader));
                    }
                }
            }
            Logger.InfoFormat("Exiting find by capacity");
            return list;
        }
    }
}