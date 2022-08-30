using System;

namespace motorcycle_contest.Controllers
{
    public enum MotorcycleEvent
    {
        REGISTERED_PARTICIPANT
    }
    
    public class MotorcycleEventArgs : EventArgs
    {
        private readonly MotorcycleEvent _motorcycleEvent;
        private readonly object _data;

        public MotorcycleEventArgs(MotorcycleEvent motorcycleEvent, object data)
        {
            _motorcycleEvent = motorcycleEvent;
            _data = data;
        }

        public object Data => _data;

        internal MotorcycleEvent MotorcycleEvent => _motorcycleEvent;
    }
}