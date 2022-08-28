"""
    Validators for Event and Person objects
"""
from domain.exceptions import ValidationException

class PersonValidator:
    """
    Validator for a person's fields
    """
    def validate(self, person):
        errors = []
        if person.getName() == "":
            errors.append("error: Person name must not be empty")
        if person.getAddress() == "":
            errors.append("error: Person address must not be empty")
        address = person.getAddress().split(" ")
        try:
            if int(address[len(address) - 1]) not in range(1, 100):
                errors.append("error: Person address is not valid")
        except:
            errors.append("error: Person address is not valid")
        if len(errors) > 0:
            raise ValidationException("\n".join(errors))


class EventValidator:
    """
    Validator for an event's fields
    """
    def validate(self, event):
        errors = []
        if event.getDate() == "":
            errors.append("error: Event date must not be empty")
        monthList = ["ianuarie", "februarie", "martie", "aprilie", "mai", "iunie", "iulie", "august", "septembrie", "octombrie", "noiembrie", "decembrie"]
        date = event.getDate().split(" ")
        try:
            if int(date[0]) not in range(1, 31):
                errors.append("error: Event date is not valid")
            if date[1] not in monthList:
                errors.append("error: Event date is not valid")
        except:
            errors.append("error: Event date is not valid")
        if event.getDescription() == "":
            errors.append("error: Event description must not be empty")
        time = event.getTime().split(":")
        if event.getTime() == "":
            errors.append("error: Event time must not be empty")
        try:
            if int(time[0]) not in range(0, 24):
                errors.append("error: Event time is not valid")
            if int(time[1]) not in range(0, 59):
                errors.append("error: Event time is not valid")
        except:
            errors.append("error: Event time is not valid")
        if len(errors) > 0:
            raise ValidationException("\n".join(errors))

class ParticipantValidator:
    """
    Validator for a participant's fields
    """
    def validate(self, participant, personRepository, eventRepository):
        errors = []
        try:
            personRepository.find(participant.getPersonId())
        except ValueError:
            errors.append("error: No person with the specified ID found")
        try:
            eventRepository.find(participant.getEventId())
        except ValueError:
            errors.append("error: No event with the specified ID found")
        if len(errors) > 0:
            raise ValidationException("\n".join(errors))