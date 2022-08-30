using MotorcycleContest.Domain.Entities;

namespace MotorcycleContest.Domain.Validators
{
    public class TeamValidator : IValidator<Team>
    {
        public void Validate(Team team)
        {
            if (team.Name.Equals(null))
            {
                throw new ValidationException("null fields");
            }
            if (team.Name.Equals(""))
            {
                throw new ValidationException("team is empty");
            }
        }
    }
}
