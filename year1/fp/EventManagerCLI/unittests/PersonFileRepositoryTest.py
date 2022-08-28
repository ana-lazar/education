import unittest
from domain.Person import Person
from domain.validators import PersonValidator
from domain.exceptions import RepositoryException, ValidationException
from fileRepository.FilePersonRepository import FilePersonRepository
from pathlib import Path


class PersonFileRepositoryTest(unittest.TestCase):

    def setUp(self):
        self.__fileText = "1;Andrei;Nasaud 22\n2;Ana;Narciselor 20\n3;Stefan;Clinicilor 10\n4;Patricia;Victor Babes 5\n5;Ioan;Nicolae Draganu 45\n6;Tudor;Al. Vaida Voievod 29\n7;Ioana;Borsec 9\n8;Maria;Andrei Saguna 8\n9;Dorin;Brancusi 34\n10;Larisa;Dorobantilor 19"
        with open("personRepositoryTest.txt", "w") as file:
            file.write(self.__fileText)
        self.__personFileRepository = FilePersonRepository(PersonValidator(), "personRepositoryTest.txt")

    def tearDown(self):
        with open("personRepositoryTest.txt", "w") as file:
            file.write(self.__fileText)

    def testSave(self):
        """
        Test function for save
        """
        self.assertEqual(len(self.__personFileRepository.findAll()), 10)
        person = Person(123, "Ion", "Brates 6")
        self.__personFileRepository.save(person)
        self.assertEqual(self.__personFileRepository.find(123), person)
        self.assertRaises(RepositoryException, self.__personFileRepository.save, Person(123, "Ioana", "Brates 6"))
        self.assertRaises(ValidationException, self.__personFileRepository.save, Person(11, "", "Brates 6"))
        self.assertRaises(ValidationException, self.__personFileRepository.save, Person(12, "Ioana", "Brates"))
        self.assertRaises(ValidationException, self.__personFileRepository.save, Person(13, "Ioana", ""))

    def testRemove(self):
        """
        Test function for remove
        """
        self.assertEqual(len(self.__personFileRepository.findAll()), 10)
        self.__personFileRepository.remove(1)
        self.assertEqual(len(self.__personFileRepository.findAll()), 9)
        self.assertRaises(RepositoryException, self.__personFileRepository.remove, 100)
        self.__personFileRepository.remove(2)
        self.__personFileRepository.remove(3)
        self.assertEqual(len(self.__personFileRepository.findAll()), 7)

    def testUpdate(self):
        """
        Test function for update
        """
        self.assertEqual(len(self.__personFileRepository.findAll()), 10)
        person = Person(123, "Ion", "Brates 6")
        self.assertRaises(RepositoryException, self.__personFileRepository.update, person)
        updatedPerson = self.__personFileRepository.update(Person(1, "Dana", None))
        self.assertEqual(updatedPerson.getName(), "Dana")
        self.assertEqual(updatedPerson.getAddress(), "Nasaud 22")
        updatedPerson = self.__personFileRepository.update(Person(2, None, "Brates 7"))
        self.assertEqual(updatedPerson.getName(), "Ana")
        self.assertEqual(updatedPerson.getAddress(), "Brates 7")
        updatedPerson = self.__personFileRepository.update(Person(3, "Ionel", "Mugurilor 5"))
        self.assertEqual(updatedPerson.getName(), "Ionel")
        self.assertEqual(updatedPerson.getAddress(), "Mugurilor 5")


if __name__ == "__main__":
    unittest.main()
