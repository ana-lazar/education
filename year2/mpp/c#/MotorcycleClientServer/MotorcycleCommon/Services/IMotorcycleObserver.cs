using MotorcycleContest.Domain.Entities;

namespace MotorcycleCommon.Services
{
    public interface IMotorcycleObserver
    {
        void RegisteredParticipant(Participant participant);
    }
}