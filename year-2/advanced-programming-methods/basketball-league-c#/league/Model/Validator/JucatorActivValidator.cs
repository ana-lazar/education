using lab_7.Domain;

namespace lab_7.Model.Validator
{
    class JucatorActivValidator : IValidator<JucatorActiv>
    {
        public void Validate(JucatorActiv jucatorActiv)
        {
            bool valid = true;
            if (jucatorActiv.IdJucator < 0)
            {
                valid = false;
            }
            if (jucatorActiv.IdMeci < 0)
            {
                valid = false;
            }
            if (jucatorActiv.NrPuncteInscrise < 0)
            {
                valid = false;
            }
            if (valid == false)
            {
                throw new ValidationException("Jucatorul Activ nu e valid");
            }
        }
    }
}
