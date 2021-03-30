package validators;

import domain.Team;

public class TeamValidator implements Validator<Team> {
    @Override
    public void validate(Team team) throws ValidationException {
        if (team.getName() == null) {
            throw new ValidationException("All team fields must be not null");
        }
        if (team.getName().equals("")) {
            throw new ValidationException("All team fields must be not empty");
        }
    }
}
