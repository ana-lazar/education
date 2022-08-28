import unittest
from domain.Event import Event
from domain.validators import EventValidator
from domain.exceptions import RepositoryException, ValidationException
from fileRepository.FileEventRepository import FileEventRepository
from pathlib import Path


class EventFileRepositoryTest(unittest.TestCase):

    def setUp(self):
        self.__fileText = "1;12 noiembrie;10:00;abc\n2;1 decembrie;9:00;art\n3;25 iunie;12:00;dgf\n4;12 noiembrie;11:30;poo\n5;17 mai;10:40;etr\n6;12 noiembrie;3:40;str\n7;9 ianuarie;10:45;mnh\n8;2 februarie;11:00;fge\n9;23 martie;7:00;iyt\n10;30 aprilie;6:00;ftr"
        self.__eventFileRepository = FileEventRepository(EventValidator(), "eventRepositoryTest.txt")

    def tearDown(self):
        with open("eventRepositoryTest.txt", "w") as file:
            file.write(self.__fileText)

    def testSave(self):
        """
        Test function for save
        """
        self.assertEqual(len(self.__eventFileRepository.findAll()), 10)
        event = Event(123, "10 noiembrie", "12:00", "Zi de nastere")
        self.__eventFileRepository.save(event)
        self.assertEqual(self.__eventFileRepository.find(123), event)
        self.assertRaises(RepositoryException, self.__eventFileRepository.save,
                          Event(1, "11 noiembrie", "10:00", "Desc"))
        self.assertRaises(ValidationException, self.__eventFileRepository.save, Event(11, "decembrie", "10:00", "Desc"))
        self.assertRaises(ValidationException, self.__eventFileRepository.save, Event(12, "11 noiembrie", "", "Desc"))
        self.assertRaises(ValidationException, self.__eventFileRepository.save, Event(13, "11 noiembrie", "10:00", ""))

    def testRemove(self):
        """
        Test function for remove
        """
        self.assertEqual(len(self.__eventFileRepository.findAll()), 10)
        self.__eventFileRepository.remove(1)
        self.assertEqual(len(self.__eventFileRepository.findAll()), 9)
        self.assertRaises(RepositoryException, self.__eventFileRepository.remove, 100)
        self.__eventFileRepository.remove(2)
        self.__eventFileRepository.remove(3)
        self.assertEqual(len(self.__eventFileRepository.findAll()), 7)

    def testUpdate(self):
        """
        Test function for update
        """
        self.assertEqual(len(self.__eventFileRepository.findAll()), 10)
        event = Event(123, "10 noiembrie", "12:00", "Zi de nastere")
        self.assertRaises(RepositoryException, self.__eventFileRepository.update, event)
        updatedEvent = self.__eventFileRepository.update(Event(1, "5 decembrie", None, None))
        self.assertEqual(updatedEvent.getDate(), "5 decembrie")
        self.assertEqual(updatedEvent.getTime(), "10:00")
        self.assertEqual(updatedEvent.getDescription(), "abc")
        updatedEvent = self.__eventFileRepository.update(Event(2, None, "11:00", None))
        self.assertEqual(updatedEvent.getDate(), "1 decembrie")
        self.assertEqual(updatedEvent.getTime(), "11:00")
        self.assertEqual(updatedEvent.getDescription(), "art")
        updatedEvent = self.__eventFileRepository.update(Event(3, None, "10:00", "Zi"))
        self.assertEqual(updatedEvent.getDate(), "25 iunie")
        self.assertEqual(updatedEvent.getTime(), "10:00")
        self.assertEqual(updatedEvent.getDescription(), "Zi")


if __name__ == "__main__":
    unittest.main()
