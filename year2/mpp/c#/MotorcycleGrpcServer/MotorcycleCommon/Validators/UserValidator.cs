using MotorcycleContest.Domain.Entities;

namespace MotorcycleContest.Domain.Validators
{
    public class UserValidator : IValidator<User>
    {
        public void Validate(User user)
        {
            if (user.Name.Equals(null) && user.Username.Equals(null) || user.Password.Equals(null))
            {
                throw new ValidationException("null fields");
            }
            if (user.Name.Equals("") && user.Username.Equals("") || user.Password.Equals(""))
            {
                throw new ValidationException("empty fields");
            }
        }
    }
}
