class PersonEventsDateDto:
    def __init__(self, personId, personName):
        self.__personId = personId
        self.__personName = personName
        self.__eventCount = 1

    def incEventCount(self):
        self.__eventCount += 1

    def getEventCount(self):
        return self.__eventCount

    def getPersonName(self):
        return self.__personName

    def getPersonId(self):
        return self.__personId