class ParticipantRepository:
    def __init__(self, validator):
        self.__participants = []
        self.__validator = validator

    def findAll(self):
        """
        Returns all the participants in the repository
        """
        return self.__participants[:]

    def save(self, participant):
        """
        Adds a participant to the repository
        participant - a Participant object
        Returns the participant added
        """
        for participantR in self.__participants:
            if participant.getEventId() == participantR.getEventId() and participant.getPersonId() == participantR.getPersonId():
                return participantR
        self.__participants.append(participant)
        return participant

    def remove(self, id):
        """
        Removes a given participant from the repository by their ID
        id - integer
        Returns the removed participant
        """
        participant = self.__participants[id]
        self.__participants.remove(participant)
        return participant

from domain.Participant import Participant
from domain.validators import ParticipantValidator

def testSave():
    """
    Test function for save
    """
    participantRepository = ParticipantRepository(ParticipantValidator())
    assert participantRepository.findAll() == []
    participant = Participant(1, 2)
    participantRepository.save(participant)
    assert participantRepository.findAll() == [participant]

def testRemove():
    """
    Test function for remove
    """
    participantValidator = ParticipantValidator()
    participantRepository = ParticipantRepository(participantValidator)
    assert participantRepository.findAll() == []
    participantRepository.save(Participant(1, 2))
    participantRepository.save(Participant(3, 4))
    assert len(participantRepository.findAll()) == 2
    participantRepository.remove(25)
    assert len(participantRepository.findAll()) == 1
    try:
        participantRepository.remove(12)
        assert False
    except ValueError:
        assert True
    participantRepository.remove(20)
    assert len(participantRepository.findAll()) == 0

# testSave()
# testRemove()