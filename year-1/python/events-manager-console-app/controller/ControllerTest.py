import unittest
from controller.Controller import Controller
from repository.ParticipantRepository import ParticipantRepository
from repository.PersonRepository import PersonRepository
from repository.EventRepository import EventRepository
from domain.validators import PersonValidator, EventValidator, ParticipantValidator
from domain.exceptions import NonexistentIdException

class ControllerTest(unittest.TestCase):
    """
    Test class for findPersonsWithMostEvents, findPersonEvents, findEventsWithMostParticipants
    """
    def setUp(self):
        """
        Executed before every called method
        """
        personValidator = PersonValidator()
        personRepository = PersonRepository(personValidator)
        eventValidator = EventValidator()
        eventRepository = EventRepository(eventValidator)
        participantValidator = ParticipantValidator()
        participantRepository = ParticipantRepository(participantValidator)
        self.__controller = Controller(personRepository, eventRepository, participantRepository, personValidator, eventValidator, participantValidator)

    def addMultipleEntries(self):
        self.__controller.createPerson(1, "Andrei", "Nasaud 22")
        self.__controller.createPerson(2, "Ana", "Narciselor 20")
        self.__controller.createPerson(3, "Stefan", "Clinicilor 10")
        self.__controller.createPerson(4, "Patricia", "Victor Babes 5")
        self.__controller.createPerson(5, "Ioan", "Nicolae Draganu 45")
        self.__controller.createPerson(6, "Tudor", "Al. Vaida Voievod 29")
        self.__controller.createPerson(7, "Ioana", "Borsec 9")
        self.__controller.createPerson(8, "Maria", "Andrei Saguna 8")
        self.__controller.createPerson(9, "Dorin", "Brancusi 34")
        self.__controller.createPerson(10, "Larisa", "Dorobantilor 19")
        self.__controller.createEvent(1, "12 noiembrie", "10:00", "abc")
        self.__controller.createEvent(2, "1 decembrie", "9:00", "art")
        self.__controller.createEvent(3, "25 iunie", "12:00", "dgf")
        self.__controller.createEvent(4, "3 iulie", "11:30", "poo")
        self.__controller.createEvent(5, "17 mai", "10:40", "etr")
        self.__controller.createEvent(6, "8 octombrie", "3:40", "str")
        self.__controller.createEvent(7, "9 ianuarie", "10:45", "mnh")
        self.__controller.createEvent(8, "2 februarie", "11:00", "fge")
        self.__controller.createEvent(9, "23 martie", "7:00", "iyt")
        self.__controller.createEvent(10, "30 aprilie", "6:00", "ftr")
        self.__controller.createParticipant(1, 2)
        self.__controller.createParticipant(1, 3)
        self.__controller.createParticipant(1, 4)
        self.__controller.createParticipant(2, 2)
        self.__controller.createParticipant(3, 2)
        self.__controller.createParticipant(7, 6)
        self.__controller.createParticipant(9, 6)
        self.__controller.createParticipant(10, 5)
        self.__controller.createParticipant(9, 7)
        self.__controller.createParticipant(10, 4)
        self.__controller.createParticipant(8, 8)

    def testFindEventsWithMostParticipants(self):
        list = self.__controller.findEventsWithMostParticipants(20)
        self.assertEqual(list, [])
        self.addMultipleEntries()
        list = self.__controller.findEventsWithMostParticipants(20)
        self.assertEqual(len(list), 2)
        self.assertEqual(list[0].getParticipantCount(), 3)
        self.assertEqual(list[0].getDescription(), "art")
        self.assertEqual(list[1].getParticipantCount(), 2)
        self.assertEqual(list[1].getDescription(), "poo")

    def testFindPersonsWithMostEvents(self):
        list = self.__controller.findPersonsWithMostEvents()
        self.assertEqual(list, [])
        self.addMultipleEntries()
        list = self.__controller.findPersonsWithMostEvents()
        self.assertEqual(len(list), 1)
        self.assertEqual(list[0].getId(), 1)
        self.assertEqual(list[0].getName(), "Andrei")
        self.assertEqual(list[0].getNumEvents(), 3)
        self.__controller.createParticipant(9, 1)
        list = self.__controller.findPersonsWithMostEvents()
        self.assertEqual(len(list), 2)

    def testFindPersonEvents(self):
        self.assertRaises(NonexistentIdException, self.__controller.findPersonEvents, 10)
        self.addMultipleEntries()
        personEvents = self.__controller.findPersonEvents(1)
        self.assertEqual(personEvents.getPersonId(), 1)
        self.assertEqual(personEvents.getPersonName(), "Andrei")
        self.assertEqual(len(personEvents.getEvents()), 3)

test = ControllerTest()