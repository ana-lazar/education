using System;

namespace MotorcycleCommon.Services
{
    public class ServerException : Exception
    {
        public ServerException(string message) : base(message) { }
    }
}