from functools import cmp_to_key

from domain.Person import Person
from domain.Event import Event
from domain.Participant import Participant
from dtos.PersonEventsDto import PersonEventsDto
from dtos.PersonDto import PersonDto
from dtos.EventDto import EventDto
from dtos.PersonEventsDateDto import PersonEventsDateDto
from domain.mySort import mySelectionSort, myShakeSort

class Controller:
    """
    Calls the services needed for certain operations on the repositories
    """
    def __init__(self, personRepository, eventRepository, participantRepository, personValidator, eventValidator, participantValidator):
        self.__personRepository = personRepository
        self.__eventRepository = eventRepository
        self.__participantRepository = participantRepository
        self.__personValidator = personValidator
        self.__eventValidator = eventValidator
        self.__participantValidator = participantValidator

    def createPerson(self, id, name, address):
        """
        Adds a new person to the repository
        person - a Person
        Returns the newly added person's details
        Raises ValueError if the person is not valid
        """
        person = Person(id, name, address)
        self.__personValidator.validate(person)
        return self.__personRepository.save(person)

    def createEvent(self, id, date, time, description):
        """
        Adds a new event to the repository
        event - an Event
        Returns the newly added event's details
        Raises ValueError if the event is not valid
        """
        event = Event(id, date, time, description)
        self.__eventValidator.validate(event)
        return self.__eventRepository.save(event)

    def createParticipant(self, personId, eventId):
        """
        Adds a new participant to the repository
        personId, eventId - string
        Returns the newly added event's details
        Raises ValueError if the participant is not valid
        """
        participant = Participant(personId, eventId)
        self.__participantValidator.validate(participant, self.__personRepository, self.__eventRepository)
        self.__participantRepository.save(participant)
        return participant

    def getPersons(self):
        """
        Returns all the persons stored in the repository
        """
        return self.__personRepository.findAll()

    def getEvents(self):
        """
        Returns all the events stored in the repository
        """
        return self.__eventRepository.findAll()

    def getParticipants(self):
        """
        Returns all the events stored in the repository
        """
        return self.__participantRepository.findAll()

    def deletePerson(self, id):
        """
        Deletes a person from the repository
        id - string
        """
        return self.__personRepository.newRemove(id)

    def deleteEvent(self, id):
        """
        Deletes an event from the repository
        id - string
        """
        return self.__eventRepository.remove(id)

    def deleteParticipant(self, number):
        """
        Deletes a participant from the repository
        id - string
        """
        return self.__participantRepository.remove(number)

    def updatePerson(self, id, name, address):
        """
        Updates a person from the repository
        id, address - string
        """
        newPerson = self.__personRepository.update(Person(id, name, address))
        self.__personValidator.validate(newPerson)
        return newPerson

    def updateEvent(self, id, date, time, description):
        """
        Updates an event from the repository
        id, address - string
        """
        newEvent = self.__eventRepository.update(Event(id, date, time, description))
        self.__eventValidator.validate(newEvent)
        return newEvent

    def searchForEvent(self, id):
        """
        Searches for an event in the repository
        id - string
        """
        return self.__eventRepository.find(id)

    def searchForPerson(self, id):
        """
        Searches for an event in the repository
        id - string
        """
        return self.__personRepository.newFind(id)

    def findPersonEvents(self, personId):
        """
        Gets all the events that a person participates in in alphabetical order
        personId - integer number
        Returns a list
        """
        participants = self.__participantRepository.findAll()
        personName = self.__personRepository.find(personId).getName()
        personEvents = PersonEventsDto(personId, personName)
        for participant in participants:
            if participant.getPersonId() == personId:
                event = self.__eventRepository.find(participant.getEventId())
                personEvents.add(event.getId(), event.getDescription(), event.getDate())
        eventList = personEvents.getEvents()
        eventList = mySelectionSort(eventList, cmp = compareByDescriptionAndDate)
        personEvents.setEvents(eventList)
        return personEvents

    def findPersonsWithMostEvents(self):
        """
        Gets the persons participating in most events
        Returns a list
        """
        participants = self.__participantRepository.findAll()
        personsDict = {}
        for participant in participants:
            id = participant.getPersonId()
            if id in personsDict:
                personsDict[id].addEvent()
            else:
                name = self.__personRepository.find(id).getName()
                personsDict[id] = PersonDto(id, name)
        greatestPersons = []
        greatest = 0
        for personId in personsDict:
            person = personsDict[personId]
            if person.getNumEvents() > greatest:
                greatest = person.getNumEvents()
                greatestPersons = [person]
            elif person.getNumEvents() == greatest:
                greatestPersons.append(person)
        return greatestPersons

    def findEventsWithMostParticipants(self, percentage):
        """
        Finds a certain percentage of events with most participants
        """
        events = self.__eventRepository.findAll()
        eventDtoDict = {}
        for event in events:
            eventDtoDict[event.getId()] = EventDto(event.getId(), event.getDescription())
        participants = self.__participantRepository.findAll()
        for participant in participants:
            eventDtoDict[participant.getEventId()].incParticipantCount()
        eventDtos = list(eventDtoDict.values())
        # eventDtos.sort(key=lambda eventDto: -eventDto.getParticipantCount())
        eventDtos = myShakeSort(eventDtos, key=lambda eventDto: eventDto.getParticipantCount(), reversed = True)
        if len(eventDtos) * percentage / 100 < 1:
            return eventDtos[:int(len(eventDtos) * percentage/100) + 1]
        return eventDtos[:int(len(eventDtos) * percentage/100)]

    # a.sort(key=lambda ass: ass[0])
    # a = sorted(a, key=cmp_to_key(fnc))

    def findPersonsWithEventsDate(self, date):
        """
        Find persons with events of a given date
        date - string
        """
        participants = self.__participantRepository.findAll()
        persons = {}
        for participant in participants:
            event = self.__eventRepository.find(participant.getEventId())
            if event.getDate() == date:
                if participant.getPersonId() in persons:
                    personEventsDate.incEventCount()
                else:
                    person = self.__personRepository.find(participant.getPersonId())
                    personEventsDate = PersonEventsDateDto(person.getId(), person.getName())
                    persons[person.getId()] = personEventsDate
        if persons == {}:
            return None
        persons = list(persons.values())
        # persons.sort(key=lambda personEventsDate: -personEventsDate.getEventCount())
        persons = mySelectionSort(persons, key = lambda personEventsDate: personEventsDate.getEventCount(), reversed = True)
        return persons


def compareByDescriptionAndDate(el1, el2):
    if el1[1] < el2[1]:
        return -1
    elif el1[1] > el2[1]:
        return 1
    else:
        date1 = el1[2].split(" ")
        date2 = el2[2].split(" ")
        monthDict = {"ianuarie": 1, "februarie": 2, "martie": 3, "aprilie": 4, "mai": 5, "iunie": 6, "iulie": 7, "august": 8, "septembrie": 9, "octombrie": 10, "noiembrie": 11, "decembrie": 12}
        if monthDict[date1[1]] < monthDict[date2[1]]:
            return -1
        elif monthDict[date1[1]] > monthDict[date2[1]]:
            return 1
        else:
            return int(date1[0]) - int(date2[0])