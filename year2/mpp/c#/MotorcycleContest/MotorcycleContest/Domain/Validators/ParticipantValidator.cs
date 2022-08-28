using MotorcycleContest.Domain.Entities;

namespace MotorcycleContest.Domain.Validators
{
    public class ParticipantValidator : IValidator<Participant>
    {
        public void Validate(Participant participant)
        {
            if (participant.Name.Equals(null) || participant.RaceId.Equals(-1) || participant.TeamId.Equals(-1))
            {
                throw new ValidationException("null fields");
            }
            if (participant.Name.Equals(""))
            {
                throw new ValidationException("name is empty");
            }
            if (participant.TeamId < 0 || participant.RaceId < 0)
            {
                throw new ValidationException("ids are negative integers");
            }
        }
    }
}
