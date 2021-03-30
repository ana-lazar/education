using System;
using System.Collections.Generic;
using System.Data;
using log4net;
using MotorcycleContest.Domain.Entities;
using MotorcycleContest.Domain.Validators;
using MotorcycleContest.Repositories.Interfaces;
using MotorcycleContest.Utils;

namespace MotorcycleContest.Repositories.Database
{
    public abstract class AbstractDbRepository<ID, E> : IRepository<ID, E> where E : Entity<ID>
    {
        private readonly IValidator<E> _validator;
        protected static readonly ILog Logger = LogManager.GetLogger("UserDbRepository");

        protected AbstractDbRepository(IValidator<E> validator)
        {
            this._validator = validator;
        }

        protected abstract E ExtractEntity(IDataReader reader);

        protected abstract string SelectByIdCommand();

        public E FindOne(ID id)
        {
            Logger.InfoFormat("Entering find with value {0}", id);
            if (!IsValidId(id))
            {
                Logger.Warn("find failed: id was invalid");
                throw new ArgumentException("Find failed: id must be valid");
            }
            IDbConnection connection = DbUtils.GetConnection();
            using (var command = connection.CreateCommand())
            {
                command.CommandText = SelectByIdCommand();
                IDataParameter parameter = command.CreateParameter();
                parameter.ParameterName = "@id";
                parameter.Value = id;
                command.Parameters.Add(parameter);
                using (var reader = command.ExecuteReader())
                {
                    if (reader.Read())
                    {
                        E entity = ExtractEntity(reader);
                        Logger.InfoFormat("Exiting find with value {0}", entity);
                        return entity;
                    }
                }
            }
            Logger.InfoFormat("Exiting find with value {0}", null);
            return null;
        }
        
        protected abstract string SelectAllCommand();

        public IEnumerable<E> FindAll()
        {
            Logger.InfoFormat("Entering find all");
            IDbConnection connection = DbUtils.GetConnection();
            IList<E> list = new List<E>();
            using (var command = connection.CreateCommand())
            {
                command.CommandText = SelectAllCommand();
                using (var reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        list.Add(ExtractEntity(reader));
                    }
                }
            }
            Logger.InfoFormat("Exiting find all");
            return list;
        }

        protected abstract void AddInsertParameters(IDbCommand command, E entity);
        
        protected abstract bool IsValidId(ID id);
        
        protected abstract string InsertCommand(E entity);
        
        protected abstract void SetId(E entity, object id);
        
        protected abstract string UpdateCommand(E entity);

        public E Save(E entity)
        {
            Logger.InfoFormat("Entering save for {0}", entity);
            if (entity == null)
            {
                Logger.Warn("save / update failed: entity was null");
                throw new ArgumentException("Save / Update failed: entity must be not null");
            }
            _validator.Validate(entity);
            IDbConnection connection = DbUtils.GetConnection();
            using (var command = connection.CreateCommand())
            {
                AddInsertParameters(command, entity);
                if (!IsValidId(entity.ID))
                {
                    command.CommandText = InsertCommand(entity);
                    command.ExecuteNonQuery();
                    var idCommand = connection.CreateCommand();
                    idCommand.CommandText = "SELECT last_insert_rowid()";
                    var result = idCommand.ExecuteScalar();
                    if ((long)result == 0)
                    {
                        Logger.Warn("saving to database failed");
                        throw new ArgumentException("Saving to database failed");
                    }
                    SetId(entity, result);
                    Logger.InfoFormat("Exiting save with value {0}", entity);
                    return entity;
                }
                Logger.InfoFormat("Updating entity {0}", entity);
                IDataParameter parameter = command.CreateParameter();
                parameter.ParameterName = "@id";
                parameter.Value = entity.ID;
                command.Parameters.Add(parameter);
                command.CommandText = UpdateCommand(entity);
                int rows = command.ExecuteNonQuery();
                if (rows == 0)
                {
                    Logger.Warn("update failed: nonexistent ID");
                    throw new ArgumentException("Update failed: nonexistent ID");
                }
                Logger.InfoFormat("Exiting update with value {0}", entity);
                return entity;
            }
        }
        
        protected abstract string GetTableName();

        public E Delete(ID id)
        {
            Logger.InfoFormat("Entering delete for {0}", id);
            if (!IsValidId(id))
            {
                Logger.Warn("Delete failed: id was invalid");
                throw new ArgumentException("Delete failed: id must be valid");
            }
            IDbConnection connection = DbUtils.GetConnection();
            E entity = FindOne(id);
            if (entity == null)
            {
                Logger.Warn("Delete failed: entity not found");
                throw new ArgumentException("Delete failed: entity not found");
            }
            using (var command = connection.CreateCommand())
            {
                command.CommandText = "DELETE FROM " + GetTableName() + " WHERE id = @id;";
                IDbDataParameter parameter = command.CreateParameter();
                parameter.ParameterName = "@id";
                parameter.Value = id;
                command.Parameters.Add(parameter);
                var rows = command.ExecuteNonQuery();
                if (rows == 0)
                {
                    Logger.Warn("delete failed: nonexistent ID");
                    throw new ArgumentException("Delete failed: nonexistent ID");
                }
                Logger.InfoFormat("Exiting delete with value {0}", entity);
                return entity;
            }
        }

        public int Size()
        {
            Logger.InfoFormat("Entering size");
            IDbConnection connection = DbUtils.GetConnection();
            using (var command = connection.CreateCommand())
            {
                command.CommandText = "SELECT COUNT(*) FROM " + GetTableName() + ";";
                int result = Convert.ToInt32(command.ExecuteScalar());
                Logger.InfoFormat("Exiting size");
                return result;
            }
        }
    }
}
