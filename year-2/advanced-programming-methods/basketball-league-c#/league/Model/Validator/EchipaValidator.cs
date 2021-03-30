using lab_7.Domain;

namespace lab_7.Model.Validator
{
    class EchipaValidator : IValidator<Echipa>
    {
        public void Validate(Echipa echipa)
        {
            bool valid = true;
            if (echipa.Nume.Equals(""))
            {
                valid = false;
            }
            if (echipa.ID < 0)
            {
                valid = false;
            }
            if (valid == false)
            {
                throw new ValidationException("Echipa nu e valida");
            }
        }
    }
}
