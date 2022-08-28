using System;
using System.Collections.Generic;
using System.Data;

using MotorcycleContest.Domain.Entities;
using MotorcycleContest.Domain.Validators;
using MotorcycleContest.Repositories.Interfaces;
using MotorcycleContest.Utils;

namespace MotorcycleContest.Repositories.Database
{
    public class UserDbRepository : AbstractDbRepository<int, User>, IUserRepository
    {
        public UserDbRepository(IValidator<User> validator) : base(validator)
        {
            Logger.Info("Creating UserRepository ...");
        }
        
        protected override User ExtractEntity(IDataReader reader)
        {
            int id = reader.GetInt32(0);
            string name = reader.GetString(1);
            string username = reader.GetString(2);
            string password = reader.GetString(3);
            User user = new User(name, username, password) {ID = id};
            return user;
        }
        
        protected override string SelectByIdCommand()
        {
            return "SELECT id, name, username, password FROM users WHERE id = @id;";
        }

        protected override string SelectAllCommand()
        {
            return "SELECT id, name, username, password FROM users;";
        }

        protected override void AddInsertParameters(IDbCommand command, User user)
        {
            IDataParameter name = command.CreateParameter();
            name.ParameterName = "@name";
            name.Value = user.Name;
            command.Parameters.Add(name);
            IDataParameter username = command.CreateParameter();
            username.ParameterName = "@username";
            username.Value = user.Username;
            command.Parameters.Add(username);
            IDataParameter password = command.CreateParameter();
            password.ParameterName = "@password";
            password.Value = user.Password;
            command.Parameters.Add(password);
        }

        protected override bool IsValidId(int id)
        {
            return id > 0;
        }

        protected override string InsertCommand(User entity)
        {
            return "INSERT INTO users (name, username, password) VALUES (@name, @username, @password);";
        }

        protected override void SetId(User entity, object id)
        {
            entity.ID = Convert.ToInt32(id);
        }

        protected override string UpdateCommand(User entity)
        {
            return "UPDATE users SET name = @name, username = @username, password = @password WHERE id = @id;";
        }

        protected override string GetTableName()
        {
            return "users";
        }

        public User FindByUsernameAndPassword(string username, string password)
        {
            Logger.InfoFormat("Entering find all");
            IDbConnection connection = DbUtils.GetConnection();
            IList<User> list = new List<User>();
            using (var command = connection.CreateCommand())
            {
                command.CommandText = "SELECT id, name, username, password FROM users WHERE username = @username AND password = @password;";
                IDataParameter userParameter = command.CreateParameter();
                userParameter.ParameterName = "@username";
                userParameter.Value = username;
                command.Parameters.Add(userParameter);
                IDataParameter passwordParameter = command.CreateParameter();
                passwordParameter.ParameterName = "@password";
                passwordParameter.Value = password;
                command.Parameters.Add(passwordParameter);
                using (var reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        list.Add(ExtractEntity(reader));
                    }
                }
                if (list.Count == 0) {
                    Logger.WarnFormat("find failed: username {0} & password {1} not found", username, password);
                    return null;
                }
            }
            Logger.InfoFormat("Exiting find all");
            return list[0];
        }
    }
}
