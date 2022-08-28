package ro.ubb.pm.bll.validator;

import ro.ubb.pm.bll.exceptions.InvalidCredentialsException;

public interface Validator<T> {
    void validate(T entity) throws InvalidCredentialsException;
}
