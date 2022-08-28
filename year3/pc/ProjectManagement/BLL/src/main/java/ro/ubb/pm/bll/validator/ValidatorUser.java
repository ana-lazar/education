package ro.ubb.pm.bll.validator;

import org.springframework.stereotype.Component;
import ro.ubb.pm.bll.exceptions.ExceptionMessages;
import ro.ubb.pm.bll.exceptions.InvalidCredentialsException;
import ro.ubb.pm.model.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class ValidatorUser implements Validator<User> {

    @Override
    public void validate(User user) throws InvalidCredentialsException {

        List<String> errors = new ArrayList<>();
        if(user.getEmail() == null || user.getEmail().trim().equals(""))
            errors.add(ExceptionMessages.invalidEmailMessage);

        if(user.getPassword() == null || user.getPassword().trim().equals(""))
            errors.add(ExceptionMessages.invalidPasswordMessage);

        String errorMessage = errors
                .stream()
                .reduce("", String::concat);

        if(!errorMessage.isEmpty()) {
            throw new InvalidCredentialsException(errorMessage);
        }

    }
}