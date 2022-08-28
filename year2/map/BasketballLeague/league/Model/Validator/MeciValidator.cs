using lab_7.Domain;

namespace lab_7.Model.Validator
{
    class MeciValidator : IValidator<Meci>
    {
        public void Validate(Meci meci)
        {
            bool valid = true;

            if (meci.ID < 0)
            {
                valid = false;
            }

            EchipaValidator echipaValidator = new EchipaValidator();
            echipaValidator.Validate(meci.Echipa1);
            echipaValidator.Validate(meci.Echipa2);

            if (valid == false)
            {
                throw new ValidationException("Elevul nu e valid");
            }
        }
    }
}
