from repository.PersonRepository import PersonRepository
from domain.Person import Person

# GOLIRE FISIER: with open(file, "w") as f: pass
# poti mosteni mai multe clase

class FilePersonRepository(PersonRepository):
    def __init__(self, validator, fileName):
        """
        validator - a PersonValidator object
        fileName - string
        """
        PersonRepository.__init__(self, validator)
        self.__fileName = fileName
        self.__loadFromFile()

    def __loadFromFile(self):
        """
        Loads all the persons from the file
        Raises FileNotFoundException if the file is not found
        Raises ValueError if there is an error reading from the file
        """
        with open(self.__fileName, "r") as file:
            for line in file:
                if line.strip() == "":
                    continue
                line = line.strip()
                attributes = line.split(";")
                person = Person(int(attributes[0]), attributes[1], attributes[2])
                PersonRepository.save(self, person)

    def __savePersonToFile(self, person, file):
        """
        Adds a new person to the file repository
        person - a Person object
        """
        personString = str(person.getId()) + ";" + person.getName() + ";" + person.getAddress()
        file.write("\n" + personString)

    def __saveToFile(self):
        """
        Overwrites the initial file with the new person list
        """
        with open(self.__fileName, "w") as file:
            persons = PersonRepository.findAll(self)
            for person in persons:
                self.__savePersonToFile(person, file)

    def save(self, person):
        """
        Saves a new person in the repository
        Returns the newly added person
        """
        PersonRepository.save(self, person)
        with open(self.__fileName, "a") as file:
            self.__savePersonToFile(person, file)
        return person

    def remove(self, id):
        """
        Removes a person from the file repository by id
        id - integer number
        """
        person = PersonRepository.remove(self, id)
        self.__saveToFile()
        return person

    def update(self, newPerson):
        """
        Updates the file repository
        newPerson - a Person object
        """
        newPerson = PersonRepository.update(self, newPerson)
        self.__saveToFile()
        return newPerson