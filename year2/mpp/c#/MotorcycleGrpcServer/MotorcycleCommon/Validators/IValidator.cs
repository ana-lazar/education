﻿namespace MotorcycleContest.Domain.Validators
{
    public interface IValidator<E>
    {
        void Validate(E entity);
    }
}
