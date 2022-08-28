package validators;

import domain.User;

public class UserValidator implements Validator<User> {
    @Override
    public void validate(User user) throws ValidationException {
        if (user.getName() == null || user.getUsername() == null || user.getPassword() == null) {
            throw new ValidationException("All user fields must be not null");
        }
        if (user.getName().equals("") || user.getUsername().equals("") || user.getPassword().equals("")) {
            throw new ValidationException("All user fields must be not empty");
        }
    }
}
