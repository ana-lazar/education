package domain.validators;

import domain.ValidationException;
import domain.entities.Participant;
import org.springframework.stereotype.Component;

@Component
public class ParticipantValidator implements Validator<Participant> {
    @Override
    public void validate(Participant participant) throws ValidationException {
        if (participant.getName() == null || participant.getRaceId() == null || participant.getTeamId() == null) {
            throw new ValidationException("All participant fields must be not null");
        }
        if (participant.getName().equals("")) {
            throw new ValidationException("All participant fields must be not empty");
        }
        if (participant.getTeamId() < 0 || participant.getRaceId() < 0) {
            throw new ValidationException("Participant ids must be positive integers");
        }
    }
}
