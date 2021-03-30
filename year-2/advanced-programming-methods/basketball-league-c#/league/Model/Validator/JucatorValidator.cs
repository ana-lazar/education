using lab_7.Domain;

namespace lab_7.Model.Validator
{
    class JucatorValidator : IValidator<Jucator>
    {
        public void Validate(Jucator jucator)
        {
            bool valid = true;
            if (jucator.Nume.Equals(""))
            {
                valid = false;
            }
            if (jucator.Scoala.Equals(""))
            {
                valid = false;
            }
            if (jucator.ID < 0)
            {
                valid = false;
            }

            EchipaValidator echipaValidator = new EchipaValidator();
            echipaValidator.Validate(jucator.Echipa);

            if (valid == false)
            {
                throw new ValidationException("Jucatorul nu e valid");
            }
        }
    }
}
