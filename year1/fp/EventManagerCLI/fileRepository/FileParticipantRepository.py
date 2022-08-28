from repository.ParticipantRepository import ParticipantRepository
from domain.Participant import Participant

class FileParticipantRepository(ParticipantRepository):
    def __init__(self, validator, fileName):
        """
        validator - a ParticipantValidator object
        fileName - string
        """
        ParticipantRepository.__init__(self, validator)
        self.__fileName = fileName
        self.__loadFromFile()

    def __loadFromFile(self):
        """
        Loads all the participants from the file
        Raises FileNotFoundException if the file is not found
        Raises ValueError if there is an error reading from the file
        """
        with open(self.__fileName, "r") as file:
            for line in file:
                if line.strip() == "":
                    continue
                line = line.strip()
                attributes = line.split(";")
                participant = Participant(int(attributes[0]), int(attributes[1]))
                ParticipantRepository.save(self, participant)

    def __saveParticipantToFile(self, participant, file):
        """
        Adds a new participant to the file repository
        participant - a Participant object
        """
        participantString = str(participant.getPersonId()) + ";" + str(participant.getEventId())
        file.write("\n" + participantString)

    def __saveToFile(self):
        """
        Overwrites the initial file with the new participant list
        """
        with open(self.__fileName, "w") as file:
            participant = ParticipantRepository.findAll(self)
            for event in participant:
                self.__saveParticipantToFile(event, file)

    def save(self, participant):
        """
        Saves a new participant in the repository
        Returns the newly added participant
        """
        ParticipantRepository.save(self, participant)
        with open(self.__fileName, "a") as file:
            self.__saveParticipantToFile(participant, file)
        return participant

    def remove(self, id):
        """
        Removes a participant from the file repository by id
        id - integer number
        """
        participant = ParticipantRepository.remove(self, id)
        self.__saveToFile()
        return participant