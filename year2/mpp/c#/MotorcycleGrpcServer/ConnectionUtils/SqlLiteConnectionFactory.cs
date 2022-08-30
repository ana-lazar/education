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
            String connectionString = "URI=file:C:/Ana/motorcycle-database;Version=3";
            return new SQLiteConnection(connectionString);
        }
    }
}
