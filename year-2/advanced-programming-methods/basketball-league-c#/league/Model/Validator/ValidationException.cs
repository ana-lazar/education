using System;

namespace lab_7.Model.Validator
{
    class ValidationException : ApplicationException
    {
        public ValidationException(String message) : base(message)
        {
        }
    }
}
