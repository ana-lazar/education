from domain.exceptions import DuplicatedIdException, NonexistentIdException

class EventRepository:
    """
    Repository for events
    """
    def __init__(self, validator):
        self.__events = []
        self.__validator = validator

    def findAll(self):
        """
        Get function for the __events field
        """
        return self.__events[:]

    def find(self, id):
        """
        Finds a given event in the event list by its id
        id - integer number
        Returns an Event object
        """
        for event in self.__events:
            if event.getId() == id:
                return event
        return None

    def save(self, event):
        """
        Saves a new event to the repository
        """
        self.__validator.validate(event)
        for eventR in self.__events:
            if eventR.getId() == event.getId():
                raise DuplicatedIdException()
        self.__events.append(event)
        return event

    def update(self, newEvent):
        """
        Updates an event's fields
        event - an Event object
        Returns the updated event
        """
        for event in self.__events:
            if event.getId() == newEvent.getId():
                if newEvent.getDescription() != None:
                    event.setDescription(newEvent.getDescription())
                if newEvent.getDate() != None:
                    event.setDate(newEvent.getDate())
                if newEvent.getTime() != None:
                    event.setTime(newEvent.getTime())
                return event
        raise NonexistentIdException()

    def remove(self, id):
        """
        Removes an event from the repository
        id - string
        """
        for event in self.__events:
            if event.getId() == id:
                self.__events.remove(event)
                return event
        raise NonexistentIdException()

from domain.validators import EventValidator
from domain.Event import Event

def testSaveInvalid(eventRepository, event):
    try:
        eventRepository.save(event)
        assert False
    except ValueError:
        assert True

def testSave():
    """
    Test function for save
    """
    eventRepository = EventRepository(EventValidator())
    assert eventRepository.findAll() == []
    event = Event(123, "10 noiembrie", "12:00", "Zi de nastere")
    eventRepository.save(event)
    assert eventRepository.findAll() == [event]
    testSaveInvalid(eventRepository, Event(123, "11 noiembrie", "10:00", "Desc"))
    testSaveInvalid(eventRepository, Event(190, "", "10:00", "Desc"))
    testSaveInvalid(eventRepository, Event(163, "11 noiembrie", "", "Desc"))
    testSaveInvalid(eventRepository, Event(183, "11 noiembrie", "10:00", ""))

def testRemove():
    """
    Test function for remove
    """
    eventValidator = EventValidator()
    eventRepository = EventRepository(eventValidator)
    assert eventRepository.findAll() == []
    eventRepository.save(Event(99, "10 noiembrie", "7:00", "VOT"))
    eventRepository.save(Event(100, "11 noiembrie", "10:00", "Desc"))
    assert len(eventRepository.findAll()) == 2
    eventRepository.remove(99)
    assert len(eventRepository.findAll()) == 1
    try:
        eventRepository.remove(12)
        assert False
    except ValueError:
        assert True
    eventRepository.remove(100)
    assert len(eventRepository.findAll()) == 0

def testUpdate():
    """
    Test function for update
    """
    eventRepository = EventRepository(EventValidator())
    assert eventRepository.findAll() == []
    eventRepository.save(Event(123, "10 noiembrie", "12:00", "Zi de nastere"))
    eventRepository.save(Event(1, "15 noiembrie", "01:00", "Inmormantare"))
    eventRepository.save(Event(21, "1 decembrie", "00:00", "Ziua nationala"))
    try:
        eventRepository.update(Event(9, "9 noiembrie", "9:00", "Zi de nastere"))
        assert False
    except ValueError:
        assert True
    updatedEvent1 = eventRepository.update(Event(123, "5 decembrie", None, None))
    assert updatedEvent1.getDate() == "5 decembrie"
    assert updatedEvent1.getTime() == "12:00"
    assert updatedEvent1.getDescription() == "Zi de nastere"
    updatedEvent2 = eventRepository.update(Event(1, None, "11:00", None))
    assert updatedEvent2.getDate() == "15 noiembrie"
    assert updatedEvent2.getTime() == "11:00"
    assert updatedEvent2.getDescription() == "Inmormantare"
    updatedEvent3 = eventRepository.update(Event(21, None, "10:00", "Zi"))
    assert updatedEvent3.getDate() == "1 decembrie"
    assert updatedEvent3.getTime() == "10:00"
    assert updatedEvent3.getDescription() == "Zi"

def testFind():
    """
    Test function for find
    """
    eventRepository = EventRepository(EventValidator())
    event1 = Event(123, "10 noiembrie", "12:00", "Zi de nastere")
    event2 = Event(1, "15 noiembrie", "01:00", "Inmormantare")
    eventRepository.save(event1)
    eventRepository.save(event2)
    try:
        foundEvent = eventRepository.find(3)
        assert False
    except ValueError:
        assert True
    foundEvent = eventRepository.find(123)
    assert foundEvent == event1
    foundEvent = eventRepository.find(1)
    assert foundEvent == event2

# testSave()
# testRemove()
# testUpdate()
# testFind()