using System.Data;

namespace MotorcycleContest.Utils
{
    public static class DbUtils
    {
        private static IDbConnection _instance;

        public static IDbConnection GetConnection()
        {
            if (_instance == null || _instance.State == ConnectionState.Closed)
            {
                _instance = GetNewConnection();
                _instance.Open();
            }
            return _instance;
        }

        private static IDbConnection GetNewConnection()
        {
            return ConnectionUtils.ConnectionFactory.GetInstance().CreateConnection();
        }

        public static void CloseConnection()
        {
            _instance.Close();
        }
    }
}
