from service import Service

# Abstract class for the Ui data type
class Ui:
    # Constructor for the Ui object
    # service - a Service object
    def __init__(self, service):
        self.service = service

    # Prints the available menu and redirects to the chosen Service function
    def start(self):
        print("E - Exit")
        print("C - Compute paths")
        while (1):
            command = input("Option: ")
            if (command == "E"):
                break
            elif (command == "C"):
                self.service.compute()
                print("Algorithms done!")
            else:
                print("Invalid Option")
