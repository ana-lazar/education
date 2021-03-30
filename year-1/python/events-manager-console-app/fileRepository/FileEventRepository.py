from repository.EventRepository import EventRepository
from domain.Event import Event

class FileEventRepository(EventRepository):
    def __init__(self, validator, fileName):
        """
        validator - an EventValidator object
        fileName - string
        """
        EventRepository.__init__(self, validator)
        self.__fileName = fileName
        self.__loadFromFile()

    def __loadFromFile(self):
        """
        Loads all the events from the file
        Raises FileNotFoundException if the file is not found
        Raises ValueError if there is an error reading from the file
        """
        with open(self.__fileName, "r") as file:
            for line in file:
                if line.strip() == "":
                    continue
                line = line.strip()
                attributes = line.split(";")
                event = Event(int(attributes[0]), attributes[1], attributes[2], attributes[3])
                EventRepository.save(self, event)

    def __saveEventToFile(self, event, file):
        """
        Adds a new event to the file repository
        event - an Event object
        """
        eventString = str(event.getId()) + ";" + event.getDate() + ";" + event.getTime() + ";" + event.getDescription()
        file.write("\n" + eventString)

    def __saveToFile(self):
        """
        Overwrites the initial file with the new event list
        """
        with open(self.__fileName, "w") as file:
            events = EventRepository.findAll(self)
            for event in events:
                self.__saveEventToFile(event, file)

    def save(self, event):
        """
        Saves a new event in the repository
        Returns the newly added event
        """
        EventRepository.save(self, event)
        with open(self.__fileName, "a") as file:
            self.__saveEventToFile(event, file)
        return event

    def remove(self, id):
        """
        Removes an event from the file repository by id
        id - integer number
        """
        event = EventRepository.remove(self, id)
        self.__saveToFile()
        return event

    def update(self, newEvent):
        """
        Updates the file repository
        newEvent - an Event object
        """
        newEvent = EventRepository.update(self, newEvent)
        self.__saveToFile()
        return newEvent