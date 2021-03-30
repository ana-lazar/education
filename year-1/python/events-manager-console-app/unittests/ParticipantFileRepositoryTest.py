import unittest
from domain.Participant import Participant
from domain.validators import ParticipantValidator
from fileRepository.FileParticipantRepository import FileParticipantRepository
from pathlib import Path


class ParticipantFileRepositoryTest(unittest.TestCase):
    def setUp(self):
        self.__fileText = "1;1\n1;2\n1;4\n2;1\n3;7\n7;10\n4;8\n9;3\n10;6\n10;5"
        self.__participantFileRepository = FileParticipantRepository(ParticipantValidator(), "participantRepositoryTest.txt")

    def tearDown(self):
        with open("participantRepositoryTest.txt", "w") as file:
            file.write(self.__fileText)

    def testSave(self):
        """
        Test function for save
        """
        self.assertEqual(len(self.__participantFileRepository.findAll()), 10)
        self.__participantFileRepository.save(Participant(1, 10))
        self.assertEqual(len(self.__participantFileRepository.findAll()), 11)
        self.__participantFileRepository.save(Participant(1, 1))
        self.assertEqual(len(self.__participantFileRepository.findAll()), 11)

    def testRemove(self):
        """
        Test function for remove
        """
        self.assertEqual(len(self.__participantFileRepository.findAll()), 10)
        self.__participantFileRepository.remove(1)
        self.assertEqual(len(self.__participantFileRepository.findAll()), 9)
        self.__participantFileRepository.remove(2)
        self.__participantFileRepository.remove(3)
        self.assertEqual(len(self.__participantFileRepository.findAll()), 7)


if __name__ == "__main__":
    unittest.main()
