using lab_7.Domain;

namespace lab_7.Model.Validator
{
    class ElevValidator : IValidator<Elev>
    {
        public void Validate(Elev elev)
        {
            bool valid = true;
            if (elev.Nume.Equals(""))
            {
                valid = false;
            }
            if (elev.Scoala.Equals(""))
            {
                valid = false;
            }
            if (elev.ID < 0)
            {
                valid = false;
            }
            if (valid == false)
            {
                throw new ValidationException("Elevul nu e valid");
            }
        }
    }
}
