using System;
using System.Configuration;
using System.Data;
using System.Data.SQLite;

namespace ConnectionUtils
{
    public class SqliteConnectionFactory : ConnectionFactory
    {
        public override IDbConnection CreateConnection()
        {
            String connectionString = ConfigurationManager.ConnectionStrings["contestDB"].ConnectionString;
            return new SQLiteConnection(connectionString);
        }
    }
}
