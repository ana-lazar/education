class EventDto:

    def __init__(self, id, description):
        self.__id = id
        self.__description = description
        self.__participantCount = 0

    def getId(self):
        """
        Get function for __id
        """
        return self.__id

    def incParticipantCount(self):
        """
        Inc function for __participantCount
        """
        self.__participantCount += 1

    def getDescription(self):
        """
        Get function for __description
        """
        return self.__description

    def getParticipantCount(self):
        """
        Get function for __participantCount
        """
        return self.__participantCount

    def __str__(self):
        """
        Returns a string
        """
        return str(self.__id) + " " + self.__description + " " + str(self.__participantCount)