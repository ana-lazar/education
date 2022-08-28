class PersonEventsDto:
    def __init__(self, personId, personName):
        """
        Initializes the EventsForPerson object
        personId - integer number
        personName - string
        """
        self.__personId = personId
        self.__personName = personName
        self.__events = []

    def getPersonId(self):
        """
        Get function for __personId
        """
        return self.__personId

    def getPersonName(self):
        """
        Get function for __personName
        """
        return self.__personName

    def getEvents(self):
        """
        Get function for __events
        """
        return self.__events

    def setEvents(self, events):
        """
        Set function for __events
        """
        self.__events = events

    def add(self, eventId, eventDescription, eventDate):
        """
        Adds a new event to the list
        eventId - integer number
        eventDescription - string
        """
        self.__events.append([eventId, eventDescription, eventDate])