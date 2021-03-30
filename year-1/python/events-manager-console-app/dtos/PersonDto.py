class PersonDto:

    def __init__(self, id, name):
        self.__id = id
        self.__name = name
        self.__numEvents = 1

    def getId(self):
        """
        Get function for __id
        """
        return self.__id

    def getName(self):
        """
        Get function for __name
        """
        return self.__name

    def getNumEvents(self):
        """
        Get function for __numEvents
        """
        return self.__numEvents

    def addEvent(self):
        """
        Increments the number of events
        """
        self.__numEvents += 1