package domain.validators;

import domain.ValidationException;

public interface Validator<E> {
    void validate(E entity) throws ValidationException;
}
