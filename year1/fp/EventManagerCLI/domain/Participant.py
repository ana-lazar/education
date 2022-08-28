import random

class Participant:
    def __init__(self, personId, eventId):
        self.__personId = personId
        self.__eventId = eventId

    def getId(self):
        """
        Get function for self.__participantId
        """
        return self.__participantId

    def getPersonId(self):
        """
        Get function for self.__personId
        """
        return self.__personId

    def getEventId(self):
        """
        Get function for self.__eventId
        """
        return self.__eventId

    def setId(self, id):
        """
        Set function for self.__participantId
        """
        self.__participantId = id

    def setPersonId(self, id):
        """
        Set function for self.__personId
        """
        self.__personId = id

    def setEventId(self, id):
        """
        Set function for self.__eventId
        """
        self.__eventId = id

    def __str__(self):
        """
        Returns a string
        """
        return "Person: " + str(self.__personId) + ", Event: " + str(self.__eventId)