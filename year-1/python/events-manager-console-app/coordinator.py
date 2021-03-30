from repository.PersonRepository import PersonRepository
from repository.EventRepository import EventRepository
from repository.ParticipantRepository import ParticipantRepository
from fileRepository.FilePersonRepository import FilePersonRepository
from fileRepository.FileEventRepository import FileEventRepository
from fileRepository.FileParticipantRepository import FileParticipantRepository
from controller.Controller import Controller
from ui.Ui import Ui
from domain.validators import PersonValidator, EventValidator, ParticipantValidator
from domain.Person import Person
from domain.Event import Event
from domain.Participant import Participant

def addSomePersons(personRepository):
    personRepository.save(Person(1, "Andrei", "Nasaud 22"))
    personRepository.save(Person(2, "Ana", "Narciselor 20"))
    personRepository.save(Person(3, "Stefan", "Clinicilor 10"))
    personRepository.save(Person(4, "Patricia", "Victor Babes 5"))
    personRepository.save(Person(5, "Ioan", "Nicolae Draganu 45"))
    personRepository.save(Person(6, "Tudor", "Al. Vaida Voievod 29"))
    personRepository.save(Person(7, "Ioana", "Borsec 9"))
    personRepository.save(Person(8, "Maria", "Andrei Saguna 8"))
    personRepository.save(Person(9, "Dorin", "Brancusi 34"))
    personRepository.save(Person(10, "Larisa", "Dorobantilor 19"))

def addSomeEvents(eventRepository):
    eventRepository.save(Event(1, "12 noiembrie", "10:00", "abc"))
    eventRepository.save(Event(2, "1 decembrie", "9:00", "art"))
    eventRepository.save(Event(3, "25 iunie", "12:00", "dgf"))
    eventRepository.save(Event(4, "12 noiembrie", "11:30", "poo"))
    eventRepository.save(Event(5, "17 mai", "10:40", "etr"))
    eventRepository.save(Event(6, "12 noiembrie", "3:40", "str"))
    eventRepository.save(Event(7, "9 ianuarie", "10:45", "mnh"))
    eventRepository.save(Event(8, "2 februarie", "11:00", "fge"))
    eventRepository.save(Event(9, "23 martie", "7:00", "iyt"))
    eventRepository.save(Event(10, "30 aprilie", "6:00", "ftr"))

def addSomeParticipants(participantRepository):
    participantRepository.save(Participant(1, 1))
    participantRepository.save(Participant(1, 2))
    participantRepository.save(Participant(1, 4))
    participantRepository.save(Participant(2, 1))
    participantRepository.save(Participant(3, 7))
    participantRepository.save(Participant(7, 10))
    participantRepository.save(Participant(4, 8))
    participantRepository.save(Participant(9, 3))
    participantRepository.save(Participant(10, 6))
    participantRepository.save(Participant(10, 5))

def main():
    """
    Creates all the needed data types and the bonds between them
    """
    personValidator = PersonValidator()
    filePersonRepository = FilePersonRepository(personValidator, "files/personRepository.txt")
    personRepository = PersonRepository(personValidator)
    addSomePersons(personRepository)
    eventValidator = EventValidator()
    fileEventRepository = FileEventRepository(eventValidator, "files/eventRepository.txt")
    eventRepository = EventRepository(eventValidator)
    addSomeEvents(eventRepository)
    participantValidator = ParticipantValidator()
    fileParticipantRepository = FileParticipantRepository(participantValidator, "files/participantRepository.txt")
    participantRepository = ParticipantRepository(participantValidator)
    addSomeParticipants(participantRepository)
    controller = Controller(filePersonRepository, fileEventRepository, fileParticipantRepository, personValidator, eventValidator, participantValidator)
    ui = Ui(controller)
    ui.start()

main()