class Person:
    """
    Person data type
    """
    def __init__(self, id, name, address):
        """
        Initializing a person with a given ID, name and address
        personID - number
        name, address - strings
        """
        self.__id = id
        self.__name = name
        self.__address = address

    def getId(self):
        """
        Get function for __personId
        """
        return self.__id

    def getName(self):
        """
        Get function for __name
        """
        return self.__name

    def getAddress(self):
        """
        Get function for __address
        """
        return self.__address

    def setId(self, id):
        """
        Set function for __id
        """
        self.__id = id

    def setName(self, name):
        """
        Set function for __name
        """
        self.__name = name

    def setAddress(self, address):
        """
        Set function for __address
        """
        self.__address = address

    def __str__(self):
        """
        Returns a string
        """
        return str(self.__id) + ". Name: " + self.__name + ", Address: " + self.__address