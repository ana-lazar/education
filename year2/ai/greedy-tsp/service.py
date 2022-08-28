
# Abstract class for the Service data type
class Service:
    # Constructor for the Service object
    # repository - a Repository object
    def __init__(self, repository):
        self.repository = repository

    # Runs both path algorithms
    def compute(self):
        self.repository.load_from_file()
        tsp = self.repository.tsp()
        md = self.repository.md()
        self.repository.save_to_file(tsp, md)
