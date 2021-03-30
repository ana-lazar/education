class Event:
    """
    Event data type
    """
    def __init__(self, id, date, time, description):
        """
        Initializing an event with a given ID, date, time and description
        eventID - number
        description - string
        """
        self.__id = id
        self.__date = date
        self.__time = time
        self.__description = description

    def getId(self):
        """
        Get function for __id
        """
        return self.__id

    def getDate(self):
        """
        Get function for __date
        """
        return self.__date

    def getTime(self):
        """
        Get function for __time
        """
        return self.__time

    def getDescription(self):
        """
        Get function for __description
        """
        return self.__description

    def setId(self, id):
        """
        Set function for __id
        """
        self.__id = id

    def setDate(self, date):
        """
        Set function for __date
        """
        self.__date = date

    def setTime(self, time):
        """
        Set function for __time
        """
        self.__time = time

    def setDescription(self, description):
        """
        Set function for __description
        """
        self.__description = description

    def __str__(self):
        """
        Returns a string
        """
        return str(self.__id) + ". Date: " + self.__date + ", Time: " + self.__time + ", Description: " + self.__description