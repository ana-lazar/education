using MotorcycleContest.Domain.Entities;

namespace MotorcycleContest.Domain.Validators
{
    public class RaceValidator : IValidator<Race>
    {
        public void Validate(Race race)
        {
            if (race.Capacity.Equals(-1))
            {
                throw new ValidationException("null fields");
            }
            if (race.Capacity < 50 || race.Capacity > 2000)
            {
                throw new ValidationException("capacity is invalid");
            }
        }
    }
}
