from repository import Repository
from service import Service
from ui import Ui

# Creates the needed instances and starts the running of the application
def main():
    print("Hello!")
    repository = Repository("graph.txt", "graph_out.txt")
    service = Service(repository)
    ui = Ui(service)
    ui.start()

if __name__ == '__main__':
    main()
