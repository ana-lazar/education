using System;
using System.Runtime.CompilerServices;

namespace MotorcycleCommon.Networking
{
    public enum RequestType
    {
        LOGIN, LOGOUT, GET_RACES, SEARCH_RACES, FILTER_RACES, GET_PARTICIPANTS, REGISTER
    }
    
    [Serializable]
    public class Request
    {
        public RequestType Type { get; set; }
        public object Data { get; set; }

        public override string ToString()
        {
            return "Type: " + Type + ", Data: " + Data.ToString();
        }
    }
}