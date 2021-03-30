from domain.exceptions import DuplicatedIdException, NonexistentIdException

class PersonRepository:
    """
    Repository for persons
    """
    def __init__(self, validator):
        self.__persons = []
        self.__validator = validator

    def findAll(self):
        """
        Get function for the __persons field
        """
        return self.__persons[:]

    """
    COMPLEXITATEA FUNCTIEI FIND
    
    Best case: persoana cautata este pe prima pozitie in lista - T(n) = 1 apartine lui Theta(1)
    Worst case: persoana cautata este pe ultima pozitie in lista - T(n) = n apartine lui Theta(n)
    Average case: persoana cautata poate fi pe oricare pozitie in lista - T(n) = (0 + 1 + 2 + ... + n - 1) / n apartine lui Theta(n)
    
    Overall complexity: T(n) apartine lui O(n)
    """
    def find(self, id):
        """
        Finds a given person in the event list by its id
        id - integer number
        Returns a Person object
        Raises NonexistentIdException if the id is not found
        """
        for person in self.__persons:
            if person.getId() == id:
                return person
        raise NonexistentIdException()

    def findRec(self, id, pos):
        """
        Finds a given person in the event list starting from the pos position
        id, pos - integer number
        Returns a Person object
        Raises NonexistentIdException if the id is not found
        """
        if pos > len(self.__persons) - 1:
            raise NonexistentIdException()
        if id == self.__persons[pos].getId():
            return self.__persons[pos]
        return self.findRec(id, pos + 1)

    def newFind(self, id):
        return self.findRec(id, 0)

    def save(self, person):
        """
        Saves a new person to the repository
        """
        self.__validator.validate(person)
        for personR in self.__persons:
            if personR.getId() == person.getId():
                raise DuplicatedIdException()
        self.__persons.append(person)
        return person

    def update(self, newPerson):
        """
        Updates a person's fields
        person - a Person object
        Returns the newly updated person
        """
        for person in self.__persons:
            if person.getId() == newPerson.getId():
                if newPerson.getName() != None:
                    person.setName(newPerson.getName())
                if newPerson.getAddress() != None:
                    person.setAddress(newPerson.getAddress())
                return person
        raise NonexistentIdException()

    def remove(self, id):
        """
        Removes a person from the repository
        id - string
        """
        for person in self.__persons:
            if person.getId() == id:
                self.__persons.remove(person)
                return person
        raise NonexistentIdException()

    def removeRec(self, id, pos):
        """
        Removes a person from the repository recursively
        id - string
        """
        if pos > len(self.__persons) - 1:
            raise NonexistentIdException()
        if id == self.__persons[pos].getId():
            person = self.__persons[pos]
            self.__persons.remove(person)
            return person
        return self.removeRec(id, pos + 1)

    def newRemove(self, id):
        return self.removeRec(id, 0)


from domain.validators import PersonValidator
from domain.Person import Person

def testSaveInvalid(personRepository, person):
    try:
        personRepository.save(person)
        assert False
    except ValueError:
        assert True

def testSave():
    """
    Test function for save
    """
    personValidator = PersonValidator()
    personRepository = PersonRepository(personValidator)
    assert personRepository.findAll() == []
    person = Person(25, "Ana", "Nasaud 22")
    personRepository.save(person)
    assert personRepository.findAll() == [person]
    testSaveInvalid(personRepository, Person(25, "Ion", "Napoca 6"))
    testSaveInvalid(personRepository, Person(9, "", "Napoca 6"))
    testSaveInvalid(personRepository, Person(20, "Ion", ""))

def testRemove():
    """
    Test function for remove
    """
    personValidator = PersonValidator()
    personRepository = PersonRepository(personValidator)
    assert personRepository.findAll() == []
    personRepository.save(Person(25, "Ana", "Nasaud 22"))
    personRepository.save(Person(20, "Ion", "Napoca 6"))
    assert len(personRepository.findAll()) == 2
    personRepository.newRemove(25)
    assert len(personRepository.findAll()) == 1
    try:
        personRepository.newRemove(12)
        assert False
    except NonexistentIdException:
        assert True
    personRepository.newRemove(20)
    assert len(personRepository.findAll()) == 0

def testUpdate():
    """
    Test function for update
    """
    personRepository = PersonRepository(PersonValidator())
    assert personRepository.findAll() == []
    personRepository.save(Person(1, "Ana", "Nasaud 22"))
    personRepository.save(Person(12, "Patricia", "Clujana 21"))
    personRepository.save(Person(90, "Ion", "Albac 16"))
    try:
        personRepository.update(Person(9, "Bianca", "Turda"))
        assert False
    except ValueError:
        assert True
    updatedPerson1 = personRepository.update(Person(1, "Dana", None))
    assert updatedPerson1.getName() == "Dana"
    assert updatedPerson1.getAddress() == "Nasaud 22"
    updatedPerson2 = personRepository.update(Person(12, None, "Turda 20"))
    assert updatedPerson2.getName() == "Patricia"
    assert updatedPerson2.getAddress() == "Turda 20"
    updatedPerson3 = personRepository.update(Person(90, "Flavia", "Nicolae Draganu 26"))
    assert updatedPerson3.getName() == "Flavia"
    assert updatedPerson3.getAddress() == "Nicolae Draganu 26"

def testFind():
    """
    Test function for find
    """
    personRepository = PersonRepository(PersonValidator())
    person1 = Person(1, "Ana", "Nasaud 22")
    person2 = Person(90, "Ion", "Albac 16")
    personRepository.save(person1)
    personRepository.save(person2)
    try:
        foundPerson = personRepository.newFind(3)
        assert False
    except NonexistentIdException:
        assert True
    foundPerson = personRepository.newFind(1)
    assert foundPerson == person1
    foundPerson = personRepository.newFind(90)
    assert foundPerson == person2

# testSave()
testRemove()
# testUpdate()
testFind()