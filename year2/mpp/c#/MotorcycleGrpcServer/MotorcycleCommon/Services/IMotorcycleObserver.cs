using MotorcycleContest.Domain.Entities;

namespace MotorcycleCommon.Services
{
    public interface IMotorcycleObserver
    {
        void RegisteredParticipant(MotorcycleContest.Domain.Entities.Participant participant);
    }
}