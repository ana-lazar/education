from graph import Graph
from service import Service


def run():
    graph = Graph("data/fricker26.txt")
    service = Service(graph)
    service.run_aco()


if __name__ == '__main__':
    run()
