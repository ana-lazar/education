using System;

namespace MotorcycleCommon.Networking
{
    public enum ResponseType
    {
        OK, ERROR, REGISTERED_PARTICIPANTS
    }
    
    [Serializable]
    public class Response
    {
        public ResponseType Type { get; set; }
        public object Data { get; set; }

        public override string ToString()
        {
            return "Type: " + Type + ", Data: " + Data.ToString();
        }
    }
}