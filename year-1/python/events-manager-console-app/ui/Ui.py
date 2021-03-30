from domain.exceptions import CrudException, InvalidIdException

class Ui:
    """
    Defines the User Interface
    """
    def __init__(self, controller):
        self.__controller = controller
        
    def ensureInt(self, arg):
        """
        Raises ValueError if the arg is not a numeric value
        arg - string
        """
        try:
            return int(arg)
        except ValueError:
            raise InvalidIdException()

    def printPersonList(self):
        """
        Prints the person list
        """
        persons = self.__controller.getPersons()
        print("\nPersons: ")
        for person in persons:
            print(person)

    def createPerson(self):
        """
        Collects all the data needed to create a person
        Returns the newly created person
        """
        id = self.ensureInt(input("Person ID: "))
        if id < 1:
            raise InvalidIdException()
        name = input("Person name: ").strip()
        address = input("Person address: ").strip()
        return "Created person: " + str(self.__controller.createPerson(id, name, address))

    def updatePerson(self):
        """
        Collects the data needed to update a person's details
        Returns the newly updated person
        """
        id = self.ensureInt(input("Person ID: "))
        if id < 1:
            raise InvalidIdException()
        option = input("Update name? Y/N \n")
        if option == "Y":
            name = input("New set name is: ")
        else:
            name = None
        option = input("Update address? Y/N \n")
        if option == "Y":
            address = input("New set address is: ")
        else:
            address = None
        return "Updated person: " + str(self.__controller.updatePerson(id, name, address))

    def deletePerson(self):
        """
        Collects the person to be deleted's data
        Returns the person deleted or an error message if they are not part of the repository
        """
        id = self.ensureInt(input("Person ID: "))
        if id < 1:
            raise InvalidIdException()
        return "Deleted person: " + str(self.__controller.deletePerson(id))

    def searchForPerson(self):
        """
        Collects the data for the search person function
        Returns the searched for person
        """
        id = self.ensureInt(input("Person ID: "))
        if id < 1:
            raise InvalidIdException()
        return "Searched for person: " + str(self.__controller.searchForPerson(id))

    def managePersons(self):
        """
        Redirects all the options available for persons to the specific run function
        """
        while True:
            self.printPersonList()
            print("\nAvailable options: Create, Update, Delete, Search, Return")
            option = input("Choose option: ")
            try:
                if option == "C":
                    print(self.createPerson())
                elif option == "U":
                    print(self.updatePerson())
                elif option == "D":
                    print(self.deletePerson())
                elif option == "S":
                    print(self.searchForPerson())
                elif option == "R":
                    return
                else:
                    print("Invalid option")
            except CrudException as err:
                print(str(err))
            except InvalidIdException as err:
                print(str(err))

    def printEventList(self):
        """
        Prints the event list
        """
        print("\nEvents:")
        events = self.__controller.getEvents()
        for event in events:
            print(event)

    def createEvent(self):
        """
        Collects all the data needed to create an event
        Returns the newly created event
        """
        id = self.ensureInt(input("Event ID: "))
        if id < 1:
            raise InvalidIdException()
        date = input("Event date: ").strip()
        time = input("Event time: ").strip()
        description = input("Event description: ").strip()
        return "Created event: " + str(self.__controller.createEvent(id, date, time, description))

    def updateEvent(self):
        """
        Collects the dataa needed to update a person's details
        Returns the newly updated person
        """
        id = self.ensureInt(input("Event ID: "))
        if id < 1:
            raise InvalidIdException()
        option = input("Update date? Y/N \n")
        if option == "Y":
            date = input("New set date: ")
        else:
            date = None
        option = input("Update time? Y/N \n")
        if option == "Y":
            time = input("New set time: ")
        else:
            time = None
        option = input("Update description? Y/N \n")
        if option == "Y":
            description = input("New set description: ")
        else:
            description = None
        return "Updated event: " + str(self.__controller.updateEvent(id, date, time, description))

    def deleteEvent(self):
        """
        Collects the event to be deleted's data
        Returns the event deleted or an error message if it is not part of the repository
        """
        id = self.ensureInt(input("Event ID: "))
        if id < 1:
            raise InvalidIdException()
        return "Deleted events: " + str(self.__controller.deleteEvent(id))

    def searchForEvent(self):
        """
        Collects the data for the search event function
        Returns the searched for event
        """
        id = self.ensureInt(input("Event ID: "))
        if id < 1:
            raise InvalidIdException()
        return "Searched for event: " + str(self.__controller.searchForEvent(id))

    def manageEvents(self):
        """
        Redirects all the options available for events to the specific run function
        """
        while True:
            self.printEventList()
            print("\nAvailable options: Create, Update, Delete, Search, Return")
            option = input("Choose option: ")
            try:
                if option == "C":
                    print(self.createEvent())
                elif option == "U":
                    print(self.updateEvent())
                elif option == "D":
                    print(self.deleteEvent())
                elif option == "S":
                    print(self.searchForEvent())
                elif option == "R":
                    return
                else:
                    print("Invalid option")
            except CrudException as err:
                print(str(err))
            except InvalidIdException as err:
                print(str(err))

    def printParticipantList(self):
        """
        Prints the participant list
        """
        print("\nParticipants:")
        participants = self.__controller.getParticipants()
        no = 0
        for participants in participants:
            no += 1
            print(no, participants)

    def createParticipant(self):
        """
        Collects all the data needed to create a participant
        Returns the newly created participant
        """
        persons = self.__controller.getPersons()
        self.printPersonList()
        personId = self.ensureInt(input("Person ID: "))
        self.printEventList()
        eventId = self.ensureInt(input("Event ID: "))
        return "Created participant: " + str(self.__controller.createParticipant(personId, eventId))

    def deleteParticipant(self):
        """
        Collects the participant to be deleted's data
        Returns the participant deleted or an error message if it is not part of the repository
        """
        number = self.ensureInt(input("Participant number: ")) - 1
        if number < 0 or number >= len(self.__controller.getParticipants()):
            raise InvalidIdException()
        return "Deleted participant: " + str(self.__controller.deleteParticipant(number))

    def manageParticipants(self):
        """
        Redirects all the options available for participants to the specific run function
        """
        while True:
            self.printParticipantList()
            print("\nAvailable options: Create, Delete, Return")
            option = input("Choose option: ")
            try:
                if option == "C":
                    print(self.createParticipant())
                elif option == "D":
                    print(self.deleteParticipant())
                elif option == "R":
                    return
                else:
                    print("Invalid option")
            except CrudException as err:
                print(str(err))
            except InvalidIdException as err:
                print(str(err))

    def showPersonEvents(self):
        """
        Finds all the events that a person participates in in alphabetical order
        Returns a string containing all the events
        """
        self.printPersonList()
        personId = self.ensureInt(input("Person ID: "))
        personEvents = self.__controller.findPersonEvents(personId)
        events = personEvents.getEvents()
        print("Events that " + personEvents.getPersonName() + " participates in: ")
        for event in events:
            print(event[0], event[1], event[2])

    def showPersonsWithMostEvents(self):
        """
        Finds the persons participating in most events
        Returns a string
        """
        persons = self.__controller.findPersonsWithMostEvents()
        printedPersons = ""
        for person in persons:
            printedPersons += "\n" + str(person.getId()) + ". " + person.getName()
        if not persons:
            print("There are no participants at the moment")
            return
        print("The persons participating in most events (" + str(person.getNumEvents()) + ") are: " + printedPersons)

    def showEventsWithMostParticipants(self, percentage):
        """
        Prints events with most participants
        percentage - integer number
        """
        eventsInfo = self.__controller.findEventsWithMostParticipants(percentage)
        print("The first", percentage, "% events are:")
        for eventInfo in eventsInfo:
            print(eventInfo.getDescription(), ":", eventInfo.getParticipantCount(), "participant")

    def showPersonsWithEventsDate(self):
        """
        Prints the persons that participate in events of a given date
        """
        date = input("Event date: ")
        personsInfo = self.__controller.findPersonsWithEventsDate(date)
        print("Persons that participate in events on " + date + ":")
        if personsInfo == None:
            return
        for personInfo in personsInfo:
            print(personInfo.getPersonName(), "has", personInfo.getEventCount(), "event(s)")

    def reports(self):
        """
        Redirects all the options available for participants to the specific run function
        """
        while True:
            self.printParticipantList()
            print("\nAvailable options: 1, 2, 3, 4")
            print("1. Print events for a given person sorted by description and date")
            print("2. Print persons with most event participation")
            print("3. Print the first 20% events with most participants")
            print("4. Print persons with events on a given date")
            print("5. Return")
            option = input("Choose option: ")
            try:
                if option == "1":
                    self.showPersonEvents()
                elif option == "2":
                    self.showPersonsWithMostEvents()
                elif option == "3":
                    self.showEventsWithMostParticipants(20)
                elif option == "4":
                    self.showPersonsWithEventsDate()
                elif option == "5":
                    return
                else:
                    print("Invalid option")
            except CrudException as err:
                print(str(err))
            except InvalidIdException as err:
                print(str(err))

    def printMenu(self):
        """
        Prints the main menu
        """
        for pos in range(0, len(self.commands)):
            print(pos + 1, self.commands[pos]["description"])
        print(pos + 2, "Exit")

    def run(self):
        """
        Runs the application, starting with the main menu
        """
        while True:
            self.printMenu()
            try:
                option = int(input("\nChoose option: "))
                if option == 5:
                    return
                if (option <= len(self.commands)) and (option > 0):
                    self.commands[option - 1]["executor"]()
                else:
                    print("\nInvalid option\n")
            except ValueError as err:
                print(str(err))
                # print("\nOption should be an integer number from 1 to", len(self.commands) + 1, "\n")

    def addCommand(self, description, executor):
        """
        Adds a new commands to the ones available
        description - string
        executor - function
        """
        self.commands.append({"description": description, "executor": executor})

    def start(self):
        """
        Creates the specified commands: Manage persons and events
        Proceeds with the running of the application
        """
        self.commands = []
        self.addCommand("Manage persons", self.managePersons)
        self.addCommand("Manage events", self.manageEvents)
        self.addCommand("Manage participants", self.manageParticipants)
        self.addCommand("Reports", self.reports)
        self.run()