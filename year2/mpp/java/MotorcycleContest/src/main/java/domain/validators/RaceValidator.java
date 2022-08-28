package domain.validators;

import domain.ValidationException;
import domain.entities.Race;
import org.springframework.stereotype.Component;

@Component
public class RaceValidator implements Validator<Race> {
    @Override
    public void validate(Race race) throws ValidationException {
        if (race.getCapacity() == null) {
            throw new ValidationException("All race fields must be not null");
        }
        if (race.getCapacity() < 50 || race.getCapacity() > 2000) {
            throw new ValidationException("Race capacity is invalid");
        }
    }
}
