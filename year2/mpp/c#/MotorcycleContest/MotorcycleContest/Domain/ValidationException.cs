using System;

namespace MotorcycleContest.Domain
{
    public class ValidationException : ApplicationException
    {
        public ValidationException(String message) : base(message)
        {
        }
    }
}
