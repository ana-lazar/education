class CrudException(Exception):
    pass

class InvalidIdException(Exception):
    def __init__(self):
        Exception.__init__(self, "invalid ID")

class ValidationException(CrudException):
    def __init__(self, errors):
        """
        Initializes the Exception
        errors - string
        """
        CrudException.__init__(self)
        self.__errors = errors

    def getMessage(self):
        """
        Returns a string
        """
        return self.__errors

    def __str__(self):
        """
        Returns a string
        """
        return self.__errors

class RepositoryException(CrudException):
    def __init__(self, message):
        """
        Initializes the Exception
        message - string
        """
        CrudException.__init__(self)
        self.__message = message

    def getMessage(self):
        """
        Returns a string
        """
        return self.__message

    def __str__(self):
        """
        Returns a string
        """
        return self.__message

class DuplicatedIdException(RepositoryException):
    def __init__(self):
        RepositoryException.__init__(self, "Duplicated ID")

class NonexistentIdException(RepositoryException):
    def __init__(self):
        RepositoryException.__init__(self, "ID not found")

class FileException(CrudException):
    def __init__(self, message):
        """
        Initializes the Exception
        message - string
        """
        CrudException.__init__(self)
        self.__message = message

    def getMessage(self):
        """
        Returns a string
        """
        return self.__message

    def __str__(self):
        """
        Returns a string
        """
        return self.__message

class FileNotFoundException(FileException):
    def __init__(self):
        FileException.__init__(self, "File not found")